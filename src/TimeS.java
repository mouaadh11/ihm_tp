import java.time.Duration;
import java.time.LocalTime;

public class TimeSession {
    private int id_TS;
    private Duration duration;
    private Duration current;
    private boolean paused;
    private LocalTime startTime;

    // Constructor
    public TimeSession(int id_TS, long durationInMinutes) {
        this.id_TS = id_TS;
        this.duration = Duration.ofMinutes(durationInMinutes);
        this.current = Duration.ZERO;
        this.paused = true;
    }

    // Start the time session
    public void start() {
        if (paused) {
            startTime = LocalTime.now();
            paused = false;
        }
    }

    // Pause the time session
    public void pause() {
        if (!paused) {
            updateCurrentTime();
            paused = true;
        }
    }

    // Resume the paused time session
    public void play() {
        if (paused) {
            startTime = LocalTime.now().minusNanos(current.toNanos());
            paused = false;
        }
    }

    // Reset the time session
    public void reset() {
        current = Duration.ZERO;
        paused = true;
        start();
    }

    // Skip the time session by a specified duration
    public void skip() {
        if (!paused) {
            current = duration;
        }
    }

    // Get the current time of the time session
    public Duration getCurrentTime() {
        if (!paused) {
            updateCurrentTime();
        }
        return current;
    }

    // Check if the time session is up
    public boolean isTimeUp() {
        updateCurrentTime();
        return !paused && current.compareTo(duration) >= 0;
    }

    private void updateCurrentTime() {
        if (!paused) {
            LocalTime now = LocalTime.now();
            Duration elapsedTime = Duration.between(startTime, now);
            current = current.plus(elapsedTime);
            startTime = now;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Example usage
        TimeSession timeSession = new TimeSession(1, 1);

        timeSession.start();

        while (!timeSession.isTimeUp()) {
            System.out.println("Current Time: " + timeSession.getCurrentTime().toMinutes() + " : " + timeSession.getCurrentTime().toSecondsPart());
            // Simulate some activity
            try {
                Thread.sleep(2000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeSession.skip();
        }

        System.out.println("Time's up!");
    }
}
