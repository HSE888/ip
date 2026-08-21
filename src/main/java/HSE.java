import java.util.Scanner;

public class HSE {
    public static void main(String[] args) {
        String banner = " _   _  ____  _____ \n"
                + "| | | |/ ___|| ____|\n"
                + "| |_| |\\___ \\|  _|  \n"
                + "|  _  | ___) | |___ \n"
                + "|_| |_||____/|_____|\n";

        String[] tasks = new String[100];
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
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }
                } else {
                    tasks[taskCount] = command;
                    taskCount++;
                    System.out.println("added: " + command);
                }
            }
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}