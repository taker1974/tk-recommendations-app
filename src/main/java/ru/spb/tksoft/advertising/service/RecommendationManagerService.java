package ru.spb.tksoft.advertising.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.RecommendationEntity;
import ru.spb.tksoft.advertising.mapper.RecommendationMapper;
import ru.spb.tksoft.advertising.model.Recommendation;
import ru.spb.tksoft.advertising.repository.RecommendationRepository;
import ru.spb.tksoft.advertising.tools.LogEx;

/**
 * Сервис управления рекомендациями. Позволяет добавлять, просматривать и удалять рекомендации с
 * правилами рекомендования.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@Service
@RequiredArgsConstructor
public class RecommendationManagerService {

    private Logger log = LoggerFactory.getLogger(RecommendationManagerService.class);

    private final RecommendationRepository recommendationRepository;

    public Recommendation addRecommendation(final Recommendation recommendation) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING, recommendation);

        RecommendationEntity entity = RecommendationMapper.toEntity(recommendation);

        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
        return RecommendationMapper.toModel(recommendationRepository.save(entity));
    }

    public List<Recommendation> getListOfAllRecommendations() {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING);

        var data = recommendationRepository.findAll().stream()
                .map(RecommendationMapper::toModel).toList();

        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
        return data;
    }

    public void deleteRecommendation(final UUID recommendationId) {
        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.SHORT_RUN,
                "recommendationId = " + recommendationId);

        recommendationRepository.deleteById(recommendationId);
    }
}
