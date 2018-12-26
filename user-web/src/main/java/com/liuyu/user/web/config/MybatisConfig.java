package com.liuyu.user.web.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * ClassName: MybatisConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-3-22 上午11:29 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Configuration
@MapperScan(basePackages = {"com.liuyu.**.dao"}, sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class MybatisConfig {


//    @Autowired
//    @Qualifier("ds")
//    private DataSource ds;

    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource ds) throws Exception {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourceResolver.getResources("classpath*:com/liuyu/**/dao/*.xml");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);
        bean.setMapperLocations(resources);


//        TypeHandler[] typeHandlers = new TypeHandler[]{
//                new LiuyuEnumIntTypeHandler<UserType>(UserType.class){}
//        };
//        bean.setTypeHandlers(typeHandlers);


        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.put("param1", "value1");
        pageInterceptor.setProperties(properties);
        bean.setPlugins(new Interceptor[]{pageInterceptor});


        //设置默认枚举处理类型,必须再最后才能调用，不然前面设置的有些参数失效
//        SqlSessionFactory sessionFactory = bean.getObject();
//        sessionFactory.getConfiguration().getTypeHandlerRegistry().setDefaultEnumTypeHandler();

        return bean;
    }
}
