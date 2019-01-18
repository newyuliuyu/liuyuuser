package com.liuyu.bs.controller;

import com.liuyu.bs.business.LPGroupLeader;
import com.liuyu.bs.service.LPGroupLeaderService;
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
@RequestMapping("/lpgroupleader")
public class LPGroupLeaderController extends BaseController {

    @Autowired
    private LPGroupLeaderService lpGroupLeaderService;

    @RequestMapping("/add-lp-group-leader/{roleId}")
    public ModelAndView addLpGroupLeader(@PathVariable long roleId,
                                         @RequestBody List<LPGroupLeader> lpGroupLeaders,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addLpGroupLeader");
        lpGroupLeaderService.adds(roleId, lpGroupLeaders);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/delete-lp-group-leader/{roleId}")
    public ModelAndView deleteLPGroupLeader(@PathVariable long roleId,
                                            @RequestBody LPGroupLeader lpGroupLeader,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".deleteLPGroupLeader");
        lpGroupLeaderService.delete(roleId, lpGroupLeader);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/list/{orgCode}")
    public ModelAndView queryLPGroupLeader(@PathVariable String orgCode,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addSchoolMaseter");
        List<LPGroupLeader> lpGroupLeaders = lpGroupLeaderService.queryLPGroupLeader(orgCode);
        return ModelAndViewFactory.instance()
                .with("lpGroupLeaders", lpGroupLeaders)
                .build();
    }

}
