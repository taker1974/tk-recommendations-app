package ru.spb.tksoft.advertising;

import org.json.JSONException;
import org.json.JSONObject;
import ru.spb.tksoft.advertising.entity.history.HistoryUserEntity;
import java.util.UUID;

/**
 * Базовый класс для тестов.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
public abstract class RecommendationControllerBaseTest {

    /**
     * Построение строки в формате JSON из объекта пользователя.
     * 
     * @param user Объект пользователя.
     * @return Строка в формате JSON.
     */
    public static String buildJson(HistoryUserEntity user) {
        try {
            return new JSONObject()
                    .put("user_id", user.getId())
                    .put("user_name", user.getUserName())
                    .put("first_name", user.getFirstName())
                    .put("last_name", user.getLastName())
                    .toString();
        } catch (JSONException e) {
            return "";
        }
    }

    /** Неизвестный идентификатор пользователя. */
    public static final UUID BAD_ID = UUID.fromString("ae81af56-45b7-58bc-b0e5-6506bc0da7d9");
}
