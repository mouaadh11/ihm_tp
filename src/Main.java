//// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
//// then press Enter. You can now see whitespace characters in your code.
//public class Main {
//    public static void main(String[] args) {
//        // Press Alt+Enter with your caret at the highlighted text to see how
//        // IntelliJ IDEA suggests fixing it.
//        System.out.println("Hello and welcome!");
//
//        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
//        for (int i = 1; i <= 5; i++) {
//
//            // Press Shift+F9 to start debugging your code. We have set one breakpoint
//            // for you, but you can always add more by pressing Ctrl+F8.
//            System.out.println("i = " + i);
//        }
//    }
//}
import java.time.Duration;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Example usage
        TimerU timerU = new TimerU(1, 5); // A timer with a duration of 5 minutes

        Thread timerThread = new Thread(() -> {
            while (true) {
                System.out.print("\rTime remaining: " + timerU.getCurrent().toMinutes() + " : " + timerU.getCurrent().toSecondsPart() );
                if (timerU.isTimeUp()) {
                    System.out.println("\nTime's up!");
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        timerThread.start();

        while (true) {
            System.out.println("\n===== Timer Menu =====");
            System.out.println("1. Pause");
            System.out.println("2. Play");
            System.out.println("3. Reset");
            System.out.println("4. Skip");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    timerU.pause();
                    break;
                case 2:
                    timerU.play();
                    break;
                case 3:
                    timerU.reset();
                    break;
                case 4:
                    System.out.print("Enter skip duration in seconds: ");
                    int skipSeconds = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    timerU.skip(Duration.ofSeconds(skipSeconds));
                    break;
                case 5:
                    System.out.println("Exiting the program.");
                    timerThread.interrupt(); // Stop the timer thread
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }

            // Display the progress
            if (timerU.isTimeUp()) {
                break;
            }
        }
    }
}
