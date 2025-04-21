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
 * @author Константин Терских, kostus.online@gmail.com, 2025
 */
@ThreadSafe
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductHitsCounter {

    @NotNull
    private final Product product;

    @NotNull
    private long hitsCount;

    private final Object hitsCountLock = new Object();

    @NotNull
    public long getHitsCount() {
        synchronized (hitsCountLock) {
            return hitsCount;
        }
    }

    public void increment() {
        synchronized (hitsCountLock) {
            hitsCount++;
        }
    }

    public void reset() {
        synchronized (hitsCountLock) {
            hitsCount = 0L;
        }
    }

    @Override
    @NotBlank
    public String toString() {
        return product.getId().toString() + ": " + getHitsCount();
    }
}
