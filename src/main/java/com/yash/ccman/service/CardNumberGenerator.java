package com.yash.ccman.service;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class CardNumberGenerator {
    private final Random random = new Random();

    public String generate() {
        // 16 digit card number
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<16;i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
