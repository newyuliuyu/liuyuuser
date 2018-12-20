package com.liuyu.user.web.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.liuyu.common.mvc.SpringMVCExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * ClassName: SpringMVCConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-6-22 下午3:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Configuration
@Slf4j
public class SpringMVCConfig extends WebMvcConfigurationSupport {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        log.debug("配置HttpMessageConverter");
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteEnumUsingToString);
        FastJsonHttpMessageConverter4 c1 = new FastJsonHttpMessageConverter4();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        c1.setSupportedMediaTypes(supportedMediaTypes);
        c1.setFastJsonConfig(fastJsonConfig);
        converters.add(c1);
    }

//    @Override
//    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        super.configureContentNegotiation(configurer);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").setCachePeriod(0);
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/resources/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/css/**")
//                .addResourceLocations("classpath:/css/");
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
//        registry.addResourceHandler("/ftl/**").addResourceLocations("classpath:/view/");
//        registry.addResourceHandler("/images/**").addResourceLocations("file:" + imagePath);
//        registry.addResourceHandler("/file/**").addResourceLocations("file:" + imagePath);
        super.addResourceHandlers(registry);
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //TODO添加自定义的拦截器
        super.addInterceptors(registry);
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        //配置跨域请求
        super.addCorsMappings(registry);

//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:9000", "null")
//                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
//                .maxAge(3600)
//                .allowCredentials(true);
    }


    @Bean(name = "viewResolver")
    @Primary
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        List<View> views = new ArrayList<View>();
        views.add(fastJsonJsonView());

        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setDefaultViews(views);
        return viewResolver;
    }

//    @Bean
//    public HttpMessageConverters customConverters() {
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.WriteNullNumberAsZero,
//                SerializerFeature.WriteNullBooleanAsFalse,
//                SerializerFeature.WriteEnumUsingToString);
//        FastJsonHttpMessageConverter4 c1 = new FastJsonHttpMessageConverter4();
//        c1.setFastJsonConfig(fastJsonConfig);
//
//        FormHttpMessageConverter c2 = new FormHttpMessageConverter();
//        ByteArrayHttpMessageConverter c3 = new ByteArrayHttpMessageConverter();
//        StringHttpMessageConverter c4 = new StringHttpMessageConverter();
//        ResourceHttpMessageConverter c5 = new ResourceHttpMessageConverter();
//        SourceHttpMessageConverter c6 = new SourceHttpMessageConverter();
//        BufferedImageHttpMessageConverter c7 = new BufferedImageHttpMessageConverter();
//        return new HttpMessageConverters(c1, c2, c3, c4, c5, c6, c7);
//    }

    @Bean
    public FastJsonJsonView fastJsonJsonView() {

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.DisableCircularReferenceDetect);
        fastJsonConfig.setDateFormat("yyyy-MM-dd");

        SerializeConfig serializeConfig = new SerializeConfig();//fastJsonConfig.getSerializeConfig();
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(long.class, ToStringSerializer.instance);
        serializeConfig.put(long[].class, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);

        FastJsonJsonView view = new FastJsonJsonView();
        view.setFastJsonConfig(fastJsonConfig);
        return view;
    }

    @Bean
    public SpringMVCExceptionResolver exceptionResolver() {
        SpringMVCExceptionResolver exceptionResolver = new SpringMVCExceptionResolver();
        exceptionResolver.setDefaultErrorView("/404");
        Properties mappings = new Properties();
        mappings.setProperty("java.lang.Exception", "/500");
        exceptionResolver.setExceptionMappings(mappings);
        Properties statusCodes = new Properties();
        statusCodes.setProperty("/500", "500");
        statusCodes.setProperty("/401", "401");
        statusCodes.setProperty("/403", "403");
        statusCodes.setProperty("/404", "404");
        statusCodes.setProperty("/405", "405");
        exceptionResolver.setStatusCodes(statusCodes);
        return exceptionResolver;
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        return resolver;
    }


}
