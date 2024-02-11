package com.example.complaintregistration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.complaintregistration.Enum.RolesEnum;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.databinding.ActivitySpalshScreenBinding;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity
{
    ActivitySpalshScreenBinding binding;
    SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpalshScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplicationContext());

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (sharedPreferenceHelper.getUserId() != null && !sharedPreferenceHelper.getUserId().isEmpty())
        {
            if (sharedPreferenceHelper.getRole().equalsIgnoreCase(RolesEnum.OFFICER.name()))
            {
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(SplashScreenActivity.this, OfficerHomeScreen.class));
                    finish();
                }, 4000);
            }
            else
            {
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }, 4000);
            }
        }
        else
        {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, LoginScreenActivity.class));
                finish();
            }, 4000);
        }
    }
}
