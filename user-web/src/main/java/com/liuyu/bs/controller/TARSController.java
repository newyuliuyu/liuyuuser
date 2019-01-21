package com.liuyu.bs.controller;

import com.liuyu.bs.business.TARS;
import com.liuyu.bs.service.TARSService;
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
@RequestMapping("/tars")
public class TARSController extends BaseController {


    @Autowired
    private TARSService tarsService;

    @RequestMapping("/add-tars")
    public ModelAndView addTARS(@RequestBody List<TARS> tarsList,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addTARS");
        tarsService.adds(tarsList);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/delete-tars")
    public ModelAndView deleteTARS(@RequestBody TARS tars,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".deleteTARS");
        tarsService.delete(tars);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/list/{orgCode}/{roleId}")
    public ModelAndView queryTARS(@PathVariable String orgCode,
                                  @PathVariable long roleId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".queryTARS");
        List<TARS> tarsList = tarsService.queryTARS(orgCode, roleId);
        return ModelAndViewFactory.instance()
                .with("tarsList", tarsList)
                .build();
    }
}
