import Exceptions.MaxCarInSetException;
import Exceptions.MaxConnectedToElectricityException;
import Exceptions.OutOfWeightException;
import Exceptions.RestrictionOnAddingException;
import Interfaces.Electrical;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class TrainSet {
    private static int counter = 1;
    private final int id;
    static ArrayList<Class<?>> cargoListClasses = new ArrayList<>();    //index 0 in class arraylist
    static ArrayList<Class<?>> passengerListClasses = new ArrayList<>();    //index 1 in class arraylist
    ArrayList<Object> trainset;             //arraylist of objects to have the ability to add locomotive and cars in one trainset
    ArrayList<Object> sortedTrainset = new ArrayList<>();       //trainset where cars are copied and sorted to output to txt in ascending order
    int conElectricity;             //field for every trainset object to check how many cars are connected to electricity
    private int weightOfTrainSet;       //field for every trainset object to check the sum of weights of cars
    static ArrayList<TrainSet> trainSetArrayList = new ArrayList<>();       //arraylist of all trainsets to be able to iterate through them
    static ArrayList<TrainSet> sortedTrainSetArrayList = new ArrayList<>();     //trainset where cars are copied and sorted to output to txt
                                                                                //in ascending order
    Random randomNum = new Random();
    static final int maxTrainSetWeight = 500;
    static final int maxNumberOfCars = 12;
    static final int maxNumberConElectricity = 6;
    int sizeOfTrainset;         //walk-around because I haven't got an ability to check the size of trainset if we are deleting(nulling car from
                                //the trainset, cause when we are removing an object - indexes are moved, and it is hard to work with trainsets later
    public Station current;         //public to be able to output in to txt file, partly connected with synchronise block(connected to path)
    public Station next;

    public TrainSet(boolean cars){      //all process of creating is done in constructor be able to call lots of iterations easily
        this.id = counter;
        counter++;
        trainset = new ArrayList<>();
        trainset.add(new Locomotive());             //adding locomotive by default
        int chooseSet = randomNum.nextInt(2);//choosing will trainset be cargo or passenger(feature,
                                                   //connected with feature about restriction on adding cars)
        int numberOfCars;
        if (cars){      //checking if adding car to trainset needed, or it is needed to create empty trainset
            numberOfCars = (int)(Math.random() * 5) + 6;
        }else numberOfCars = 0;

        for (int i = 0; i < numberOfCars; i++){
            Cars lastCar = null;

            if (chooseSet == 0){            //if was chosen cargo trainset
                try {               //try block if in process we will throw custom exception, or necessary exception connected with instances to classes
                    trainset.add(cargoListClasses.get(randomNum.nextInt(cargoListClasses.size())).getDeclaredConstructor().newInstance());
                                //returning the constructor object and creating instance of randomly chosen class(throws NoSuchMethodException and
                                //InstantiationException | IllegalAccessException | InvocationTargetException
                    lastCar = (Cars)trainset.get(trainset.size() - 1);
                    sizeOfTrainset++;
                    if (maxNumberOfCars < sizeOfTrainset) throw new MaxCarInSetException("Trainset "
                            + id + " reached the maximum number of cars");
                    else {
                        weightOfTrainSet += lastCar.getGrossWeight();
                        if (weightOfTrainSet > maxTrainSetWeight)
                            throw new OutOfWeightException("The weight of trainset "
                                    + id + " was exceeded by " + (weightOfTrainSet - maxTrainSetWeight));
                        else if (lastCar instanceof Electrical) conElectricity++;
                        if (conElectricity > maxNumberConElectricity) throw new MaxConnectedToElectricityException("Trainset "
                                + id + " reached the maximum number of cars connected to electricity");
                    }
                }catch (MaxCarInSetException e){
                    System.err.println(e.getMessage());
                    trainset.remove(trainset.size() - 1);
                    Cars.minusCounter();
                    sizeOfTrainset--;
                }catch (OutOfWeightException e){
                    System.err.println(e.getMessage());
                    weightOfTrainSet -= lastCar.getGrossWeight();
                    trainset.remove(trainset.size() - 1);
                    Cars.minusCounter();
                    sizeOfTrainset--;
                }catch (MaxConnectedToElectricityException e){
                    System.err.println(e.getMessage());
                    trainset.remove(trainset.size() - 1);
                    Cars.minusCounter();
                    conElectricity--;
                    sizeOfTrainset--;
                }catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                        NoSuchMethodException e)
                        //InstantiationException-thrown when can't create instance on created class(abstract, void, primitive)
                        //IllegalAccessException-thrown when can't get access to some fields or methods
                        //InvocationTargetException-thrown to understand was exception occurred due to failure in calling or inside called method
                        //NoSuchMethodException-thrown when method can't be found
                {
                    throw new RuntimeException(e);
                }
            }else {
                try {
                    //repeating previous process but if passenger type of trainset was chosen
                    trainset.add(passengerListClasses.get(randomNum.nextInt(passengerListClasses.size())).getDeclaredConstructor().newInstance());
                    lastCar = (Cars)trainset.get(trainset.size() - 1);
                    sizeOfTrainset++;
                    if (maxNumberOfCars < (trainset.size() - 1)) throw new MaxCarInSetException("Trainset "
                            + id + " reached the maximum number of cars");
                    else {
                        weightOfTrainSet += lastCar.getGrossWeight();
                        if (weightOfTrainSet > maxTrainSetWeight)
                            throw new OutOfWeightException("The weight of trainset "
                                    + id + " was exceeded by " + (weightOfTrainSet - maxTrainSetWeight));
                        else if (lastCar instanceof Electrical) conElectricity++;
                        if (conElectricity > maxNumberConElectricity) throw new MaxConnectedToElectricityException("Trainset "
                                    + id + " reached the maximum number of cars connected to electricity");
                    }
                }catch (MaxCarInSetException e){
                    System.err.println(e.getMessage());
                    trainset.remove(trainset.size() - 1);
                    Cars.minusCounter();
                    sizeOfTrainset--;
                }catch (OutOfWeightException e){
                    System.err.println(e.getMessage());
                    weightOfTrainSet -= lastCar.getGrossWeight();
                    trainset.remove(trainset.size() - 1);
                    Cars.minusCounter();
                    sizeOfTrainset--;
                }catch (MaxConnectedToElectricityException e){
                    System.err.println(e.getMessage());
                    trainset.remove(trainset.size() - 1);
                    Cars.minusCounter();
                    conElectricity--;
                    sizeOfTrainset--;
                }catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                        NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        sortedTrainSetArrayList.addAll(trainSetArrayList);      //adding all cars to sorted trainset arraylist to sort it in future
    }

    public void addOneCar(int index){
        Cars lastCar = null;
        try {
            switch (index) {        //adding one exact car from menu to particular trainset
                case 1 -> this.trainset.add(new PassengerCar());
                case 2 -> this.trainset.add(new RestaurantCar());
                case 3 -> this.trainset.add(new BaggageMailCar());
                case 4 -> this.trainset.add(new PostOfficeCar());
                case 5 -> this.trainset.add(new RefrigeratedCar());
                case 6 -> this.trainset.add(new ToxicCar());
                case 7 -> this.trainset.add(new ExplosivesCar());
                case 8 -> this.trainset.add(new LiquidToxicCar());
                case 9 -> this.trainset.add(new HeavyCar());
                case 10 -> this.trainset.add(new GaseousCar());
                case 11 -> this.trainset.add(new BasicCar());
                case 12 -> this.trainset.add(new LiquidCar());
            }
        lastCar = (Cars)trainset.get(trainset.size() - 1);
            sizeOfTrainset++;
            if (lastCar.restrictionOnAdding(trainset)) throw new RestrictionOnAddingException("This car can't be added to trainset "
                    + id + " (Reason: non-passenger car)");
            else if (maxNumberOfCars < sizeOfTrainset) throw new MaxCarInSetException("Trainset "
                    + id + " reached the maximum number of cars");
            else {
                weightOfTrainSet += lastCar.getGrossWeight();
                if (weightOfTrainSet > maxTrainSetWeight) throw new OutOfWeightException("The weight of trainset "
                            + id + " was exceeded by " + (weightOfTrainSet - maxTrainSetWeight));
                else if (lastCar instanceof Electrical) conElectricity++;
                if (conElectricity > maxNumberConElectricity) throw new MaxConnectedToElectricityException("Trainset "
                        + id + " reached the maximum number of cars connected to electricity");
            }
        }catch (RestrictionOnAddingException | MaxCarInSetException e){
            System.err.println(e.getMessage());
            trainset.remove(trainset.size() - 1);
            Cars.minusCounter();
            sizeOfTrainset--;
        } catch (OutOfWeightException e){
            System.err.println(e.getMessage());
            weightOfTrainSet -= lastCar.getGrossWeight();
            trainset.remove(trainset.size() - 1);
            Cars.minusCounter();
            sizeOfTrainset--;
        }catch (MaxConnectedToElectricityException e){
            System.err.println(e.getMessage());
            trainset.remove(trainset.size() - 1);
            Cars.minusCounter();
            conElectricity--;
            sizeOfTrainset--;
        }
    }

    public static void addToList(){                         //creating arraylist of classes to divide trainsets to cargo and passenger
        passengerListClasses.add(PassengerCar.class);
        passengerListClasses.add(RestaurantCar.class);
        passengerListClasses.add(BaggageMailCar.class);
        passengerListClasses.add(PostOfficeCar.class);
        cargoListClasses.add(RefrigeratedCar.class);
        cargoListClasses.add(ToxicCar.class);
        cargoListClasses.add(ExplosivesCar.class);
        cargoListClasses.add(LiquidToxicCar.class);
        cargoListClasses.add(HeavyCar.class);
        cargoListClasses.add(GaseousCar.class);
        cargoListClasses.add(BasicCar.class);
        cargoListClasses.add(LiquidCar.class);
    }

    static void addTrainset(int iter, boolean cars){
        for (int i = 0; i < iter; i++){
            trainSetArrayList.add(new TrainSet(cars));      //adding any quantity of trainsets to arraylist of trainset with parameter are cars needed
            Moving.threadList.add(new Moving());            //adding new thread of moving logic to thread-list connected to new trainset object
            SpeedChange.speedChanges.add(new SpeedChange());//adding new thread(changing loco's speed) to list connected to new trainset object

            sortedTrainSetArrayList.clear();        //re-adding all trainsets to sorted arraylist(because we can add in future new trainset from menu)
            sortedTrainSetArrayList.addAll(trainSetArrayList);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID of trainset ").append(id).append("\n");   //adding id of this trainset
        sb.append(getLoco().toString());                        //adding information about loco
        for (int i = 1; i < sortedTrainset.size(); i++){
            if (sortedTrainset.get(i) != null){     //checking for non-null object due to walk-around connected to removing cars from trainset from menu
                sb.append("\n");
                sb.append(sortedTrainset.get(i).toString());    //adding information about each car
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    public void choosePath(){
        Locomotive loco = (Locomotive)trainset.get(0);
        loco.generatePath();
    }

    public Locomotive getLoco(){
        return (Locomotive)trainset.get(0);
    }

    public void sortTrainset(){
        sortedTrainset.clear();
        sortedTrainset.addAll(trainset);                    //sorting this trainset(but which is copied and then sorted, just for outputting)
        sortedTrainset.sort(new ComparatorOfCars());        //sorting using custom overridden comparator interface
    }

    public boolean loadUnload(int carId){
        Cars car = null;
        for (int i = 1; i < trainset.size(); i++){      //looking for necessary car by provided id
            car = (Cars)trainset.get(i);
            if (car != null) {                          //checking for non-null as walk-around about removing cars
                if (car.getID() == carId) break;
            }
        }
        if (car.getID() != carId){
            System.err.println("No such ID");
            return false;
        }
        car.loaded = !car.loaded;
        car.changeWeight();             //change weight of car dependent on loading or unloading car
        return true;
    }

    public boolean nullifyCar(int carId){       //walk-around about removing car from trainset(haven't got an ability to remove, because I'm working
                                                //with indexes of cars, and they are moving if object is removed from arraylist
        Cars car = null;
        for (int i = 1; i < trainset.size(); i++){
                car = (Cars)trainset.get(i);
            if (car != null){                       //checking if it wasn't deleted earlier
                if (car.getID() == carId) break;
            }
        }
        if (car.getID() != carId){
            System.err.println("No such ID");
            return false;
        }
        if (car instanceof Electrical) conElectricity--;        //checking and removing necessary info about car in trainset to be able to
                                                                //add new cars in future without exceptions
        sizeOfTrainset--;
        weightOfTrainSet -= car.getGrossWeight();
        trainset.set(trainset.indexOf(car), null);
        return true;
    }

    public int getTrainGetID(){
        return id;
    }

    public int getSize(){
        return trainset.size();
    }

    public ArrayList<Object> getTrainSet(){
        return trainset;
    }
}