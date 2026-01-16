package com.yash.ccman.repository;

import com.yash.ccman.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findByUserId(Long userId);
    boolean existsByCardNumber(String cardNumber);
}
