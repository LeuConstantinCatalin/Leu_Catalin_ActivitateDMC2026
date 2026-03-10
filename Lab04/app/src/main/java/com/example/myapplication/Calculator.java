package com.example.myapplication;

import java.io.Serializable;

public class Calculator implements Serializable {
    private String model;
    private boolean gaming;
    private int ramGb;
    private Brand brand;
    private float rating;
    private boolean warranty;
    private boolean bluetoothEnabled;
    private String osType;

    public Calculator(String model, boolean gaming, int ramGb, Brand brand,
                      float rating, boolean warranty, boolean bluetoothEnabled, String osType) {
        this.model = model;
        this.gaming = gaming;
        this.ramGb = ramGb;
        this.brand = brand;
        this.rating = rating;
        this.warranty = warranty;
        this.bluetoothEnabled = bluetoothEnabled;
        this.osType = osType;
    }

    public String getModel() {
        return model;
    }

    public boolean isGaming() {
        return gaming;
    }

    public int getRamGb() {
        return ramGb;
    }

    public Brand getBrand() {
        return brand;
    }

    public float getRating() {
        return rating;
    }

    public boolean hasWarranty() {
        return warranty;
    }

    public boolean isBluetoothEnabled() {
        return bluetoothEnabled;
    }

    public String getOsType() {
        return osType;
    }

    @Override
    public String toString() {
        return "Model: " + model +
                "\nBrand: " + brand +
                "\nRAM: " + ramGb + " GB" +
                "\nGaming: " + (gaming ? "Da" : "Nu") +
                "\nRating: " + rating +
                "\nGarantie: " + (warranty ? "Da" : "Nu") +
                "\nBluetooth: " + (bluetoothEnabled ? "Pornit" : "Oprit") +
                "\nSistem operare: " + osType;
    }
}