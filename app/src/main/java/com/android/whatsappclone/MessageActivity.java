package com.android.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.whatsappclone.Adapter.MessageAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    FirebaseUser fuser;
    DatabaseReference reference, ref;
    DatabaseReference databaseReference;
    String userName, value;
    MaterialToolbar appbar;

    RecyclerView chatrecyclerView;
    ImageButton send;
    EditText msgedittext;

    ImageView propic;
    TextView title;

    MessageAdapter messageAdapter;
    List<Chat> mChat;
    private String TAG="UserData";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        value = intent.getStringExtra("userId");
        fuser = FirebaseAuth.getInstance().getCurrentUser();


        mChat=new ArrayList<>();
        appbar = findViewById(R.id.appBar);
        appbar.setTitle("");
        propic = findViewById(R.id.userImageMessageActivity);
        title = findViewById(R.id.usernameMessageActivity);

        send = findViewById(R.id.btn_send);
        chatrecyclerView = findViewById(R.id.chatRecyclerView);
        msgedittext = findViewById(R.id.msgedittext);

        //  Toast.makeText(MessageActivity.this,value,Toast.LENGTH_SHORT).show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = String.valueOf(snapshot.child(value).child("name").getValue());
                title.setText(userName);
                Users users=snapshot.getValue(Users.class);
                readMessage(fuser.getUid(),value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        setSupportActionBar(appbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //appbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        appbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class));
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = msgedittext.getText().toString();
                    sendMessage(fuser.getUid(), value, msg);
                msgedittext.setText("");
            }
        });
        chatrecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chatrecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void sendMessage(String sender, String reciever, String message) {
        reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("reciever", reciever);
        hashMap.put("message", message);
        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(final String myId, final String userId) {
        ref = FirebaseDatabase.getInstance().getReference("Chats");
        mChat.clear();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Chat chat = snapshot1.getValue(Chat.class);
                    System.out.println(snapshot1.toString());
                    if (chat.getReciever().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReciever().equals(userId) && chat.getSender().equals(myId)) {
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, mChat);
                    chatrecyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}