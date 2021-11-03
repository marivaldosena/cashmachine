package cachemachine.services;

import cachemachine.domain.Account;

public interface BalanceService<T extends Account> {

    T getAccountById(Long accountId);

}
