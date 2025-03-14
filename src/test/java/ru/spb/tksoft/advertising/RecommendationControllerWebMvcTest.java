package ru.spb.tksoft.advertising;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.controller.RecommendationController;
import ru.spb.tksoft.advertising.controller.RecommendationControllerAdvice;
import ru.spb.tksoft.advertising.entity.recommendation.Recommendation;
import ru.spb.tksoft.advertising.entity.recommendation.RecommendationsDto;
import ru.spb.tksoft.advertising.entity.transaction.Product;
import ru.spb.tksoft.advertising.entity.transaction.Transaction;
import ru.spb.tksoft.advertising.entity.transaction.User;
import ru.spb.tksoft.advertising.repository.recommendation.RecommendationRepository;
import ru.spb.tksoft.advertising.repository.transaction.TransactionRepository;
import ru.spb.tksoft.advertising.service.recommendation.RecommendationService;
import ru.spb.tksoft.advertising.service.transaction.TransactionService;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
 * Класс для тестирования контроллера рекомендаций.
 * Здесь никак отдельно не проверяется бэкэнд для выборки транзакций
 * пользователя.
 * 
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2025
 * @version 0.0.1
 */
@RequiredArgsConstructor
@ContextConfiguration(classes = { RecommendationController.class,
        RecommendationControllerAdvice.class,
        RecommendationService.class, TransactionService.class,
        RecommendationRepository.class, TransactionRepository.class })
@WebMvcTest(controllers = RecommendationController.class)
class RecommendationControllerWebMvcTest extends RecommendationControllerBaseTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    RecommendationRepository recommendationRepository;

    @MockitoBean
    TransactionRepository transactionRepository;

    @MockitoSpyBean
    RecommendationService recommendationService;

    @MockitoSpyBean
    TransactionService transactionService;

    @InjectMocks
    RecommendationController recommendationController;

    @Test
    @DisplayName("Запрашиваем все рекомендации -> получаем полный список рекомендаций")
    void whenGetAllRecommendations_thenReturnsFullList() throws Exception {

        final List<Recommendation> recommendations = new ArrayList<>(Arrays.asList(
                new Recommendation(UUID.randomUUID(), "name", "desc"),
                new Recommendation(UUID.randomUUID(), "name", "desc"),
                new Recommendation(UUID.randomUUID(), "name", "desc")));

        // TODO Сейчас в сервисе заглушка, возвращающая полный список рекомендаций
        when(recommendationRepository.findAllRecommendations()).thenReturn(recommendations);
        String url = "/recommendation/" + UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders
                .get(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.recommendations.size()").value(recommendations.size()));

        when(recommendationRepository.findAllRecommendations()).thenReturn(Collections.emptyList());
        mvc.perform(MockMvcRequestBuilders
                .get("/recommendation/" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.recommendations.size()").value(0));
    }
}
