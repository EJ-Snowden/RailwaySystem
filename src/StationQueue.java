import java.util.LinkedList;
import java.util.Queue;

public class StationQueue {
    private final Queue<Station> path;
    StringBuilder sb = new StringBuilder();

    public StationQueue() {
        path = new LinkedList<>();
    }

    public void addStation(Station station) {
        path.add(station);
    }

    public Station getNextStation() {
        if (hasMoreStations()) return path.remove();
        else return null;
    }

    public boolean hasMoreStations() {
        return !path.isEmpty();
    }

    public String toString() {
        sb.delete(0, sb.length());
        int i = 0;
        for (Station station : this.path) {
            sb.append(station.toString());
            i++;
            if (i != path.size()) sb.append(" -> ");
        }
        return sb.toString();
    }

    public boolean wasntInList(Station find){
        for (Station station : path) {
            if (station.equals(find))
                return false;
        }
        return true;
    }
}
