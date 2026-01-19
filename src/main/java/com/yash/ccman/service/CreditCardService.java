package com.yash.ccman.service;

import com.yash.ccman.entity.CardStatus;
import com.yash.ccman.entity.CreditCard;
import com.yash.ccman.entity.User;
import com.yash.ccman.exception.BadRequestException;
import com.yash.ccman.exception.NotFoundException;
import com.yash.ccman.repository.CreditCardRepository;
import com.yash.ccman.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final UserRepository userRepository;
    private final CardNumberGenerator cardNumberGenerator;

    public CreditCard issueCard(Long userId, Double creditLimit) {

        // 1. Check user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // 2. Ensure user doesn't already have a card
        creditCardRepository.findByUserId(userId).ifPresent(card -> {
            throw new BadRequestException("User already has a credit card");
        });

        // 3. Generate unique card number
        String cardNumber;
        do {
            cardNumber = cardNumberGenerator.generate();
        } while (creditCardRepository.existsByCardNumber(cardNumber));

        // 4. Create card
        CreditCard card = CreditCard.builder()
                .cardNumber(cardNumber)
                .cardHolderName(user.getFullName())
                .creditLimit(creditLimit)
                .availableLimit(creditLimit)
                .status(CardStatus.ACTIVE)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        // 5. Save
        return creditCardRepository.save(card);
    }

    // Get card by UserId
    public CreditCard getCardByUserId(Long userId) {
        return creditCardRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Credit card not found for user"));
    }

    // To Block/Unblock card
    public CreditCard blockCard(Long cardId) {
        CreditCard card =  creditCardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found"));

        card.setStatus(CardStatus.BLOCKED);
        return creditCardRepository.save(card);
    }
    public CreditCard unblockCard(Long cardId) {
        CreditCard card =  creditCardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card not found"));

        card.setStatus(CardStatus.ACTIVE);
        return creditCardRepository.save(card);
    }

}
