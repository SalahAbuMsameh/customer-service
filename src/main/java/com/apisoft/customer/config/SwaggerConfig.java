package com.apisoft.customer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger api doc configs.
 *
 * @author Salah Abu Msameh
 */
@Configuration
@EnableSwagger2
@Profile("!prod") //tells Swagger not to configure Swagger for Production environment.
public class SwaggerConfig {
    
    @Value("Customer API's")
    private String title;
    
    @Value("Customer API's")
    private String description;
    
    @Value("1.0")
    private String version;
    
    @Value("com.apisoft.customer")
    private String basePackage;
    
    /**
     * swagger api doclet configs.
     *
     * @return
     */
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .apiInfo(metaData());
    }

    /**
     * Swagger meta data.
     *
     * @return
     */
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
            .title(title)
            .description(description)
            .version(version)
            .build();
    }
}
