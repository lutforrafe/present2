package com.example.android.present;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendData extends AppCompatActivity {
    public static final int SERVERPORT = 3003;
    private TextView textView;
    private String roll;
    public static String SERVER_IP = "192.168.0.0";
    private Handler handler;
    private ClientThread clientThread;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        SERVER_IP= bundle.getString("IPAddress");

        handler = new Handler();

//        msgList = findViewById(R.id.msgList);
        //    private LinearLayout msgList;
        int clientTextColor = ContextCompat.getColor(this, R.color.textColor);
        roll = "160041011";
        textView = findViewById(R.id.ip_txt);
        textView.setText(SERVER_IP);
        Button sendInfo = findViewById(R.id.send_info);
        sendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String message = getStudentInfo();
                    clientThread.sendMessage(message);
                    textView.setText(message);
                }
                catch (Exception e){

                }
            }
        });
    }

//    public void showMessage(final String message, final int color) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                msgList.addView(textView(message, color));
//            }
//        });
//    }

    String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    String getStudentInfo(){
        StringBuilder message = null;
        message.append(roll) ;
        message.append(" [");
        message.append(getTime());
        message.append("]");
        String Info = message.toString();
        return Info;
    }

    class ClientThread implements Runnable {

        private Socket socket;

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        void sendMessage(final String message) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (null != socket) {
                            PrintWriter out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream())),
                                    true);
                            out.println(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != clientThread) {
            clientThread.sendMessage("Disconnect");
            clientThread = null;
        }
    }

}
