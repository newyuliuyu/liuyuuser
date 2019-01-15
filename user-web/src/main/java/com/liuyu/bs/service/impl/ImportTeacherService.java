package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liuyu.bs.business.Org;
import com.liuyu.bs.business.Teacher;
import com.liuyu.bs.service.OrgService;
import com.liuyu.bs.service.TeacherService;
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
public class ImportTeacherService implements Runnable {
    private String importFile;
    private WebProgress webProgress;
    private String userCode;
    private long timestamp;
    private String key;
    private IdGenerator idGenerator;
    private Map<String, Org> orgMap = Maps.newHashMap();
    private OrgService orgService;

    public ImportTeacherService(String importFile, String userCode) {
        this(importFile, userCode, false);
    }

    public ImportTeacherService(String importFile, String userCode, boolean isDebug) {
        this.importFile = importFile;
        this.userCode = userCode;
        this.timestamp = System.currentTimeMillis();
        orgService = SpringContextUtil.getBean(OrgService.class);
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
            log.error("导入机构数据失败", e);
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
            List<Teacher> orgs = Lists.newArrayList();
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                orgs.add(createTeacher(rowdata));
                num++;
                if (num % 100 == 0) {
                    webProgress.info(30, "正在了" + num + "条");
                }
            }
            webProgress.info(90, "一共导入" + num + "条");
            webProgress.info(90, "正在保存导入数据库中");
            TeacherService teacherService = SpringContextUtil.getBean(TeacherService.class);
            teacherService.adds(orgs);
            webProgress.info(90, "保存成功");
        } finally {
            fileProcess.close();
        }
    }


    private Teacher createTeacher(Rowdata rowdata) {
        Teacher teacher = new Teacher();
        teacher.setCode(idGenerator.nextId() + "");
        teacher.setName(getStringValue(rowdata, "姓名"));
        teacher.setAccount(getStringValue(rowdata, "帐号"));
        teacher.setPhone(getStringValue(rowdata, "手机"));
        teacher.setEmail(getStringValue(rowdata, "邮箱"));
        String orgName = getStringValue(rowdata, "所属学校");
        if (StringUtils.isEmpty(teacher.getName())) {
            throw new RuntimeException("教师名字不能为空");
        }
        if (StringUtils.isEmpty(teacher.getAccount())) {
            throw new RuntimeException("教师[" + teacher.getName() + "]帐号不能为空.");
        }
        if (StringUtils.isEmpty(orgName)) {
            throw new RuntimeException("教师[" + teacher.getName() + "]所属学校不能为空");
        }
        teacher.setOrg(getOrg(orgName));
        return teacher;
    }

    private Org getOrg(String name) {
        Org org = orgMap.get(name);
        if (org == null) {
            org = orgService.getWithName(name);
        }
        if (org == null) {
            throw new RuntimeException("學校[" + name + "]数据库中不存在,请请检查以后名字以后再导入");
        }
        return org;
    }

    private String getStringValue(Rowdata rowdata, String name) {
        String value = rowdata.getData(name);
        value = value == null ? "" : value;
        return value;
    }


}
