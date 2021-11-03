package cachemachine.services;

import cachemachine.domain.CurrentAccount;
import cachemachine.domain.Withdrawal;
import cachemachine.domain.enums.Ballot;
import cachemachine.exceptions.AmountNotValid;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountWithdrawalService implements WithdrawalService<CurrentAccount> {
    @Override
    public Withdrawal withdraw(CurrentAccount account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Balance is not sufficient");
        }

        List<Integer> ballotsToDivide = Ballot.stream().map(it -> it.getValue()).collect(Collectors.toList());
        BigDecimal newAmount = amount;
        Integer numberOfBallots = 0;

        for (Integer ballot : ballotsToDivide) {
            numberOfBallots += getNumberOfBallots(newAmount, ballot);
            newAmount = getNewBalance(newAmount, ballot);
        }

        checkIfValueIsValid(newAmount);

        account.withdraw(amount);

        return new Withdrawal(numberOfBallots, amount);
    }

    private void checkIfValueIsValid(BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.ZERO) != 0) {
            throw new AmountNotValid("Withdrawal amount is not valid");
        }
    }

    private Integer getNumberOfBallots(BigDecimal desiredAmount, Integer ballot) {
        return desiredAmount.divide(BigDecimal.valueOf(ballot)).intValue();
    }

    private BigDecimal getNewBalance(BigDecimal desiredAmount, Integer ballot) {
        return desiredAmount.remainder(BigDecimal.valueOf(ballot));
    }
}
