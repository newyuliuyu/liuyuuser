package com.liuyu.user.web.service.impl;

import com.liuyu.user.web.dao.UserDao;
import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-26 下午5:37 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User get(String username) {
        User user = null;
        user = userDao.queryWithUserName(username);
        if (user == null) {
            user = userDao.queryWithPhone(username);
        }
        if (user == null) {
            user = userDao.queryWithPhone(username);
        }
        return user;
    }
}
