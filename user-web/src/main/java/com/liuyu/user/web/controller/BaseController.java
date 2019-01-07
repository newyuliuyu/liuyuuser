package com.liuyu.user.web.controller;

import com.liuyu.common.spring.SpringContextUtil;
import com.liuyu.user.web.domain.Role;
import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * ClassName: BaseController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-27 上午9:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class BaseController {
    protected Subject getSubject() {
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    protected User getUser() {
        Subject subject = getSubject();
        User user = (User) subject.getPrincipal();
        if (user.getRoles() == null) {
            UserService userService = SpringContextUtil.getBean(UserService.class);
            List<Role> roles = userService.queryRoles(user.getId());
            user.setRoles(roles);
        }
        return user;
    }
}
