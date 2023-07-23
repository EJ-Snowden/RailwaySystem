import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu extends Thread{
    private final Scanner scan;

    public Menu(Scanner scan){
        this.scan = scan;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("\nMENU");
            System.out.println("Choose what do you want to do");
            System.out.println("1 - Add new empty trainset(Just locomotive)");
            System.out.println("2 - Add new trainset(Locomotive with cars)");
            System.out.println("3 - Add new car");
            System.out.println("4 - Add new Station(With connections)");
            System.out.println("5 - Load or unload existing car");
            System.out.println("6 - Delete existing trainset");
            System.out.println("7 - Delete existing car");
            System.out.println("8 - Print how many % exact trainset competed");
            System.out.println("9 - Print all information about trainset(and it's cars)");
            try {
                int num = scan.nextInt();
                switch (num) {
                    case 1 -> {
                        TrainSet.addTrainset(1, false);
                        TrainSet.trainSetArrayList.get(TrainSet.trainSetArrayList.size() - 1).choosePath();
                        Moving.threadList.get(Moving.threadList.size() - 1).start();
                        System.out.println("Trainset was added with ID: " + TrainSet.trainSetArrayList.get
                                (TrainSet.trainSetArrayList.size() - 1).getTrainGetID());
                    }
                    case 2 -> {
                        TrainSet.addTrainset(1, true);
                        TrainSet.trainSetArrayList.get(TrainSet.trainSetArrayList.size() - 1).choosePath();
                        Moving.threadList.get(Moving.threadList.size() - 1).start();
                        System.out.println("Trainset was added with ID: " + TrainSet.trainSetArrayList.get
                                (TrainSet.trainSetArrayList.size() - 1).getTrainGetID());
                    }
                    case 3 -> {
                        System.out.println("To which trainset do you want to add car?");
                        for (TrainSet trainSet : TrainSet.trainSetArrayList) {
                            if (trainSet != null) {
                                System.out.println("Trainset " + trainSet.getTrainGetID());
                            }
                        }
                        int ind1 = scan.nextInt();
                        if (ind1 > TrainSet.trainSetArrayList.size() || ind1 < 1) {
                            System.err.println("No such trainset");
                            break;
                        }
                        System.out.println("Which type of car do you want to add?");
                        System.out.println("1 - PassengerCar");
                        System.out.println("2 - RestaurantCar");
                        System.out.println("3 - BaggageMailCar");
                        System.out.println("4 - PostOfficeCar");
                        System.out.println("5 - RefrigeratedCar");
                        System.out.println("6 - ToxicCar");
                        System.out.println("7 - ExplosivesCar");
                        System.out.println("8 - LiquidToxicCar");
                        System.out.println("9 - HeavyCar");
                        System.out.println("10 - GaseousCar");
                        System.out.println("11 - BasicCar");
                        System.out.println("12 - LiquidCar");
                        int ind2 = scan.nextInt();
                        int before = TrainSet.trainSetArrayList.get(ind1 - 1).getSize();
                        TrainSet.trainSetArrayList.get(ind1 - 1).addOneCar(ind2);
                        if (before != TrainSet.trainSetArrayList.get(ind1 - 1).getSize())
                            System.out.println("Your car was added");
                        else System.err.println("Your car wasn't added");
                    }
                    case 4 -> {
                        Station.addStation(1);
                        Connection.addConnectionForNewStation();
                        System.out.println("Your station was added with ID: " + Station.stations.get(Station.stations.size() - 1).id);
                        System.out.println("Station connected to other stations automatically");
                    }
                    case 5 -> {
                        System.out.println("From which trainset do you want to load(unload) car?");
                        for (TrainSet trainSet : TrainSet.trainSetArrayList) {
                            if (trainSet != null) {
                                System.out.println("Trainset " + trainSet.getTrainGetID());
                            }
                        }
                        int ind3 = scan.nextInt();
                        if (ind3 > TrainSet.trainSetArrayList.size() || ind3 < 1 || TrainSet.trainSetArrayList.get(ind3 - 1) == null) {
                            System.err.println("No such trainset");
                            break;
                        }
                        if (TrainSet.trainSetArrayList.get(ind3 - 1).getSize() > 1) {
                            System.out.println("Which car do you want to load(unload)?");
                            System.out.println(TrainSet.trainSetArrayList.get(ind3 - 1));
                            int ind4 = scan.nextInt();
                            if (TrainSet.trainSetArrayList.get(ind3 - 1).loadUnload(ind4))
                                System.out.println("Was done");
                        } else System.err.println("There aren't any cars");
                    }
                    case 6 -> {
                        System.out.println("Which trainset do you want to delete?");
                        for (TrainSet trainSet : TrainSet.trainSetArrayList) {
                            if (trainSet != null) {
                                System.out.println("Trainset " + trainSet.getTrainGetID());
                            }
                        }
                        int ind4 = scan.nextInt();
                        if (ind4 > TrainSet.trainSetArrayList.size() || ind4 < 1)
                            System.err.println("Such trainset is not existing");
                        else {
                            if (TrainSet.trainSetArrayList.get(ind4 - 1) != null) {
                                TrainSet.trainSetArrayList.set(ind4 - 1, null);
                                TrainSet.sortedTrainSetArrayList.addAll(TrainSet.trainSetArrayList);
                                SpeedChange.speedChanges.get(ind4 - 1).interrupt();
                                Moving.threadList.get(ind4 - 1).interrupt();
                                System.out.println("Deleted");
                            } else System.err.println("Such trainset is not existing");
                        }
                    }
                    case 7 -> {
                        System.out.println("From which trainset do you want to delete car?");
                        for (TrainSet trainSet : TrainSet.trainSetArrayList) {
                            if (trainSet != null) {
                                System.out.println("Trainset " + trainSet.getTrainGetID());
                            }
                        }
                        int ind5 = scan.nextInt();
                        if (ind5 > TrainSet.trainSetArrayList.size() || ind5 < 1 || TrainSet.trainSetArrayList.get(ind5 - 1) == null) {
                            System.err.println("Such trainset is not existing");
                        } else {
                            if (TrainSet.trainSetArrayList.get(ind5 - 1).getSize() > 1) {
                                System.out.println("Which car do you want to delete?");
                                System.out.println(TrainSet.trainSetArrayList.get(ind5 - 1));
                                int ind6 = scan.nextInt();
                                if (TrainSet.trainSetArrayList.get(ind5 - 1).nullifyCar(ind6))
                                    System.out.println("Deleted");
                                else System.err.println("No such car in trainset");
                            } else System.err.println("There aren't any cars");
                        }
                    }
                    case 8 -> {
                        System.out.println("About which trainset do you want to get information about the distance?");
                        for (TrainSet trainSet : TrainSet.trainSetArrayList) {
                            if (trainSet != null) {
                                System.out.println("Trainset " + trainSet.getTrainGetID());
                            }
                        }
                        int ind7 = scan.nextInt();
                        if (ind7 > TrainSet.trainSetArrayList.size() || ind7 < 1 || TrainSet.trainSetArrayList.get(ind7 - 1) == null) {
                            System.err.println("No such trainset");
                            break;
                        }
                        if (TrainSet.trainSetArrayList.get(ind7 - 1) != null) {
                            System.out.print("This trainset passed ");
                            double percentageCon = TrainSet.trainSetArrayList.get(ind7 - 1).getLoco().percentageConnection();
                            System.out.printf("%.2f", percentageCon);
                            System.out.println("% of this part of way");
                            System.out.print("This trainset passed ");
                            double percentageWay = TrainSet.trainSetArrayList.get(ind7 - 1).getLoco().percentagePath();
                            System.out.printf("%.2f", percentageWay);
                            System.out.println("% of whole way");
                        }
                    }
                    case 9 -> {
                        System.out.println("About which trainset do you want to get information?");
                        for (TrainSet trainSet : TrainSet.trainSetArrayList) {
                            if (trainSet != null) {
                                System.out.println("Trainset " + trainSet.getTrainGetID());
                            }
                        }
                        int ind8 = scan.nextInt();
                        if (ind8 > TrainSet.trainSetArrayList.size() || ind8 < 1) {
                            System.err.println("No such trainset");
                            break;
                        }
                        if (TrainSet.trainSetArrayList.get(ind8 - 1) != null) {
                            System.out.println(TrainSet.trainSetArrayList.get(ind8 - 1).getLoco());
                            for (int i = 1; i < TrainSet.trainSetArrayList.get(ind8 - 1).getSize(); i++) {
                                TrainSet trainSet = TrainSet.trainSetArrayList.get(ind8 - 1);
                                Cars car;
                                if (trainSet != null) {
                                    car = (Cars) trainSet.getTrainSet().get(i);
                                    if (car != null) System.out.println(car);
                                }
                            }
                            synchronized (TrainSet.trainSetArrayList.get(ind8 - 1).getLoco()) {
                                System.out.println("Path is: ");
                                if (TrainSet.trainSetArrayList.get(ind8 - 1).next != null
                                        && TrainSet.trainSetArrayList.get(ind8 - 1).getLoco().getPath().hasMoreStations())
                                    System.out.println(TrainSet.trainSetArrayList.get(ind8 - 1).current
                                            + " -> " + TrainSet.trainSetArrayList.get(ind8 - 1).next
                                            + " -> " + TrainSet.trainSetArrayList.get(ind8 - 1).getLoco().getPath());
                                else if (TrainSet.trainSetArrayList.get(ind8 - 1).next != null
                                        && !TrainSet.trainSetArrayList.get(ind8 - 1).getLoco().getPath().hasMoreStations())
                                    System.out.println(TrainSet.trainSetArrayList.get(ind8 - 1).current + " -> "
                                            + TrainSet.trainSetArrayList.get(ind8 - 1).next);
                                else System.out.println("Trainset has reached the end station: " + TrainSet.trainSetArrayList.get(ind8 - 1).current);
                            }
                        } else System.err.println("Such trainset is not existing");
                    }
                    default -> System.err.println("You've chosen wrong number");
                }
            }catch (InputMismatchException e){      //if user entered something except of number
                System.err.println("Enter a number");
                scan.nextLine();
            }
        }
    }
}
