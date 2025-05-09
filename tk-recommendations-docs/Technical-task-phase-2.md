# Техническое задание 2 этапа

## Динамические правила

Вам необходимо создать API в вашей системе для динамического добавления правил рекомендаций и рекомендуемых продуктов.

Само АПИ должно состоять из трех методов:

добавление правила,
удаление правила,
получение списка всех правил.
Вначале дадим определение динамическому правилу и опишем его основные компоненты на примере одного из правил первого технического задания.

Для рекомендации продукта «Простой кредит» мы использовали следующий набор правил:

Пользователь не использует продукты с типом CREDIT.
Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.
Динамическое правило для этого продукта будет выглядеть следующим образом:

```Json
[
    {
        "query": "USER_OF",
        "arguments": [
            "CREDIT"
        ],
        "negate": true
    },
    {
        "query": "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW",
        "arguments": [
            "DEBIT",
            ">"
        ],
        "negate": false
    },
    {
        "query": "TRANSACTION_SUM_COMPARE",
        "arguments": [
            "DEBIT",
            "DEPOSIT"
            ">",
            "100000"
        ],
        "negate": false
    }
]
```

Можно определить следующие свойства динамического правила:

Динамическое правило представляет собой массив объектов запросов.
Каждый объект запроса описывается следующими полями:
query — тип запроса;
arguments — аргументы запроса;
negate — модификатор отрицания.
Можно считать, что каждый запрос — это некоторая операция, которая возвращает логический результат true или false.

Правило выполняется тогда и только тогда, когда все запросы внутри него вернули true.

То есть если Q1, Q2 и Q3 — это результаты запросов, то результат выполнения правила QR — это QR = Q1 && Q2 && Q3

Для запроса

```Text
/recommendation/<user.name>
```

 логика выполнения должна быть изменена таким образом, чтобы система получала все уже созданные динамические правила рекомендаций из базы данных и проверяла их выполнение по отношению к переданному пользователю.

Если динамическое правило выполняется, то в список рекомендаций пользователя должен попасть продукт, описываемый данным динамическим правилом рекомендаций.

Динамические правила рекомендаций нужно создавать в отдельной базе данных, далее по уроку вы можете найти рекомендации по ее подключению. Для работы с самими сущностями динамических правил можно использовать JPA.

Старые фиксированные правила рекомендаций не нужно переводить в динамический формат, достаточно просто запускать их проверку вместе с проверкой динамических правил.

Типы запросов
Всего мы хотим поддерживать четыре типа запросов:

## Является пользователем продукта — USER_OF

Этот запрос проверяет, является ли пользователь, для которого ведется поиск рекомендаций, пользователем продукта X, где X — это первый аргумент запроса.

Напомним, что пользователь продукта X — этот пользователь, у которого есть хотя бы одна транзакция по продуктам данного типа X.

```Json
{
        "query": "USER_OF",
        "arguments": [
            "CREDIT"
        ],
        "negate": true
}
```

Данный запрос принимает только один аргумент:
Первый аргумент — тип продукта: DEBIT, CREDIT, INVEST, SAVING.

## Является активным пользователем продукта — ACTIVE_USER_OF

Этот запрос проверяет, является ли пользователь, для которого ведется поиск рекомендаций, активным пользователем продукта X, где X — это первый аргумент запроса.

Активный пользователь продукта X — это пользователь, у которого есть хотя бы пять транзакций по продуктам данного типа X.

```Json
{
        "query": "ACTIVE_USER_OF",
        "arguments": [
            "DEBIT"
        ],
        "negate": false
}
```

Данный запрос принимает только один аргумент:
Первый аргумент — тип продукта: DEBIT, CREDIT, INVEST, SAVING.

## Сравнение суммы транзакций с константой — TRANSACTION_SUM_COMPARE

Этот запрос сравнивает сумму всех транзакций типа Y по продуктам типа X с некоторой константой C.
Где X — первый аргумент запроса, Y — второй аргумент запроса, а C — четвертый аргумент запроса.
Сама операция сравнения — O — может быть одной из пяти операций:

> — сумма строго больше числа C.
< — сумма строго меньше числа C.
= — сумма строго равна числу C.
>= — сумма больше или равна числу C.
<= — сумма меньше или равна числу C.

Таким образом, если запрос выглядит так:

```Json
{
        "query": "TRANSACTION_SUM_COMPARE",
        "arguments": [
            "DEBIT",
            "DEPOSIT",
            ">",
            "100000"
        ],
        "negate": false
}
```

Это означает, что мы должны с помощью SQL-запроса посчитать сумму всех транзакций типа DEPOSIT для всех продуктов типа DEBIT данного пользователя и сравнить ее с помощью оператора > с числом 100 000.

Данный запрос принимает четыре аргумента:
Первый аргумент — тип продукта: DEBIT, CREDIT, INVEST, SAVING.
Второй аргумент — тип транзакции: WITHDRAW, DEPOSIT.
Третий аргумент — тип сравнения (все возможные варианты перечислены выше).
Четвертый аргумент — число C, с которым сравниваем сумму, может являться только неотрицательным целым числом (больше либо равным нулю), которое умещается в тип int.

## Сравнение суммы пополнений с тратами по всем продуктам одного типа — TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW

Этот запрос сравнивает сумму всех транзакций типа 
DEPOSIT с суммой всех транзакций типа WITHDRAW по продукту X.

Где X — первый аргумент запроса, а операция сравнения — второй аргумент запроса.
Таким образом, если запрос выглядит так:

```Json
{
        "query": "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW",
        "arguments": [
            "DEBIT",
            ">"
        ],
        "negate": false
}
```

Это означает, что мы должны посчитать:

- сумму всех транзакций с типом DEPOSIT по продуктам типа DEBIT;
- сумму всех транзакций с типом WITHDRAW по продуктам типа DEBIT;
- сравнить их между собой, используя операцию сравнения >.

Данный запрос принимает два аргумента:
Первый аргумент — тип продукта: DEBIT, CREDIT, INVEST, SAVING.
Второй аргумент — тип сравнения (все возможные варианты перечислены выше).

## API создания нового динамического правила рекомендаций в базе данных сервиса

```Json
Request:
POST /rule
{
    "product_name": "Простой кредит", //Имя продукта, который мы рекомендуем
    "product_id": "ab138afb-f3ba-4a93-b74f-0fcee86d447f", //id продукта, который мы рекомендуем
    "product_text": "<текст рекомендации>",
    "rule": [
        {
            "query": "USER_OF",
            "arguments": [
                "CREDIT"
            ],
            "negate": true
        },
        {
            "query": "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW",
            "arguments": [
                "DEBIT",
                ">"
            ],
            "negate": false
        },
        {
            "query": "TRANSACTION_SUM_COMPARE",
            "arguments": [
                "DEBIT",
                "DEPOSIT",
                ">",
                "100000"
            ],
            "negate": false
        }
    ]
}
```

```Json
Response:
200 OK
{
    "id": "<некий сгенерированный id>"
    "product_name": "Простой кредит", //Имя продукта, который мы рекомендуем
    "product_id": "ab138afb-f3ba-4a93-b74f-0fcee86d447f", //id продукта, который мы рекомендуем
    "product_text": "<текст рекомендации>",
    "rule": [
        {
            "query": "USER_OF",
            "arguments": [
                "CREDIT"
            ],
            "negate": true
        },
        {
            "query": "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW",
            "arguments": [
                "DEBIT",
                ">"
            ],
            "negate": false
        },
        {
            "query": "TRANSACTION_SUM_COMPARE",
            "arguments": [
                "DEBIT",
                "DEPOSIT",
                ">",
                "100000"
            ],
            "negate": false
        }
    ]
}
```

Листинг правил рекомендаций:

```Json
Request:
GET /rule
Response:
200 OK
{
    "data": [
        {
            "id": "<некий сгенерированный id>",
            "product_name": "Простой кредит",
            "product_id": "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
            "product_text": "<текст рекомендации>",
            "rule": [
                {
                    "query": "USER_OF",
                    "arguments": [
                        "CREDIT"
                    ],
                    "negate": true
                },
                {
                    "query": "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW",
                    "arguments": [
                        "DEBIT",
                        ">"
                    ],
                    "negate": false
                },
                {
                    "query": "TRANSACTION_SUM_COMPARE",
                    "arguments": [
                        "DEBIT",
                        "DEPOSIT",
                        ">",
                        "100000"
                    ],
                    "negate": false
                }
            ]
        },
        .... //Может содержать еще объекты
    ]
}
```

Удаление правила рекомендации:

```Json
Request:
DELETE /rule/<product_id>
Response:
204 No Content
```

## Кеширование результатов запросов

Так как наша база знаний о пользователе не изменяется в процессе работы приложения, кеширование результатов SQL запросов в нее будет отличным подспорьем для производительности сервиса. Поскольку рекомендации по одному и тому же пользователю могут запрашиваться достаточно часто.

На уровне репозитория базы знаний о пользователях вам нужно будет добавить кеширование ответов из базы знаний.

Для кеширования можно использовать, например, библиотеку Caffeine. Можно также использовать Spring Cache и его аннотации, однако проще будет реализовать кеширование напрямую через Caffeine в данном случае.

Так как каждый запрос в базу знаний отличается количеством и видом аргументов, вам может понадобиться несколько кешей — по одному на запрос.

Количество запросов в вашей системе фиксированное, и при правильной декомпозиции у вас должно получиться не больше трех методов репозитория и, соответственно, не больше трех кешей.

Ключ каждого кеша будет составным и будет зависеть от аргументов запроса.

Проверить правильность кеширования можно путем последовательного получения рекомендаций для одного и того же пользователя — следующий запрос должен занимать меньше времени, чем первый запрос.

## Подключение второй базы данных

Вторая база данных в режиме Read/Write будет нужна вам для того, чтобы сохранять динамические правила рекомендаций в виде сущности БД и производить с ними операции создания, удаления и выборки.

Для того чтобы подключить вторую базу данных, вам нужно будет добавить в конфигурацию DataSource следующий метод:

```Java
@Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
```

Настраивать эту базу данных можно так же, как раньше, через переменные в application.properties.

Например:

`spring.datasource.url=jdbc:h2:mem://test-db`

В качестве второй базы данных рекомендуется выбрать PostgreSQL. Создание таблиц в ней должно выполняться только с помощью механизма миграций Liquibase.

Критерии выполнения ТЗ
Работа будет оценена по следующим критериям
В системе можно создать, получить и удалить динамические правила рекомендаций.
Рекомендации выдаются согласно созданным динамическим правилам.
Правила рекомендаций из первого ТЗ также отрабатывают либо в виде отдельных компонентов, либо в виде динамических правил.
Все запросы в базу знаний кешируются. Повторные запросы к системе с одним и тем же uuid пользователя отрабатывают быстрее, чем первый запрос.

## Пример запроса на добавление продукта

```Json
    {
        "product_name": "Интересный кредит",
        "product_id": "130ce0a6-c85c-5d1d-a021-1e8828b08ca8",
        "product_text": "Это очень интересный кредит на приобретение нового автомобиля",
        "rule": [
            {
                "query": "USER_OF",
                "arguments": [
                    "CREDIT"
                ],
                "negate": true
            },
            {
                "query": "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW",
                "arguments": [
                    "DEBIT",
                    ">"
                ],
                "negate": false
            },
            {
                "query": "TRANSACTION_SUM_COMPARE",
                "arguments": [
                    "DEBIT",
                    "DEPOSIT",
                    ">",
                    "100000"
                ],
                "negate": false
            }
        ]
    }
```
