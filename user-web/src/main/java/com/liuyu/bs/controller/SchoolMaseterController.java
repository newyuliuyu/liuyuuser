package com.liuyu.bs.controller;

import com.liuyu.bs.business.SchoolMaseter;
import com.liuyu.bs.service.SchoolMaseterService;
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
@RequestMapping("/schoolmaseter")
public class SchoolMaseterController extends BaseController {


    @Autowired
    private SchoolMaseterService schoolMaseterService;

    @RequestMapping("/add-school-maseter")
    public ModelAndView addSchoolMaseter(@RequestBody List<SchoolMaseter> schoolMasters,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addSchoolMaseter");
        schoolMaseterService.adds(schoolMasters);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/delete-school-maseter")
    public ModelAndView deleteSchoolMaseter(@RequestBody SchoolMaseter schoolMaster,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addSchoolMaseter");
        schoolMaseterService.delete(schoolMaster);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/list/{orgCode}/{roleId}")
    public ModelAndView querySchoolMaseter(@PathVariable String orgCode,
                                           @PathVariable long roleId,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addSchoolMaseter");
        List<SchoolMaseter> schoolMaseters = schoolMaseterService.querySchoolMaseters(orgCode, roleId);
        return ModelAndViewFactory.instance()
                .with("schoolMaseters", schoolMaseters)
                .build();
    }
}
