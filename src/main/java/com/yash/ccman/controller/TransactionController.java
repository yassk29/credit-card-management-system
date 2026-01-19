package com.yash.ccman.controller;

import com.yash.ccman.entity.Transaction;
import com.yash.ccman.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/spend")
    public Transaction spend (
        @RequestParam Long cardId,
        @RequestParam Double amount
        ) {
        return transactionService.spend(cardId, amount);
    }
    @PostMapping("/refund")
    public Transaction refund (
            @RequestParam Long cardId,
            @RequestParam Double amount
    ) {
        return transactionService.refund(cardId, amount);
    }
}
