package com.example.complaintregistration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.databinding.ActivityHomeOfficerBinding;

public class OfficerHomeScreen extends AppCompatActivity
{
    ActivityHomeOfficerBinding binding;
    SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeOfficerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = new SharedPreferenceHelper(OfficerHomeScreen.this);

        getUserName();

        sharedPreferenceHelper = new SharedPreferenceHelper(OfficerHomeScreen.this);

        binding.btnViewComplaints.setOnClickListener(v -> startActivity(new Intent(this, ViewAllComplaintsActivity.class)));

        binding.btnViewFeedback.setOnClickListener(v -> startActivity(new Intent(this, ViewAllFeedbackActivity.class)));

        binding.btnViewUser.setOnClickListener(v -> startActivity(new Intent(this, ViewUsersActivity.class)));

        binding.btnMyProfile.setOnClickListener(v -> startActivity(new Intent(this, UserProfileActivity.class)));

        binding.cardLogout.setOnClickListener(v -> logoutAccount());
    }

    private void logoutAccount()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(OfficerHomeScreen.this);
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
}
