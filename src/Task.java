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
    //jibhom ml database
    int shortBreakTime = 1;
    int longBreakTime = 3;
    int inFocusTime = 5;
    boolean autoStartBreak;
    boolean disableBreaks;
    boolean autoStartPomodoro;
    boolean autoStart;
    // id-time-session_genrator
    int id;
    private String groupeName;

    // Constructor
    public Task(int idTask, String name, String groupename ,Date deadline, Tags priority, int numberOfPomodoro,
                String description, Status status, Date reminder, int inFocusTimes) {
        this.idTask = idTask;
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.numberOfPomodoro = numberOfPomodoro;
        this.description = description;
        this.status = status;
        this.reminder = reminder;
        this.groupeName = groupename;
        this.timeSessions = new ArrayList<>(); // Initialize the list
        this.inFocusTimes = inFocusTimes;
        initList(inFocusTimes);
        if (autoStartPomodoro || autoStart){
            startTask();
        }
    }

    // Other methods...
    // Start the task with timers starting one after another
    public void startTask() {
        if (status == Status.TODO) {
            status = Status.IN_PROGRESS;
            for (TimeSession timeSession : timeSessions) {
                if ((timeSession.getLabel() == "Short break" ||timeSession.getLabel() == "Long Break") && !disableBreaks){
                    if ((timeSession.getLabel() == "Short break" ||timeSession.getLabel() == "Long Break") && (!autoStartBreak || !autoStart )){
                        // TODO: 14/12/2023 ask for starting
                    }else {
                        timeSession.start();
                        // Wait for the current time session to complete before starting the next one
                        while (!timeSession.isTimeUp()) {
                            System.out.println("time: " + timeSession.getCurrent().toMinutes() + ":" + timeSession.getCurrent().toSecondsPart());
                            try {
                                Thread.sleep(1000); // You can adjust the delay as needed (in milliseconds)
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
            status = Status.DONE;
        } else {
            System.out.println("Task is already in progress or completed.");
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
    private TimeSession createShortBreakSession(int id, int duration) {return new ShortBreakS(id, duration);}

    // Initialize the list of TimeSessions with different types
    private void initList(int inFocusTimes) {
        for (int i = 1; i <= numberOfPomodoro; i++) {
//            System.out.println(i % inFocusTimes);
            if (i % inFocusTimes == 0) {
//                System.out.println("long + in F");
                // Add an InFocusSession
                timeSessions.add(createInFocusSession(id, inFocusTime ));
                // Add a LongBreakSession after every 'inFocusTimes' InFocusSessions
                timeSessions.add(createLongBreakSession(id, longBreakTime ));
            }else if ( i  == numberOfPomodoro ) {
//                System.out.println("in F");

                // Add an InFocusSession
                timeSessions.add(createInFocusSession(id, inFocusTime ));
            } else {
//                System.out.println("in F + short");
                // Add an InFocusSession
                timeSessions.add(createInFocusSession(id, inFocusTime ));
                // After each InFocusSession, add a ShortBreakSession
                timeSessions.add(createShortBreakSession(id, shortBreakTime ));
            }
        }
        for (TimeSession timeSession : timeSessions) {
            System.out.println(timeSession.getLabel());
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupeName() {
        return groupeName;
    }

    public void setInFocusTime(int inFocusTime) {
        this.inFocusTime = inFocusTime;
    }

    public void setInFocusTimes(int inFocusTimes) {
        this.inFocusTimes = inFocusTimes;
    }

    public void setLongBreakTime(int longBreakTime) {
        this.longBreakTime = longBreakTime;
    }

    public void setShortBreakTime(int shortBreakTime) {
        this.shortBreakTime = shortBreakTime;
    }

    public void setDisableBreaks(boolean disableBreaks) {
        this.disableBreaks = disableBreaks;
    }

    public void setAutoStartBreak(boolean autoStartBreak) {
        this.autoStartBreak = autoStartBreak;
    }

    public void setAutoStartPomodoro(boolean autoStartPomodoro) {
        this.autoStartPomodoro = autoStartPomodoro;
    }
}
