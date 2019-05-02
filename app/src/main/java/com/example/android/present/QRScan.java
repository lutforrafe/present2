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

        String QRVal=result.getText();
        String IP1=null,IP2=null,IP3=null,IP4=null,Subject=null,CourseID=null;
        int divider,code;
        String[] arrOfStr = QRVal.split("\\.", 7);

        try {
            IP1 = arrOfStr[0];
            IP2 = arrOfStr[1];
            IP3 = arrOfStr[2];
            IP4 = arrOfStr[3];
            switch (arrOfStr[4]) {
                case ("1"):
                    Subject = "CSE";
                    break;
                case ("2"):
                    Subject = "EEE";
                    break;
                case ("3"):
                    Subject = "CEE";
                    break;
                case ("4"):
                    Subject = "MCE";
                    break;
                case ("5"):
                    Subject = "MAT";
                    break;
                case ("6"):
                    Subject = "HUM";
                    break;
                case ("7"):
                    Subject = "BTM";
                    break;
                case ("8"):
                    Subject = "TVE";
                    break;
                default:
                    Subject = "QQQ";
            }
            code = Integer.valueOf(arrOfStr[5]);
            divider = Integer.valueOf(arrOfStr[6]);
            CourseID = String.valueOf(code/divider);
        }
        catch(Exception e){

        }

        try{

            Intent intent = new Intent(QRScan.this, SendData.class);
            SendData.SERVER_IP = IP1+"."+IP2+"."+IP3+"."+IP4;
            SendData.course = Subject+" "+CourseID;
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
