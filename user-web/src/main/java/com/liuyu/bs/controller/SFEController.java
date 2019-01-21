package com.liuyu.bs.controller;

import com.liuyu.bs.business.SFE;
import com.liuyu.bs.service.SFEService;
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
@RequestMapping("/sfe")
public class SFEController extends BaseController {


    @Autowired
    private SFEService sfeService;

    @RequestMapping("/add-sfe")
    public ModelAndView addSFE(@RequestBody List<SFE> sfes,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addSFE");
        sfeService.adds(sfes);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/delete-sfe")
    public ModelAndView deleteSFE(@RequestBody SFE sfe,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".deleteSFE");
        sfeService.delete(sfe);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/list/{orgCode}/{roleId}")
    public ModelAndView querySFE(@PathVariable String orgCode,
                                 @PathVariable long roleId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".querySFE");
        List<SFE> sfes = sfeService.querySFE(orgCode, roleId);
        return ModelAndViewFactory.instance()
                .with("sfes", sfes)
                .build();
    }
}
