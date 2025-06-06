package com.tool.box.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 *
 * @Author v_haimiyang
 * @Date 2024/1/17 17:04
 * @Version 1.0
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("商城工具箱")
                        .description("平台接口文档")
                        .contact(new Contact()
                                .name("meritco")
                                .url("北京"))
                        .version("1.0"));
    }

}
