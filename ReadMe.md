# TK-Recommendations-App

## О приложении

Серверное приложение для рекомендаций банковских продуктов.
Spring Boot, Java, Telegram API.

Основное приложение - tk-recommendations.
Бот Telegram для основного приложения - tk-recommendations-bot.

Связанный модуль DTO - tk-recommendations-dto.

## Краткие инструкции по сборке и развёртыванию

Серверное приложение для рекомендаций банковских продуктов.
Смотри [tk-recommendations/ReadMe.md](https://github.com/taker1974/tk-recommendations-app/blob/main/tk-recommendations/ReadMe.md)

Бот для Telegram. Приложение-компаньон для серверного приложения рекомендаций банковских продуктов.
Смотри [tk-recommendations-bot/ReadMe.md](https://github.com/taker1974/tk-recommendations-app/blob/main/tk-recommendations-bot/ReadMe.md)

Файлы-описания сервисов для systemd - tk-recommendations*.service в соответствующих директориях.

## Документы-основания для разработки и результаты анализа требований

Тексты заданий практически без правок - смотри [tk-recommendations-docs/Technical-task-phase-*.md](https://github.com/taker1974/tk-recommendations-app/blob/main/tk-recommendations-docs/).

Анализ, рассуждения и прочее - смотри [tk-recommendations-docs/Phase-*-Analyze.md](https://github.com/taker1974/tk-recommendations-app/blob/main/tk-recommendations-docs/).

## Замечания по текущей версии

1. Документация в формате Json/Swagger генерируется нормально, но документация в формате Json/Html из JavaDoc генерируется без ошибок не для каждого проекта: встречаются "ошибки", обусловленные тем, что не все классы имеют комментарий, не все конструкторы, генерируемые Lombok, имеют комментарии и т.п.
Требуется понять/узнать, как в других проектах, у других разработчиков актуально решается этот вопрос: игнорироование таких "ошибок", создание тривиальных комментариев и т.п.
Здесь временно приостановлена автогенерация отдельной документации из JavaDoc.

2. Диаграммы основных классов выполнены на PlantUML (puml), а диаграммы активности - на Mermaid (mmd).  
Кажется, что надо полностью всё делать на Mermaid.
