package Exception;

public class InputException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Invalid input";
    }
}
