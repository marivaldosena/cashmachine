package cachemachine.exceptions;

public class AppDefaultErrorException extends RuntimeException {
    public AppDefaultErrorException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return "error";
    }
}
