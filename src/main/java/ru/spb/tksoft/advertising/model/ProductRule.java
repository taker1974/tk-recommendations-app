package ru.spb.tksoft.advertising.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.annotation.concurrent.ThreadSafe;
import org.springframework.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import ru.spb.tksoft.advertising.api.SuitableUser;
import ru.spb.tksoft.advertising.api.dynamic.DynamicApiBoolean;
import ru.spb.tksoft.advertising.exception.NullDynamicApiException;

/**
 * Правила рекомендования продукта. По сути - описание RMI в виде
 * "query:имя_метода,arguments:аргумент_метода_1;.."
 * 
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
@Value
public class ProductRule implements SuitableUser {

    @NotBlank
    private final String query;

    @NotNull
    private final List<String> arguments;

    private final boolean negate;

    @Nullable
    private final DynamicApiBoolean dynamicApiBoolean;

    @NotNull
    public List<String> getArguments() {

        return Collections.unmodifiableList(null == arguments ? new ArrayList<>() : arguments);
    }

    @Override
    @NotBlank
    public String toString() {

        var sb = new StringBuilder();
        sb.append(query)
                .append('(')
                .append(String.join(",", arguments))
                .append(')');
        return sb.toString();
    }

    /**
     * Проверяет, соответствует ли пользователь требованиям. Это могут быть требования по
     * вызываемому методу или их совокупности.
     * 
     * Если нет динамического API, то метод всегда возвращает false: динамический API не всегда
     * нужен при создании модели.
     * 
     * @param userId Идентификатор пользователя.
     * @return true, если пользователь соответствует требованиям.
     */
    public boolean isUserSuitable(@NotNull final UUID userId) {

        if (null == dynamicApiBoolean) {
            return false;
        }

        return negate != dynamicApiBoolean.invoke(userId, query, arguments);
    }
}
