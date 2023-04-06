package com.saitejajanjirala.quizme;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public static boolean validateEmail(String em){
        return !TextUtils.isEmpty(em) && Patterns.EMAIL_ADDRESS.matcher(em).matches();
    }
    private static boolean isPasswordValid(String pwd){
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }

    private static boolean isPasswordLengthValid(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6 && password.length() <= 16;
    }

    public static boolean validatePassword(String password) {
        return isPasswordLengthValid(password) && isPasswordValid(password);
    }

    public static boolean validateName(String name) {
        return !name.isEmpty() && !name.trim().isEmpty() && name.length()>3 ;
    }

    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}
