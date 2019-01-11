package com.liuyu.bs.service.impl;

import com.liuyu.bs.business.Subject;
import com.liuyu.bs.service.SchoolService;
import com.liuyu.user.web.UserWebApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: SchoolServiceImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-10 下午1:44 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserWebApplication.class)
@Slf4j
public class SchoolServiceImplTest {

    @Autowired
    SchoolService schoolService;

    @Test
    public void addSubject() throws Exception {
        Subject subject = Subject.builder().name("123").build();
        schoolService.addSubject("11", subject);
        System.out.println();
    }
}