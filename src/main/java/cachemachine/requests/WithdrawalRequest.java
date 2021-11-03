package cachemachine.requests;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class WithdrawalRequest {
    @DecimalMin("5.00")
    private BigDecimal amount;

    @JsonCreator
    public WithdrawalRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
