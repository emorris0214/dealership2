package com.pluralsight;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private Scanner scanner = new Scanner(System.in);

    public void display() {
        init();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> processGetByPriceRequest();
                case 2 -> processGetByMakeModelRequest();
                case 3 -> processGetByYearRequest();
                case 4 -> processGetByColorRequest();
                case 5 -> processGetByMileageRequest();
                case 6 -> processGetByVehicleTypeRequest();
                case 7 -> processAllVehiclesRequest();
                case 8 -> processAddVehicleRequest();
                case 9 -> processRemoveVehicleRequest();
                case 10 -> processSellOrLeaseVehicle();
                case 99 -> running = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("See ya around, thanks for visiting Decker's Dealership!");
    }

    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
    }

    private void displayMenu() {
        System.out.println("\nWelcome to Decker's Dealership! Please choose an option:");
        System.out.println("1 - Find vehicles within a price range");
        System.out.println("2 - Find vehicles by make / model");
        System.out.println("3 - Find vehicles by year range");
        System.out.println("4 - Find vehicles by color");
        System.out.println("5 - Find vehicles by mileage range");
        System.out.println("6 - Find vehicles by type (car, truck, SUV, van)");
        System.out.println("7 - List ALL vehicles");
        System.out.println("8 - Add a vehicle");
        System.out.println("9 - Remove a vehicle");
        System.out.println("10 - Sell/Lease a vehicle");
        System.out.println("99 - Quit");
        System.out.print("Enter your choice: ");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        for (Vehicle v : vehicles) {
            System.out.println(v);
        }
    }

    private void processAllVehiclesRequest() {
        List<Vehicle> vehicles = dealership.getAllVehicles();
        displayVehicles(vehicles);
    }

    private void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        List<Vehicle> results = dealership.getVehiclesByPrice(minPrice, maxPrice);
        displayVehicles(results);
    }

    private void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        List<Vehicle> results = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(results);
    }

    private void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int minYear = scanner.nextInt();
        System.out.print("Enter maximum year: ");
        int maxYear = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Vehicle> results = dealership.getVehiclesByYear(minYear, maxYear);
        displayVehicles(results);
    }

    private void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();

        List<Vehicle> results = dealership.getVehiclesByColor(color);
        displayVehicles(results);
    }

    private void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int minMileage = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int maxMileage = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Vehicle> results = dealership.getVehiclesByMileage(minMileage, maxMileage);
        displayVehicles(results);
    }

    private void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type (car, truck, SUV, van): ");
        String type = scanner.nextLine();

        List<Vehicle> results = dealership.getVehiclesByType(type);
        displayVehicles(results);
    }

    private void processAddVehicleRequest() {
        System.out.print("Enter VIN: ");
        int vin = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter make: ");
        String make = scanner.nextLine();

        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        System.out.print("Enter type: ");
        String type = scanner.nextLine();

        System.out.print("Enter color: ");
        String color = scanner.nextLine();

        System.out.print("Enter odometer: ");
        int odometer = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        dealership.addVehicle(vehicle);
        DealershipFileManager.saveDealership(dealership);

        System.out.println("Vehicle added successfully.");
    }

    private void processRemoveVehicleRequest() {
        System.out.print("Enter VIN of vehicle to remove: ");
        int vin = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Vehicle toRemove = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) {
                toRemove = v;
                break;
            }
        }

        if (toRemove != null) {
            dealership.removeVehicle(vin);
            DealershipFileManager.saveDealership(dealership);
            System.out.println("Vehicle removed successfully.");
        } else {
            System.out.println("Vehicle with VIN " + vin + " not found.");
        }
    }

    private void processSellOrLeaseVehicle() {
        System.out.println("Enter VIN of vehicle to sell/lease: ");
        int vin = Integer.parseInt(scanner.nextLine());

        List<Vehicle> matches = dealership.getVehiclesByVin(vin);

        if(matches.isEmpty()) {
            System.out.println("No vehicles found with VIN: ");
            return;
        }

        Vehicle vehicle = matches.get(0); // Vin is unique

        int currentYear = LocalDate.now().getYear();

        System.out.println("Enter customer name: ");
        String customerName = scanner.nextLine();
        System.out.println("Enter customer email: ");
        String customerEmail = scanner.nextLine();
        System.out.println("Is this a sale or a lease? (Enter 'sale' or 'lease'): ");
        String contractType = scanner.nextLine().trim().toLowerCase();

        String date = LocalDate.now().toString();
        Contract contract;

        if (contractType.equals("sale")) {
            System.out.println("Will the customer finance the vehicle? (yes/no): ");
            boolean isFinanced = scanner.nextLine().trim().equalsIgnoreCase("yes");

            contract = new SalesContract(date, customerName, customerEmail, vehicle, isFinanced);

        } else if (contractType.equals("lease")) {
            if (currentYear - vehicle.getYear() > 3) {
                System.out.println("Cannot lease a vehicle over 3 years old.");
                return;
            }

            contract = new LeaseContract(date, customerName, customerEmail, vehicle);
        } else {
            System.out.println("Invalid contract type. Must be 'sale' or 'lease'.");
            return;
        }

        ContractDataManager.saveContract(contract);
        dealership.removeVehicle(vin);

        System.out.println("Thank you for your purchase! Refer your friends! Remember to hit the Deck'! \uD83D\uDE0F ");
        System.out.println("Contact process and remove vehicle from inventory.");

    }
}
