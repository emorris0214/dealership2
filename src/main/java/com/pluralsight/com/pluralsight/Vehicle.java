package com.pluralsight;

public class Vehicle {
    private int vin;
    private int year;
    private int odometer;
    private double price;
    private String make;
    private String model;
    private String vehicleType;
    private String color;

    public Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer, double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public int getYear() {
        return year;
    }

    public int getOdometer() {
        return odometer;
    }

    public double getPrice() {
        return price;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("VIN: %d | Year: %d | Make: %s | Model: %s | Type: %s | Color: %s | Odometer: %d | Price: $%.2f",
                vin, year, make, model, vehicleType, color, odometer, price);
    }
}