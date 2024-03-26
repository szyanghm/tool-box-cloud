package com.tool.box.minio;

import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author v_haimiyang
 * @Date 2024/3/21 15:53
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
@ConditionalOnProperty(value = "oss.name", havingValue = "minio")
public class MinioConfig {

    @Resource
    private MinioProperties ossProperties;

    @Bean
    @SneakyThrows
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(ossProperties.getEndpoint())
                .credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey())
                .build();
    }

}
