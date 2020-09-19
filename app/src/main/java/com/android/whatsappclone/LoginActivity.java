package com.android.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.whatsappclone.MainActivity;
import com.android.whatsappclone.R;
import com.android.whatsappclone.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button gotosignup,loginbtn;
    private TextInputLayout loginemail,loginpassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginemail=findViewById(R.id.et_login_email);
        loginpassword=findViewById(R.id.et_loginpassword);

        gotosignup=findViewById(R.id.gotosignuppage);
        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        auth=FirebaseAuth.getInstance();
        loginbtn=findViewById(R.id.btn_login);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser(v);
            }
        });



    }

    private void loginuser(View v) {
        if(!validateEmail() | !validatePassword())
        {
            return;
        }
        else
        {
            isUser();
        }
    }

    private void isUser() {

        String passwordlogstr=loginpassword.getEditText().getText().toString();
        String emailogstr=loginemail.getEditText().getText().toString();

        auth.signInWithEmailAndPassword(emailogstr, passwordlogstr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                            assert firebaseUser != null;
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private Boolean validateEmail()
    {
        String val=loginemail.getEditText().getText().toString();

        if(val.isEmpty())
        {
            loginemail.setError("Cannot be Empty");
            return false;
        }
        else {
            loginemail.setError(null);
            loginemail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword()
    {
        String val=loginpassword.getEditText().getText().toString();

        if(val.isEmpty())
        {
            loginpassword.setError("Cannot be empty");
            return false;
        }
        else {
            loginpassword.setError(null);
            return true;
        }
    }
}