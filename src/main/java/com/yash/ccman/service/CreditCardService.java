package com.yash.ccman.service;

import com.yash.ccman.entity.CardStatus;
import com.yash.ccman.entity.CreditCard;
import com.yash.ccman.entity.User;
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Ensure user doesn't already have a card
        creditCardRepository.findByUserId(userId).ifPresent(card -> {
            throw new RuntimeException("User already has a credit card");
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
}
