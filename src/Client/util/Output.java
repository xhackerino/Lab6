package Client.util;

import java.io.PrintStream;

public class Output {
    private final PrintStream out;

    public Output(PrintStream out) {
        this.out = out;
    }

    public void print(String message) {
        out.print(message);
    }

    public void println(String message) {
        out.println(message);
    }
}
