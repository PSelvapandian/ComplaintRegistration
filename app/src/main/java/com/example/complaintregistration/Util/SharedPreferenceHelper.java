package com.example.complaintregistration.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceHelper
{
    private static final String USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_EMAIL = "user_email";
    private static final String LOGIN_STATUS = "login_status";
    private static final String ROLE = "role";

    private static SharedPreferenceHelper instance;
    private final SharedPreferences preferences;

    public SharedPreferenceHelper(Context context)
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferenceHelper getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new SharedPreferenceHelper(context);
        }
        return instance;
    }

    public void setUserId(String userId)
    {
        preferences.edit().putString(USER_ID, userId).apply();
    }

    public String getUserId()
    {
        return preferences.getString(USER_ID, "");
    }

    public void setUserName(String userName)
    {
        preferences.edit().putString(USER_NAME, userName).apply();
    }

    public String getUserName()
    {
        return preferences.getString(USER_NAME, "");
    }

    public void setUserEmail(String email)
    {
        preferences.edit().putString(USER_EMAIL, email).apply();
    }

    public String getUserEmail()
    {
        return preferences.getString(USER_EMAIL, "");
    }

    public void setLoginStatus(Boolean loginStatus)
    {
        preferences.edit().putBoolean(LOGIN_STATUS, loginStatus).apply();
    }
    public Boolean getLoginStatus()
    {
        return preferences.getBoolean(LOGIN_STATUS, false);
    }

    public void setRole(String role)
    {
        preferences.edit().putString(ROLE, role).apply();
    }

    public String getRole()
    {
        return preferences.getString(ROLE, "");
    }

}
