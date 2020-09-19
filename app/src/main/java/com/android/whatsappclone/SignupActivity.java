package com.android.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.whatsappclone.LoginActivity;
import com.android.whatsappclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseDatabase rootnode;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;
    private String namestr,passwordstr,emailstr,mobnostr;
    private TextInputLayout name_su,email_su,password_su,mobno_su,city_su;
    private Button gotologin,signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name_su=findViewById(R.id.signup_name);
        email_su=findViewById(R.id.signup_Email);
        password_su=findViewById(R.id.signup_password);
        mobno_su=findViewById(R.id.signup_mobileno);
        signupbtn=findViewById(R.id.btn_signup);

        firebaseAuth=FirebaseAuth.getInstance();

        gotologin=findViewById(R.id.gotologinpage);

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() | !validateEmail() | !validatePassword() | !validatePhno())
                {
                    return;
                }

                rootnode= FirebaseDatabase.getInstance();
                reference = rootnode.getReference("Users");

                namestr =name_su.getEditText().getText().toString();
                emailstr=email_su.getEditText().getText().toString();
                passwordstr=password_su.getEditText().getText().toString();
                mobnostr=mobno_su.getEditText().getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(emailstr, passwordstr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    assert user != null;
                                    String userUid=user.getUid();

                                    Signuphelperclass signuphelperclass=new Signuphelperclass(namestr,emailstr,passwordstr,mobnostr);
                                    reference.child(userUid).setValue(signuphelperclass);

                                    startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });




    }

    private Boolean validateName()
    {
        String val=name_su.getEditText().getText().toString();

        if(val.isEmpty())
        {
            name_su.setError("Cannot be empty");
            return false;
        }
        else {
            name_su.setError(null);
            name_su.setErrorEnabled(false);  //remove previously entered space
            return true;
        }
    }

    private Boolean validateEmail()
    {
        String val=email_su.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty())
        {
            email_su.setError("Cannot be empty");
            return false;
        }
        else if (!val.matches(emailPattern)) {
            email_su.setError("Invalid Email");
            return false;
        }
        else {
            email_su.setError(null);
            return true;
        }
    }
    private Boolean validatePassword()
    {
        String val=password_su.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if(val.isEmpty())
        {
            password_su.setError("Cannot be empty");
            return false;
        }
        else if (!val.matches(passwordVal)) {
            password_su.setError("Password is too weak");
            return false;
        }
        else {
            password_su.setError(null);
            return true;
        }
    }

    private Boolean validatePhno()
    {
        String val=mobno_su.getEditText().getText().toString();

        if(val.isEmpty())
        {
            mobno_su.setError("Cannot be empty");
            return false;
        }
        else {
            mobno_su.setError(null);
            return true;
        }
    }

}