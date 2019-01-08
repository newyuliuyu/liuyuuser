package com.liuyu.bs.service.impl;

import com.liuyu.user.web.UserWebApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ClassName: ImportOrgServiceTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-8 上午11:11 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserWebApplication.class)
@Slf4j
public class ImportOrgServiceTest {

    @Test
    public void importOrg() throws Exception {
        Path path = Paths.get("/home/liuyu/tmp/excle", "机构导入模板.xls");
        ImportOrgService importOrgService = new ImportOrgService(path.toString(), "", true);
        importOrgService.run();
    }

    @Test
    public void test12() throws Exception {
        try {
            throw new RuntimeException("ddd");
        } catch (Exception e) {
            System.out.println("catch");
        } finally {
            System.out.println("finally");
        }
    }
}