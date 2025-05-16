package com.pluralsight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class DealershipFileManager {
    private static final String FILE_NAME = "inventory.csv";

    public com.pluralsight.Dealership getDealership() {
        Dealership dealership = null;
        try (Scanner keyboard = new Scanner(new File(FILE_NAME))) {
            if (keyboard.hasNextLine()) {
                String[] dealerInfo = keyboard.nextLine().split("\\|");
                dealership = new Dealership(dealerInfo[0], dealerInfo[1], dealerInfo[2]);
            }

            while (keyboard.hasNextLine()) {
                String[] vehicleData = keyboard.nextLine().split("\\|");
                if (vehicleData.length == 8) {
                    Vehicle vehicle = new Vehicle(
                            Integer.parseInt(vehicleData[0]),
                            Integer.parseInt(vehicleData[1]),
                            vehicleData[2],
                            vehicleData[3],
                            vehicleData[4],
                            vehicleData[5],
                            Integer.parseInt(vehicleData[6]),
                            Double.parseDouble(vehicleData[7])
                    );
                    dealership.addVehicle(vehicle);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + FILE_NAME);
        } catch (Exception e) {
            System.out.println("Error loading dealership: " + e.getMessage());
        }
        return dealership;
    }

    public static void saveDealership(Dealership dealership) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            // Dealership info on the first line
            writer.println(dealership.getName() + "|" + dealership.getAddress() + "|" + dealership.getPhone());

            for (Vehicle v : dealership.getAllVehicles()) {
                writer.printf("%d|%d|%s|%s|%s|%s|%d|%.2f\n",
                        v.getVin(), v.getYear(), v.getMake(), v.getModel(), v.getVehicleType(), v.getColor(), v.getOdometer(), v.getPrice());
            }
        } catch (Exception e) {
            System.out.println("Error saving new dealership catalog to" + FILE_NAME + ": " + e.getMessage());
        }
    }
}
