import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WriterToFile extends Thread{
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Scanner scan;

    public WriterToFile(Scanner scan){
        this.scan = scan;
    }

    public void clearFile(){
        try {
            FileWriter writer = new FileWriter("output.txt", false);    //clearing our txt
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            FileWriter fileWriter = new FileWriter("output.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            while (!isInterrupted()) {
                    LocalTime currentTime = LocalTime.now();

                    if (TrainSet.sortedTrainSetArrayList.size() != TrainSet.trainSetArrayList.size()){
                        TrainSet.sortedTrainSetArrayList.clear();
                        TrainSet.sortedTrainSetArrayList.addAll(TrainSet.trainSetArrayList);
                    }
                    TrainSet.sortedTrainSetArrayList.sort(new ComparatorOfTrainsets());

                    bufferedWriter.write("Current time: " + currentTime.format(formatter));
                    bufferedWriter.newLine();
                    for (TrainSet t : TrainSet.sortedTrainSetArrayList) {
                        if (t != null) {
                            t.sortTrainset();
                            bufferedWriter.write(t.toString());
                            synchronized (t.getLoco()) {    //synchronising writing to txt, not to edit it by moving during writing
                                if (t.next != null && t.getLoco().getPath().hasMoreStations()) bufferedWriter.write("Path is: "
                                        + t.current + " -> " + t.next + " -> " + t.getLoco().getPath());
                                else if (t.next != null && !t.getLoco().getPath().hasMoreStations()) bufferedWriter.write("Path is: "
                                        + t.current + " -> " + t.next);
                                else bufferedWriter.write("Path is: ended. Current station: " + t.current);
                            }
                            bufferedWriter.newLine();
                            bufferedWriter.write("Length of whole path is: " + t.getLoco().getPathLength());
                            bufferedWriter.newLine();
                            bufferedWriter.newLine();
                            bufferedWriter.flush();     //print to txt all information that is currently in buffer
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
