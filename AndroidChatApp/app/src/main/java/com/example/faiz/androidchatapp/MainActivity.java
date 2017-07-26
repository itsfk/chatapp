package com.example.faiz.androidchatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
TextView registerUser;
EditText username, password;
    Button loginButton;
    private FirebaseAuth mAuth;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerUser = (TextView)findViewById(R.id.register);
        username=(EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);
        setAuthInstance();
        registerUser.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(MainActivity.this,Register.class));
          }
      });
  loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          onLogInUser();
      }
  });

    }

    private void setAuthInstance() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void onLogInUser() {
        if(getUsername().equals("") || getPassword().equals("")){
            Toast.makeText(this, "Not a Proper User", Toast.LENGTH_SHORT).show();
            //showFieldsAreRequired();
        }else {
            logIn(getUsername(),getPassword());
        }

   }

    private void logIn(String username, String password) {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
 mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
     @Override
     public void onComplete(@NonNull Task<AuthResult> task) {

         if(task.isSuccessful()){
             setUserOnline();

         }else {
             showAlertDialog(task.getException().getMessage(),true);
         }
     }
 });

    }

    private void setUserOnline() {
        if(mAuth.getCurrentUser()!=null ) {
            String userId = mAuth.getCurrentUser().getUid();
            FirebaseDatabase.getInstance()
                    .getReference().
                    child("users").
                    child(userId).
                    child("connection");
                 //   setValue(UsersChatAdapter.ONLINE);
        }

    }

    private void showFieldsAreRequired() {

        //showAlertDialog(getString(R.string.error_incorrect_email_pass),true);
    }
    private String getUsername() {
        return username.getText().toString().trim();
    }
    private String getPassword() {
        return password.getText().toString().trim();
    }

private void showAlertDialog(String message,boolean isCanceable){

}

}
