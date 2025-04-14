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

    // Количество фиксированных рекомендаций.
    public static final int FIXED_RECOMMENDATIONS_COUNT = 3;

    // f37ba8a8-3cd5-4976-9f74-2b21f105da67, sheron.berge, Ernest, Sporer
    public static final String REAL_USER_ID = "f37ba8a8-3cd5-4976-9f74-2b21f105da67";

    // @Test
    // @DisplayName("Получение списка всех рекомендаций -> список рекомендаций")
    // void whenGetAllRecommendations_thenReturnsExpectedList() {

    //     final UUID userId = UUID.fromString(REAL_USER_ID);
    //     final String urlGet = apiUrl + "/" + userId;

    //     ResponseEntity<UserRecommendationsDto> getResponse =
    //             rest.getForEntity(urlGet, UserRecommendationsDto.class);

    //     Assertions.assertThat(getResponse).isNotNull();
    //     Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    //     Assertions.assertThat(getResponse.getBody()).isNotNull();

    //     Assertions.assertThat(getResponse.getBody().getUserId()).isEqualTo(userId);
    //     Assertions.assertThat(getResponse.getBody().getRecommendations())
    //              .hasSize(FIXED_RECOMMENDATIONS_COUNT);
    // }
}
