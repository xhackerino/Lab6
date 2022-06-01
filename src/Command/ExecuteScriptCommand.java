package Command;

import Client.util.Input;
import Client.util.CommandResult;

import java.io.File;
import java.io.IOException;

public class ExecuteScriptCommand {
    private final String script;
    public ExecuteScriptCommand(String script) {
        this.script = script;
    }
    public void execute(Input input) throws IOException {
        try {
            input.connectToFile(new File(script));
            new CommandResult("Script executed successfully.");
        } catch (IOException e) {
            new CommandResult("Script execution failed, check if the file exists or if you have the right permissions.");
        } catch (UnsupportedOperationException e) {
            new CommandResult("Script execution failed, the file is not a valid script.");
        }
    }
}