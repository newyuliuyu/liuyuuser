package com.liuyu.bs.controller;

import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.user.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/school")
public class SchoolController extends BaseController {


    @RequestMapping("/test")
    public ModelAndView test(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        log.debug("进入" + this.getClass().getSimpleName() + ".test");
        byte[] bytes = new byte[1024*1024];
        return ModelAndViewFactory.instance().with("bytes", bytes).build();
    }


}
