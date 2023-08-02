package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText emailEt,passwordEt;
    TextView create_Account_textview_btn;
    Button Login_btn;
    ProgressBar progressBar1;
    String password,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEt=findViewById(R.id.emailid_LOGIN);
        passwordEt=findViewById(R.id.pass1id_lOGIN);


        create_Account_textview_btn=findViewById(R.id.create_Account_textview_btn);
        Login_btn=findViewById(R.id.lOGIN_btn);
        progressBar1=findViewById(R.id.prog1_LOGIN);

        Login_btn.setOnClickListener(V->Login_user());
        create_Account_textview_btn.setOnClickListener(V->startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class)));

    }

    void Login_user(){

         email=emailEt.getText().toString();
         password=passwordEt.getText().toString();


        boolean isvalidated=validatedata(email,password);

        if(isvalidated){
            Login_Account_in_firebase(email,password);
        }
    }

    void changeInprogerss(boolean inprogeress) {
        if (inprogeress) {
            Login_btn.setVisibility(View.GONE);
            progressBar1.setVisibility((View.VISIBLE));
        } else {
            Login_btn.setVisibility(View.VISIBLE);
            progressBar1.setVisibility((View.GONE));
        }
    }


    boolean validatedata(String email,String password){

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEt.setError("invalid Email ");
            return false;
        }
        if(password.length()<6){
            passwordEt.setError("invalid password length");
            return  false;
        }
        return  true;
    }


    void Login_Account_in_firebase(String email,String password){
        changeInprogerss(true);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInprogerss(false);
                if(task.isSuccessful()){

                  if(firebaseAuth.getCurrentUser().isEmailVerified()){

                  startActivity(new Intent(LoginActivity.this,MainActivity.class));
                  finish();
                  }
                  else {
                      Utility.showTest(LoginActivity.this,"email not verified. please verify your email.");
                  }

                }
                else {
                    Utility.showTest(LoginActivity.this,task.getException().getLocalizedMessage());
                }

            }
        });

    }

}