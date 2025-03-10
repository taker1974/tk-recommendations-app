package ru.spb.tksoft.advertising.entity;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {

    private UUID id;

    private String type;
    private String name;
}
