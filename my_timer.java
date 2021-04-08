import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public abstract class my_timer extends AnimationTimer {

    long nanosBetweenPulses=1* 1000000000L;
    long lastPulseTimeStamp;

    @Override
    public void handle(long now) {
        long nanosSinceLastPulse = now - lastPulseTimeStamp;
        if(nanosSinceLastPulse > nanosBetweenPulses){
            lastPulseTimeStamp = now;
            run();
        }
    }

    abstract void run();

}