/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class Settings {
    private int sessionDuration;
    private int shortBreak;
    private int longBreak;
    private int shortBreakAfter;
    private boolean autoStartBreak;
    private boolean autoStartPomodoro;
    private boolean autoStart;
    private boolean disableBreaks;

    public Settings(int sessionDuration, int shortBreak, int longBreak, int shortBreakAfter,
                    boolean autoStartBreak, boolean autoStartPomodoro, boolean autoStart, boolean disableBreaks) {
        this.sessionDuration = sessionDuration;
        this.shortBreak = shortBreak;
        this.longBreak = longBreak;
        this.shortBreakAfter = shortBreakAfter;
        this.autoStartBreak = autoStartBreak;
        this.autoStartPomodoro = autoStartPomodoro;
        this.autoStart = autoStart;
        this.disableBreaks = disableBreaks;
    }

    public int getLongBreak() {
        return longBreak;
    }

    public int getSessionDuration() {
        return sessionDuration;
    }

    public int getShortBreak() {
        return shortBreak;
    }

    public int getShortBreakAfter() {
        return shortBreakAfter;
    }

    public boolean isAutoStart() {
        return autoStart;
    }

    public boolean isAutoStartBreak() {
        return autoStartBreak;
    }

    public boolean isAutoStartPomodoro() {
        return autoStartPomodoro;
    }

    public boolean isDisableBreaks() {
        return disableBreaks;
    }

    public void setDisableBreaks(boolean disableBreaks) {
        this.disableBreaks = disableBreaks;
    }
}
