package co.id.gmedia.travelocscanner.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSharedPreferences {
    private  static final String TOKEN = "token";
    private  static final String USER_NAME = "username";
    private  static final String LOGIN_PREF = "login";

    private static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isLoggedIn(Context context){
        return getPreferences(context).getBoolean(LOGIN_PREF, false);
    }

    public static String getUserName(Context context){
        return getPreferences(context).getString(USER_NAME, "");
    }

    public static String getToken(Context context){
        return getPreferences(context).getString(TOKEN, "");
    }


    /*public static void setFcmId(Context context, String fcm){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(FCM_PREF, fcm);
        editor.apply();
    }*/

    public static void Login(Context context, String token){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGIN_PREF, true);
        //editor.putString(USER_NAME, username);
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public static void Logout(Context context){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGIN_PREF, false);
        editor.putString(USER_NAME, "");
        editor.putString(TOKEN, "");
        editor.apply();
    }
}