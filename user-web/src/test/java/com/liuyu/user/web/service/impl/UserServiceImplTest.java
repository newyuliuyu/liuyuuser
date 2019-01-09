package com.liuyu.user.web.service.impl;

import com.liuyu.user.web.UserWebApplication;
import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: UserServiceImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-3 下午4:12 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserWebApplication.class)
@Slf4j
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void queryUserResourcesWithGroup() throws Exception {
        User user = User.builder().id(1077867179942256641L).build();
        List<Resource> reses = userService.queryUserResourcesWithGroup(user, "user-main-menu");
        System.out.println();
    }

    @Test
    public void getUser() throws Exception {
        User user = userService.get("123");
        System.out.println();
    }
}