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

### Swagger -> HTML

redoc-cli был перемещен в состав @redocly/cli.  
Можно использовать npx без установки:

```bash
npx @redocly/cli build-docs <путь-к-openapi-файлу>
```

Генерирование статического HTML из Swagger на примере основного приложения tk-recommendations:

```Bash
curl http://localhost:8090/tk-recommendations/api-docs -o tk-recommendations-api-spec.json
npx @redocly/cli build-docs tk-recommendations-api-spec.json -o tk-recommendations-swagger.html 
```

### JavaDoc -> HTML

```Bash
mvn compile javadoc:javadoc
```

## Другое

### Проверка версий зависимостей

Добавьте плагин в pom.xml:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>versions-maven-plugin</artifactId>
            <version>2.16.2</version>
        </plugin>
    </plugins>
</build>
```

Запустите проверку:

```bash
mvn versions:display-dependency-updates
```

Плагин _versions-maven-plugin_ позволяет обновить версии в pom.xml автоматически:

```bash
mvn versions:use-latest-versions
```

После выполнения проверьте изменения в pom.xml.

**Ограничения**
SNAPSHOT-версии: Плагин _versions-maven-plugin_ игнорирует их по умолчанию.  
Используйте флаг -DallowSnapshots=true, чтобы их включить.

Кастомные репозитории: Если артефакт размещен не в Maven Central, убедитесь, что  
репозиторий добавлен в pom.xml/settings.xml.
