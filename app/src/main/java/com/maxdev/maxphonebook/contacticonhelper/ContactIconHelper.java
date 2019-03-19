package com.maxdev.maxphonebook.contacticonhelper;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;

import java.util.Random;

public class ContactIconHelper {
    private static Random random = new Random();
    public static Drawable getIconBackground(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    public static ContactIconColor generateColor(String startChars) {
        int color = Color.argb(255,
                random.nextInt(255), random.nextInt(255), random.nextInt(255));
        return new ContactIconColor(color, startChars);
    }
}
