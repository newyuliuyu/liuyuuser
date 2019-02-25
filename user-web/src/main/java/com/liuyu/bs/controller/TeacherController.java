package com.liuyu.bs.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuyu.bs.business.Org;
import com.liuyu.bs.business.SysConfig;
import com.liuyu.bs.business.Teacher;
import com.liuyu.bs.service.OrgService;
import com.liuyu.bs.service.TeacherService;
import com.liuyu.bs.service.impl.ImportTeacherService;
import com.liuyu.common.excel.ExcelTable;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.common.thread.ThreadExecutor;
import com.liuyu.common.util.HttpReqUtils;
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
import java.util.List;

/**
 * ClassName: TeacherController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-14 上午10:09 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SysConfig config;

    @RequestMapping("/add")
    public ModelAndView add(@RequestBody Teacher teacher,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".add");
        teacherService.add(teacher);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestBody Teacher teacher,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".update");
        teacherService.update(teacher);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/delete/{code}")
    public ModelAndView delete(@PathVariable String code,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".delete");
        teacherService.delete(code);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/get/{code}")
    public ModelAndView get(@PathVariable String code,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".get");
        Teacher teacher = teacherService.get(code);
        return ModelAndViewFactory.instance().with("teacher", teacher).build();
    }

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".list");
        int pageNum = HttpReqUtils.getParamInt(request, "pageNum");
        if (pageNum == 0) {
            pageNum = 1;
        }
        int pageSize = HttpReqUtils.getParamInt(request, "pageSize");
        if (pageSize == 0) {
            pageSize = 10;
        }
        String search = HttpReqUtils.getParamString(request, "search");
        String orgCode = HttpReqUtils.getParamString(request, "orgCode");

        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> teachers = teacherService.queryTeacher(orgCode, search);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachers);
        return ModelAndViewFactory.instance()
                .with("teachers", teachers)
                .with("pageInfo", pageInfo)
                .build();
    }

    @RequestMapping(value = "/import")
    public ModelAndView importTeacher(@RequestBody UploadFileDTO uploadFileDTO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".importTeacher");
        uploadFileDTO = solveRealPath(uploadFileDTO);
        ImportTeacherService importTeacherService = new ImportTeacherService(uploadFileDTO.getNewName(), "");
        String key = importTeacherService.getKey();
        ThreadExecutor.getInstance().getExecutorService().submit(importTeacherService);
        return ModelAndViewFactory.instance().with("key", key).build();
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

        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String("教师导入模板".getBytes("UTF-8"), "iso-8859-1") + ".xls");
        OutputStream os = response.getOutputStream();
        createTemplate(os, org);
        os.close();
    }

    private void createTemplate(OutputStream os, Org org) throws Exception {
        int rowIdx = 0;
        ExcelTable et = new ExcelTable();
        et.addSheet("模板");
        CellStyle style = et.getHeaderStyle();
        et.createRowAndCells(rowIdx++, style, "姓名", "帐号", "手机", "邮箱", "所属学校");
        et.createRowAndCells(rowIdx++, "", "", "", "", org.getName());
        et.createDropDownMenu(1, 1, 4, 4, new String[]{org.getName()});
        et.save(os);
    }
}
