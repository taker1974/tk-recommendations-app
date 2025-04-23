package ru.spb.tksoft.advertising.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRecommendationService {

    private final Logger log = LoggerFactory.getLogger(UserRecommendationService.class);
}
