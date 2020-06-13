package kr.yapp.teamplay.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.Nullable;

public class PreferenceManager {
    public static final String PREFERENCES_NAME = "rebuild_preference";

    private static final String tokenKey = "tokenKey";

    private static final String refreshTokenKey = "refreshToken";

    private static final String userIdKey = "userId";
    private static final String selectedTeamIdKey = "selectedTeamId";


    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setTokenKey(Context context, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(tokenKey, value);
        editor.commit();
    }

    public static String getTokenKey(Context context) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(tokenKey, "잉?");
        return value;
    }

    public static void setRefreshTokenKey(Context context, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(refreshTokenKey, value);
        editor.commit();
    }

    public static String getRefreshTokenKey(Context context) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(refreshTokenKey, "잉?");
        return value;
    }

    public static void setUserId(Context context, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(userIdKey, value);
        editor.commit();
    }

    public static String getUserId(Context context) {

        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(userIdKey, "잉?");
        return value;
    }

    public static void setSelectedTeamId(Context context, int id) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(selectedTeamIdKey, id);
        editor.commit();
    }

    public static int getSelectedTeamId(Context context) {

        SharedPreferences prefs = getPreferences(context);
        int value = prefs.getInt(selectedTeamIdKey, 0);
        return value;
    }
}
