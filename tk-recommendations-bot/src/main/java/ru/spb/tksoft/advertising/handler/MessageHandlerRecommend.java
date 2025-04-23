package ru.spb.tksoft.advertising.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.spb.tksoft.advertising.bot.RecommendationsBot;
import ru.spb.tksoft.advertising.proxy.UserRecommendationProxy;
import jakarta.annotation.PostConstruct;
import ru.spb.tksoft.advertising.service.UserRecommendationServiceCached;
import ru.spb.tksoft.advertising.tools.StringEx;
import ru.spb.tksoft.recommendations.dto.user.HistoryUserDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendationsDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendedProductDto;

/**
 * Handle command /recommend
 */
@Component
public class MessageHandlerRecommend extends MessageHandler {

    final Logger log = LoggerFactory.getLogger(MessageHandlerRecommend.class);

    private final UserRecommendationServiceCached userRecommendationServiceCached;

    public MessageHandlerRecommend(
            @Autowired UserRecommendationServiceCached userRecommendationServiceCached) {

        super(List.of("/recommend", "recommend", "/r"));
        this.userRecommendationServiceCached = userRecommendationServiceCached;
    }

    @PostConstruct
    private void postConstruct() {
        // ...
    }

    @Override
    public String getHelp() {
        return "/recommend <UUID> или /r <UUID> - рекомендовать продукты для пользователя с указанным UUID";
    }

    @Override
    public String handle(final long chatId, final int messageId, final String rawMessage) {

        Optional<String> checkResponse = checkMessageTooLong(rawMessage);
        if (checkResponse.isPresent()) {
            return checkResponse.get();
        }

        final String messageTrimmed = removeCommand(StringEx.removeAdjacentSpaces(rawMessage));
        checkResponse = checkMessageTooShort(messageTrimmed);
        if (checkResponse.isPresent()) {
            return checkResponse.get();
        }

        Optional<HistoryUserDto> userInfoOptional = userRecommendationServiceCached
                .getUserInfo(UUID.fromString(messageTrimmed));
        if (userInfoOptional.isEmpty()) {
            return "Пользователь не найден.";
        }
        final HistoryUserDto userInfo = userInfoOptional.get();

        Optional<UserRecommendationsDto> recommendationsOptional = userRecommendationServiceCached
                .getRecommendations(UUID.fromString(messageTrimmed));
        if (recommendationsOptional.isEmpty()) {
            return "Не удалось получить рекомендации для пользователя.";
        }
        final UserRecommendationsDto recommendations = recommendationsOptional.get();

        String name = userInfo.getFirstName();
        if (name == null || name.isBlank()) {
            name = userInfo.getUserName();
        }

        var sb = new StringBuilder("😍 " + name + "! Дорогой наш человечек!\n");

        Set<UserRecommendedProductDto> list = recommendations.getRecommendations();
        if (list.isEmpty()) {
            sb.append(
                    "К нашему сожалению и на ваше счастье мы не нашли для вас подходящих продуктов 😢");
        } else {
            sb.append("🔎 Мы поискали и нашли для вас вот такие замечательные продукты:\n");
            Iterator<Integer> iterator = IntStream.range(0, list.size()).iterator();
            list.forEach(product -> sb.append(
                    (iterator.next() + 1) + ") " +
                            product.getProductName() + "\n")); // Здесь должны быть ссылки на
                                                               // продукты с подробным описанием.
            sb.append("Обязательно почитайте подробнее об этих продуктах! Ждём обратной связи 👋");
        }

        return sb.toString();
    }
}
