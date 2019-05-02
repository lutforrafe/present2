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

public class SendData extends AppCompatActivity implements View.OnClickListener {
    public static final int SERVERPORT = 3003;
    private TextView textView;
    public static String roll,course;
    public static String SERVER_IP = "192.168.0.0";
    private Handler handler;
    private ClientThread clientThread;
    private Thread thread;
    private int clientTextColor;
    private LinearLayout msgList;
    public boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);



        handler = new Handler();
        msgList = findViewById(R.id.msgList);
//        msgList = findViewById(R.id.msgList);
        //    private LinearLayout msgList;
        clientTextColor = ContextCompat.getColor(this, R.color.textColor);
        roll = "160041011";
//        course = "CSE 4502";
        textView = findViewById(R.id.ip_txt);
        textView.setText(SERVER_IP);
    }

    public TextView textView(String message, int color) {
        if (null == message || message.trim().isEmpty()) {
            message = "<Empty Message>";
        }
        TextView tv = new TextView(this);
        tv.setTextColor(color);
        tv.setText(message + " [" + getTime() + "]");
        tv.setTextSize(20);
        tv.setPadding(0, 5, 0, 0);
        return tv;
    }

    public void showMessage(final String message, final int color) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                msgList.addView(textView(message, color));
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.connect){
            msgList.removeAllViews();
            showMessage("Connecting",clientTextColor);
            clientThread = new ClientThread();
            thread = new Thread(clientThread);
            thread.start();
            showMessage("Connected",clientTextColor);
            return;
        }
        if(view.getId() == R.id.send_info && flag){
            String message = getStudentInfo();
            showMessage(message,Color.RED);
            if(null != clientThread){
                clientThread.sendMessage(message);
                textView.setText(message);
                flag = false;
            }

        }
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:YYYY");
        return sdf.format(new Date());
    }

    private String getStudentInfo(){
        String info= roll+" "+course;
        return info;
    }

    class ClientThread implements Runnable {

        private Socket socket;
        private BufferedReader input;
        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);

                while(!Thread.currentThread().isInterrupted()){
                    this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = input.readLine();
                    if(null == message){
                        Thread.interrupted();
                        break;
                    }
                }
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
