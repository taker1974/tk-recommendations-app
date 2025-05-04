package ru.spb.tksoft.advertising.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для работы с FeignClient.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@Configuration
@EnableFeignClients(basePackages = "ru.spb.tksoft.advertising.proxy")
public class FeignConfig {

}
