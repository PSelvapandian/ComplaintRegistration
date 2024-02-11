package com.example.complaintregistration;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.complaintregistration.Enum.ComplaintStatus;
import com.example.complaintregistration.Enum.Department;
import com.example.complaintregistration.ModelClasses.Address;
import com.example.complaintregistration.ModelClasses.Complaint;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityRegisterComplaintBinding;

import java.util.Objects;

public class ComplaintRegisterActivity extends AppCompatActivity
{
    ActivityRegisterComplaintBinding binding;
    String complaintType = "";
    SharedPreferenceHelper sharedPreferenceHelper;
    RetrofitViewModel retrofitViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplicationContext());
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        binding.spinnerComplaintType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.complaint_type)));

        binding.spinnerComplaintType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (binding.spinnerComplaintType.getSelectedItem().equals(Department.SL.getDept()))
                {
                    complaintType = Department.SL.name();
                }
                else if (binding.spinnerComplaintType.getSelectedItem().equals(Department.RWT.getDept()))
                {
                    complaintType = Department.RWT.name();
                }
                else if (binding.spinnerComplaintType.getSelectedItem().equals(Department.WPL.getDept()))
                {
                    complaintType = Department.WPL.name();
                }
                else if (binding.spinnerComplaintType.getSelectedItem().equals(Department.R.getDept()))
                {
                    complaintType = Department.R.name();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSubmit.setOnClickListener(v -> submitComplaint());

        retrofitViewModel.complaintResult.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                showErrorResponseDialog(s);
                clearData();
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                showErrorResponseDialog(s);
            }
        });
    }

    private void clearData()
    {
        binding.edtName.setText("");
        binding.edtLandmark.setText("");
        binding.edtAddress.setText("");
        binding.edtComplaintMsg.setText("");
        binding.spinnerComplaintType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.complaint_type)));
    }

    private void submitComplaint()
    {
        String name = !Objects.requireNonNull(binding.edtName.getText()).toString().isEmpty() ? binding.edtName.getText().toString() : "";
        String landmark = !Objects.requireNonNull(binding.edtLandmark.getText()).toString().isEmpty() ? binding.edtLandmark.getText().toString() : "";
        String address = !Objects.requireNonNull(binding.edtAddress.getText()).toString().isEmpty() ? binding.edtAddress.getText().toString() : "";
        String complaintMsg = !Objects.requireNonNull(binding.edtComplaintMsg.getText()).toString().isEmpty() ? binding.edtComplaintMsg.getText().toString() : "";
        if (!landmark.isEmpty() && !complaintMsg.isEmpty())
        {
            binding.constraintProgressBar.setVisibility(View.VISIBLE);
            Complaint complaint = new Complaint();
            Address address1 = new Address();
            address1.setStreet(address);
            complaint.createComplaint(name, Long.valueOf(sharedPreferenceHelper.getUserId()), landmark, address1, complaintType, complaintMsg, ComplaintStatus.PENDING.name());
            retrofitViewModel.createComplaint(complaint);
        }
        else
        {
            if (binding.edtLandmark.getText().toString().isEmpty())
            {
                binding.landmarkLayout.setError("Landmark required");
            }
            if (binding.edtComplaintMsg.getText().toString().isEmpty())
            {
                binding.complaintLayout.setError("Complaint message required");
            }
        }
    }

    private void showErrorResponseDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintRegisterActivity.this);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.cancel());
        builder.setCancelable(false);
        builder.create().show();
    }
}
