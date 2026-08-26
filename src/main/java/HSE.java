import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HSE {
    public static Task[] tasks = new Task[100];
    public static int taskCount = 0;

    public static void main(String[] args) {
        String banner = " _   _  ____  _____ \n"
                + "| | | |/ ___|| ____|\n"
                + "| |_| |\\___ \\|  _|  \n"
                + "|  _  | ___) | |___ \n"
                + "|_| |_| |____/|_____|\n";

        System.out.println(banner);
        System.out.println("Hello! I'm HSE.");
        System.out.println("What can I do for you?");

        Scanner scanner = new Scanner(System.in);
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // File(directory, name) works on Windows, macOS, and Linux.
        File duke = new File(directory, "duke.txt");
        if (duke.exists()) {
            try {
                loadtasks(duke);
            } catch (FileNotFoundException e) {
                System.out.println("OOPS!!! Could not load saved tasks.");
            }
        }

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                break;
            }

            try {
                if (command.equals("list")) {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i]);
                    }

                } else if (command.startsWith("mark ")) {
                    int index = getIndex(command);
                    tasks[index].markAsDone();
                    saveTasks();

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[index]);

                } else if (command.startsWith("unmark ")) {
                    int index = getIndex(command);
                    tasks[index].markAsNotDone();
                    saveTasks();

                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[index]);

                } else if (command.equals("todo")) {
                    throw new IllegalArgumentException(
                            "Please give the description of a todo.");

                } else if (command.startsWith("delete ")) {
                    int index = getIndex(command);
                    Task deletedTask = tasks[index];

                    for (int i = index; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                    }
                    tasks[taskCount - 1] = null;
                    taskCount--;
                    saveTasks();

                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + deletedTask);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");

                } else if (command.startsWith("todo ")) {
                    String description = command.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new IllegalArgumentException(
                                "Please give the description of a todo.");
                    }
                    tasks[taskCount] = new ToDos(description);
                    taskCount++;
                    writeToFile(tasks[taskCount - 1] + System.lineSeparator(), true);
                    printAddedTask();

                } else if (command.startsWith("deadline ")) {
                    String[] parts = command.substring(9).split(" /by ", 2);
                    if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                        throw new IllegalArgumentException(
                                "Please use: deadline DESCRIPTION /by DATE.");
                    }
                    tasks[taskCount] = new Deadline(parts[0], parts[1]);
                    taskCount++;
                    writeToFile(tasks[taskCount - 1] + System.lineSeparator(), true);
                    printAddedTask();

                } else if (command.startsWith("event ")) {
                    String[] fromParts = command.substring(6).split(" /from ", 2);
                    if (fromParts.length != 2) {
                        throw new IllegalArgumentException(
                                "Please use: event DESCRIPTION /from START /to END.");
                    }
                    String[] toParts = fromParts[1].split(" /to ", 2);
                    if (fromParts[0].isEmpty() || toParts.length != 2
                            || toParts[0].isEmpty() || toParts[1].isEmpty()) {
                        throw new IllegalArgumentException(
                                "Please use: event DESCRIPTION /from START /to END.");
                    }
                    tasks[taskCount] = new Event(fromParts[0], toParts[0], toParts[1]);
                    taskCount++;
                    writeToFile(tasks[taskCount - 1] + System.lineSeparator(), true);
                    printAddedTask();

                } else {
                    throw new IllegalArgumentException("Please input a valid command.");
                }
            } catch (NumberFormatException e) {
                System.out.println("OOPS!!! Please enter a valid task number.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("OOPS!!! That task number does not exist.");
            } catch (IllegalArgumentException e) {
                System.out.println("OOPS!!! " + e.getMessage());
            } catch (IOException e) {
                System.out.println("OOPS!!! Could not save your tasks.");
            }
        }

        scanner.close();
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static int getIndex(String command) {
        String[] parts = command.split(" ");
        int index = Integer.parseInt(parts[1]) - 1;
        if (index < 0 || index >= taskCount) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return index;
    }

    public static void printAddedTask() {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + tasks[taskCount - 1]);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    // append is true for a new task, false when rewriting the whole list
    public static void writeToFile(String textToAdd, boolean append) throws IOException {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        FileWriter writer = new FileWriter(new File(directory, "duke.txt"), append);
        writer.write(textToAdd);
        writer.close();
    }

    // Mark, unmark, and delete change old tasks, so save the complete list again.
    public static void saveTasks() throws IOException {
        String text = "";
        for (int i = 0; i < taskCount; i++) {
            text += tasks[i] + System.lineSeparator();
        }
        writeToFile(text, false);
    }

    public static void loadtasks(File file) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine() && taskCount < tasks.length) {
            String line = fileScanner.nextLine();

            if (line.startsWith("[T][X] ")) {
                tasks[taskCount] = new ToDos(line.substring(7));
                tasks[taskCount].markAsDone();
            } else if (line.startsWith("[T][ ] ")) {
                tasks[taskCount] = new ToDos(line.substring(7));
            } else if (line.startsWith("[D][X] ") || line.startsWith("[D][ ] ")) {
                String text = line.substring(7);
                String[] parts = text.split(" \\(by: ", 2);
                if (parts.length != 2 || !parts[1].endsWith(")")) {
                    continue;
                }
                tasks[taskCount] = new Deadline(parts[0], parts[1].substring(0, parts[1].length() - 1));
                if (line.startsWith("[D][X] ")) {
                    tasks[taskCount].markAsDone();
                }
            } else if (line.startsWith("[E][X] ") || line.startsWith("[E][ ] ")) {
                String text = line.substring(7);
                String[] parts = text.split(" \\(from: ", 2);
                if (parts.length != 2 || !parts[1].endsWith(")")) {
                    continue;
                }
                String[] times = parts[1].substring(0, parts[1].length() - 1).split(" to: ", 2);
                if (times.length != 2) {
                    continue;
                }
                tasks[taskCount] = new Event(parts[0], times[0], times[1]);
                if (line.startsWith("[E][X] ")) {
                    tasks[taskCount].markAsDone();
                }
            } else {
                continue; // Ignore a corrupted line in the file.
            }
            taskCount++;
        }

        fileScanner.close();
    }
}
