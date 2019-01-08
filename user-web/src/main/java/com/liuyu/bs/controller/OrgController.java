package com.liuyu.bs.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuyu.bs.business.Org;
import com.liuyu.bs.business.SysConfig;
import com.liuyu.bs.service.OrgService;
import com.liuyu.bs.service.impl.ImportOrgService;
import com.liuyu.common.excel.ExcelTable;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.common.thread.ThreadExecutor;
import com.liuyu.common.util.HttpReqUtils;
import com.liuyu.user.web.controller.BaseController;
import com.liuyu.user.web.dto.UploadFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/org")
public class OrgController extends BaseController {


    @Autowired
    private OrgService orgService;

    @Autowired
    private SysConfig config;

    @RequestMapping("/add")
    public ModelAndView add(@RequestBody Org org,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".add");
        orgService.add(org);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestBody Org org,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".update");
        orgService.update(org);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestBody Org org,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".delete");
        orgService.delete(org);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/get/{code}")
    public ModelAndView get(@PathVariable String code,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".get");
        Org org = orgService.get(code);
        return ModelAndViewFactory.instance().with("org", org).build();
    }

    @RequestMapping("/listwithnotschool")
    public ModelAndView listWithNotSchool(HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".listWithNotSchool");
        List<Org> orgs = orgService.queryOrgWithNotSchool();
        return ModelAndViewFactory.instance().with("orgs", orgs).build();
    }

    @RequestMapping("/child/{parentCode}")
    public ModelAndView child(@PathVariable String parentCode,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".listWithNotSchool");
        int pageNum = HttpReqUtils.getParamInt(request, "pageNum");
        if (pageNum == 0) {
            pageNum = 1;
        }
        int pageSize = HttpReqUtils.getParamInt(request, "pageSize");
        if (pageSize == 0) {
            pageSize = 10;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Org> orgs = orgService.queryChild(parentCode);
        PageInfo<Org> pageInfo = new PageInfo<>(orgs);
        return ModelAndViewFactory.instance()
                .with("orgs", orgs)
                .with("pageInfo", pageInfo).build();
    }


    @RequestMapping(value = "/import")
    public ModelAndView importOrg(@RequestBody UploadFileDTO uploadFileDTO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".importOrg");
        uploadFileDTO = solveRealPath(uploadFileDTO);
        ImportOrgService importOrgService = new ImportOrgService(uploadFileDTO.getNewName(), "");
        String key = importOrgService.getKey();
        ThreadExecutor.getInstance().getExecutorService().submit(importOrgService);
        return ModelAndViewFactory.instance().with("key", key).build();
    }

    private UploadFileDTO solveRealPath(UploadFileDTO uploadFileDTO) {
        Path path = Paths.get(config.getUploadFileDir(), uploadFileDTO.getNewName());
        uploadFileDTO.setNewName(path.toString());
        return uploadFileDTO;
    }


    @RequestMapping(value = "/download/tmpl")
    public void downloadTemplate(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String("机构导入模板".getBytes("UTF-8"), "iso-8859-1") + ".xls");
        OutputStream os = response.getOutputStream();
        createTemplate(os);
        os.close();
    }

    private void createTemplate(OutputStream os) throws Exception {
        int rowIdx = 0;
        ExcelTable et = new ExcelTable();
        et.addSheet("模板");
        CellStyle style = et.getHeaderStyle();
        et.createRowAndCells(rowIdx++, style, "机构代码", "机构名称", "上级机构代码", "类型");
        et.createRowAndCells(rowIdx++, "", "", "", "省");
        et.createDropDownMenu(1, 1, 3, 3, new String[]{"省", "地市", "区县", "学校"});
        et.save(os);
    }


}
