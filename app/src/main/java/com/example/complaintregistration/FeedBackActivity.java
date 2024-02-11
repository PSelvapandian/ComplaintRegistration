package com.example.complaintregistration;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityUserFeedbackBinding;

import java.util.Objects;

public class FeedBackActivity extends AppCompatActivity implements TextWatcher {
    ActivityUserFeedbackBinding binding;
    long userId = 0;
    SharedPreferenceHelper sharedPreferenceHelper;
    RetrofitViewModel retrofitViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(this);
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSubmit.setOnClickListener(v -> insertFeedback());

        retrofitViewModel.feedBackSuccess.observe(this, s -> {
            if (s != null)
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                if (!s.isEmpty())
                {
                    binding.edtFeedback.setText("");
                    MessageDialog.showAlertDialog(s, FeedBackActivity.this);
                }
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, FeedBackActivity.this);
            }
        });

        binding.edtFeedback.addTextChangedListener(this);
    }

    private void insertFeedback()
    {
        userId = Long.parseLong(sharedPreferenceHelper.getUserId());
        String feedback = Objects.requireNonNull(binding.edtFeedback.getText()).toString();
        if (!feedback.isEmpty())
        {
            binding.constraintProgressBar.setVisibility(View.VISIBLE);
            retrofitViewModel.insertFeedback(userId, feedback);
        }
        else
        {
            binding.feedbackLayout.setError("Feedback required!");
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
        if (s == binding.edtFeedback.getEditableText())
        {
            binding.feedbackLayout.setErrorEnabled(false);
        }
    }
}
