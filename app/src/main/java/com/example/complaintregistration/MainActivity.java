package com.example.complaintregistration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    ActivityMainBinding binding;
    SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplicationContext());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getUserName();

        binding.btnRegisterComplaints.setOnClickListener(v -> startActivity(new Intent(this, ComplaintRegisterActivity.class)));

        binding.btnComplainStatus.setOnClickListener(v -> startActivity(new Intent(this, ViewComplaintStatusActivity.class)));

        binding.btnViewFeedback.setOnClickListener(v -> startActivity(new Intent(this, ViewAllFeedbackActivity.class)));

        binding.btnComplainFeedback.setOnClickListener(v -> startActivity(new Intent(this, FeedBackActivity.class)));

        binding.btnMyProfile.setOnClickListener(v -> startActivity(new Intent(this, UserProfileActivity.class)));

        binding.cardLogout.setOnClickListener(v -> logoutAccount());
    }

    @SuppressLint("SetTextI18n")
    private void getUserName()
    {
        if (sharedPreferenceHelper.getUserName() != null && !sharedPreferenceHelper.getUserName().isEmpty())
        {
            binding.userName.setText(sharedPreferenceHelper.getUserName() + "!");
        }
        else
        {
            if (sharedPreferenceHelper.getUserEmail() != null && !sharedPreferenceHelper.getUserEmail().isEmpty())
            {
                binding.userName.setText(sharedPreferenceHelper.getUserEmail() + "!");
            }
            else
            {
                binding.userName.setText("");
            }
        }
    }

    private void logoutAccount()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to logout!");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            sharedPreferenceHelper.setUserId("");
            sharedPreferenceHelper.setUserName("");
            sharedPreferenceHelper.setUserEmail("");
            sharedPreferenceHelper.setRole("");
            startActivity(new Intent(this, LoginScreenActivity.class));
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }
}