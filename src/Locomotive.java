import Interfaces.Electrical;

import java.util.Random;

public class Locomotive implements Electrical {

    public double speed;
    double passed;      //length that need to be passed on connection
    double globalPassed;        //length that need to be passed on whole path
    double lengthPassed;    //length that was passed
    private static int counter = 1;
    private final int id;
    private final Station homeStation;      //station where trainset was created
    public Station startStation;    //start station of path
    public Station endStation;
    private Station currentStation;
    StationQueue path;
    double lengthOfConnection;
    Random rand = new Random();
    private double pathLength;

    public Locomotive(){
        this.id = counter;
        counter++;
        this.homeStation = Station.stations.get(rand.nextInt(Station.stations.size()));
    }

    public void generatePath(){
        globalPassed = 0;       //setting all lengths to 0s
        lengthPassed = 0;
        int error = 0;
        path = new StationQueue();      //adding new path
        Station temp = null;
        Station tempStart = null;
        pathLength = 0;
        if (startStation == null){
            startStation = homeStation;
            while (endStation == null || endStation == startStation){                           //choosing new end station(avoiding end == start)
                endStation = Station.stations.get(rand.nextInt(Station.stations.size()));
            }
        }else{
            tempStart = startStation;
            startStation = endStation;
            endStation = tempStart;
        }
        if (currentStation == null) currentStation = startStation;
        path.addStation(currentStation);

        while (currentStation != endStation) {      //while we didn't find the whole path
            double min = Double.MAX_VALUE;
            for (Connection connection : Connection.connections) {      //looking for the closest station to end from connection that is connecting
                                                                        //current station and all others
                if (connection.start.equals(currentStation)){               //station could be assigned to 2 field, so checking both
                    double length1 = Math.sqrt(Math.pow((connection.end.x - endStation.x), 2) +
                            Math.pow((connection.end.y - endStation.y), 2));
                    if (path.wasntInList(connection.end) && length1 <= min) {
                        min = length1;
                        temp = connection.end;      //if it was the closest one, setting this in temporary
                    }
                }else if (connection.end.equals(currentStation)) {
                    double length2 = Math.sqrt(Math.pow((connection.start.x - endStation.x), 2) +
                            Math.pow((connection.start.y - endStation.y), 2));
                    if (path.wasntInList(connection.start) && length2 <= min) {
                        min = length2;
                        temp = connection.start;
                    }
                }
                if (min == 0) break;        //if we found that current station have connection with end station - break
            }
            if (path.wasntInList(temp)) {       //to avoid infinite paths checking if that station wasn't added yet
                path.addStation(temp);
                pathLength += (Math.sqrt(Math.pow((currentStation.x - temp.x), 2) +
                        Math.pow((currentStation.y - temp.y), 2)));
                currentStation = temp;
            }else{
                error++;        //walk-around in algorithm, I didn't know to fix it, because in 99% of situations it's generating correctly
                                //if something went wrong - we are reGenerating new path for this trainset
                if (error >= 2) {
                    generatePath();
                }
            }
        }
    }

    @Override
    public String toString(){
        return "Locomotive " + id;
    }

    public StationQueue getPath() {
        return path;
    }

    public double getPathLength(){
        return pathLength;
    }
    public boolean move(Station current, Station next){
        double time = 1;
        lengthOfConnection = Math.sqrt(Math.pow((current.x - next.x), 2) +
                Math.pow((current.y - next.y), 2));
        passed += speed * time;         //moving trainset once a second based on the current speed of trainset
        globalPassed += speed * time;
        if (lengthOfConnection <= passed){
            lengthPassed += lengthOfConnection;
            globalPassed = lengthPassed;
            passed = 0;
            return false;
        }
        return true;
    }

    public double percentageConnection(){
        double percentage = (this.passed / this.lengthOfConnection) * 100;
        if (percentage <= 100) return percentage;
        else return 100;
    }

    public double percentagePath(){
        double percentage = (this.globalPassed / this.pathLength) * 100;
        if (percentage <= 100) return percentage;
        else return 100;
    }
}