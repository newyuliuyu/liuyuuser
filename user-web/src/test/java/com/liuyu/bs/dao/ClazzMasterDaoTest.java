package com.liuyu.bs.dao;

import com.liuyu.user.web.UserWebApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: ClazzMasterDaoTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午6:00 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserWebApplication.class)
@Slf4j
public class ClazzMasterDaoTest {
    @Autowired
    ClazzMasterDao clazzMasterDao;

    @Test
    public void teacheHasClazzMaster() throws Exception {
        boolean bool = clazzMasterDao.teacheHasClazzMaster("222");
        System.out.println();
    }
}