import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;


public class TimeSession {
    private String label;
    private int id_TS;
    private Duration duration;
    private AtomicReference<Duration> current;
    private Timer timer;
    private int helper = 0;

    private boolean paused;
    //private LocalTime startTime;
    private boolean timeUp;

    // Constructor
    public TimeSession(int id_TS, long durationInMinutes, String label) {
        this.id_TS = id_TS;
        this.duration = Duration.ofMinutes(durationInMinutes);
        this.current = new AtomicReference<>(Duration.ZERO);
        this.timer = new Timer();
        this.paused = true;
        this.timeUp = false;
        this.label = label;
    }


    // Start the time session
    public void start() {
        if (paused) {
//            startTime = LocalTime.now();
            timer = new Timer();  // Create a new Timer instance
            createTimerTask();
            paused = false;
        }
    }

    // Pause the time session
    public void pause() {
        if (!paused) {
            timer.cancel();
            timer.purge();
//            updateCurrentTime();
            paused = true;
        }
    }

    // Resume the paused time session
    public void play() {
        if (paused) {
            timer = new Timer();  // Create a new Timer instance
            createTimerTask();
            paused = false;
        }
    }

    // Reset the time session
    public void reset() {
        timer.cancel();
        timer.purge();
        helper = 0;
        timer = new Timer();
        paused = true;
    }

    public boolean isTimeUp() {
        return timeUp;
    }

    // Skip the time session by a specified duration
    public void skip(Duration skipDuration) {
        if (!paused) {
            timer.cancel();
            timer.purge();
            timeUp = true;
        }
    }

    public synchronized Duration getCurrent() {
        return current.get();
    }

    private void createTimerTask() {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
//                updateCurrentTime();
                if(!paused){
                helper ++;
                current.set(Duration.ofSeconds(helper));
//                System.out.println("Current Time: " + current.get().toMinutes() + ":" + current.get().toSecondsPart());
                    if (helper >= duration.toSeconds()) {
                        timeUp = true;
                        System.out.println("Time's up!");
                        timer.cancel();
                        timer.purge();
                    }
                }
            }
        };

        // Schedule the task to run every 1000 milliseconds (1 second)
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public String getLabel() {
        return label;
    }

}


    // Main method for testing
//    public static void main(String[] args) throws InterruptedException {
//        // Example usage
//        TimerU timeSession = new TimerU(1, 2);
//
//        timeSession.start();
//        while (!timeSession.isTimeUp()) {
//            //System.out.println("Current Time: " + timeSession.getCurrentTime().toMinutes() + " : " + timeSession.getCurrentTime().toSecondsPart());
//            // Simulate some activity
//            try {
//                Thread.sleep(1000); // Sleep for 1 second
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        System.out.println("Time's up!");
//
//    }
//}
