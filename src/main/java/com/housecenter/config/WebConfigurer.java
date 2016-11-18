package com.housecenter.config;

import com.housecenter.DlfcRestApplication;
import com.housecenter.common.interceptor.DlfcInterceptor;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by mao-siyu on 16-11-17.
 * 拦截器入口
 */
@Configuration
@AutoConfigureAfter({ DlfcInterceptor.class })
public class WebConfigurer extends WebMvcConfigurerAdapter {

    private static Logger logger = Logger.getLogger(WebConfigurer.class);

    /**
     * 放行路径
     */
    private String[] excludePathPatterns = { "/**" };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        logger.info(registry);
        DlfcInterceptor interceptor = new DlfcInterceptor();
        // 添加拦截器
        InterceptorRegistration addInterceptor = registry.addInterceptor(interceptor);
        // 放行
        addInterceptor.excludePathPatterns(excludePathPatterns);
    }
}
