package cachemachine.responses;

import cachemachine.domain.Withdrawal;

import java.math.BigDecimal;

public class WithdrawalResponse {

    private BigDecimal cashRequested;
    private Integer numberOfBallots;

    public WithdrawalResponse(Withdrawal withdrawal) {
        this.numberOfBallots = withdrawal.getNumberOfBallots();
        this.cashRequested = withdrawal.getAmount();
    }

    public BigDecimal getCashRequested() {
        return cashRequested;
    }

    public Integer getNumberOfBallots() {
        return numberOfBallots;
    }
}
