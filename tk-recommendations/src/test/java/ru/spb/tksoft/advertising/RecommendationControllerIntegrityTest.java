package ru.spb.tksoft.advertising;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.spb.tksoft.advertising.controller.UserRecommendationsController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecommendationControllerIntegrityTest
        extends RecommendationControllerBaseTest {

    private final UserRecommendationsController recommendationController;

    private final String apiUrl;

    RecommendationControllerIntegrityTest(@LocalServerPort int port,
            @Autowired UserRecommendationsController recommendationController){

        this.recommendationController = recommendationController;
        apiUrl = "http://localhost:" + port + "/tk-recommendations/recommendation";
    }

    @AfterEach
    void tearDown() {
        // ...
    }

    @Test
    @DisplayName("Валидация контекста")
    void contextLoads() {
        Assertions.assertThat(apiUrl).isNotBlank();
        Assertions.assertThat(recommendationController).isNotNull();
    }

    // Количество фиксированных рекомендаций.
    public static final int FIXED_RECOMMENDATIONS_COUNT = 3;

    // f37ba8a8-3cd5-4976-9f74-2b21f105da67, sheron.berge, Ernest, Sporer
    public static final String REAL_USER_ID = "f37ba8a8-3cd5-4976-9f74-2b21f105da67";
}
