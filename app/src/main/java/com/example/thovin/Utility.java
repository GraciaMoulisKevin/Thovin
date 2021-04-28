package com.example.thovin;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class Utility {

    public final static int STATE_SUCCESS = 0;
    public final static int STATE_ERROR = 1;
    public final static int STATE_FAILURE = 2;

    public final static int TYPE_LOGIN = 0;
    public final static int TYPE_REGISTER = 1;

    public final static Gson GSON = new Gson();

    // ----------------- TEXT INPUT -----------------
    // --- Error fields
    public static void setErrorOnField(Context context, TextInputLayout field, String errMessage) {
        ColorStateList colorState = context.getColorStateList(R.color.red_500);

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
                    if (type == TYPE_LOGIN) Utility.setErrorOnField(context, fields.get("login_email"), errMessage);
                    else Utility.setErrorOnField(context, fields.get("register_email"), errMessage);
                    break;
                case "password":
                    if (type == TYPE_LOGIN) Utility.setErrorOnField(context, fields.get("login_password"), errMessage);
                    else Utility.setErrorOnField(context, fields.get("register_password"), errMessage);
                    break;
                case "address.zip":
                    if (fields.get("register_zip") != null)
                        Utility.setErrorOnField(context, fields.get("register_zip"), errMessage);
                    break;
                case "phone":
                    Utility.setErrorOnField(context, fields.get("register_phone"), errMessage);
                    break;
                default:
                    break;
            }
        }
    }


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
        snackbar.setBackgroundTint(context.getColor(R.color.red_500));
        return snackbar;
    }

    // ----------------- PROGRESS SPINNER -----------------
    public static void toggleSpinner(FragmentActivity activity, Boolean isLoading) {
        RelativeLayout pSpinner = activity.findViewById(R.id.progress_spinner);
        if (isLoading) pSpinner.setVisibility(View.VISIBLE);
        else pSpinner.setVisibility(View.INVISIBLE);
    }

    // ----------------- AUTH TEXTLISTENER -----------------
    public static void addTextChangedListener(Context context, ArrayList<TextInputLayout> t){
        for (TextInputLayout field : t) {
            field.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0)
                        setErrorOnField(context, field, context.getString(R.string.err_empty_field));
                    else
                        field.setError(null);
                }
            });
        }
    }

    public static void addTextChangedListenerLogin(Context context, TextInputLayout t){
        t.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    setErrorOnField(context, t, context.getString(R.string.err_empty_field));
                else if (s.length() < 8)
                    setErrorOnField(context, t, context.getString(R.string.err_password_length));
                else
                    t.setError(null);
            }
        });
    }

    public static void addTextChangedListenerRegister(Context context, TextInputLayout t, TextInputLayout t_check){
        t.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    setErrorOnField(context, t, context.getString(R.string.err_empty_field));
                    if (t_check.getEditText().getText().toString().equals(s.toString()))
                        t_check.setError(null);
                    else
                        setErrorOnField(context, t_check, context.getString(R.string.err_password_not_equals));
                } else if (s.length() < 8) {
                    setErrorOnField(context, t, context.getString(R.string.err_password_length));
                    if (t_check.getEditText().getText().toString().equals(s.toString()))
                        t_check.setError(null);
                    else
                        setErrorOnField(context, t_check, context.getString(R.string.err_password_not_equals));
                } else {
                    t.setError(null);
                    if (t_check.getEditText().getText().toString().equals(s.toString()))
                        t_check.setError(null);
                    else
                        setErrorOnField(context, t_check, context.getString(R.string.err_password_not_equals));
                }
            }
        });

        t_check.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    setErrorOnField(context, t_check, context.getString(R.string.err_empty_field));
                else if (!s.toString().equals(t.getEditText().getText().toString()))
                    setErrorOnField(context, t_check, context.getString(R.string.err_password_not_equals));
                else
                    t_check.setError(null);
            }
        });
    }

    public static <T> Intent getHomeIntent(Context context, Class<T> activity) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    public static Intent getLogoutIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("STATE", context.getString(R.string.warn_logout));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }
}



// --- ARCHIVED
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
//    }//    public static void setSuccessOnField(Context context, TextInputLayout field) {
////        ColorStateList colorState = context.getColorStateList(R.color.main_green);
////
////        // --- Remove error
////        field.setError(null);
////
////        // --- Box and hint
////        field.setBoxStrokeColorStateList(colorState);
////        field.setHintTextColor(colorState);
////
////        // --- Icon
////        field.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
////        Drawable icon = context.getDrawable(R.drawable.ic_baseline_check_circle_24).mutate();
////        icon.setColorFilter(context.getColor(R.color.main_green), PorterDuff.Mode.SRC_ATOP);
////        field.setEndIconDrawable(icon);
////    }
