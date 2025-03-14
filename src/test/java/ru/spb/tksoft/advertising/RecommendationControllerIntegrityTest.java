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

import ru.spb.tksoft.advertising.controller.RecommendationController;
import ru.spb.tksoft.advertising.entity.recommendation.RecommendationsDto;
import ru.spb.tksoft.advertising.repository.recommendation.RecommendationRepository;
import ru.spb.tksoft.advertising.repository.transaction.TransactionRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecommendationControllerIntegrityTest
        extends RecommendationControllerBaseTest {

    private final RecommendationController recommendationController;
    private final RecommendationRepository recomendationRepository;

    private final TransactionRepository transactionRepository;
    private final TestRestTemplate rest;
    private final String apiUrl;

    RecommendationControllerIntegrityTest(@LocalServerPort int port,
            @Autowired RecommendationController recommendationController,
            @Autowired RecommendationRepository recomendationRepository,
            @Autowired TransactionRepository transactionRepository,
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

    @SuppressWarnings({ "null" }) // Ошибка SonarQube
                                  // в VSCode после (getResponse.getBody()).isNotNull()
    @Test
    @DisplayName("Получение списка всех рекомендаций -> список рекомендаций")
    void whenGetAllRecommendations_thenReturnsExpectedList() {

        final UUID randomUuid = UUID.randomUUID();
        final String urlGet = apiUrl + "/" + randomUuid;

        // TODO Сейчас в сервисе заглушка, возвращающая полный список рекомендаций
        ResponseEntity<RecommendationsDto> getResponse = rest.getForEntity(urlGet, RecommendationsDto.class);

        Assertions.assertThat(getResponse).isNotNull();
        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getResponse.getBody()).isNotNull();

        Assertions.assertThat(getResponse.getBody().getUserId()).isEqualTo(randomUuid);
        Assertions.assertThat(getResponse.getBody().getRecommendations()).hasSize(DEFAULT_RECOMMENDATIONS_COUNT);
    }
}
