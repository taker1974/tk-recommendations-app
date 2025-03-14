package ru.spb.tksoft.advertising.entity.transaction;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transaction {

    private UUID id;

    private UUID productId;
    private UUID userId;

    private String type;
    private int amount;
}
