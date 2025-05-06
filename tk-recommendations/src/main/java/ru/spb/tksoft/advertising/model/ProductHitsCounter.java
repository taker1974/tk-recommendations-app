package ru.spb.tksoft.advertising.model;

import javax.annotation.concurrent.ThreadSafe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Счётчик срабатываний рекомендации/продукта.
 * 
 * @author Konstantin Terskikh, kostus.online.1974@yandex.ru, 2025
 */
@ThreadSafe
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductHitsCounter {

    /** Продукт. */
    @NotNull
    private final Product product;

    /** Счётчик срабатываний. */
    @NotNull
    private long hitsCount;

    private final Object hitsCountLock = new Object();

    /**
     * @return Счётчик срабатываний.
     */
    @NotNull
    public long getHitsCount() {
        synchronized (hitsCountLock) {
            return hitsCount;
        }
    }

    /**
     * Увеличивает счётчик срабатываний.
     */
    public void increment() {
        synchronized (hitsCountLock) {
            hitsCount++;
        }
    }

    /**
     * Сбрасывает счётчик срабатываний.
     */
    public void reset() {
        synchronized (hitsCountLock) {
            hitsCount = 0L;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotBlank
    public String toString() {
        return product.getId().toString() + ": " + getHitsCount();
    }
}
