package cachemachine.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class CurrentAccount implements Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;
    private String accountOwner;

    @CreationTimestamp
    private LocalDateTime openedAt;

    @Deprecated
    public CurrentAccount() {
    }

    public CurrentAccount(String accountOwner, BigDecimal initialBalance) {
        this.accountOwner = accountOwner;
        this.balance = initialBalance;
    }

    public Long getId() {
        return id;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new RuntimeException("Balance is not sufficient");
        }

        balance = balance.subtract(amount);
    }

    @Override
    public BigDecimal deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO)  <= 0) {
            throw new RuntimeException("Deposit should be positive");
        }

        balance = balance.add(amount);

        return balance;
    }
}
