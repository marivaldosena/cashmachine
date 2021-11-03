package cachemachine.domain;

import java.math.BigDecimal;

public interface Account {

    BigDecimal getBalance();

    void withdraw(BigDecimal amount);

    BigDecimal deposit(BigDecimal amount);

}
