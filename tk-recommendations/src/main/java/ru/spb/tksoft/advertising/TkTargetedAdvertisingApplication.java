package ru.spb.tksoft.advertising;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс, запускающий приложение.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@SpringBootApplication
public class TkTargetedAdvertisingApplication {

	/**
	 * Запуск приложения.
	 * 
	 * @param args Аргументы командной строки.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TkTargetedAdvertisingApplication.class, args);
	}

}
