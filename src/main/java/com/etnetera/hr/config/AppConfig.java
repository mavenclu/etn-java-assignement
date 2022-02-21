package com.etnetera.hr.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public OpenAPI customOpenAPI(@Value("${application.description}") String description,
                                 @Value("${application.version}") String version) {
        return new OpenAPI()
                .info(new Info()
                        .title("JSFramework API")
                        .description(description)
                        .version(version)

                        .license(new License().name("All Rights Reserved").url("https://github.com/mavenclu/etnetera-java-assignement")))
                ;
    }


}
