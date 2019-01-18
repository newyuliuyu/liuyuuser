package com.liuyu.user.web;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.Clazz;
import com.liuyu.bs.business.Org;
import com.liuyu.bs.business.OrgHelper;
import com.liuyu.bs.business.School;
import com.liuyu.common.util.ClazzNameToNumber;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Comparator;
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

    @Test
    public void beanUtils() throws Exception {
        Org org = Org.builder().id(1).code("1").name("1").parentCode("0").deep(1).build();
//        School school  = BeanUtils.instantiateClass(School.class);
//        BeanUtils.copyProperties(org,school);

        School school = OrgHelper.converTo(org, School.class);
        System.out.println();
    }

    @Test
    public void listSort() throws Exception {
        List<Clazz> clazzes = Lists.newArrayList();
        clazzes.add(Clazz.builder().name("1班").teachClazz(false).build());
        clazzes.add(Clazz.builder().name("3班").teachClazz(false).build());
        clazzes.add(Clazz.builder().name("3班").teachClazz(true).build());
        clazzes.add(Clazz.builder().name("1班").teachClazz(true).build());
        clazzes.add(Clazz.builder().name("2班").teachClazz(false).build());
        clazzes.add(Clazz.builder().name("5班").teachClazz(false).build());

        clazzes.sort(Comparator.comparingInt((Clazz c) -> c.isTeachClazz() ? 1 : 0).thenComparingInt(c -> ClazzNameToNumber.toNum(c.getName())));

        clazzes.forEach(c -> System.out.println(c.getName() + "==" + c.isTeachClazz()));
        System.out.println();
    }
    @Test
    public void precision() throws Exception {
        double a=1.4;
        double b=1.4;

        System.out.println(a+b);
    }

}
