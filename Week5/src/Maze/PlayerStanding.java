package Maze;


public class PlayerStanding implements Runnable {
    int deathCount;
    long millisecondCounter;
    int hours, minutes, seconds;
    boolean Running;

    PlayerStanding () {
        deathCount = 0;
        millisecondCounter = 0;
        hours = 0;
        minutes = 0;
        seconds = 0;
        Running = false;
    }
    protected void playerDied () {
        deathCount ++;
    }
    protected String getDeathCount() {
        return "Deaths : " + Integer.toString(deathCount);
    }
    public void stopClock() {
        Running = false;
    }
    public void startClock () {
        Running = true;
    }
    protected String getCurrentTime () {

        String returnThis = null;
        if (hours != 0 ) {
            return Integer.toString(hours) + " : " + getTimeSegment(minutes) + " : " + Integer.toString(seconds);
        }
        return getTimeSegment(minutes) + " : " + Integer.toString(seconds);
    }
    private String getTimeSegment (int Segment) {
        if (Segment < 0) {
            return "0" + Integer.toString(Segment);
        }
        else {
            return Integer.toString(Segment);
        }
    }
    public void run() {
        while (true) {
            while (Running) {
                try {
                    wait(10);
                    millisecondCounter = millisecondCounter + 10;
                    if (millisecondCounter >= 1000) {
                        seconds++;
                        if (seconds >= 60) {
                            seconds = 0;
                            minutes ++;
                            if (minutes >= 60) {
                                minutes = 0;
                                hours ++;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
