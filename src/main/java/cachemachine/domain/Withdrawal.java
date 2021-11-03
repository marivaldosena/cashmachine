package cachemachine.domain;

import java.math.BigDecimal;

public class Withdrawal {
    private Integer numberOfBallots;
    private BigDecimal amount;

    public Withdrawal(Integer numberOfBallots, BigDecimal amount) {
        this.numberOfBallots = numberOfBallots;
        this.amount = amount;
    }

    public Integer getNumberOfBallots() {
        return numberOfBallots;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
