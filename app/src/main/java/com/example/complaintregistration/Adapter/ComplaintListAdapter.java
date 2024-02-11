package com.example.complaintregistration.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaintregistration.Enum.ComplaintStatus;
import com.example.complaintregistration.ModelClasses.Complaint;
import com.example.complaintregistration.databinding.ListItemComplaintsBinding;

import java.util.List;

public class ComplaintListAdapter extends RecyclerView.Adapter<ComplaintListAdapter.ComplaintListViewHolder>
{
    List<Complaint> list;
    CompliantInterface compliantInterface;

    public ComplaintListAdapter()
    {

    }

    public void complaintList(List<Complaint> list, CompliantInterface compliantInterface)
    {
        this.list = list;
        this.compliantInterface = compliantInterface;
    }

    @NonNull
    @Override
    public ComplaintListAdapter.ComplaintListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemComplaintsBinding binding = ListItemComplaintsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ComplaintListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintListAdapter.ComplaintListViewHolder holder, int position)
    {
        holder.binding.complaint.setText(list.get(position).getComplaintDetails() != null && !list.get(position).getComplaintDetails().isEmpty() ? list.get(position).getComplaintDetails() : "");
        holder.binding.address.setText(list.get(position).getLandmark() != null && !list.get(position).getLandmark().isEmpty() ? list.get(position).getLandmark() : "");
        holder.binding.complaintStatus.setText(list.get(position).getComplaintStatus() != null && !list.get(position).getComplaintStatus().isEmpty() ? list.get(position).getComplaintStatus() : ComplaintStatus.PENDING.name());

        holder.binding.cardComplaint.setOnClickListener(v -> compliantInterface.passComplaintId(list.get(position).getComplaintId()));
    }

    @Override
    public int getItemCount()
    {
        return list != null ? list.size() : 0;
    }

    public class ComplaintListViewHolder extends RecyclerView.ViewHolder
    {
        ListItemComplaintsBinding binding;

        public ComplaintListViewHolder(@NonNull ListItemComplaintsBinding itemView)
        {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public interface CompliantInterface
    {
        void passComplaintId(Long complaintId);
    }
}
