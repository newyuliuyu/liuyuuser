package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liuyu.bs.business.*;
import com.liuyu.bs.service.OrgConfigService;
import com.liuyu.bs.service.OrgService;
import com.liuyu.bs.service.StudentService;
import com.liuyu.common.file.reader.FileProcess;
import com.liuyu.common.file.reader.FileProcessUtil;
import com.liuyu.common.file.reader.Rowdata;
import com.liuyu.common.progress.WebProgress;
import com.liuyu.common.spring.SpringContextUtil;
import com.liuyu.common.util.IdGenerator;
import com.liuyu.common.util.ThrowableToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ImportOrg <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 下午5:27 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class ImportStudentService implements Runnable {
    private String importFile;
    private WebProgress webProgress;
    private String userCode;
    private long timestamp;
    private String key;
    private IdGenerator idGenerator;
    private Map<String, School> schoolMap = Maps.newHashMap();
    private Map<String, Clazz> clazzMap = Maps.newHashMap();
    private OrgService orgService;
    private OrgConfigService orgConfigService;

    public ImportStudentService(String importFile, String userCode) {
        this(importFile, userCode, false);
    }

    public ImportStudentService(String importFile, String userCode, boolean isDebug) {
        this.importFile = importFile;
        this.userCode = userCode;
        this.timestamp = System.currentTimeMillis();
        orgService = SpringContextUtil.getBean(OrgService.class);
        orgConfigService = SpringContextUtil.getBean(OrgConfigService.class);
        idGenerator = SpringContextUtil.getBean(IdGenerator.class);

        key = idGenerator.nextId() + "";
        webProgress = new WebProgress(key, 100, isDebug);
        webProgress.start("开始进入导入");
    }

    public String getKey() {
        return key;
    }

    @Override
    public void run() {
        try {
            process();
            webProgress.info(90, "导入成功");
        } catch (Exception e) {
            log.error("导入学生数据失败", e);
            webProgress.error(90, "导入失败" + ThrowableToString.formatHtml(e));
        } finally {
            webProgress.over("导入完成");
        }
    }

    public void process() {
        webProgress.info(30, "正在导入...");
        FileProcess fileProcess = FileProcessUtil.getFileProcess(importFile);
        try {
            int num = 0;
            List<Student> students = Lists.newArrayList();
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                students.add(createStudent(rowdata));
                num++;
                if (num % 100 == 0) {
                    webProgress.info(30, "处理了" + num + "条");
                }
            }
            webProgress.info(90, "一共导入" + num + "条");
            webProgress.info(90, "正在保存导入数据库中");
            StudentService studentService = SpringContextUtil.getBean(StudentService.class);
            studentService.adds(students);
            webProgress.info(90, "保存成功");
        } finally {
            fileProcess.close();
        }
    }


    private Student createStudent(Rowdata rowdata) {
        Student student = new Student();
        student.setName(getStringValue(rowdata, "姓名"));
        student.setAccount(getStringValue(rowdata, "帐号"));
        student.setZkzh(getStringValue(rowdata, "准考证号"));
        student.setStudentId(getStringValue(rowdata, "学号"));


        String schoolName = getStringValue(rowdata, "学校");
        String clazzNameExpression = getStringValue(rowdata, "班级");
        if (StringUtils.isEmpty(student.getName())) {
            throw new RuntimeException("学生名字不能为空");
        }
        if (StringUtils.isEmpty(student.getAccount())) {
            throw new RuntimeException("学生[" + student.getName() + "]帐号不能为空.");
        }
        if (StringUtils.isEmpty(schoolName)) {
            throw new RuntimeException("学生[" + student.getName() + "]所属学校不能为空");
        }
        if (StringUtils.isEmpty(clazzNameExpression)) {
            throw new RuntimeException("学生[" + student.getName() + "]所属班级不能为空");
        }
        School school = getSchool(schoolName);
        Clazz clazz = getClazz(school, clazzNameExpression);
        student.setSchool(school);
        student.setGrade(clazz.getGrade());
        student.setClazz(clazz);
        return student;
    }

    private School getSchool(String schoolName) {
        School school = schoolMap.get(schoolName);

        if (school == null) {
            Org org = orgService.getWithName(schoolName);
            if (org != null) {
                school = OrgHelper.converTo(org, School.class);
                schoolMap.put(schoolName, school);
            }
        }
        if (school == null) {
            throw new RuntimeException("學校[" + schoolName + "]数据库中不存在,请请检查以后名字以后再导入");
        }
        return school;
    }

    private Clazz getClazz(School school, String clazzNameExpression) {
        String[] clazzNames = clazzNameExpression.split("-");
        if (clazzNames.length != 2 && clazzNames.length != 3) {
            throw new RuntimeException("班级[" + clazzNameExpression + "]格式不对,格式为年级-班级名称-科目");
        }
        String key = school.getCode() + "." + clazzNameExpression;
        Clazz clazz = clazzMap.get(key);
        if (clazz == null) {
            String subjectName = null;
            if (clazzNames.length == 3) {
                subjectName = clazzNames[2];
            }
            clazz = orgConfigService.getClazz(school.getCode(), clazzNames[1], clazzNames[0], subjectName);
            if (clazz != null) {
                clazzMap.put(key, clazz);
            }
        }
        if (clazz == null) {
            throw new RuntimeException("学校[" + school.getName() + "]没有班级[" + clazzNameExpression + "]数据");
        }
        return clazz;
    }

    private String getStringValue(Rowdata rowdata, String name) {
        String value = rowdata.getData(name);
        value = value == null ? "" : value;
        return value;
    }


}
