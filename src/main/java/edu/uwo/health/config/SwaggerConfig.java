package edu.uwo.health.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {

    Boolean swaggerEnabled=true;
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
                .enable(swaggerEnabled).select()
                .apis(RequestHandlerSelectors.basePackage("edu.uwo.health.controller"))
                .paths(PathSelectors.any()).build().pathMapping("/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Health Code Application API Document")
                .description("API description")
//                .contact(new Contact("author name", "home url", "email"))
                .version("1.0.0")
                .build();
    }
}

