package com.yash.ccman.entity;

import jakarta.persistence.*;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "cardId")
    private CreditCard creditCard;
}
