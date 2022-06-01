package Client.util;

import StudyGroup.*;
import Exception.*;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Scanner;

import static util.ConsoleManager.Print;
import static util.ConsoleManager.PrintError;

public class AskGroup {
    private Scanner scanner;
    private static final String ERROR_MESSAGE = "Your enter was not correct type. Try again";
    private final Output output;
    private final Asker asker;

    public AskGroup(Input input, Output output) {
        this.output = output;
        this.asker = new Asker(input, output);
    }

    public StudyGroup makeStudyGroup() throws IOException {
        return askGroup(null);
    }

    public StudyGroup askGroup(Long Id) {
        Print("Your id = " + Id);
        String name = parseString("Enter group name:");
        ZonedDateTime creationDate = ZonedDateTime.now();
        long stdCnt;
        while (true) {
            stdCnt = parseLong("Enter number of students in group\n" +
                    "for example '10'\n" +
                    "number should be integer:");
            if (stdCnt <= 0) {
                PrintError("Number of students must be non-negative and non-zero");
            } else {
                break;
            }
        }
        Long xpdStd = parseLongXpd("Enter number of expelled students");
        FormOfEducation foe = parseFormOfEducation("Enter form of education");
        Semester sem = parseSemester("Enter semester");
        String grAdm = parseString("Enter group admins name \n(for example: 'Alexander'):");
        String passId = parseString("Enter passport ID:");
        Color color = parseColor("Enter color:");
        Country nazi = parseCountry("Enter nationality");
        Long x = parseLong("Enter X coordinate \n(for example '7'):");
        double y;
        while (true) {
            y = parseDouble("Enter Y coordinate as fractional number or simple number \n (for example '4', '3.7') \n " +
                    "Y should be greater than -352:");
            if (y < -352) {
                Print("Error: y must be greater than -352");
            } else {
                break;
            }
        }
        return new StudyGroup(Id, name, creationDate, stdCnt,
                xpdStd, foe, sem, new Person(grAdm, passId, color, nazi), new Coordinates(x, y));
    }

    public String parseString(String message) {
        String str = null;
        while (str == null) {
            try {
                Print(message);
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new EmptyIOException();
                str = message2;
            } catch (EmptyIOException e) {
                PrintError("It's an empty string");
            }
        }
        return str;
    }

    public Long parseLong(String message) {
        Long out = null;
        while (out == null) {
            try {
                Print(message);
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new EmptyIOException();
                out = Long.parseLong(message2);
            } catch (EmptyIOException e) {
                PrintError("It's an empty string");
            } catch (NumberFormatException e) {
                PrintError("It's not a number");
            }
        }
        return out;
    }

    public Long parseLongXpd(String message) {
        Long out = null;
        try {
            Print(message);
            String message2 = scanner.nextLine().trim();
            out = Long.parseLong(message2);
        } catch (IllegalArgumentException e) {
            Print("Empty number of expelled students, but it's ok");
        }
        return out;
    }


    public Double parseDouble(String message) {
        Double outta = null;
        while (outta == null) {
            try {
                Print(message);
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new EmptyIOException();
                outta = Double.parseDouble(message2);
            } catch (EmptyIOException e) {
                PrintError("It's an empty string");
            } catch (NumberFormatException e) {
                PrintError("It's not a number");
            }
        }
        return outta;
    }

    public Semester parseSemester(String message) {
        Semester out = null;
        while (out == null) {
            try {
                Print(message);
                Print("List of semesters:\n" + Arrays.toString(Semester.values()));
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new EmptyIOException();
                out = Semester.valueOf(message2);
            } catch (EmptyIOException e) {
                PrintError("It's an empty string");
            } catch (IllegalArgumentException e) {
                PrintError("That's not a semester, please, use list of semesters");
            }
        }
        return out;
    }

    public FormOfEducation parseFormOfEducation(String message) {
        FormOfEducation out = null;
        try {
            Print(message);
            Print("Forms of education:\n" + Arrays.toString(FormOfEducation.values()));
            String message2 = scanner.nextLine().trim();
            out = FormOfEducation.valueOf(message2);
        } catch (IllegalArgumentException e) {
            Print("Not a form of education, please, next time use list of forms of education");
        }
        return out;
    }

    public Color parseColor(String message) {
        Color out = null;
        while (out == null) {
            try {
                Print(message);
                Print("Colors:\n" + Arrays.toString(Color.values()));
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new EmptyIOException();
                out = Color.valueOf(message2);
            } catch (EmptyIOException e) {
                PrintError("It's an empty string");
            } catch (IllegalArgumentException e) {
                PrintError("That's not a color, please, use list of colors");
            }
        }
        return out;
    }

    public Country parseCountry(String message) {
        Country out = null;
        try {
            Print(message);
            Print("Nationality\n" + Arrays.toString(Country.values()));
            String message2 = scanner.nextLine().trim();
            out = Country.valueOf(message2);
        } catch (IllegalArgumentException e) {
            Print("That's not a country, please, next time use list of countries");
        }
        return out;
    }

    public static class Asker {

        private final Input input;
        private final Output output;


        public Asker(Input input, Output output) {
            this.input = input;
            this.output = output;
        }
    }

    public void ChangeScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
