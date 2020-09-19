package com.android.whatsappclone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.lang.UScript;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.whatsappclone.MessageActivity;
import com.android.whatsappclone.R;
import com.android.whatsappclone.UserLinkedList;
import com.android.whatsappclone.Users;
import com.android.whatsappclone.fragmnts.UsersFragment;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {


    public UserAdapter(Context context,UserLinkedList userList, int adapcount, ValueEventListener listener)
    {
        this.context=context;
        this.adapcount=adapcount;
        this.userlist = userList;
    }

    UserLinkedList userlist;
    int adapcount;
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.user_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(userlist.getUserr(position).getName());
        holder.imageView.setImageResource(R.drawable.ic_baseline_person_24);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MessageActivity.class);
                intent.putExtra("userId",userlist.getUserr(position).getUserId());
              //  Toast.makeText(context,userlist.getUserr(position).getUserId(),Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adapcount;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.propicuseritem);
            textView = itemView.findViewById(R.id.textViewuserItem);

        }

    }
}
