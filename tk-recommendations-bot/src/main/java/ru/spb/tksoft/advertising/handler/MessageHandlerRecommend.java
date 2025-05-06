package ru.spb.tksoft.advertising.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.spb.tksoft.advertising.service.UserRecommendationServiceCached;
import ru.spb.tksoft.utils.log.LogEx;
import ru.spb.tksoft.utils.string.StringEx;
import ru.spb.tksoft.recommendations.dto.user.HistoryUserDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendationsDto;
import ru.spb.tksoft.recommendations.dto.user.UserRecommendedProductDto;

/**
 * Обработка команды /recommend
 */
@Component
public class MessageHandlerRecommend extends MessageHandler {

    private final Logger log = LoggerFactory.getLogger(MessageHandlerRecommend.class);

    private final UserRecommendationServiceCached userRecommendationServiceCached;

    /**
     * Создание нового объекта обработчика команды /recommend
     * 
     * @param userRecommendationServiceCached Сервис получения рекомендаций.
     */
    public MessageHandlerRecommend(
            @Autowired UserRecommendationServiceCached userRecommendationServiceCached) {

        super(List.of("/recommend", "/r"));
        this.userRecommendationServiceCached = userRecommendationServiceCached;
    }

    /**
     * Получить описание команды.
     * 
     * @return Описание команды.
     */
    @Override
    public String getHelp() {
        return "/recommend <user.name> или /r <user.name> - рекомендовать продукты для пользователя с указанным user.name";
    }

    /**
     * Обработка команды.
     * 
     * @param chatId Идентификатор чата.
     * @param messageId Идентификатор сообщения.
     * @param rawMessage Сырой текст сообщения с командой.
     * @return Текст ответа.
     */
    @Override
    public String handle(final long chatId, final int messageId, final String rawMessage) {

        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STARTING);

        Optional<String> checkResponse = checkMessageTooLong(rawMessage);
        if (checkResponse.isPresent()) {
            final String errorMessage = checkResponse.get();
            LogEx.error(log, LogEx.getThisMethodName(), errorMessage);
            return errorMessage;
        }

        final String messageTrimmed = removeCommand(StringEx.removeAdjacentSpaces(rawMessage));
        checkResponse = checkMessageTooShort(messageTrimmed);
        if (checkResponse.isPresent()) {
            final String errorMessage = checkResponse.get();
            LogEx.error(log, LogEx.getThisMethodName(), errorMessage);
            return errorMessage;
        }

        Optional<HistoryUserDto> userInfoOptional =
                userRecommendationServiceCached.getUserInfo(messageTrimmed);
        if (userInfoOptional.isEmpty()) {
            final String errorMessage =
                    "🤔 Пользователь не найден. Может, просто нет доступа к основному приложению ❓";
            LogEx.error(log, LogEx.getThisMethodName(), errorMessage);
            return errorMessage;
        }
        final HistoryUserDto userInfo = userInfoOptional.get();

        Optional<UserRecommendationsDto> recommendationsOptional =
                userRecommendationServiceCached.getRecommendations(userInfo.getId());
        if (recommendationsOptional.isEmpty()) {
            final String errorMessage =
                    "🤔 Не удалось получить рекомендации для пользователя. Это точно ошибка❗️";
            LogEx.error(log, LogEx.getThisMethodName(), errorMessage);
            return errorMessage;
        }
        final UserRecommendationsDto recommendations = recommendationsOptional.get();

        String name = userInfo.getFirstName();
        if (name == null || name.isBlank()) {
            name = userInfo.getUserName();
        }

        var sb = new StringBuilder("😍 " + name + ", дорогой наш человечек!\n");

        Set<UserRecommendedProductDto> list = recommendations.getRecommendations();
        if (list.isEmpty()) {
            sb.append(
                    "Поздравляем! Вы сломали систему - мы не нашли для вас подходящих продуктов 👏");
        } else {
            sb.append("🔎 Мы хорошо поискали и нашли для вас вот такие замечательные продукты:\n");
            Iterator<Integer> iterator = IntStream.range(0, list.size()).iterator();
            list.forEach(product -> sb.append(
                    (iterator.next() + 1) + ") " +
                            product.getProductName() + "\n")); // Здесь должны быть ссылки на
                                                               // продукты с подробным описанием.
            sb.append(
                    "Переходите по ссылкам 👆, внимательно изучайте описания продуктов! Ждём обратной связи 👋");
        }

        LogEx.trace(log, LogEx.getThisMethodName(), LogEx.STOPPING);
        return sb.toString();
    }
}
