package com.example.complaintregistration.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaintregistration.Interface.UserInterface;
import com.example.complaintregistration.ModelClasses.CustomUser;
import com.example.complaintregistration.databinding.ListItemAllUsersBinding;

import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder>
{
    List<CustomUser> list;
    UserInterface userInterface;

    public UserListAdapter()
    {

    }

    public void userList(List<CustomUser> list, UserInterface userInterface)
    {
        this.list = list;
        this.userInterface = userInterface;
    }

    @NonNull
    @Override
    public UserListAdapter.UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemAllUsersBinding binding = ListItemAllUsersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserListViewHolder holder, int position)
    {
        holder.binding.userName.setText(list.get(position).getName() != null && !list.get(position).getName().isEmpty() ? list.get(position).getName() : "");
        holder.binding.email.setText(list.get(position).getEmail() != null && !list.get(position).getEmail().isEmpty() ? list.get(position).getEmail() : "");
        holder.binding.mobile.setText(list.get(position).getMobile() != null && !list.get(position).getMobile().isEmpty() ? list.get(position).getMobile() : "");
        holder.binding.address.setText(list.get(position).getAddress() != null && list.get(position).getAddress().getStreet() != null && !list.get(position).getAddress().getStreet().isEmpty() ? list.get(position).getAddress().getStreet() : "");

        holder.binding.cardUser.setOnClickListener(v -> userInterface.passUserId(list.get(position).getUserId()));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder
    {
        ListItemAllUsersBinding binding;

        public UserListViewHolder(@NonNull ListItemAllUsersBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
