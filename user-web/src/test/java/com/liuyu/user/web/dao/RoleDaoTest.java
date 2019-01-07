package com.liuyu.user.web.dao;

import com.liuyu.user.web.UserWebApplication;
import com.liuyu.user.web.domain.Role;
import com.liuyu.user.web.domain.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: RoleDaoTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-26 上午11:30 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserWebApplication.class)
@Slf4j
public class RoleDaoTest {

    @Autowired
    RoleDao roleDao;

    @Test
    public void add() throws Exception {


        Role role = Role.builder().id(1L).name("test").systemBuiltin(true).roleType(RoleType.builder().code("Admin").build()).build();
        roleDao.add(role);
    }

    @Test
    public void get() throws Exception {
        Role role = roleDao.get(1L);
        System.out.println();
    }

    @Test
    public void queryUserRoles() throws Exception {
        List<Role> role = roleDao.queryUserRoles(1L);
        System.out.println();
    }

}