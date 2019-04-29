package com.example.android.present;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    
    private Firebase mRoot;
    private EditText memail;
    private EditText mpassword;
    private Button msubmit;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();


        memail=findViewById(R.id.email);
        mpassword=findViewById(R.id.password);
        msubmit=findViewById(R.id.submit);
        mAuthListener=new FirebaseAuth.AuthStateListener( ) {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(MainActivity.this, AccountActivity.class));
                }
            }
        };

        msubmit.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                startSignIn();

            }
        });

    }

    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
        String email= memail.getText().toString();
        String password=mpassword.getText().toString();
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this,"Enter Valid info",Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>( ) {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Sign In problem",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
