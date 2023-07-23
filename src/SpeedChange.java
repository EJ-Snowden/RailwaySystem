import Exceptions.RailroadHazardException;

import java.util.ArrayList;
import java.util.Random;

public class SpeedChange extends Thread{
    public static ArrayList<SpeedChange> speedChanges = new ArrayList<>();
    final TrainSet trainSet;
    static int index;
    double currentSpeed;
    final double changing = 0.03;
    Random random = new Random();
    int changesTime;        //field for checking if SpeedChange and Moving were synchronised correctly

    public SpeedChange(){
        this.trainSet = TrainSet.trainSetArrayList.get(index);
        index++;
        this.currentSpeed = 100;        //start speed = 100
    }

    @Override
    public void run(){
        while (!isInterrupted()){
            synchronized (this.trainSet) {      //synchronising pretty all method because it is change once in a second before moving the train
                if (this.changesTime == Moving.threadList.get(speedChanges.indexOf(this)).changesTime) {    //checking if some thread wasn't called
                                                                                                            // several times
                    int rand = random.nextInt(2);       //randomizing rising and falling od speed
                    if (rand == 0) {
                        try {
                            currentSpeed = currentSpeed + currentSpeed * changing;
                            if (currentSpeed >= 200) throw new RailroadHazardException("Speed is more than 200");
                            else if (currentSpeed < 5) currentSpeed += 10;  //if something in a long time will go wrong and speed will decrease to
                                                                            //such small number - increase it not to block whole system
                        } catch (RailroadHazardException e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        try {
                            currentSpeed = currentSpeed - currentSpeed * changing;
                            if (currentSpeed >= 200) throw new RailroadHazardException("Speed is more than 200");
                            else if (currentSpeed < 5) currentSpeed += 10;
                        } catch (RailroadHazardException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                    trainSet.getLoco().speed = currentSpeed;
                    changesTime++;
                }
                this.trainSet.notify();     //notifying moving thread that speed was changed, and it can move now
                try {
                    this.trainSet.wait();           //waiting while trainset is moving
                } catch (InterruptedException ignored) {
                    //if we are deleting trainset from menu - we are getting message to console that it is now null,
                    // user doesn't need this information
                }
            }
        }
    }
}
