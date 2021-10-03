package Maze;


public class PlayerStanding extends Thread {
    int deathCount;
    long millisecondCounter;
    int hours, minutes, seconds;
    boolean Running;
    PlayerStanding  (int deathCount, int hours, int minutes, int seconds ) {
        this.deathCount = deathCount;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
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
                //System.out.println("Time has started");
                try {

                    if (millisecondCounter >= 1000) {
                        millisecondCounter = 0;
                        seconds++;
                        Maze.statusDisplay.setText (
                                getDeathCount() + " Time: " + getCurrentTime()
                        );
                        if (seconds >= 60) {
                            seconds = 0;
                            minutes ++;
                            if (minutes >= 60) {
                                minutes = 0;
                                hours ++;
                            }
                        }
                    }
                    sleep(10);
                    millisecondCounter = millisecondCounter + 10;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
