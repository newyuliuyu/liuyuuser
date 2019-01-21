package com.liuyu.bs.controller;

import com.liuyu.bs.business.ResearchStaff;
import com.liuyu.bs.service.ResearchStaffService;
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
@RequestMapping("/researchstaff")
public class ResearchStaffController extends BaseController {


    @Autowired
    private ResearchStaffService researchStaffService;

    @RequestMapping("/add-researchstaff")
    public ModelAndView addResearchStaff(@RequestBody List<ResearchStaff> researchStaffs,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addResearchStaff");
        researchStaffService.adds(researchStaffs);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/delete-researchstaff")
    public ModelAndView deleteResearchStaff(@RequestBody ResearchStaff researchStaff,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".deleteResearchStaff");
        researchStaffService.delete(researchStaff);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/list/{orgCode}/{roleId}")
    public ModelAndView queryResearchStaff(@PathVariable String orgCode,
                                           @PathVariable long roleId,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".queryResearchStaff");
        List<ResearchStaff> researchStaffs = researchStaffService.queryResearchStaff(orgCode, roleId);
        return ModelAndViewFactory.instance()
                .with("researchStaffs", researchStaffs)
                .build();
    }
}
