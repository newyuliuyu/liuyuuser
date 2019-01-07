package com.liuyu.user.web.controller;

import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName: ResourceController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-27 下午1:12 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RestController
@Slf4j
@RequestMapping("/user/res")
public class UserResourceController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/query/{group}")
    public ModelAndView queryResource(@PathVariable String group,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        log.debug("获取资源....UserResourceController.queryResource");
        User user = getUser();
        List<Resource> reses = userService.queryUserResourcesWithGroup(user, group);
        return ModelAndViewFactory.instance().with("reses", reses).build();
    }

}
