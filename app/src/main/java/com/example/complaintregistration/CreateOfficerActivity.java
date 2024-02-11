package com.example.complaintregistration;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.complaintregistration.Enum.Gender;
import com.example.complaintregistration.Enum.RolesEnum;
import com.example.complaintregistration.ModelClasses.Address;
import com.example.complaintregistration.ModelClasses.CustomUser;
import com.example.complaintregistration.ModelClasses.Roles;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityCreateOfficerBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class CreateOfficerActivity extends AppCompatActivity implements TextWatcher
{
    ActivityCreateOfficerBinding binding;
    RetrofitViewModel retrofitViewModel;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        binding = ActivityCreateOfficerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        binding.edtDate.setOnClickListener(v -> getDate());

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnRegister.setOnClickListener(v -> insertUserData());

        retrofitViewModel.loginResult.observe(this, customUser -> {
            if (customUser != null)
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog( "Officer created successfully...!", CreateOfficerActivity.this);
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, CreateOfficerActivity.this);
            }
        });

        binding.edtEmail.addTextChangedListener(this);
        binding.edtPassword.addTextChangedListener(this);
        binding.edtMobileNumber.addTextChangedListener(this);
    }

    private void insertUserData()
    {
        List<Roles> rolesList = new ArrayList<>();
        Roles roles = new Roles();
        roles.setRoleName(RolesEnum.OFFICER.name());
        rolesList.add(roles);

        String gender = getGender();
        String name = !Objects.requireNonNull(binding.edtName.getText()).toString().isEmpty() ? binding.edtName.getText().toString() : "";
        String address = !Objects.requireNonNull(binding.edtAddress.getText()).toString().isEmpty() ? binding.edtAddress.getText().toString() : "";
        String email = !Objects.requireNonNull(binding.edtEmail.getText()).toString().isEmpty() ? binding.edtEmail.getText().toString() : "";
        String contactNumber = !Objects.requireNonNull(binding.edtMobileNumber.getText()).toString().isEmpty() ? binding.edtMobileNumber.getText().toString() : "";
        String password = !Objects.requireNonNull(binding.edtPassword.getText()).toString().isEmpty() ? binding.edtPassword.getText().toString() : "";
        if (!email.isEmpty() && !password.isEmpty())
        {
            if (contactNumber.matches("[6-9][0-9]{9}"))
            {
                binding.constraintProgressBar.setVisibility(View.VISIBLE);
                CustomUser customUser = new CustomUser();
                Address addressModel = new Address();
                addressModel.setArea(address);
                customUser.insertUser(name, email, null, gender, contactNumber, password, addressModel, rolesList);
                retrofitViewModel.createUser(customUser);
            }
            else
            {
                binding.mobileNumberLayout.setError("Invalid mobile number!");
            }
        }
        else
        {
            if (binding.edtEmail.getText().toString().isEmpty())
            {
                binding.emailLayout.setError("Email required");
            }
            if (binding.edtPassword.getText().toString().isEmpty())
            {
                binding.passwordLayout.setError("Password required");
            }
        }
    }

    private void clearData()
    {
        binding.edtName.setText("");
        binding.edtDate.setText("");
        binding.edtEmail.setText("");
        binding.edtMobileNumber.setText("");
        binding.edtAddress.setText("");
        binding.edtPassword.setText("");
        binding.radioBtnMale.setChecked(false);
        binding.radioBtnFemale.setChecked(false);
        startActivity(new Intent(this, LoginScreenActivity.class));
        finish();
    }

    private String getGender()
    {
        if (binding.radioBtnMale.isChecked())
        {
            return Gender.M.name();
        }
        else if (binding.radioBtnFemale.isChecked())
        {
            return Gender.F.name();
        }
        else if (binding.radioBtnTrans.isChecked())
        {
            return Gender.TG.name();
        }
        else
        {
            return "";
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void getDate()
    {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            String dateString = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
            binding.edtDate.setText(dateString);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        if (s == binding.edtEmail.getEditableText())
        {
            binding.emailLayout.setErrorEnabled(false);
        }
        if (s == binding.edtPassword.getEditableText())
        {
            binding.passwordLayout.setErrorEnabled(false);
        }
        if (s == binding.edtMobileNumber.getEditableText())
        {
            binding.mobileNumberLayout.setErrorEnabled(false);
        }
    }
}
