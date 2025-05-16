package com.pluralsight;

// Business Logic Layer (Determines contract type, manages contract flow)
/**
 * ContractDataManager handles the business logic of saving contract data
 * and deciding how to handle different contract types (sale or lease).
 * It delegates actual file saving to ContractFileManager.
 */
public class ContractDataManager {
    protected static ContractFileManager fileManager = new ContractFileManager();

    // Constructor
    public ContractDataManager() {
        this.fileManager = new ContractFileManager();
    }

    /**
     * Saves the contract by checking its type and sending it to the file manager.
     * Uses instanceof to distinguish between SalesContract and LeaseContract.
     *
     * @param contract The contract to be saved (either sale or lease)
     */
    public static void saveContract(Contract contract) {
        // Determine contract type
        try {
            if (contract instanceof SalesContract salesContract) {
                fileManager.saveContract(salesContract);
            } else if (contract instanceof LeaseContract leaseContract) {
                fileManager.saveContract(leaseContract);
            } else {
                System.out.println("Unknown contract type. Contract not saved.");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
}