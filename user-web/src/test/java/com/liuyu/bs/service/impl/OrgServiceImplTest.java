package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.Org;
import com.liuyu.bs.service.OrgService;
import com.liuyu.user.web.UserWebApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: OrgServiceImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 上午11:16 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserWebApplication.class)
@Slf4j
public class OrgServiceImplTest {

    @Autowired
    OrgService orgService;

    @Test
    public void add() throws Exception {

        Org org = Org.builder().code("1").name("1").parentCode("0").deep(1).build();
        try {
            orgService.add(org);

        } catch (Exception e) {
//            DuplicateKeyException
            System.out.println();
        }
    }

    @Test
    public void adds() throws Exception {
        List<Org> orgs = Lists.newArrayList();
        orgs.add(Org.builder().code("11").name("11").parentCode("0").deep(1).build());
        orgs.add(Org.builder().code("12").name("12").parentCode("0").deep(1).build());
        orgs.add(Org.builder().code("13").name("12").parentCode("0").deep(1).build());
        orgService.adds(orgs);
    }

}