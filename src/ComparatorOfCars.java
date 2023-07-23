import java.util.Comparator;

public class ComparatorOfCars implements Comparator {
    @Override
    public int compare(Object car1, Object car2) {
        if (car1 != null && car2 != null){
            if (car1 instanceof Cars && car2 instanceof Cars){
                return Integer.compare(((Cars) car1).getGrossWeight(), ((Cars) car2).getGrossWeight());
            }
        }
        return 0;
    }
}
