package ru.spb.tksoft.advertising;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс, запускающий приложение.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@SpringBootApplication
public class TkRecommendationsBotApplication {

	/**
	 * Запуск приложения.
	 * 
	 * @param args Аргументы командной строки.
	 */
	public static void main(String[] args) {

		SpringApplication.run(TkRecommendationsBotApplication.class, args);
	}

}
