package com.liuyu.user.web.service;

import com.liuyu.user.web.domain.User;

/**
 * ClassName: UserService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-26 下午5:36 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface UserService {
    User get(String username);
}
