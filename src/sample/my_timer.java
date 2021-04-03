package sample;

public class my_timer{
    public void timeCheck(Int secs, Label needed_label){
        secs*=secs;
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