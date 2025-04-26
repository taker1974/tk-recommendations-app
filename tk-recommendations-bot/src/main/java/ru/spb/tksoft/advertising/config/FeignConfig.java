package ru.spb.tksoft.advertising.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "ru.spb.tksoft.advertising.proxy")
public class FeignConfig {

}
