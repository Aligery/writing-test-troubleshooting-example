package org.example.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Data
public class AccountEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private int accountNumber;
    private Long sumMoneyRub;
    private Long userId;
    @CreationTimestamp
    private LocalDateTime createdAt;

}
