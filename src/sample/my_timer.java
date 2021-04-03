package sample;

import javafx.scene.control.Label;

public class my_timer{
    public static void timeCheck(int secs, Label needed_label){
        secs*=1000;
        long elapsedTime=0;
        long startTime = System.currentTimeMillis();
        System.out.println(elapsedTime);
        while (elapsedTime<secs){
            elapsedTime = System.currentTimeMillis() - startTime;
            if(needed_label!=null){
                needed_label.setText(String.valueOf(elapsedTime/1000));
            }
            
        }
    }
}