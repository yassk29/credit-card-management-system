package com.yash.ccman.controller;

import com.yash.ccman.entity.CreditCard;
import com.yash.ccman.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping("/issue")
    public CreditCard issueCard(
            @RequestParam Long userId,
            @RequestParam Double creditLimit
    ) {
        return creditCardService.issueCard(userId, creditLimit);
    }

    // Get card by userId
    @GetMapping("/user/{userId}")
    public CreditCard getCardByUser(@PathVariable Long userId) {
        return creditCardService.getCardByUserId(userId);
    }

    // To Block/Unblock card
    @PutMapping("/{cardId}/block")
    public CreditCard blockCard(@PathVariable Long cardId) {
        return creditCardService.blockCard(cardId);
    }
    @PutMapping("/{cardId}/unblock")
    public CreditCard unblockCard(@PathVariable Long cardId) {
        return creditCardService.unblockCard(cardId);
    }

}
