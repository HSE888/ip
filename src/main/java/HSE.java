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
                        System.out.println((i + 1) + ".[" 
                                + tasks[i].getStatusIcon() + "] "
                                + tasks[i].getDescription());
                    }
                } else if (command.startsWith("mark ")) {
                    String[] parts = command.split(" ");
                    int index = Integer.parseInt(parts[1]) - 1;

                    tasks[index].markAsDone();

                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("[" + tasks[index].getStatusIcon() + "] "
                            + tasks[index].getDescription());

                } else if (command.startsWith("unmark ")) {
                    String[] parts = command.split(" ");
                    int index = Integer.parseInt(parts[1]) - 1;

                    tasks[index].markAsNotDone();

                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("[" + tasks[index].getStatusIcon() + "] "
                            + tasks[index].getDescription());
                 } else {
                    tasks[taskCount] = new Task(command);
                    taskCount++;
                    System.out.println("added: " + command);
                }
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
