package com.latihan.rs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    Context context;
    List<UserData> userList;

    public UserAdapter(Context context, List<UserData> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bottom_dialog, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserHolder holder, int position) {
        holder.tvUsername.setText((CharSequence) userList.get(position).getUsername());
        holder.tvAlamat.setText((CharSequence) userList.get(position).getAlamat());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        TextView tvUsername, tvAlamat;
        public UserHolder(View view) {
            super(view);
            tvUsername = view.findViewById(R.id.tv_username);
            tvAlamat = view.findViewById(R.id.tv_alamat);
        }
    }
}
