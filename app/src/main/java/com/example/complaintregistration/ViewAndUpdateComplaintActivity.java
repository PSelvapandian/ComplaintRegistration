package com.example.complaintregistration;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.complaintregistration.Enum.ComplaintStatus;
import com.example.complaintregistration.ModelClasses.Complaint;
import com.example.complaintregistration.Util.GPSTracker;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityViewComplaintBinding;
import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ViewAndUpdateComplaintActivity extends AppCompatActivity implements TextWatcher {
    ActivityViewComplaintBinding binding;
    String complaintId = "";
    RetrofitViewModel retrofitViewModel;
    Complaint updatedComplaint = new Complaint();

    GPSTracker gps;
    String latitude = "", longitude = "";
    int MY_PERMISSIONS_REQUEST_LOCATION = 1001, LOCATION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityViewComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        getComplaintId();

        binding.btnUpdate.setOnClickListener(v -> updateComplaint());

        binding.viewMap.setOnClickListener(v -> checkLocationPermission());

        binding.spinnerComplaintStatus.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.complaint_status)));

        binding.btnBack.setOnClickListener(v -> finish());

        retrofitViewModel.complaintData.observe(this, complaint -> {
            if (complaint != null)
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                setComplaintData(complaint);
            }
        });

        retrofitViewModel.successResponse.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, ViewAndUpdateComplaintActivity.this);
                getComplaintId();
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, ViewAndUpdateComplaintActivity.this);
            }
        });

        binding.edtReason.addTextChangedListener(this);
    }

    private void updateComplaint()
    {
        String status = binding.spinnerComplaintStatus.getSelectedItem().toString();
        String reason = Objects.requireNonNull(binding.edtReason.getText()).toString();
        if (status.equalsIgnoreCase(ComplaintStatus.PENDING.name()))
        {
            MessageDialog.showAlertDialog("Select status!", ViewAndUpdateComplaintActivity.this);
        }
        else
        {
            if (!reason.isEmpty())
            {
                updatedComplaint.setReason(reason);
                updatedComplaint.setComplaintStatus(status);
                binding.constraintProgressBar.setVisibility(View.VISIBLE);
                retrofitViewModel.updateComplaint(updatedComplaint);
            }
            else
            {
                binding.nameLayout.setError("Reason Required!");
            }
        }
    }

    private void setComplaintData(Complaint complaint)
    {
        updatedComplaint = complaint;
        String complaintStatus = complaint.getComplaintStatus() != null && !complaint.getComplaintStatus().isEmpty() ? complaint.getComplaintStatus() : ComplaintStatus.PENDING.name();
        binding.complaint.setText(complaint.getComplaintDetails() != null && !complaint.getComplaintDetails().isEmpty() ? complaint.getComplaintDetails() : "");
        binding.complaintStatus.setText(complaint.getComplaintStatus() != null && !complaint.getComplaintStatus().isEmpty() ? complaint.getComplaintStatus() : "");
        binding.address.setText(complaint.getLandmark() != null && !complaint.getLandmark().isEmpty() ? complaint.getLandmark() : "");

        if (complaintStatus.equalsIgnoreCase(ComplaintStatus.COMPLETED.name()))
        {
            binding.btnUpdate.setVisibility(View.GONE);
            binding.tvReason.setVisibility(View.GONE);
            binding.nameLayout.setVisibility(View.GONE);
        }

        getLatLonFromAddress(complaint.getLandmark() != null && !complaint.getLandmark().isEmpty() ? complaint.getLandmark() : "Coimbatore");
    }

    private void getComplaintId()
    {
        complaintId = getIntent().getStringExtra("COMPLAINT_ID") != null ? getIntent().getStringExtra("COMPLAINT_ID") : "";
        binding.constraintProgressBar.setVisibility(View.VISIBLE);
        retrofitViewModel.getComplaintByComplaintId(complaintId);
    }

    private void checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
        else
        {
            checkLocationEnableOrNot();
        }
    }

    private void checkLocationEnableOrNot()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            showLocationDialog();
        }
        else
        {
            openGoogleMap();
        }
    }

    private void openGoogleMap()
    {
        gps = new GPSTracker(getApplicationContext());

        double currentLat = gps.getLatitude();
        double currentLon = gps.getLongitude();

        Log.d("Location Latitude"," Longitude : " + currentLat + " / " + currentLon + " / " + latitude + " / " + longitude);
        String mapsUrl = "https://www.google.com/maps/dir/?api=1&origin=" + currentLat + "," + currentLon + "&destination=" + latitude + "," + longitude +
                "&travelmode=driving&dir_action=navigate";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl)));
    }


    private void showLocationDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewAndUpdateComplaintActivity.this);
        builder.setMessage("To continue, turn on device location, Which uses Google's location service");
        builder.setPositiveButton("Ok", (dialog, i) -> {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), LOCATION_REQUEST_CODE);
            dialog.cancel();
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private void getLatLonFromAddress(String address)
    {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try
        {
            addresses = geocoder.getFromLocationName(address, 1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
        if (addresses.size() > 0)
        {
            latitude = String.valueOf(addresses.get(0).getLatitude());
            longitude = String.valueOf(addresses.get(0).getLongitude());
            Log.d("LatLon from ", "address : " + latitude + " / " + longitude);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_REQUEST_CODE)
        {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                Toast.makeText(this, "Please enable your location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                checkLocationEnableOrNot();
            }
            else
            {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
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
        if (s == binding.edtReason.getEditableText())
        {
            binding.nameLayout.setErrorEnabled(false);
        }
    }
}
