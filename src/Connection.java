import Exceptions.ConnectionNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Connection{
    double length;
    Station start;
    Station end;
    int id;
    static int more;            //static fields to be able to add new connection from menu in future
    static int less;
    private static int counter = 1;
    static ArrayList<Connection> connections = new ArrayList<>();

    public Connection(double length, Station start, Station end){
        this.id = counter++;
        this.length = length;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString(){
        return  "\n" + id + " Start: Station " + start.id + " End: Station " + end.id + " --- length: " + length;
    }

    public static void addConnection() {
        double length;
        more = 100;                 //creating rounded sectors to find stations in some radius f.e.(0-100)(100-200)... not to have repeats of connections
        less = 0;
        do {
            for (int i = 0; i < Station.stations.size(); i++) {                     //looking  for length between all stations and if it is in out sector
                for (int j = i + 1; j < Station.stations.size(); j++) {             //add it to arraylist
                    length = Math.sqrt(Math.pow((Station.stations.get(i).x - Station.stations.get(j).x), 2) +
                            Math.pow((Station.stations.get(i).y - Station.stations.get(j).y), 2));
                    if (length <= more && length >= less) {
                        Connection.connections.add(new Connection(length, Station.stations.get(i), Station.stations.get(j)));
                    }
                }
            }
            more += 100;            //extending our sector of looking
            less += 100;
        }while (!isPathCompleted());        //checking if all stations are connected to one holistic system
    }

    public static boolean isPathCompleted() {
        LinkedList<Station> stationsList = new LinkedList<>();
        Set<Station> was = new HashSet<>();
        stationsList.add(Station.stations.get(0));                 //starting from 0th stations
        while (!stationsList.isEmpty()) {                          //repeating while all stations won't be excluded in queue logic
            Station current = stationsList.poll();
            was.add(current);                                       //adding stations without repeating, so using hashset
            for (Connection connection : Connection.connections) {              //looking for all connections from the last stations
                if (connection.start.equals(current) && !was.contains(connection.end))
                    stationsList.add(connection.end);
                else if (connection.end.equals(current) && !was.contains(connection.start))         //adding all those connections to stationList
                    stationsList.add(connection.start);                                             //to check it later
            }
        }
        return was.size() == Station.stations.size();                               //checking if all stations were added to holistic system
    }

    public static Connection getConnection(Station start, Station end){
        for (Connection connection : connections){
            if (start == connection.start && end == connection.end){
                return connection;
            } else if (end == connection.start && start == connection.end) {
                return connection;
            }
        }
        try {
            throw new ConnectionNotFoundException("Connections weren't found between " + start + " and " + end);        //to fulfill method
        } catch (ConnectionNotFoundException e) {                                               //cause all connections that are needed were created
            throw new RuntimeException(e);
        }
    }

    public static void addConnectionForNewStation() {                           //function to add connection for just 1 station, that was created from
        Station last = Station.stations.get(Station.stations.size() - 1);       //the menu
        for (int i = 0; i < Station.stations.size() - 1; i++) {
            double length = Math.sqrt(Math.pow((last.x - Station.stations.get(i).x), 2) +
                    Math.pow((last.y - Station.stations.get(i).y), 2));
            if (length <= more) {                                                 //creating all connections to the maximal size of connection to have
                Connection.connections.add(new Connection(length, last, Station.stations.get(i)));      //the same logic as in default function
            }
        }
    }
}