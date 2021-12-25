import dataAccess.CarDataAccess;
import dataAccess.DriverDataAccess;
import dataAccess.PassengerDataAccess;
import dataAccess.TripDataAccess;
import enumeration.*;
import model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Mahsa Alikhani m-58
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static DriverDataAccess driverDao;
    private static PassengerDataAccess passengerDao;
    private static TripDataAccess tripDao;
    private static CarDataAccess carDao;
    public static void main(String[] args) {
        driverDao = new DriverDataAccess();
        passengerDao = new PassengerDataAccess();
        tripDao = new TripDataAccess();
        carDao = new CarDataAccess();
        while (true) {
            MainMenu.showMainMenu();
            String choice = getChoiceNumber();

            int choiceNumber = Integer.parseInt(choice);

            switch (choiceNumber) {
                case 1:
                    addGroupOfDriversByAdmin();
                    break;
                case 2:
                    addGroupOfPassengersByAdmin();
                    break;
                case 3:
                    DriverSignUpOrLogin();
                    break;
                case 4:
                    passengerSignUpOrLogin();
                    break;
                case 5:
                    showOngoingTravels();
                    break;
                case 6:
                    showListOfDrivers();
                    break;
                case 7:
                    showListOfPassengers();
                    break;
                default:
                    System.out.println("Invalid input!");

            }
        }
    }

    private static void showOngoingTravels() {
        List<Trip> ongoingTrips =  tripDao.getOngoingTravels();
        for (Trip item:ongoingTrips) {
            System.out.println(item);
        }
    }

    private static void showListOfDrivers() {
        List<Driver> drivers = driverDao.getListOfDrivers();
        for (Driver item : drivers) {
            System.out.println(item.toString());
        }
    }

    private static void showListOfPassengers() {
        List<Passenger> passengers = passengerDao.getListOfPassengers();
        for (Passenger item : passengers) {
            System.out.println(item.toString());
        }
    }

    private static void addGroupOfDriversByAdmin() {
        String numberOfDrivers;
        do {
            System.out.println("Enter number of drivers");
            numberOfDrivers = scanner.next();
        } while (!ValidationUtil.isNumeric(numberOfDrivers));
        int driverNumbers = Integer.parseInt(numberOfDrivers);
        List<Car> cars = addGroupOfCarByAdmin(driverNumbers);
        System.out.println("Enter drivers information");
        List<Driver> drivers = new ArrayList<Driver>();
        for (int i = 0; i < driverNumbers; i++) {
            String driverName = getNameFromInput();
            String driverFamily = getFamilyFromInput();
            String username = getUsernameFromInput();
            String phoneNumber = getPhoneNumberFromInput();
            long nationalCode = getNationalCodeFromInput();
            Date birthDate = getBirthDateFromInput();
            String plaque = getCarPlaqueFromInput();
            Car car = cars.get(i);
            Driver driver = new Driver();
            driver.setName(driverName);
            driver.setFamily(driverFamily);
            driver.setUsername(username);
            driver.setPhoneNumber(phoneNumber);
            driver.setNationalCode(nationalCode);
            driver.setBirthDate(birthDate);
            driver.setPlaque(plaque);
            driver.setCar(car);
            drivers.add(driver);
        }
        if (drivers.size() == driverNumbers) {
            driverDao.saveGroupOfDrivers(drivers);
            System.out.println("New drivers saved successfully.");
        }
    }

    public static List<Car> addGroupOfCarByAdmin(int number)  {
        System.out.println("Enter cars information");
        List<Car> cars = new ArrayList<Car>();
        for (int i = 0; i < number; i++) {
            String model = getCarModelFromInput();
            String carColor = getCarColorFromInput();
            Car car = new Car();
            car.setModel(model);
            car.setCarColor(carColor);
            cars.add(car);
        }
        if (cars.size() == number) {
            carDao.saveGroupOfCar(cars);
            System.out.println("Cars are saved successfully.");
            return cars;
        }
        return null;
    }

    private static void addGroupOfPassengersByAdmin() {
        String numberOfPassengers;
        do {
            System.out.println("Enter number of passengers:");
            numberOfPassengers = scanner.next();
        } while (!ValidationUtil.isNumeric(numberOfPassengers));
        int passengerNumbers = Integer.parseInt(numberOfPassengers);
        List<Passenger> passengers = new ArrayList<Passenger>();
        for (int i = 0; i < passengerNumbers; i++) {
            String passengerName = getNameFromInput();
            String passengerFamily = getFamilyFromInput();
            String username = getUsernameFromInput();
            String phoneNumber = getPhoneNumberFromInput();
            long nationalCode = getNationalCodeFromInput();
            Date birthDate = getBirthDateFromInput();
            Passenger passenger = new Passenger(passengerName, passengerFamily, username, phoneNumber, nationalCode, birthDate, TripStatus.STOP, 0);
            passengers.add(passenger);
        }
        if (passengers.size() == passengerNumbers) {
            passengerDao.saveGroupOfPassengers(passengers);
            System.out.println("New passengers saved successfully.");
        }
    }

    private static void DriverSignUpOrLogin() {
        System.out.println("Username:");
        String username = getUsernameFromInput();
        Driver foundDriver = driverDao.findDriverByUsername(username);
        if (foundDriver != null) {
            try {
                if(foundDriver.getStatus() == TripStatus.WAIT){
                        Double[] point = getDriverLocation();
                        foundDriver.setCurrentLocationLat(point[0]);
                        foundDriver.setCurrentLocationLong(point[1]);
                        driverDao.UpdateDriverLocation(foundDriver);
                    int choiceNumber;
                    do{
                        System.out.println("You are waiting for a trip request.");
                        System.out.println("1. Exit");
                        String choice = getChoiceNumber();
                        choiceNumber = Integer.parseInt(choice);
                    }while (choiceNumber != 1);
                }else if(foundDriver.getStatus() == TripStatus.ONGOING){
                    System.out.println(foundDriver);
                    int choiceNumber;
                    do{
                        DriverLoginMenu.showDriverLoginMenu();
                        String choice = getChoiceNumber();
                        choiceNumber = Integer.parseInt(choice);
                        switch (choiceNumber){
                            case 1:
                                confirmCashReceiptByDriver(foundDriver);
                                break;
                            case 2:
                                TravelFinishByDriver(foundDriver);
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid number!");
                        }
                    }while (choiceNumber != 3);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            int choiceNumber;
            do {
                SignupMenu.showSignupMenu();
                String choice = getChoiceNumber();
                choiceNumber = Integer.parseInt(choice);
                switch (choiceNumber) {
                    case 1:
                        driverRegister();
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Invalid number!");
                }
            } while (choiceNumber != 2);
        }
    }

    private static void confirmCashReceiptByDriver(Driver driver) {
        Trip foundTrip = tripDao.findTripByDriver(driver);
        if(foundTrip.getPayStatus() == PayStatus.CASH){
            foundTrip.setPayStatus(PayStatus.PAYED);
            tripDao.updateTripPayStatusAfterPaying(foundTrip);
            System.out.println("Confirmed");
        }
    }

    private static void TravelFinishByDriver(Driver driver) {
        Trip foundTrip = tripDao.findTripByDriver(driver);
        if(foundTrip.getPayStatus() == PayStatus.PAYED){
            double destinationLat = foundTrip.getDestinationLat();
            double destinationLong = foundTrip.getDestinationLong();
            driver.setCurrentLocationLat(destinationLat);
            driver.setCurrentLocationLong(destinationLong);
            driverDao.updateDriverLocation(driver);
            driver.setStatus(TripStatus.WAIT);
            driverDao.updateDriverStatusToWaitByDriver(driver);
            Passenger passenger = tripDao.findPassengerByDriverAndTripId(driver, foundTrip.getId());
            passenger.setStatus(TripStatus.STOP);
            passengerDao.updatePassengerStatusToSTOP(passenger);
            System.out.println("Travel is finished and your location is updated.");
        }
    }

    private static Car addNewCar() {
        System.out.println("Enter car information");
        String model = getCarModelFromInput();
        String carColor = getCarColorFromInput();
        Car car = new Car();
        car.setModel(model);
        car.setCarColor(carColor);
        carDao.saveNewCar(car);
        System.out.println("New car is saved successfully.");
        return car;
    }

    private static String getCarColorFromInput() {
        String carColor;
        do {
            System.out.println("Enter color of car:");
            carColor = scanner.next();
        } while (!ValidationUtil.isLetter(carColor));
        return carColor;
    }

    private static String getCarModelFromInput() {
        String model;
        do {
            System.out.println("Enter model of car:");
            model = scanner.next();
        } while (!ValidationUtil.isAlphabetic(model));
        return model;
    }

    private static void driverRegister() {
        Car car = addNewCar();
        System.out.println("Enter your information");
        String username;
        String name = getNameFromInput();
        String family = getFamilyFromInput();
        do {
            username = getUsernameFromInput();
        } while (driverDao.findDriverByUsername(username) != null);
        String phoneNumber = getPhoneNumberFromInput();
        long nationalCode = getNationalCodeFromInput();
        Date birthDate = getBirthDateFromInput();
        String plaque = getCarPlaqueFromInput();
        Driver driver = new Driver();
        driver.setName(name);
        driver.setFamily(family);
        driver.setUsername(username);
        driver.setPhoneNumber(phoneNumber);
        driver.setNationalCode(nationalCode);
        driver.setBirthDate(birthDate);
        driver.setStatus(TripStatus.WAIT);
        driver.setPlaque(plaque);
        driver.setCar(car);
        driverDao.saveNewDriver(driver);
        System.out.println("Your information was successfully registered.");
    }

    private static void passengerSignUpOrLogin() {
        System.out.println("Username:");
        String username = getUsernameFromInput();
        Passenger foundPassenger = passengerDao.findPassengerByUsername(username);
        if (foundPassenger != null) {
            System.out.println(foundPassenger);
            int choiceNumber;
            do {
                PassengerLoginMenu.showPassengerLoginMenu();
                String choice = getChoiceNumber();
                choiceNumber = Integer.parseInt(choice);
                switch (choiceNumber) {
                    case 1:
                        if(foundPassenger.getStatus() == TripStatus.STOP){
                            Double[] point = getOriginDestination();
                            double originLat = point[0];
                            double originLong = point[1];
                            double destinationLat = point[2];
                            double destinationLong = point[3];
                            int tripPrice = calculateTripPrice(originLat, originLong, destinationLat, destinationLong);
                            findAvailableDriver(foundPassenger, originLat, originLong, destinationLat, destinationLong, PayStatus.CASH, tripPrice);
                        }
                        break;
                    case 2:
                        if(foundPassenger.getStatus() == TripStatus.STOP){
                            Double[] point = getOriginDestination();
                            double originLat = point[0];
                            double originLong = point[1];
                            double destinationLat = point[2];
                            double destinationLong = point[3];
                            int tripPrice = calculateTripPrice(originLat, originLong, destinationLat, destinationLong);
                            if(foundPassenger.getBalance() < tripPrice){
                                System.out.println("Your balance is not enough!");
                                do{
                                    System.out.println("1. Increase account balance");
                                    System.out.println("2. Exit");
                                    choice = getChoiceNumber();
                                    choiceNumber = Integer.parseInt(choice);
                                }while (choiceNumber == 2);
                                switch (choiceNumber){
                                    case 1:
                                        increasePassengerBalance(foundPassenger);
                                        break;
                                    case 2:
                                        break;
                                    default:
                                        System.out.println("Invalid value");
                                }
                            }else{
                                findAvailableDriver(foundPassenger, originLat, originLong, destinationLat, destinationLong, PayStatus.ACCOUNT, tripPrice);
                            }
                        }
                        break;
                    case 3:
                        increasePassengerBalance(foundPassenger);
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("Invalid number!");
                }
            } while (choiceNumber != 4);
        } else {
            System.out.println("Username " + username + " doesn't exist, Register or Exit");
            int choiceNumber;
            do {
                SignupMenu.showSignupMenu();
                String choice = getChoiceNumber();
                choiceNumber = Integer.parseInt(choice);
                switch (choiceNumber) {
                    case 1:
                        passengerRegister();
                        break;
                    case 2:
                        break;
                    default:
                        System.out.println("Invalid number!");
                }
            } while (choiceNumber != 2);
        }
    }

    private static int calculateTripPrice(double origLat, double origLong, double destLat, double destLong){
        double distance = Math.sqrt((Math.exp(origLat - destLat)) + (Math.exp(origLong - destLong)));
        return (int) (1000 * distance);
    }

    private static Double[] getDriverLocation(){
        Double[] point = new Double[2];
        String latitude;
        do{
            System.out.println("Enter your location latitude:");
            latitude = scanner.next();
        }while (!ValidationUtil.isDouble(latitude));
        point[0] = Double.parseDouble(latitude);
        String longitude;
        do{
            System.out.println("Enter your location longitude:");
            longitude = scanner.next();
        }while (!ValidationUtil.isDouble(longitude));
        point[1] = Double.parseDouble(longitude);
        return point;
    }

    private static Double[] getOriginDestination() {
        System.out.println("Enter the origin and destination of your travel:");
        Double[] point = new Double[4];
        String originLat;
        do{
            System.out.println("Origin latitude:");
            originLat = scanner.next();
        }while (!ValidationUtil.isDouble(originLat));
        point[0] = Double.parseDouble(originLat);
        String originLong;
        do{
            System.out.println("Origin longitude:");
            originLong = scanner.next();
        }while (!ValidationUtil.isDouble(originLong));
        point[1] = Double.parseDouble(originLong);
        String destinationLat;
        do{
            System.out.println("Destination latitude:");
            destinationLat = scanner.next();
        }while (!ValidationUtil.isDouble(destinationLat));
        point[2] = Double.parseDouble(destinationLat);
        String destinationLong;
        do{
            System.out.println("Destination longitude:");
            destinationLong = scanner.next();
        }while (!ValidationUtil.isDouble(destinationLong));
        point[3] = Double.parseDouble(destinationLong);
        return point;
    }
    private static void findAvailableDriver(Passenger passenger, double originLat, double originLong, double destinationLat, double destinationLong, PayStatus payStatus, int tripPrice) {
        List<Driver>foundDrivers = driverDao.findDriverByWaitStatus();
        List<Double>distances = new ArrayList<>();
        for (Driver item:foundDrivers) {
            double locLat = item.getCurrentLocationLat();
            double locLong = item.getCurrentLocationLong();
            double distance = Math.sqrt((Math.exp(locLat) - Math.exp(originLat)) + ((Math.exp(locLong)) - Math.exp(originLong)));
            distances.add(distance);
        }
        double minDistance = Collections.min(distances);
        int index = distances.indexOf(minDistance);
        Driver availableDriver = foundDrivers.get(index);
        Date tripDate = getTripDateFromInput();
        Trip trip = new Trip();
        trip.setPassenger(passenger);
        trip.setDriver(availableDriver);
        trip.setOriginLat(originLat);
        trip.setOriginLong(originLong);
        trip.setDestinationLat(destinationLat);
        trip.setDestinationLong(destinationLong);
        trip.setTripDate(tripDate);
        trip.setPrice(tripPrice);
        trip.setTripDate(tripDate);
        trip.setPayStatus(payStatus);
        tripDao.saveTrip(trip);
        passenger.setStatus(TripStatus.ONGOING);
        passengerDao.updatePassengerStatusToONGOING(passenger);
        availableDriver.setStatus(TripStatus.ONGOING);
        driverDao.updateDriverStatusToONGOING(availableDriver);
        System.out.println("Your request accepted by " + foundDrivers.get(index).getName() + ", "+
                foundDrivers.get(index).getFamily() + ", plaque number: " + foundDrivers.get(index).getPlaque());
        Trip foundTrip = tripDao.findTripByDriver(availableDriver);
        if(foundTrip.getPayStatus() == PayStatus.ACCOUNT){
            decreasePassengerBalance(passenger, tripPrice);
            foundTrip.setPayStatus(PayStatus.PAYED);
            tripDao.updateTripPayStatusAfterPaying(foundTrip);
        }
    }

    private static void increasePassengerBalance(Passenger passenger) {
        String amount;
        do {
            System.out.println("Enter amount in RIAL:");
            amount = scanner.next();
        } while (!ValidationUtil.isNumeric(amount));
        int amountNumber = Integer.parseInt(amount);
        passenger.setBalance(amountNumber + passenger.getBalance());
        passengerDao.increaseBalance(passenger);
        System.out.println("Your balance increased.");
    }
    private static void decreasePassengerBalance(Passenger passenger, int tripPrice) {
        int decreasedBalance = passenger.getBalance() - tripPrice;
        passenger.setBalance(decreasedBalance);
        passengerDao.decreasePassengerBalance(passenger);
        System.out.println("Your balance decreased.");
    }

    private static void passengerRegister() {
        String name = getNameFromInput();
        String family = getFamilyFromInput();
        String username;
        do {
            username = getUsernameFromInput();
        } while (passengerDao.findPassengerByUsername(username) != null);
        String phoneNumber = getPhoneNumberFromInput();
        long nationalCode = getNationalCodeFromInput();
        Date birthDate = getBirthDateFromInput();
        Passenger passenger = new Passenger(name, family, username, phoneNumber, nationalCode, birthDate, TripStatus.STOP, 0);
        passengerDao.saveNewPassenger(passenger);
        System.out.println("Your information was successfully registered.");
    }

    private static String getChoiceNumber() {
        String choice;
        do {
            choice = scanner.next();
        } while (!ValidationUtil.isNumeric(choice));
        return choice;
    }

    private static String getCarPlaqueFromInput() {
        String plaque;
        do {
            System.out.println("Enter driver car plate number:");
            plaque = scanner.next();
        } while (!ValidationUtil.isAlphabetic(plaque));
        return plaque;
    }

    private static Date getBirthDateFromInput(){
        String date;
        do {
            System.out.println("Enter birth date like 1370-02-12:");
            date = scanner.next();
        } while (!ValidationUtil.isValidFormatDate(date));
        Date birthDate = Date.valueOf(date);//converting string into sql date
        return birthDate;
    }
    private static Date getTripDateFromInput(){
        String date;
        do {
            System.out.println("Enter trip date like 1400-07-01:");
            date = scanner.next();
        } while (!ValidationUtil.isValidFormatDate(date));
        Date TripDate = Date.valueOf(date);//converting string into sql date
        return TripDate;
    }

    private static long getNationalCodeFromInput() {
        String nationalCode;
        do {
            System.out.println("Enter national code:");
            nationalCode = scanner.next();
        } while (!ValidationUtil.isNumeric(nationalCode) && !ValidationUtil.isIranianNationalCode(nationalCode));
        return Long.parseLong(nationalCode);
    }

    private static String getPhoneNumberFromInput() {
        String phoneNumber;
        do {
            System.out.println("Enter phone number:");
            phoneNumber = scanner.next();
        } while (!ValidationUtil.isValidPhoneNumber(phoneNumber));
        return phoneNumber;
    }

    private static String getUsernameFromInput() {
        String username;
        do {
            System.out.println("Enter username:\nUsername must be longer than 4 character.");
            username = scanner.next();
        } while (!ValidationUtil.isValidUsername(username));
        return username;
    }

    private static String getFamilyFromInput() {
        String family;
        do {
            System.out.println("Enter family:");
            family = scanner.next();
        } while (!ValidationUtil.isLetter(family));
        return family;
    }

    private static String getNameFromInput() {
        String name;
        do {
            System.out.println("Enter name:");
            name = scanner.next();
        } while (!ValidationUtil.isLetter(name));
        return name;
    }

    private static void chooseVehicleByPassenger(){
        Vehicle.showVehicleMenu();
        String choice = getChoiceNumber();
        int choiceNumber = Integer.parseInt(choice);
        switch (choiceNumber){
            case 1:
                System.out.println("Car");
                //TODO
                break;
            case 2:
                System.out.println("Motor cycle");
                //TODO
                break;
            case 3:
                System.out.println("Van");
                //TODO
                break;
            case 4:
                System.out.println("RV");
                //TODO
                break;
            default:
                System.out.println("Invalid value!");
        }
    }

}
