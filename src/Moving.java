import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Moving extends Thread{
    Station current;
    Station next;
    final TrainSet trainSet;
    static int index;
    public static ArrayList<Moving> threadList = new ArrayList<>();
    static Set<Connection> occupiedList = new HashSet<>();          //creating a set of connections to check if someone is using it rn
    Connection currentConnection;
    int changesTime;        //field for checking if SpeedChange and Moving were synchronised correctly

    public Moving(){
        this.trainSet = TrainSet.trainSetArrayList.get(index);
        index++;
    }

    @Override
    public void run(){
        boolean stillMove = true;

        while (!isInterrupted()) {
            synchronized (trainSet.getLoco()) {                 //synchronisation with writer thread, not to edit it during writing
                current = trainSet.getLoco().path.getNextStation();
                trainSet.current = current;
                next = trainSet.getLoco().path.getNextStation();
                trainSet.next = next;
            }
            do {
                this.currentConnection = Connection.getConnection(current, next);
                synchronized (currentConnection) {              //using a monitor of object current connection to prevent using it by several trains
                    if (occupiedList.contains(currentConnection)) {     //checking if someone is using it
                        try {
                            currentConnection.wait();           //waiting till the train will notify
                        } catch (InterruptedException e) {
                            System.err.println(e.getMessage());
                        }
                    } else{
                        occupiedList.add(currentConnection);    //if it isn't occupied - occupy it
                    }
                    while (stillMove) {                 //moving through 1 connection
                        synchronized (this.trainSet) {      //synchronising with SpeedChange thread using trainset monitor
                            this.trainSet.notify();         //notifying SpeedChange thread to change speed
                            try {
                                this.trainSet.wait();       //waiting until the process of changing will finish
                            } catch (InterruptedException e) {
                                System.err.println(e.getMessage());
                            }
                            stillMove = trainSet.getLoco().move(current, next);     //if trainset didn't reach the end of connection - continue to move
                            try {
                                Thread.sleep(1000);         //moving every 1 second
                            } catch (InterruptedException ignore){
                                //if we are deleting trainset from menu - we are getting message to console that sleeping is interrupted,
                                // user doesn't need this information
                            }
                            changesTime++;
                        }
                    }
                    try {
                        if (occupiedList.contains(currentConnection)) {     //giving an ability for other trainsets to move through this connection
                            occupiedList.remove(currentConnection);
                        }
                        Thread.sleep(2000);
                        stillMove = true;
                    } catch (InterruptedException ignore) {
                        //if we are deleting trainset from menu - we are getting message to console that sleeping is interrupted,
                        // user doesn't need this information
                    }
                    currentConnection.notify();         //now notifying other trainsets that were synchronised by monitor(currentConnection)
                    current = next;
                    trainSet.current = current;
                    synchronized (trainSet.getLoco()) {         //again synchronising with writer thread, not to edit it during writing
                        next = trainSet.getLoco().path.getNextStation();
                        trainSet.next = next;
                    }
                }
            }while (next != null);      //while trainset won't reach the end of path
            try {
                Thread.sleep(27000);        //sleeping for 27 seconds, cause before it was sleeping for 1 + 2 seconds
            } catch (InterruptedException ignore) {
                //if we are deleting trainset from menu - we are getting message to console that sleeping is interrupted,
                // user doesn't need this information
            }
            synchronized (trainSet.getLoco()) {     //again synchronising with writer thread, not to edit it during writing
                trainSet.choosePath();
            }
        }
    }
}