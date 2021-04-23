package com.example.thovin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class Utility {

    // ----------------- TEXT INPUT -----------------
    // --- Error fields
    public static void setErrorOnField(Context context, TextInputLayout field, String errMessage) {
        ColorStateList colorState = context.getColorStateList(R.color.dark_red);

        // --- Error
        field.setError(errMessage);
        field.setErrorTextColor(colorState);

        // --- Box and hint
        field.setBoxStrokeErrorColor(colorState);
        field.setHintTextColor(colorState);
    }

    public static void setErrorOnFields(Context context, ArrayList<TextInputLayout> fields, String errMessage) {
        for (TextInputLayout field : fields) {
            Utility.setErrorOnField(context, field, errMessage);
        }
    }

    public static void setErrorOnFields(Context context, HashMap<String, TextInputLayout> fields, String[] errFields, String errMessage, int type) {
        for (String field : errFields) {
            switch (field) {
                case "email":
                    if (type == 0) Utility.setErrorOnField(context, fields.get("login_email"), errMessage);
                    else Utility.setErrorOnField(context, fields.get("register_email"), errMessage);
                    break;
                case "password":
                    if (type == 0) Utility.setErrorOnField(context, fields.get("login_password"), errMessage);
                    else Utility.setErrorOnField(context, fields.get("register_password"), errMessage);
                    break;
                case "address.zip":
                    Utility.setErrorOnField(context, fields.get("register_zip"), errMessage);
                    break;
                case "phone":
                    Utility.setErrorOnField(context, fields.get("register_phone"), errMessage);
                    break;
            }
        }
    }


//    public static void setSuccessOnField(Context context, TextInputLayout field) {
//        ColorStateList colorState = context.getColorStateList(R.color.main_green);
//
//        // --- Remove error
//        field.setError(null);
//
//        // --- Box and hint
//        field.setBoxStrokeColorStateList(colorState);
//        field.setHintTextColor(colorState);
//
//        // --- Icon
//        field.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
//        Drawable icon = context.getDrawable(R.drawable.ic_baseline_check_circle_24).mutate();
//        icon.setColorFilter(context.getColor(R.color.main_green), PorterDuff.Mode.SRC_ATOP);
//        field.setEndIconDrawable(icon);
//    }

    // ----------------- SNACKBARS -----------------
    // --- Success Snackbars
    public static Snackbar getSuccessSnackbar(Context context, View v, String message, int length) {
        Snackbar snackbar = Snackbar.make(v, message, length);
        snackbar.setBackgroundTint(context.getColor(R.color.main_green));
        return snackbar;
    }

    // --- Warning Snackbars
    public static Snackbar getWarningSnackbar(Context context, View v, String message, int length) {
        Snackbar snackbar = Snackbar.make(v, message, length);
        snackbar.setBackgroundTint(context.getColor(R.color.main_yellow));
        return snackbar;
    }

    // --- Error Snackbars
    public static Snackbar getErrorSnackbar(Context context, View v, String message, int length) {
        Snackbar snackbar = Snackbar.make(v, message, length);
        snackbar.setBackgroundTint(context.getColor(R.color.dark_red));
        return snackbar;
    }
}
