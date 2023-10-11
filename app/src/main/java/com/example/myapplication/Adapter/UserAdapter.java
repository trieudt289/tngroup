package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.User.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> mListUser;

    public UserAdapter(List<User> mListUser) {
        this.mListUser = mListUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        if (user != null) {
            holder.tvName.setText("ID: " + user.getName());
            holder.tvGmail.setText("Gmail: " + user.getGmail());

            if(user.getGender()==true){
                holder.imgStaff.setImageResource(R.drawable.male);
            } else if (user.getGender()==false) {
                holder.imgStaff.setImageResource(R.drawable.female);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mListUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGmail;
        private TextView tvName;
        private ImageView imgStaff;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_Name);
            tvGmail = itemView.findViewById(R.id.txt_Gmail);
            imgStaff = itemView.findViewById(R.id.imgStaff);
        }
    }
}
