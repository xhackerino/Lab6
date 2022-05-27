package Command;

import java.io.IOException;
import Exception.EmptyIOException;

/**
 * интерфейс с объявленными методами, присущие всем командам
 */
public interface Command {
    /**
     * имя команды
     * @return name имя команды
     */
    String getName();

    /**
     * Описание команды
     * @return Description описание команды
     */
    String getDescription();

    /**
     * Execute команду
     * @param arg Аргумент команды
     * @return Завершена или не завершена
     * @throws IOException в случае ошибки ввода-вывода.
     * @throws EmptyIOException в случае пустого ввода-вывода.
     */
    boolean execute(String arg) throws IOException, EmptyIOException;
}
