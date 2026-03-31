package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CalculatorAdapter extends ArrayAdapter<Calculator> {

    public CalculatorAdapter(@NonNull Context context, List<Calculator> calculators) {
        super(context, 0, calculators);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_calculator, parent, false);
        }

        Calculator calculator = getItem(position);

        TextView tvItemModel = convertView.findViewById(R.id.tvItemModel);
        TextView tvItemBrand = convertView.findViewById(R.id.tvItemBrand);
        TextView tvItemRam = convertView.findViewById(R.id.tvItemRam);
        TextView tvItemOs = convertView.findViewById(R.id.tvItemOs);
        TextView tvItemDetails = convertView.findViewById(R.id.tvItemDetails);

        if (calculator != null) {
            tvItemModel.setText(calculator.getModel());
            tvItemBrand.setText("Brand: " + calculator.getBrand());
            tvItemRam.setText("RAM: " + calculator.getRamGb() + " GB");
            tvItemOs.setText("OS: " + calculator.getOsType());

            String details = "Gaming: " + (calculator.isGaming() ? "Da" : "Nu")
                    + " | Bluetooth: " + (calculator.isBluetoothEnabled() ? "Da" : "Nu")
                    + " | Garantie: " + (calculator.isWarranty() ? "Da" : "Nu");

            tvItemDetails.setText(details);
        }

        return convertView;
    }
}