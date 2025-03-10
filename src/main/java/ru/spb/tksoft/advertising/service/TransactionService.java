package ru.spb.tksoft.advertising.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.Transaction;
import ru.spb.tksoft.advertising.repository.TransactionRepository;

@Service
@RequiredArgsConstructor
public class TransactionService {

    Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionsRepository;

    public List<Transaction> getTestTransactions(int limit) {
         return transactionsRepository.getTestTransactions(limit);
    }
}
