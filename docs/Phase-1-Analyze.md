# Этап 1. Анализ требований по рекомендациям

## 1. Invest 500

1. Пользователь использует как минимум один продукт с типом DEBIT.
2. Пользователь не использует продукты с типом INVEST.
3. Сумма пополнений продуктов с типом SAVING больше 1000.

Псевдокод и проверенный SQL:

1,2:    boolean user.isUsingProduct(product.type);

SELECT COUNT(t.PRODUCT_ID)
FROM TRANSACTIONS t JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
WHERE USER_ID = 'c71f76a1-2659-4b46-818e-02003558cf76' AND p."TYPE" = 'DEBIT';

3:      double user.getProductSum(product.type, transaction.type)

SELECT t.PRODUCT_ID, p."TYPE", t.USER_ID, t.AMOUNT FROM TRANSACTIONS t
JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
WHERE USER_ID = 'c71f76a1-2659-4b46-818e-02003558cf76' AND
p."TYPE" = 'INVEST' AND t."TYPE" = 'DEPOSIT';

SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t
JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
WHERE USER_ID = 'c71f76a1-2659-4b46-818e-02003558cf76' AND
p."TYPE" = 'INVEST' AND t."TYPE" = 'DEPOSIT';

## 2. Top Saving

1. Пользователь использует как минимум один продукт с типом DEBIT.
2. Сумма пополнений (DEPOSIT) по всем продуктам типа DEBIT больше или равна 50 000
ИЛИ
   Сумма пополнений (DEPOSIT) по всем продуктам типа SAVING больше или равна 50 000.
3. Сумма пополнений (DEPOSIT) по всем продуктам типа DEBIT больше,
чем сумма трат (WITHDRAW) по всем продуктам типа DEBIT.

1,2,3: См. INVEST 500;

SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t
JOIN PRODUCTS p ON p.ID = t.PRODUCT_ID
WHERE USER_ID = 'c71f76a1-2659-4b46-818e-02003558cf76' AND
p."TYPE" = 'DEBIT' AND t."TYPE" = 'DEPOSIT';

## 3. Простой кредит

1. Пользователь не использует продукты с типом CREDIT.
2. Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
3. Сумма трат по всем продуктам типа DEBIT больше, чем 100 000.

1:      См. INVEST 500;
2,3:    См. Top Saving.
