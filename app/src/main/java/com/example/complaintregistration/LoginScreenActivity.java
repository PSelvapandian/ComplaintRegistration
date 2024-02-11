package com.example.complaintregistration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.complaintregistration.Enum.RolesEnum;
import com.example.complaintregistration.ModelClasses.CustomUser;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityLoginScreenBinding;

import java.util.Locale;
import java.util.Objects;

public class LoginScreenActivity extends AppCompatActivity implements TextWatcher
{
    ActivityLoginScreenBinding binding;
    RetrofitViewModel retrofitViewModel;
    SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = new SharedPreferenceHelper(LoginScreenActivity.this);
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        binding.btnLogin.setOnClickListener(v -> loginUser());

        binding.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, UserRegistrationActivity.class));
            finish();
        });

        binding.edtUserName.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEND)
            {
                loginUser();
                return true;
            }
            return false;
        });

        binding.edtPassword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEND)
            {
                loginUser();
                return true;
            }
            return false;
        });


        retrofitViewModel.loginResult.observe(this, customUser -> {
            if (customUser != null)
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                Toast.makeText(this, "User created successfully...!", Toast.LENGTH_SHORT).show();
                if (sharedPreferenceHelper.getRole().equalsIgnoreCase(RolesEnum.OFFICER.name()))
                {
                    startActivity(new Intent(this, OfficerHomeScreen.class));
                }
                else
                {
                    startActivity(new Intent(this, MainActivity.class));
                }
                finish();
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, LoginScreenActivity.this);
            }
        });

        binding.edtUserName.addTextChangedListener(this);
        binding.edtPassword.addTextChangedListener(this);
    }

    private void loginUser()
    {
        String userName = !Objects.requireNonNull(binding.edtUserName.getText()).toString().isEmpty() ? binding.edtUserName.getText().toString().trim() : "";
        String password = !Objects.requireNonNull(binding.edtPassword.getText()).toString().isEmpty() ? binding.edtPassword.getText().toString().trim() : "";
        if (!userName.isEmpty() && !password.isEmpty())
        {
            if (userName.toLowerCase(Locale.ROOT).equals("admin") && password.toLowerCase(Locale.ROOT).equals("admin"))
            {
                sharedPreferenceHelper.setRole(RolesEnum.ADMIN.name());
                binding.constraintProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    binding.constraintProgressBar.setVisibility(View.GONE);
                    startActivity(new Intent(LoginScreenActivity.this, AdminHomeScreen.class));
                    finish();
                }, 3000);
            }
            else
            {
//                showErrorResponseDialog("Invalid username password, try again!");
                binding.constraintProgressBar.setVisibility(View.VISIBLE);
                CustomUser customUser = new CustomUser();
                customUser.setUserName(userName);
                customUser.setPassword(password);
                retrofitViewModel.loginAccount(customUser);
            }
        }
        else
        {
            if (binding.edtUserName.getText().toString().isEmpty())
            {
                binding.userNameLayout.setError("Username required");
            }
            if (binding.edtPassword.getText().toString().isEmpty())
            {
                binding.passwordLayout.setError("Password required");
            }
        }
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
        if (s == binding.edtUserName.getEditableText())
        {
            binding.userNameLayout.setErrorEnabled(false);
        }
        if (s == binding.edtPassword.getEditableText())
        {
            binding.passwordLayout.setErrorEnabled(false);
        }
    }
}
