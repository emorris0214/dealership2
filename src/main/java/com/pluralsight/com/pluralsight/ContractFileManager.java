package com.pluralsight;

import com.pluralsight.*;

import java.io.FileWriter;
import java.io.IOException;

//Persistence Layer (Appends contract data to contracts file)
public class ContractFileManager {

    private static final String CONTRACTS_FILE = "contracts.csv";

    public void saveContract(Contract contract) {
        try (FileWriter writer = new FileWriter(CONTRACTS_FILE)) {
            StringBuilder sb = new StringBuilder();

            if (contract instanceof SalesContract) {
                SalesContract sc = (SalesContract) contract;
                sb.append("SALE!");
                sb.append(sc.getDate()).append("|");
                sb.append(sc.getCustomerName()).append("|");
                sb.append(sc.getCustomerEmail()).append("|");

                Vehicle v = sc.getVehicleSold();
                sb.append(v.getVin()).append("|").append(v.getYear()).append("|");
                sb.append(v.getMake()).append("|").append(v.getModel()).append("|");
                sb.append(v.getVehicleType()).append("|").append(v.getColor()).append("|");
                sb.append(v.getOdometer()).append("|").append(v.getPrice()).append("|");

                double salesTax = v.getPrice() * 0.05;
                double recordingFee = 100;
                double processingFee = v.getPrice() < 10000 ? 295 : 495;

                sb.append(String.format("%.2f|%.2f|%.2f|%.2f", salesTax, recordingFee, processingFee, sc.getTotalPrice()));
                sb.append(sc.isFinanced() ? "YES" : "NO").append("|");
                sb.append(String.format("%.2f", sc.getMonthlyPayment()));
            } else if (contract instanceof LeaseContract) {
                LeaseContract lc = (LeaseContract) contract;
                sb.append("LEASE!");
                sb.append(lc.getDate()).append("|");
                sb.append(lc.getCustomerName()).append("|");
                sb.append(lc.getCustomerEmail()).append("|");

                Vehicle v = lc.getVehicleSold();
                sb.append(v.getVin()).append("|").append(v.getYear()).append("|");
                sb.append(v.getMake()).append("|").append(v.getModel()).append("|");
                sb.append(v.getVehicleType()).append("|").append(v.getColor()).append("|");
                sb.append(v.getOdometer()).append("|").append(v.getPrice()).append("|");

                double expectedEndingValue = v.getPrice() * 0.5;
                double leaseFee = v.getPrice() * 0.07;
                double totalPrice = lc.getTotalPrice();
                double monthlyPayment = lc.getMonthlyPayment();

                sb.append(String.format("%.2f|%.2f|%.2f|%.2f", expectedEndingValue, leaseFee, totalPrice, monthlyPayment));

            }

            sb.append("\n");
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println("ERROR SAVING CONTRACT: " + e.getMessage());
        }
    }
}
