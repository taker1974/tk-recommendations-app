# TK-Recommendations

## О приложении

Серверное приложение для рекомендаций банковских продуктов.
Приложение Spring Boot, Java.

## Подготовка к развёртыванию на узле

**Требуемое ПО**:

- PostgreSQL >= 15;
- Java >= 21.

Версии ПО могут быть другими. При разработке используется Postgres 15 и 17 и Java 21: нет никаких явных ограничений на использование других версий ПО.

**В Postgres**:

- предварительно смотрим application.yml;
- создаём БД tk_recommendations, создаём пользователя "recommendations_god" с паролем "87654321", отдаём БД tk_recommendations во владение пользователю recommendations_god:

```Bash
$ sudo -u postgres psql
postgres=# CREATE DATABASE tk_recommendations;
CREATE DATABASE
postgres=# CREATE USER recommendations_god WITH LOGIN PASSWORD '87654321';
CREATE ROLE
postgres=# ALTER DATABASE tk_recommendations OWNER TO recommendations_god;
ALTER DATABASE
```

При реальном использовании приложения, при дальнейшей перепубликации, логины и пароли, разумеется, следует скрывать: как минимум их надо просто передавать через параметры командной строки при запуске приложения.

**Java**:

- установить JDK или JRE версии не ниже 21 (на этой версии ведётся разработка: нет никаких явных ограничений на использование других версий Java);
- убедиться в правильности установки, в доступности java, javac, maven (mvn);

## Запуск приложения

Смотри pom.xml. В корне проекта:

```Bash
$mvn clean install
$java -jar target/tk-recommendations-0.0.1-SNAPSHOT.jar
```

В браузере:

http://localhost:8090/tk-recommendations/swagger-ui/index.html
