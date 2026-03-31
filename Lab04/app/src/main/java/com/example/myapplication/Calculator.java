package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Calculator implements Parcelable {
    private String model;
    private boolean gaming;
    private int ramGb;
    private Brand brand;
    private float rating;
    private boolean warranty;
    private Date warrantyExpiryDate;
    private boolean bluetoothEnabled;
    private String osType;

    public Calculator(String model, boolean gaming, int ramGb, Brand brand, float rating,
                      boolean warranty, Date warrantyExpiryDate,
                      boolean bluetoothEnabled, String osType) {
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

    protected Calculator(Parcel in) {
        model = in.readString();
        gaming = in.readByte() != 0;
        ramGb = in.readInt();
        String brandName = in.readString();
        brand = brandName != null ? Brand.valueOf(brandName) : null;
        rating = in.readFloat();
        warranty = in.readByte() != 0;

        long dateMillis = in.readLong();
        warrantyExpiryDate = dateMillis == -1 ? null : new Date(dateMillis);

        bluetoothEnabled = in.readByte() != 0;
        osType = in.readString();
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(model);
        dest.writeByte((byte) (gaming ? 1 : 0));
        dest.writeInt(ramGb);
        dest.writeString(brand != null ? brand.name() : null);
        dest.writeFloat(rating);
        dest.writeByte((byte) (warranty ? 1 : 0));
        dest.writeLong(warrantyExpiryDate != null ? warrantyExpiryDate.getTime() : -1);
        dest.writeByte((byte) (bluetoothEnabled ? 1 : 0));
        dest.writeString(osType);
    }

    public String toSummary() {
        return brand + " " + model;
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

    public boolean isWarranty() {
        return warranty;
    }

    public Date getWarrantyExpiryDate() {
        return warrantyExpiryDate;
    }

    public boolean isBluetoothEnabled() {
        return bluetoothEnabled;
    }

    public String getOsType() {
        return osType;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setGaming(boolean gaming) {
        this.gaming = gaming;
    }

    public void setRamGb(int ramGb) {
        this.ramGb = ramGb;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setWarranty(boolean warranty) {
        this.warranty = warranty;
    }

    public void setWarrantyExpiryDate(Date warrantyExpiryDate) {
        this.warrantyExpiryDate = warrantyExpiryDate;
    }

    public void setBluetoothEnabled(boolean bluetoothEnabled) {
        this.bluetoothEnabled = bluetoothEnabled;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String toFileString() {
        long expiryTime = warrantyExpiryDate != null ? warrantyExpiryDate.getTime() : -1;

        return model + ";" +
                ramGb + ";" +
                gaming + ";" +
                brand + ";" +
                rating + ";" +
                warranty + ";" +
                expiryTime + ";" +
                bluetoothEnabled + ";" +
                osType + "\n";
    }

    public static Calculator fromFileString(String line) {
        String[] parts = line.split(";");

        String model = parts[0];
        int ramGb = Integer.parseInt(parts[1]);
        boolean gaming = Boolean.parseBoolean(parts[2]);
        Brand brand = Brand.valueOf(parts[3]);
        float rating = Float.parseFloat(parts[4]);
        boolean warranty = Boolean.parseBoolean(parts[5]);

        Date warrantyExpiryDate = null;
        boolean bluetoothEnabled;
        String osType;

        if (parts.length == 9) {
            long time = Long.parseLong(parts[6]);
            if (time != -1) {
                warrantyExpiryDate = new Date(time);
            }
            bluetoothEnabled = Boolean.parseBoolean(parts[7]);
            osType = parts[8];
        } else if (parts.length == 8) {
            bluetoothEnabled = Boolean.parseBoolean(parts[6]);
            osType = parts[7];
        } else {
            throw new IllegalArgumentException("Linie invalida: " + line);
        }

        return new Calculator(
                model,
                gaming,
                ramGb,
                brand,
                rating,
                warranty,
                warrantyExpiryDate,
                bluetoothEnabled,
                osType
        );
    }
}