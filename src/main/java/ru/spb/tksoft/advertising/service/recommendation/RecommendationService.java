package ru.spb.tksoft.advertising.service.recommendation;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.recommendation.Recommendation;
import ru.spb.tksoft.advertising.entity.recommendation.RecommendationsDto;
import ru.spb.tksoft.advertising.repository.recommendation.RecommendationRepository;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationRepository recomendationRepository;

    public RecommendationsDto getRecommendations(UUID userId) {

        // TODO Реализовать рекомендации
        List<Recommendation> recommendations = recomendationRepository.findAllRecommendations();
        var result = new RecommendationsDto(userId, recommendations);
        return result;
    }
}
