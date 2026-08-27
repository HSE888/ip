import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** Interprets user commands, task numbers, and dates. */
public class Parser {
    /** Supported command categories. */
    public enum CommandType { BYE, LIST, FIND, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, INVALID }

    private static final DateTimeFormatter ISO_DATE_FORMAT = DateTimeFormatter.ofPattern("uuuu-M-d");
    private static final DateTimeFormatter SLASH_DATE_FORMAT = DateTimeFormatter.ofPattern("d/M/uuuu");
    private static final DateTimeFormatter DASH_DATE_FORMAT = DateTimeFormatter.ofPattern("d-M-uuuu");
    private static final DateTimeFormatter ISO_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("uuuu-M-d HHmm");
    private static final DateTimeFormatter SLASH_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
    private static final DateTimeFormatter DASH_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("d-M-uuuu HHmm");

    /** Returns the category of a command without executing it. */
    public static CommandType parseCommandType(String command) {
        if (command.equals("bye")) return CommandType.BYE;
        if (command.equals("list")) return CommandType.LIST;
        if (command.startsWith("find ")) return CommandType.FIND;
        if (command.startsWith("mark ")) return CommandType.MARK;
        if (command.startsWith("unmark ")) return CommandType.UNMARK;
        if (command.startsWith("delete ")) return CommandType.DELETE;
        if (command.equals("todo") || command.startsWith("todo ")) return CommandType.TODO;
        if (command.startsWith("deadline ")) return CommandType.DEADLINE;
        if (command.startsWith("event ")) return CommandType.EVENT;
        return CommandType.INVALID;
    }

    /** Converts the task number in a command into a validated zero-based index. */
    public static int getIndex(String command, int taskCount) {
        String[] parts = command.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;
        if (index < 0 || index >= taskCount) throw new IndexOutOfBoundsException();
        return index;
    }

    /** Parses a supported date format. */
    public static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, ISO_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(date, SLASH_DATE_FORMAT);
            } catch (DateTimeParseException e2) {
                return LocalDate.parse(date, DASH_DATE_FORMAT);
            }
        }
    }

    /** Parses a supported date-time format, or treats a date as midnight. */
    public static LocalDateTime parseDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, ISO_DATE_TIME_FORMAT);
        } catch (DateTimeParseException e) {
            try {
                return LocalDateTime.parse(dateTime, SLASH_DATE_TIME_FORMAT);
            } catch (DateTimeParseException e2) {
                try {
                    return LocalDateTime.parse(dateTime, DASH_DATE_TIME_FORMAT);
                } catch (DateTimeParseException e3) {
                    return parseDate(dateTime).atStartOfDay();
                }
            }
        }
    }
}
