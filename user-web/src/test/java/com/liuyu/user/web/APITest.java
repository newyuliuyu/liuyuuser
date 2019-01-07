package com.liuyu.user.web;

import com.google.common.collect.Lists;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: APITest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-27 上午9:42 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class APITest {

    @Test
    public void logtest() throws Exception {
        log.debug("==={}==={}", "test1", "test2");
        log.debug(MessageFormat.format("=={0}=={1}=", "test1", "test2"));

    }

    @Test
    public void freemarker() throws Exception {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        String templateContent = "欢迎：${name}";
        stringLoader.putTemplate("myTemplate", templateContent);
        cfg.setTemplateLoader(stringLoader);
        Template template = cfg.getTemplate("myTemplate", "utf-8");
        Map root = new HashMap();
        root.put("name", "javaboy2012");
        StringWriter writer = new StringWriter();
        template.process(root, writer);
        log.debug(writer.toString());
    }

    @Test
    public void list() throws Exception {
        List<Integer> aList = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        List<Integer> bList = Lists.newArrayList(4, 5, 6, 7, 8, 9);

        aList.retainAll(bList);

        System.out.println();
    }

}
