package Exception;

public class NoArgumentException extends Exception {
    @Override
    public String getMessage() {
        return "No argument was given";
    }
}
