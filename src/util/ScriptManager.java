package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static util.ConsoleManager.PrintError;

/**
 * Класс для работы со скриптами
 */
public class ScriptManager {
    private Scanner scriptReader;

    /**
     * Конструктор для создания экземпляра класса ScriptManager
     *
     * @param file путь к файлу скрипта
     */
    public ScriptManager(String file) {
        try {
            scriptReader = new Scanner(new FileReader(file));
        } catch (FileNotFoundException e) {
            PrintError("File not found");
        }
    }

    /**
     * Получение следующей строки скрипта
     *
     * @return следующая строка скрипта
     */
    public String getNextLine() {
        try {
            return scriptReader.nextLine().trim();
        } catch (NullPointerException | NoSuchElementException e){
            return null;
        }
    }
    public Scanner getScanner() {
        return scriptReader;
    }
}
