package com.example.complaintregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.databinding.ActivityHomeAdminBinding;

public class AdminHomeScreen extends AppCompatActivity
{
    ActivityHomeAdminBinding binding;
    SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = new SharedPreferenceHelper(AdminHomeScreen.this);

        binding.btnMyProfile.setVisibility(View.GONE);

        binding.btnViewComplaints.setOnClickListener(v -> startActivity(new Intent(this, ViewAllComplaintsActivity.class)));

        binding.btnViewFeedback.setOnClickListener(v -> startActivity(new Intent(this, ViewAllFeedbackActivity.class)));

        binding.btnViewUser.setOnClickListener(v -> startActivity(new Intent(this, ViewUsersActivity.class)));

        binding.btnCreateOfficer.setOnClickListener(v -> startActivity(new Intent(this, CreateOfficerActivity.class)));

        binding.btnViewReport.setOnClickListener(v -> startActivity(new Intent(this, ViewReportActivity.class)));

        binding.cardLogout.setOnClickListener(v -> logoutAccount());
    }

    private void logoutAccount()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminHomeScreen.this);
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
