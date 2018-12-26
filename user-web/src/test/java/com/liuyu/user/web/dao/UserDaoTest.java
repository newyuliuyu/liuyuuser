package com.liuyu.user.web.dao;

import com.liuyu.common.util.IdGenerator;
import com.liuyu.user.web.UserWebApplication;
import com.liuyu.user.web.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * ClassName: UserDaoTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-26 下午2:56 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserWebApplication.class)
@Slf4j
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    public void addUser() throws Exception {
        IdGenerator id = new IdGenerator();

        for (int i = 0; i < 1000; i++) {
            User user = User.builder().id(id.nextId()).userName(name()).realName("").email("").phone("").password("111").build();
            userDao.add(user);
        }

    }


    private String name() {
        Random ra = new Random();
        StringBuilder text = new StringBuilder();
        int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < 6; i++) {
            int num = ra.nextInt(999999999);
            int postion = num % 6;
            text.append(nums[postion]);
        }
        return text.toString();
    }
}