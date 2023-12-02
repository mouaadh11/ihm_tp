import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaskManagerCLI {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Task> tasks = new ArrayList<>();
    private static List<TaskGroup> taskGroups = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            int choice = getChoice(1, 4);

            switch (choice) {
                case 1:
                    createTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    startTask();
                    break;
                case 4:
                    System.out.println("Exiting Task Manager. Goodbye!");
                    System.exit(0);
                    break;
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\nTask Manager Menu:");
        System.out.println("1. Create a new task");
        System.out.println("2. View tasks");
        System.out.println("3. Start a task");
        System.out.println("4. Exit");
    }
    private static int getChoice(int min, int max) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return choice;
    }
    private static void startTask() {
        System.out.print("Enter the ID of the task you want to start: ");
        int taskId = Integer.parseInt(scanner.nextLine());

        Task task = findTaskById(taskId);

        if (task != null) {
            task.startTask();
            System.out.println("Task started!");
        } else {
            System.out.println("Task not found with ID: " + taskId);
        }
    }

    private static Task findTaskById(int taskId) {
        for (Task task : tasks) {
            if (task.getIdTask() == taskId) {
                return task;
            }
        }
        return null;
    }
    private static void createTask() {
        System.out.print("Enter the name of the task: ");
        String name = scanner.nextLine();

        System.out.print("Enter the number of focusing sessions for the task: ");
        int numFocusSessions = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the number of focusing sessions for a long break session: ");
        int numFocusSessionsForLongBreak = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the name of the group (press Enter if the task doesn't belong to a group): ");
        String groupName = scanner.nextLine();



        Task task;
        if (!groupName.isEmpty()) {
            TaskGroup group = findOrCreateGroup(groupName);
            task = new Task(tasks.size() + 1, name,group.getGroupName(),null ,Tags.DEFAULT, numFocusSessions,
                    "", Status.TODO, null, numFocusSessionsForLongBreak);
            group.addTask(task);
        }
        else {
            task = new Task(tasks.size() + 1, name,null,null ,Tags.DEFAULT, numFocusSessions,
                    "", Status.TODO, null, numFocusSessionsForLongBreak);
        }
        tasks.add(task);


        System.out.println("Task created successfully!");
    }

    private static TaskGroup findOrCreateGroup(String groupName) {
        for (TaskGroup group : taskGroups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }

        TaskGroup newGroup = new TaskGroup(taskGroups.size() + 1, groupName, new ArrayList<>());
        taskGroups.add(newGroup);
        return newGroup;
    }

    private static void viewTasks() {
        System.out.println("\nView Tasks:");
        for (Task task : tasks) {
            System.out.println("Task ID: " + task.getIdTask() + " - Name: " + task.getName() +
                    " - Status: " + task.getStatus() + " - belongs to: " + task.getGroupeName());
        }

        System.out.println("\nView Task Groups:");
        for (TaskGroup group : taskGroups) {
            System.out.println("Group ID: " + group.getGroupId() + " - Name: " + group.getGroupName());
            System.out.println("Tasks in the group:");
            for (Task task : group.getTasks()) {
                System.out.println("- Task ID: " + task.getIdTask() + " - Name: " + task.getName() +
                        " - Status: " + task.getStatus());
            }
            System.out.println();
        }
    }
}
