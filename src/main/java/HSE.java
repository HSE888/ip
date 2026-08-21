import java.util.Scanner;

public class HSE {
    public static void main(String[] args) {
        String banner = " _   _  ____  _____ \n"
                + "| | | |/ ___|| ____|\n"
                + "| |_| |\\___ \\|  _|  \n"
                + "|  _  | ___) | |___ \n"
                + "|_| |_||____/|_____|\n";

        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(banner);
        System.out.println("Hello! I'm HSE.");
        System.out.println("What can I do for you?");

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();

                if (command.equals("bye")) {
                    break;
                } else if (command.equals("list")) {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "."
                                + tasks[i].toString());
                    }
                } else if (command.startsWith("mark ")) {
                    String[] parts = command.split(" ");
                    int index = Integer.parseInt(parts[1]) - 1;

                    tasks[index].markAsDone();

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks[index].toString());

                } else if (command.startsWith("unmark ")) {
                    String[] parts = command.split(" ");
                    int index = Integer.parseInt(parts[1]) - 1;

                    tasks[index].markAsNotDone();

                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks[index].toString());

                } else if (command.equals("todo")) {
                    try {
                        throw new IllegalArgumentException(
                                "OOPS!!! Please give the description of a todo.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (command.equals("blah")) {
                    try {
                        throw new IllegalArgumentException(
                                "OOPS!!! Please input a task.");
                    }
                        catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                else {
                    if (command.startsWith("todo ")) {
                        tasks[taskCount] = new ToDos(command);
                    }
                    if (command.startsWith("deadline ")) {
                        String input = command.substring(9);
                        String[] parts = input.split(" /by ", 2);

                        String description = parts[0];
                        String deadline = parts[1];
                        tasks[taskCount] = new Deadline(description, deadline);

                    }
                    if (command.startsWith("event ")) {
                        String input = command.substring(6);

                        String[] fromParts = input.split(" /from ", 2);
                        String description = fromParts[0];

                        String[] toParts = fromParts[1].split(" /to ", 2);
                        String from = toParts[0];
                        String to = toParts[1];

                        tasks[taskCount] = new Event(description, from, to);

                    }

                    taskCount++;
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");

                }

            }


            System.out.println("Bye. Hope to see you again soon!");
        }
    }
}
