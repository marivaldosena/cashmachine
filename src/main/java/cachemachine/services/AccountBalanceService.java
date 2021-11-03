package cachemachine.services;

import cachemachine.domain.CurrentAccount;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class AccountBalanceService implements BalanceService<CurrentAccount> {
    @Override
    public CurrentAccount getAccountById(Long accountId) {
        return null;
    }
}
