package ru.spb.tksoft.advertising.mapper;

import javax.annotation.concurrent.ThreadSafe;
import ru.spb.tksoft.advertising.entity.history.HistoryUserEntity;
import ru.spb.tksoft.advertising.model.HistoryUser;
import ru.spb.tksoft.recommendations.dto.user.HistoryUserDto;

/**
 * Маппер для History*.
 *
 * Здесь нужны только преобразования dto <- model <- entity.
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
public final class HistoryMapper {

    private HistoryMapper() {}

    /** DTO <- Model. */
    public static HistoryUserDto toDto(final HistoryUser model) {

        return new HistoryUserDto(model.getId(),
                model.getUserName(), model.getFirstName(), model.getLastName());
    }

    /** Model <- Entity. */
    public static HistoryUser toModel(final HistoryUserEntity entity) {

        return new HistoryUser(entity.getId(),
                entity.getUserName(), entity.getFirstName(), entity.getLastName());
    }

    /** DTO <- Entity */
    public static HistoryUserDto toDto(final HistoryUserEntity entity) {

        return new HistoryUserDto(entity.getId(),
                entity.getUserName(), entity.getFirstName(), entity.getLastName());
    }
}
