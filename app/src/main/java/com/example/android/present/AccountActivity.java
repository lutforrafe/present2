package com.example.android.present;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class AccountActivity extends AppCompatActivity {

    private Button mlogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mlogout=findViewById(R.id.logout);


        mlogout.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
            }
        });
    }
}
