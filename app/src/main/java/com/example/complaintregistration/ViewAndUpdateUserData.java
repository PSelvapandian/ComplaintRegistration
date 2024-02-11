package com.example.complaintregistration;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.complaintregistration.Enum.RolesEnum;
import com.example.complaintregistration.ModelClasses.CustomUser;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityViewUserDataBinding;

public class ViewAndUpdateUserData extends AppCompatActivity
{
    ActivityViewUserDataBinding binding;
    RetrofitViewModel retrofitViewModel;
    SharedPreferenceHelper sharedPreferenceHelper;
    String userId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewUserDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = new SharedPreferenceHelper(this);
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        getUserId();

        binding.btnUpdate.setOnClickListener(v -> updateUser());

        binding.spinnerRoles.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.roles)));

        retrofitViewModel.loginResult.observe(this, customUser -> {
            if (customUser != null)
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                setUser(customUser);
            }
        });

        retrofitViewModel.successResponse.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, ViewAndUpdateUserData.this);
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, ViewAndUpdateUserData.this);
            }
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void updateUser()
    {
        if (binding.spinnerRoles.getSelectedItem().toString().equalsIgnoreCase(RolesEnum.USER.name()))
        {
            MessageDialog.showAlertDialog("Select role!", ViewAndUpdateUserData.this);
        }
        else
        {
            binding.constraintProgressBar.setVisibility(View.GONE);
            retrofitViewModel.updateUser(userId, binding.spinnerRoles.getSelectedItem().toString());
        }
    }

    private void setUser(CustomUser customUser)
    {
        binding.userName.setText(customUser.getName() != null && !customUser.getName().isEmpty() ? customUser.getName() : "");
        binding.email.setText(customUser.getEmail() != null && !customUser.getEmail().isEmpty() ? customUser.getEmail() : "");
        binding.mobileNumber.setText(customUser.getMobile() != null && !customUser.getMobile().isEmpty() ? customUser.getMobile() : "");
        binding.address.setText(customUser.getAddress().getStreet() != null && !customUser.getAddress().getStreet().isEmpty() ? customUser.getAddress().getStreet() : "");
    }

    private void getUserId()
    {
        userId = getIntent().getStringExtra("USER_ID") != null ? getIntent().getStringExtra("USER_ID") : "";
        binding.constraintProgressBar.setVisibility(View.VISIBLE);
        retrofitViewModel.getUserData(userId);
    }
}
