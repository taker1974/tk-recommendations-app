package ru.spb.tksoft.advertising;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.spb.tksoft.advertising.controller.UserRecommendationsController;
import ru.spb.tksoft.advertising.dto.user.UserRecommendationsDto;
import ru.spb.tksoft.advertising.repository.ProductsRepository;
import ru.spb.tksoft.advertising.repository.HistoryTransactionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecommendationControllerIntegrityTest
        extends RecommendationControllerBaseTest {

    private final UserRecommendationsController recommendationController;
    private final ProductsRepository recomendationRepository;

    private final HistoryTransactionRepository transactionRepository;
    private final TestRestTemplate rest;
    private final String apiUrl;

    RecommendationControllerIntegrityTest(@LocalServerPort int port,
            @Autowired UserRecommendationsController recommendationController,
            @Autowired ProductsRepository recomendationRepository,
            @Autowired HistoryTransactionRepository transactionRepository,
            @Autowired TestRestTemplate restTemplate) {

        this.recommendationController = recommendationController;
        this.recomendationRepository = recomendationRepository;

        apiUrl = "http://localhost:" + port + "/tk-recommendations/recommendation";
        this.transactionRepository = transactionRepository;
        this.rest = restTemplate;
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

    static final int DEFAULT_RECOMMENDATIONS_COUNT = 3;

    @SuppressWarnings({"null"}) // Ошибка SonarQube
                                // в VSCode после (getResponse.getBody()).isNotNull()
    @Test
    @DisplayName("Получение списка всех рекомендаций -> список рекомендаций")
    void whenGetAllRecommendations_thenReturnsExpectedList() {

        final UUID randomUuid = UUID.randomUUID();
        final String urlGet = apiUrl + "/" + randomUuid;

        // TODO Сейчас в сервисе заглушка, возвращающая полный список рекомендаций
        ResponseEntity<UserRecommendationsDto> getResponse =
                rest.getForEntity(urlGet, UserRecommendationsDto.class);

        Assertions.assertThat(getResponse).isNotNull();
        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getResponse.getBody()).isNotNull();

        Assertions.assertThat(getResponse.getBody().getUserId()).isEqualTo(randomUuid);
        // Assertions.assertThat(getResponse.getBody().getRecommendations())
        //         .hasSize(DEFAULT_RECOMMENDATIONS_COUNT);
    }
}
