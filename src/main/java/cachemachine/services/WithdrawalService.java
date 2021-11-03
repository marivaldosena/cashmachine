package cachemachine.services;

import cachemachine.domain.Account;
import cachemachine.domain.Withdrawal;

import java.math.BigDecimal;

public interface WithdrawalService<T extends Account> {

    Withdrawal withdraw(T balance, BigDecimal amount);

}
