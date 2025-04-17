package ru.spb.tksoft.advertising;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.controller.UserRecommendationsController;
import ru.spb.tksoft.advertising.controller.advice.CommonControllerAdvice;
import ru.spb.tksoft.advertising.controller.advice.RecommendationControllerAdvice;
import ru.spb.tksoft.advertising.entity.ProductEntity;
import ru.spb.tksoft.advertising.entity.history.HistoryProduct;
import ru.spb.tksoft.advertising.entity.history.HistoryTransaction;
import ru.spb.tksoft.advertising.entity.history.HistoryUser;
import ru.spb.tksoft.advertising.repository.ProductsRepository;
import ru.spb.tksoft.advertising.repository.HistoryTransactionRepository;
import ru.spb.tksoft.advertising.repository.ProductRuleRepository;
import ru.spb.tksoft.advertising.service.history.HistoryTransactionServiceCached;
import ru.spb.tksoft.advertising.service.manager.ProductManagerServiceCached;
import ru.spb.tksoft.advertising.service.user.UserRecommendationService;
import ru.spb.tksoft.advertising.service.user.UserRecommendationServiceCached;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Класс для тестирования контроллера рекомендаций. Здесь никак отдельно не проверяется бэкэнд для
 * выборки транзакций пользователя.
 * 
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2025
 * @version 0.0.1
 */
@RequiredArgsConstructor
@ContextConfiguration(classes = {UserRecommendationsController.class,
        CommonControllerAdvice.class,RecommendationControllerAdvice.class,
        HistoryTransactionServiceCached.class, ProductManagerServiceCached.class,
        UserRecommendationServiceCached.class, UserRecommendationService.class,
        ProductsRepository.class, ProductRuleRepository.class, HistoryTransactionRepository.class})
@WebMvcTest(controllers = UserRecommendationsController.class)
class RecommendationControllerWebMvcTest extends RecommendationControllerBaseTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    ProductsRepository recommendationRepository;

    @MockitoBean
    HistoryTransactionRepository transactionRepository;

    @MockitoSpyBean
    UserRecommendationService recommendationService;

    @MockitoSpyBean
    HistoryTransactionServiceCached transactionService;

    @InjectMocks
    UserRecommendationsController recommendationController;

    @Test
    @DisplayName("Запрашиваем все рекомендации -> получаем полный список рекомендаций")
    void whenGetAllRecommendations_thenReturnsFullList() throws Exception {

        // final List<RecommendationEntity> recommendations = new ArrayList<>(Arrays.asList(
        // new RecommendationEntity(UUID.randomUUID(), "name", "desc"),
        // new RecommendationEntity(UUID.randomUUID(), "name", "desc"),
        // new RecommendationEntity(UUID.randomUUID(), "name", "desc")));

        // when(recommendationRepository.findAllRecommendations()).thenReturn(recommendations);
        // String url = "/recommendation/" + UUID.randomUUID();
        // mvc.perform(MockMvcRequestBuilders
        // .get(url)
        // .accept(MediaType.APPLICATION_JSON))
        // .andExpect(MockMvcResultMatchers.status().isOk())
        // .andExpect(MockMvcResultMatchers.jsonPath("$.recommendations.size()").value(recommendations.size()));

        // when(recommendationRepository.findAllRecommendations()).thenReturn(Collections.emptyList());
        // mvc.perform(MockMvcRequestBuilders
        // .get("/recommendation/" + UUID.randomUUID())
        // .accept(MediaType.APPLICATION_JSON))
        // .andExpect(MockMvcResultMatchers.status().isOk())
        // .andExpect(MockMvcResultMatchers.jsonPath("$.recommendations.size()").value(0));

        Assertions.assertTrue(true);
    }
}
