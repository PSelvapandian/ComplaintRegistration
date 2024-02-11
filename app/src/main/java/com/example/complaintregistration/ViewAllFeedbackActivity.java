package com.example.complaintregistration;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.complaintregistration.Adapter.UserListAdapter;
import com.example.complaintregistration.Adapter.ViewFeedbackAdapter;
import com.example.complaintregistration.ModelClasses.FeedBack;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityViewOfficerFeedbackBinding;

import java.util.List;

public class ViewAllFeedbackActivity extends AppCompatActivity
{
    ActivityViewOfficerFeedbackBinding binding;
    RetrofitViewModel retrofitViewModel;
    ViewFeedbackAdapter viewFeedbackAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewOfficerFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        retrofitViewModel.feedbackList.observe(this, list -> {
            if (list != null)
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                setFeedbackList(list);
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, ViewAllFeedbackActivity.this);
            }
        });

        binding.btnBack.setOnClickListener(v -> finish());

        getAllFeedback();
    }

    private void setFeedbackList(List<FeedBack> list)
    {
        viewFeedbackAdapter = new ViewFeedbackAdapter();
        binding.recyclerviewFeedbacks.setLayoutManager(new LinearLayoutManager(this));
        viewFeedbackAdapter.feedbackList(list);
        binding.recyclerviewFeedbacks.setAdapter(viewFeedbackAdapter);
        binding.emptyString.setVisibility(list.size() > 0 ? View.GONE : View.VISIBLE);
    }

    private void getAllFeedback()
    {
        binding.constraintProgressBar.setVisibility(View.VISIBLE);
        retrofitViewModel.getAllFeedBacks();
    }
}
