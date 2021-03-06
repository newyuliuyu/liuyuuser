package com.liuyu.bs.controller;

import com.liuyu.bs.business.Admin;
import com.liuyu.bs.service.AdminService;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.user.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName: SchoolMaseterController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-17 下午3:14 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {


    @Autowired
    private AdminService adminService;

    @RequestMapping("/add-admin")
    public ModelAndView addAdmin(@RequestBody List<Admin> admins,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addAdmin");
        adminService.adds(admins);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/delete-admin")
    public ModelAndView deleteAdmin(@RequestBody Admin admin,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".deleteAdmin");
        adminService.delete(admin);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/list/{orgCode}/{roleId}")
    public ModelAndView queryAdmin(@PathVariable String orgCode,
                                   @PathVariable long roleId,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addSchoolMaseter");
        List<Admin> admins = adminService.queryAdmins(orgCode, roleId);
        return ModelAndViewFactory.instance()
                .with("admins", admins)
                .build();
    }
}
