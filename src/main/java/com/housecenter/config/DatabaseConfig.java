package com.housecenter.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DatabaseConfig implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    private static Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Override
    public void setEnvironment(Environment arg0) {

        // 设置参数
        // 以jdbc为开始的配置 （application.properties）
        log.info("以jdbc为开始的配置(application.properties)");
        this.propertyResolver = new RelaxedPropertyResolver(arg0, "dlfc.jdbc.");
    }

    /**
     * 数据源
     * 
     * @return
     */
    @Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
    @Primary
    public DataSource dataSource() {

        log.info("写入数据源配置信息");
        // 声明druid 数据源
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(propertyResolver.getProperty("url"));
        datasource.setDriverClassName(propertyResolver.getProperty("driverClassName"));
        datasource.setUsername(propertyResolver.getProperty("username"));
        datasource.setPassword(propertyResolver.getProperty("password"));

        // 数据源其他参数配置
        //配置最大连接
        datasource.setMaxActive(Integer.valueOf(propertyResolver.getProperty("maxActive")));
        //配置初始连接
        datasource.setInitialSize(Integer.valueOf(propertyResolver.getProperty("initialSize")));
        //配置最小连接
        datasource.setMinIdle(Integer.valueOf(propertyResolver.getProperty("maxActive")));
        //连接等待超时时间
        datasource.setMaxWait(Integer.valueOf(propertyResolver.getProperty("maxActive")));
        //间隔多久进行检测,关闭空闲连接
        datasource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(propertyResolver.getProperty("TimeBetweenEvictionRunsMillis")));
        //一个连接最小生存时间
        datasource.setMinEvictableIdleTimeMillis(Integer.valueOf(propertyResolver.getProperty("MinEvictableIdleTimeMillis")));
        //用来检测是否有效的sql
        datasource.setValidationQuery("select 'x'");
        datasource.setTestWhileIdle(true);
        datasource.setTestOnBorrow(false);
        datasource.setTestOnReturn(false);
        //打开PSCache,并指定每个连接的PSCache大小
        datasource.setPoolPreparedStatements(true);
        datasource.setMaxOpenPreparedStatements(Integer.valueOf(propertyResolver.getProperty("MaxOpenPreparedStatements")));
        //配置sql监控的filter
//        datasource.setFilters("stat,wall,log4j");

        return datasource;
    }
}
