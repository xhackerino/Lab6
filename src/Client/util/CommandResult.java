package Client.util;

import java.io.Serializable;

public class CommandResult implements Serializable {
    private final Serializable result;

    public CommandResult(Serializable result) {
        this.result = result;
    }

    public Serializable getResult() {
        return result;
    }
    @Override
    public String toString() {
        return "CommandResult{" +
                "result=" + result +
                '}';
    }
}
