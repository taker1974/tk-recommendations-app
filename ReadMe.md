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

## Документация

Документация JavaDoc в формате статического HTML находится в подпроекте [tk-recommendations-docs](https://github.com/taker1974/tk-recommendations-app/blob/main/tk-recommendations-docs/).

Генерирование статического HTML из Swagger на примере основного приложения tk-recommendations:

```Bash
cd tk-recommendations-docs
curl http://localhost:8090/tk-recommendations/api-docs -o tk-recommendations-api-spec.json
npx @redocly/cli build-docs tk-recommendations-api-spec.json -o tk-recommendations-swagger.html 
```

Документация JavaDoc для модулей tk-log-utils и tk-string-utils находится в директориях javadoc этих проектов соответственно.
