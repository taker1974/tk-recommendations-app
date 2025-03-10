package ru.spb.tksoft.advertising.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.Transaction;
import ru.spb.tksoft.advertising.service.TransactionService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(value = "/transactions")
@Tag(name = "Транзакции пользователя")
@RequiredArgsConstructor
public class RecommendationsController {

    private final TransactionService transactionsService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Тестовый метод получения списка транзакций")
    @GetMapping
    public List<Transaction> getTestTransactions(@RequestParam int limit) {
        return transactionsService.getTestTransactions(limit);
    }
}
