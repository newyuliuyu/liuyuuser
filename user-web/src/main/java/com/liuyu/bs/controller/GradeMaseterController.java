package com.liuyu.bs.controller;

import com.liuyu.bs.business.GradeMaseter;
import com.liuyu.bs.service.GradeMaseterService;
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
 * ClassName: LPGroupLeaderController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-18 上午10:51 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RestController
@Slf4j
@RequestMapping("/grademaseter")
public class GradeMaseterController extends BaseController {

    @Autowired
    private GradeMaseterService gradeMaseterService;

    @RequestMapping("/add-grade-maseter/{roleId}")
    public ModelAndView addGradeMaseter(@PathVariable long roleId,
                                        @RequestBody List<GradeMaseter> gradeMaseters,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addGradeMaseter");
        gradeMaseterService.adds(roleId, gradeMaseters);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/delete-grade-maseter/{roleId}")
    public ModelAndView deleteGradeMaseter(@PathVariable long roleId,
                                           @RequestBody GradeMaseter gradeMaseter,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".deleteGradeMaseter");
        gradeMaseterService.delete(roleId, gradeMaseter);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/list/{orgCode}")
    public ModelAndView queryGradeMaseter(@PathVariable String orgCode,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".queryGradeMaseter");
        List<GradeMaseter> gradeMaseters = gradeMaseterService.queryGradeMaseter(orgCode);
        return ModelAndViewFactory.instance()
                .with("gradeMaseters", gradeMaseters)
                .build();
    }

}
