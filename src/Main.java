import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        TrainSet.addToList();
        Station.addStation(100);
        Connection.addConnection();
        TrainSet.addTrainset(25, true);
        for (int i = 0; i < TrainSet.trainSetArrayList.size(); i++) {
            TrainSet.trainSetArrayList.get(i).choosePath();
        }
        for (Thread thread : Moving.threadList){
            thread.start();
        }
        for (Thread thread : SpeedChange.speedChanges){
            thread.start();
        }

        WriterToFile threadWriter = new WriterToFile(scan);
        Menu menu = new Menu(scan);

        threadWriter.clearFile();
        threadWriter.start();

        menu.start();
    }
}