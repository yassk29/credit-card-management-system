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
}
