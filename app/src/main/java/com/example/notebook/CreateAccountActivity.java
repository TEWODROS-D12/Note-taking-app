package com.example.notebook;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {

    EditText emailEt,passwordEt,cofirmpasswordEt;
    TextView login_textview_btn;
    Button create_account_btn;
    ProgressBar progressBar1;
    String confirm_password;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acount);

        emailEt=findViewById(R.id.emailid);
        passwordEt=findViewById(R.id.pass1id);
        cofirmpasswordEt=findViewById(R.id.pass2id);

        login_textview_btn=findViewById(R.id.login_textview);
        create_account_btn=findViewById(R.id.create_account_btn);
        progressBar1=findViewById(R.id.prog1);


        create_account_btn.setOnClickListener(V->createAccount());
        login_textview_btn.setOnClickListener(V->finish());

    }

    void  createAccount(){
        String email=emailEt.getText().toString();
              password=passwordEt.getText().toString();
              confirm_password=cofirmpasswordEt.getText().toString();

        boolean isvalidated=validatedata(email,password,confirm_password);

        if(isvalidated){
            create_Account_in_firebase(email,password);
        }

    }

    boolean validatedata(String email,String password,String conf_password){

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEt.setError("Email is invalid ");
            return false;
        }
        if(password.length()<6){
            passwordEt.setError("invalid password length");
            return  false;
        }

       if(!password.equals(confirm_password)){
            cofirmpasswordEt.setError("password not matched");
           return  false;
        }
     return  true;
    }

    void create_Account_in_firebase(String email,String password)
    {
        changeInprogerss(true);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CreateAccountActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInprogerss(false);
                if(task.isSuccessful()){
                    Toast.makeText(CreateAccountActivity.this, "Account is created successfully check email to verify", Toast.LENGTH_SHORT).show();
                 firebaseAuth.getCurrentUser().sendEmailVerification();
                 firebaseAuth.signOut();
                 finish();
                }
                else  {
                    Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


 void changeInprogerss(boolean inprogeress) {
     if (inprogeress) {
         create_account_btn.setVisibility(View.GONE);
         progressBar1.setVisibility((View.VISIBLE));
     } else {
         create_account_btn.setVisibility(View.VISIBLE);
         progressBar1.setVisibility((View.GONE));
     }

 }
}