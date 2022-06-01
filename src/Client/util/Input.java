package Client.util;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class Input {
    private final Scanner scanner;
    private final Stack<BufferedReader> currentFilesReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();

    public Input(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }


    public String nextLine() throws IOException {
        if (!currentFilesReaders.isEmpty()) {
            String input = currentFilesReaders.peek().readLine();
            if (input == null) {
                currentFiles.pop();
                currentFilesReaders.pop().close();
                return nextLine();
            } else {
                return input;
            }
        } else {
            return scanner.nextLine();
        }
    }

    public void connectToFile(File file) throws IOException, UnsupportedOperationException {
        if (currentFiles.contains(file)) {
            throw new UnsupportedOperationException("File already connected");
        } else {
            BufferedReader newReader = new BufferedReader(new FileReader(file));
            currentFiles.push(file);
            currentFilesReaders.push(newReader);
        }
    }

}
