package com.liuyu.user.web.config;

import com.liuyu.common.db.DatasourceProperties;
import com.liuyu.common.db.InitDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * ClassName: DSConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-7-18 下午5:27 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Configuration
@PropertySource("classpath:ds.properties")
public class DSConfig {
    @Bean(name = "datasourceProperties")
    @ConfigurationProperties(prefix = "ds")
    public DatasourceProperties datasourceProperties() {
        return new DatasourceProperties();
    }

    @Bean(name = "ds")
    public DataSource dataSource(DatasourceProperties datasourceProperties) {
        return InitDataSource.init(datasourceProperties);
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
