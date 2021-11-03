package cachemachine.exceptions;

public class AmountNotValid extends AppDefaultErrorException {
    public AmountNotValid(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "amount-not-valid";
    }
}
