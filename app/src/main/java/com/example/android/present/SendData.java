package com.example.android.present;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SendData extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String IPAddress= bundle.getString("IPAddress");


        textView = findViewById(R.id.ip_txt);
        textView.setText(IPAddress);
    }
}
