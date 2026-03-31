package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UiUtils {

    public static void applySettingsToView(Context context, View view) {
        SharedPreferences prefs = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE);

        int textSize = prefs.getInt("text_size", 16);
        String colorName = prefs.getString("text_color", "Black");

        int color = Color.BLACK;

        switch (colorName) {
            case "Red":
                color = Color.RED;
                break;
            case "Blue":
                color = Color.BLUE;
                break;
            case "Green":
                color = Color.GREEN;
                break;
        }

        applyRecursive(view, textSize, color);
    }

    private static void applyRecursive(View view, int textSize, int color) {
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(textSize);
            ((TextView) view).setTextColor(color);
        }

        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                applyRecursive(group.getChildAt(i), textSize, color);
            }
        }
    }
}