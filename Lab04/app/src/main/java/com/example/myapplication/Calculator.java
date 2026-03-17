package com.example.myapplication;

import java.io.Serializable;
import java.util.Date;

public class Calculator implements Serializable {
    private String model;
    private boolean gaming;
    private int ramGb;
    private Brand brand;
    private float rating;
    private boolean warranty;

    private Date warrantyExpiryDate;
    private boolean bluetoothEnabled;
    private String osType;

    public Calculator(String model, boolean gaming, int ramGb, Brand brand,
                      float rating, boolean warranty, Date warrantyExpiryDate, boolean bluetoothEnabled, String osType) {
        this.model = model;
        this.gaming = gaming;
        this.ramGb = ramGb;
        this.brand = brand;
        this.rating = rating;
        this.warranty = warranty;
        this.warrantyExpiryDate = warrantyExpiryDate;
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

    public Date getWarrantyExpiryDate() { return warrantyExpiryDate; }

    public boolean isBluetoothEnabled() {
        return bluetoothEnabled;
    }

    public String getOsType() {
        return osType;
    }

    @Override
    public String toString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");

        return "Model: " + model +
                "\nBrand: " + brand +
                "\nRAM: " + ramGb + " GB" +
                "\nGaming: " + (gaming ? "Da" : "Nu") +
                "\nRating: " + rating +
                "\nGarantie: " + (warranty ? "Da" : "Nu") +
                "\nData expirare garantie: " + sdf.format(warrantyExpiryDate) +
                "\nBluetooth: " + (bluetoothEnabled ? "Pornit" : "Oprit") +
                "\nSistem operare: " + osType;
    }

    public String toSummary() {
        return brand.toString() + " " +  model;
    }
}