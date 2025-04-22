package ru.spb.tksoft.advertising;

import org.json.JSONException;
import org.json.JSONObject;
import ru.spb.tksoft.advertising.entity.history.HistoryUserEntity;
import java.util.UUID;

public abstract class RecommendationControllerBaseTest {

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

    public static final UUID BAD_ID = UUID.fromString("ae81af56-45b7-58bc-b0e5-6506bc0da7d9");
}
