package com.housecenter.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConditionalOnClass({ EnableTransactionManagement.class })
@AutoConfigureAfter({ DatabaseConfig.class })
@MapperScan(basePackages = { "com.housecenter.dao" })
public class MybatisConfig implements EnvironmentAware {

    /**
     * 数据源注入
     */
    @Resource(name = "dataSource")
    private DataSource dataSource;

    private RelaxedPropertyResolver propertyResolver;

    private static Logger log = LoggerFactory.getLogger(MybatisConfig.class);

    @Override
    public void setEnvironment(Environment environment) {

        // 设置取得属性文件前缀
        this.propertyResolver = new RelaxedPropertyResolver(environment, "dlfc.mybatis.");

    }

    @Bean
    @ConditionalOnMissingBean
    public DataSourceTransactionManager transactionManager() {

        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 声明SqlSessionFactory
     * 
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() {

        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

            // 多数据源注入
            sessionFactory.setDataSource(dataSource);
            // 实体扫描 
//            sessionFactory.setTypeAliasesPackage(propertyResolver.getProperty("typeAliasesPackage"));  
            sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(propertyResolver.getProperty("mapperLocations"))); // 扫描xml文件
            sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(propertyResolver.getProperty("configLocation"))); // mybatis 配置文件处理

            return sessionFactory.getObject();
        } catch (Exception e) {
            log.warn("不能装配 mybatis session factory");
            return null;
        }
    }
}
