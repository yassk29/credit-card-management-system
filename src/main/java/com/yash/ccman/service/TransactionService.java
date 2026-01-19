package com.yash.ccman.service;


import com.yash.ccman.entity.*;
import com.yash.ccman.exception.BadRequestException;
import com.yash.ccman.exception.NotFoundException;
import com.yash.ccman.repository.CreditCardRepository;
import com.yash.ccman.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final CreditCardRepository creditCardRepository;
    private final TransactionRepository transactionRepository;

    public Transaction spend(Long cardId, Double amount) {
        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found"));

        if(card.getStatus() == CardStatus.BLOCKED) {
            throw new BadRequestException("Card is Blocked");
        }

        if(amount > card.getAvailableLimit()) {
            throw new BadRequestException("Insufficient available limit");
        }

        card.setAvailableLimit(card.getAvailableLimit() - amount);
        creditCardRepository.save(card);

        Transaction tx = Transaction.builder()
                .amount(amount)
                .type(TransactionType.SPEND)
                .creditCard(card)
                .createdAt(LocalDateTime.now())
                .build();
        return transactionRepository.save(tx);
    }
    public Transaction refund(Long cardId, Double amount) {
        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found"));

        double newLimit = card.getAvailableLimit() + amount;

        if(newLimit > card.getCreditLimit()) {
            newLimit = card.getCreditLimit();
        }
        card.setAvailableLimit(newLimit);
        creditCardRepository.save(card);

        Transaction tx = Transaction.builder()
                .amount(amount)
                .type(TransactionType.REFUND)
                .creditCard(card)
                .createdAt(LocalDateTime.now())
                .build();
        return transactionRepository.save(tx);
    }
}
