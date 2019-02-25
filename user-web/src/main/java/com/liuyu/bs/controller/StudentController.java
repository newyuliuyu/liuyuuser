package com.liuyu.bs.controller;

import com.liuyu.bs.business.Clazz;
import com.liuyu.bs.business.Org;
import com.liuyu.bs.business.Student;
import com.liuyu.bs.business.SysConfig;
import com.liuyu.bs.service.OrgConfigService;
import com.liuyu.bs.service.OrgService;
import com.liuyu.bs.service.StudentService;
import com.liuyu.bs.service.impl.ImportStudentService;
import com.liuyu.common.excel.ExcelTable;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.common.thread.ThreadExecutor;
import com.liuyu.common.util.ClazzNameToNumber;
import com.liuyu.user.web.controller.BaseController;
import com.liuyu.user.web.dto.UploadFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

/**
 * ClassName: OrgController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 上午10:28 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Autowired
    private OrgService orgService;
    @Autowired
    private OrgConfigService orgConfigService;
    @Autowired
    private SysConfig config;

    @Autowired
    private StudentService studentService;

    @RequestMapping("/add")
    public ModelAndView add(@RequestBody Student student,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".add");
        studentService.add(student);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestBody Student student,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".update");
        studentService.update(student);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable long id,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".delete");
        Student student = Student.builder().id(id).build();
        studentService.delete(student);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/get/{id}")
    public ModelAndView get(@PathVariable long id,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".get");
        Student student = studentService.get(id);
        return ModelAndViewFactory.instance()
                .with("student", student)
                .build();
    }

    @RequestMapping(value = "/list/{schoolCode}/{clazzCode}")
    public ModelAndView studentList(@PathVariable String schoolCode,
                                    @PathVariable String clazzCode,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".studentList");

        List<Student> students = studentService.queryListWithClazz(schoolCode, clazzCode);
        return ModelAndViewFactory.instance()
                .with("students", students)
                .build();
    }

    @RequestMapping(value = "/import")
    public ModelAndView importStudent(@RequestBody UploadFileDTO uploadFileDTO,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".importStudent");
        uploadFileDTO = solveRealPath(uploadFileDTO);
        ImportStudentService importStudentService = new ImportStudentService(uploadFileDTO.getNewName(), "");
        String key = importStudentService.getKey();
        ThreadExecutor.getInstance().getExecutorService().submit(importStudentService);
        return ModelAndViewFactory.instance()
                .with("key", key)
                .build();
    }

    private UploadFileDTO solveRealPath(UploadFileDTO uploadFileDTO) {
        Path path = Paths.get(config.getUploadFileDir(), uploadFileDTO.getNewName());
        uploadFileDTO.setNewName(path.toString());
        return uploadFileDTO;
    }

    @RequestMapping(value = "/download/tmpl/{orgCode}")
    public void downloadTemplate(@PathVariable String orgCode,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Org org = orgService.get(orgCode);
        Assert.notNull(org, "机构代码[" + orgCode + "]在数据库中不存在.");
        List<Clazz> clazzes = orgConfigService.queryClazz(orgCode);
        Assert.notNull(clazzes, "机构代码[" + orgCode + "]没有班级数据.");
        clazzes.sort(Comparator.comparingInt((Clazz c) -> c.getGrade().getLearnSegment())
                .thenComparingInt(c -> ClazzNameToNumber.toNum(c.getGrade().getName()))
                .thenComparingInt((Clazz c) -> c.isTeachClazz() ? 1 : 0)
                .thenComparingInt(c -> ClazzNameToNumber.toNum(c.getName())));

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String("学生导入模板".getBytes("UTF-8"), "iso-8859-1") + ".xls");
        OutputStream os = response.getOutputStream();
        createTemplate(os, org, clazzes);
        os.close();
    }

    private void createTemplate(OutputStream os, Org org, List<Clazz> clazzes) throws Exception {

        String[] clazzNames = new String[clazzes.size()];
        String clazzName = "";
        int idx = 0;
        for (Clazz clazz : clazzes) {
            String tmp = clazz.getGrade().getName() + "-" + clazz.getName();
            if (clazz.isTeachClazz()) {
                tmp += "-" + clazz.getSubjectName();
            }
            clazzNames[idx] = tmp;
            if (idx == 0) {
                clazzName = tmp;
            }
            idx++;
        }
        int rowIdx = 0;
        ExcelTable et = new ExcelTable();
        et.addSheet("模板");
        CellStyle style = et.getHeaderStyle();
        et.createRowAndCells(rowIdx++, style, "姓名", "帐号", "学号", "准考证号", "学校", "班级");
        et.createRowAndCells(rowIdx++, "", "", "", "", org.getName(), clazzName);
        et.createDropDownMenu(1, 1, 4, 4, new String[]{org.getName()});
        et.createDropDownMenu(1, 1, 5, 5, clazzNames);
        et.save(os);
    }
}
