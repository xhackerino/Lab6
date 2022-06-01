package Client.util;

import Command.Command;

import java.io.Serializable;
import java.util.Objects;

public class CommandFromClient implements Serializable {
    private final Command command;
    public CommandFromClient(Command command){
        this.command = command;
    }
    public Command getCommand(){
        return command;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandFromClient that = (CommandFromClient) o;

        return Objects.equals(command, that.command);
    }
    @Override
    public int hashCode() {
        return Objects.hash(command);
    }
}
