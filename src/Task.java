import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
enum Tags {
    IMPORTANT,
    URGENT,
    DEFAULT
}

enum Status {
    DONE,
    TODO,
    IN_PROGRESS
}
public class Task {
    private int idTask;
    private String name;
    private Date deadline;
    private Tags priority;
    private int numberOfPomodoro;
    private String description;
    private Status status;
    private Date reminder;
    private List<TimeSession> timeSessions;
    private int inFocusTimes;
    //        jibhom ml database
    int shortBreakTime = 5;
    int longBreakTime = 25;
    int inFocusTime = 30;
    //        id_genrator
    int id = 1;

    // Constructor
    public Task(int idTask, String name, Date deadline, Tags priority, int numberOfPomodoro,
                String description, Status status, Date reminder, int inFocusTimes) {
        this.idTask = idTask;
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.numberOfPomodoro = numberOfPomodoro;
        this.description = description;
        this.status = status;
        this.reminder = reminder;
        this.timeSessions = new ArrayList<>(); // Initialize the list
        this.inFocusTimes = inFocusTimes;
        initList(inFocusTimes);
    }

    // Other methods...
    // Start the task with timers starting one after another
    public void startTask() {
        for (TimeSession timeSession : timeSessions) {
            timeSession.start();
            // Wait for the current time session to complete before starting the next one
            while (!timeSession.isTimeUp()) {
                try {
                    Thread.sleep(1000); // You can adjust the delay as needed (in milliseconds)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Mark the task as complete
    public void markAsComplete() {
        status = Status.DONE;
        pauseAllTimeSessions();
    }

    // Rename the task
    public void rename(String newName) {
        this.name = newName;
    }

    // Reset time sessions with a new number of pomodoros
    public void resetTimeSessions(int newPomodoro) {
        this.numberOfPomodoro = newPomodoro;
        resetAllTimeSessions();
    }

    // Edit task description
    public void editDescription(String newDescription) {
        this.description = newDescription;
    }

    // Reschedule task with a new deadline
    public void reschedule(Date newDeadline) {
        this.deadline = newDeadline;
    }

    // Reset reminder with a new date
    public void resetReminder(Date newReminder) {
        this.reminder = newReminder;
    }

    // Set priority with a new value
    public void setPriority(Tags newPriority) {
        this.priority = newPriority;
    }

    // Create a new TimeSession
    private TimeSession createInFocusSession(int id, int duration) {
        return new InFocusS(id, duration);
    }
    private TimeSession createLongBreakSession(int id, int duration) {
        return new LongBreakS(id, duration);
    }
    private TimeSession createShortBreakSession(int id, int duration) {
        return new TimeSession(id, duration);
    }

    // Initialize the list of TimeSessions with different types
    private void initList(int inFocusTimes) {
        for (int i = 1; i <= numberOfPomodoro; i++) {
            if (i % inFocusTimes == 0) {
                // Add a LongBreakSession after every 'inFocusTimes' InFocusSessions
                timeSessions.add(createLongBreakSession(id, longBreakTime ));
            } else {
                // Add an InFocusSession
                timeSessions.add(createInFocusSession(id, longBreakTime ));
                // After each InFocusSession, add a ShortBreakSession
                timeSessions.add(createLongBreakSession(id, longBreakTime ));
            }
        }
    }

    // Add a new TimeSession to the list
    public void addSession() {
//        timeSessions.add(createTimer());
        numberOfPomodoro ++;
        if (numberOfPomodoro % inFocusTimes == 1) {
            // Add an InFocusSession
            timeSessions.add(new InFocusS(id, inFocusTime));
            // Add a LongBreakSession after every 'inFocusTimes' InFocusSessions
            timeSessions.add(createLongBreakSession(id, longBreakTime ));

        } else {
            // Add an InFocusSession
            timeSessions.add(new InFocusS(id, inFocusTime));
            // After each InFocusSession, add a ShortBreakSession
            timeSessions.add(new ShortBreakS(id, shortBreakTime));
        }
    }

    // Remove a TimeSession from the list
    public void removeTimer() {
        if (!timeSessions.isEmpty()) {
            timeSessions.remove(timeSessions.size() - 1); // Remove the last added TimeSession
        }
    }

    // Pause all time sessions
    private void pauseAllTimeSessions() {
        for (TimeSession timeSession : timeSessions) {
            timeSession.pause();
        }
    }

    // Reset all time sessions
    private void resetAllTimeSessions() {
        for (TimeSession timeSession : timeSessions) {
            timeSession.reset();
        }
    }
}
