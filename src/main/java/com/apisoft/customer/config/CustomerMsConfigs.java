package com.apisoft.customer.config;

import com.apisoft.customer.control.interceptor.HttpRequestCounterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Customer microservice configurations.
 *
 * @author Salah Abu Msameh
 */
@Configuration
public class CustomerMsConfigs implements WebMvcConfigurer {

    private HttpRequestCounterInterceptor httpRequestCounterInterceptor;

    /**
     *
     * @param httpRequestCounterInterceptor
     */
    public CustomerMsConfigs(final HttpRequestCounterInterceptor httpRequestCounterInterceptor) {
        this.httpRequestCounterInterceptor = httpRequestCounterInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(httpRequestCounterInterceptor).addPathPatterns("/api/**");
    }
}
