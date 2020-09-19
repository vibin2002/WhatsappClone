package com.android.whatsappclone.fragmnts;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.whatsappclone.Adapter.UserAdapter;
import com.android.whatsappclone.MessageActivity;
import com.android.whatsappclone.R;
import com.android.whatsappclone.UserLinkedList;
import com.android.whatsappclone.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UsersFragment extends Fragment {

    int count=0;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    DatabaseReference databaseReference;


    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewusers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int childrenCount = (int) snapshot.getChildrenCount();
                Users[] users = new Users[childrenCount];
                UserLinkedList userLinkedList = new UserLinkedList();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String x = String.valueOf(dataSnapshot.child("name").getValue());
                    String y = String.valueOf(dataSnapshot.getKey());
                    users[count] = new Users(x,y,"default");
                    userLinkedList.insertItem(users[count]);
                }
                userAdapter = new UserAdapter(getActivity(),userLinkedList, childrenCount,this);
                recyclerView.setAdapter(userAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;


    }
}