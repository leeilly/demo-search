package com.pmo.demo.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("PMO SEARCH API DOCUMENT")
                .description("검색 API 문서입니다. "
                        + "\n -공식 가이드 : [http://swagger.io](http://swagger.io)")
                .license(" ")
                .licenseUrl("http://unlicense.org")
                .termsOfServiceUrl("")
                .version("0.0.1")
                .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pmo.demo.api"))
                .build()
                .apiInfo(apiInfo());
    }

}
