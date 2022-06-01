package Exception;

public class LimitedStackException extends Exception {
    @Override
    public String getMessage() {
        return "Stack is out of space";
    }
}
