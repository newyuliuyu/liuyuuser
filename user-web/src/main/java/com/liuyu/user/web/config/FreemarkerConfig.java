package com.liuyu.user.web.config;

import com.google.common.collect.Maps;
import com.liuyu.common.freemarker.FreemarkerStaticModels;
import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * ClassName: FreemarkerConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-6-22 下午4:11 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Configuration
public class FreemarkerConfig {

    @Bean
    public FreemarkerStaticModels freemarkerStaticModels(){
        Map<String,String> maps = Maps.newHashMap();
        maps.put("Json","com.ez.common.json.Json2");
        FreemarkerStaticModels staticModels = new FreemarkerStaticModels(maps);
        return staticModels;
    }

    @Bean
    public ViewResolver viewResolverFtl() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(false);
        resolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
        resolver.setRequestContextAttribute("request");
        resolver.setExposeRequestAttributes(true);
        resolver.setExposeSessionAttributes(true);
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setOrder(0);
        resolver.setAttributesMap(freemarkerStaticModels());
        return resolver;
    }

    @Bean
    public ViewResolver viewResolverHtml() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(false);
        resolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
        resolver.setRequestContextAttribute("request");
        resolver.setExposeRequestAttributes(true);
        resolver.setExposeSessionAttributes(true);
        resolver.setOrder(1);
        resolver.setSuffix(".html");
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setAttributesMap(freemarkerStaticModels());
        return resolver;
    }


    @Bean
    public FreeMarkerConfigurer freemarkerConfig2() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPath("classpath:/ftl/");
        factory.setDefaultEncoding("UTF-8");
        factory.setPreferFileSystemAccess(false);
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        freemarker.template.Configuration configuration = factory.createConfiguration();
        configuration.setClassicCompatible(true);
        result.setConfiguration(configuration);
        Properties settings = new Properties();
        settings.put("template_update_delay", "0");
        settings.put("default_encoding", "UTF-8");
        settings.put("number_format", "0.######");
        settings.put("classic_compatible", true);
        settings.put("template_exception_handler", "ignore");
        result.setFreemarkerSettings(settings);
        return result;
    }
}
