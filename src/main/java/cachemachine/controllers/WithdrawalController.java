package cachemachine.controllers;

import cachemachine.domain.Account;
import cachemachine.domain.Withdrawal;
import cachemachine.requests.WithdrawalRequest;
import cachemachine.responses.WithdrawalResponse;
import cachemachine.services.BalanceService;
import cachemachine.services.WithdrawalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class WithdrawalController {

    private WithdrawalService withdrawalService;
    private BalanceService accountBalanceService;

    public WithdrawalController(WithdrawalService withdrawalService, BalanceService accountBalanceService) {
        this.withdrawalService = withdrawalService;
        this.accountBalanceService = accountBalanceService;
    }

    @PostMapping("/current-account/{id}/withdraw")
    public ResponseEntity<WithdrawalResponse> withdraw(@PathVariable Long id, @Valid @RequestBody WithdrawalRequest request) {
        Account account = accountBalanceService.getAccountById(id);
        Withdrawal withdrawal = withdrawalService.withdraw(account, request.getAmount());
        WithdrawalResponse response = new WithdrawalResponse(withdrawal);

        return ResponseEntity.ok(response);
    }
}
