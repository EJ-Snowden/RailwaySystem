import java.util.Comparator;

public class ComparatorOfTrainsets implements Comparator<TrainSet> {
    @Override
    public int compare(TrainSet trainSet1, TrainSet trainSet2) {
        if (trainSet1 != null && trainSet2 != null){
            return Double.compare(trainSet2.getLoco().getPathLength(), trainSet1.getLoco().getPathLength());
        }
        return 0;
    }
}
