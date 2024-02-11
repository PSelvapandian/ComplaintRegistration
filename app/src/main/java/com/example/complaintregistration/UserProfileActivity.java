package com.example.complaintregistration;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.complaintregistration.ModelClasses.CustomUser;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityUserProfileBinding;

public class UserProfileActivity extends AppCompatActivity
{
    ActivityUserProfileBinding binding;
    RetrofitViewModel retrofitViewModel;
    SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(this);
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        getUserData();

        retrofitViewModel.loginResult.observe(this, customUser -> {
            if (customUser != null)
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                setUser(customUser);
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, UserProfileActivity.this);
            }
        });

        binding.ivBack.setOnClickListener(v -> finish());
    }

    private void setUser(CustomUser customUser)
    {
        binding.userName.setText(customUser.getName() != null && !customUser.getName().isEmpty() ? customUser.getName() : "");
        binding.userEmail.setText(customUser.getEmail() != null && !customUser.getEmail().isEmpty() ? customUser.getEmail() : "");
        binding.userMobile.setText(customUser.getMobile() != null && !customUser.getMobile().isEmpty() ? customUser.getMobile() : "");
        binding.userAddress.setText(customUser.getAddress() != null && customUser.getAddress().getStreet() != null && !customUser.getAddress().getStreet().isEmpty() ? customUser.getAddress().getStreet() : (customUser.getAddress().getArea() != null && !customUser.getAddress().getArea().isEmpty() ? customUser.getAddress().getArea() : ""));
    }

    private void getUserData()
    {
        binding.constraintProgressBar.setVisibility(View.VISIBLE);
        retrofitViewModel.getUserData(sharedPreferenceHelper.getUserId());
    }
}
