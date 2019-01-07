package com.liuyu.user.web.service.impl;

import com.liuyu.user.web.UserWebApplication;
import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: ResourceServiceImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-27 下午1:21 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserWebApplication.class)
@Slf4j
public class ResourceServiceImplTest {

    @Autowired
    ResourceService resService;


}