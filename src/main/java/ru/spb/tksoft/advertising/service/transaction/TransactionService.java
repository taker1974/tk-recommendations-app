package ru.spb.tksoft.advertising.service.transaction;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.spb.tksoft.advertising.entity.transaction.Transaction;
import ru.spb.tksoft.advertising.repository.transaction.TransactionRepository;

@Service
@RequiredArgsConstructor
public class TransactionService {

    Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    public List<Transaction> getTestTransactions(int limit) {
         return transactionRepository.getTestTransactions(limit);
    }
}
