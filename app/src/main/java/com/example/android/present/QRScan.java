package com.example.android.present;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }
    @Override
    public void handleResult(Result result) {

        String IPAddress=result.getText();

        try{

            Intent intent = new Intent(QRScan.this, SendData.class);
            intent.putExtra("IPAddress",IPAddress);
            startActivity(intent);
        }

        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Scanning error occured. Try again.",Toast.LENGTH_SHORT).show();
        }



    }
    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }
    @Override
    protected void onResume() {
        super.onResume();

        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }


}
