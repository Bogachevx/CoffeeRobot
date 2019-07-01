package ru.robowizard.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.net.Socket;

import ru.robowizard.myapplication.R;


public class MainActivity extends Activity {
    public ImageButton BB;
    public Socket socket;
    TT t;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BB = (ImageButton)findViewById(R.id.button);
        BB.setOnClickListener(pr);
        t=new TT();
    }

    private android.view.View.OnClickListener pr = new android.view.View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            t.start();
        }
    };

    class TT extends Thread{
        @Override
        public void run() {
            super.run();
            try
            {
                socket = new Socket("192.168.1.50", 52000);
                Log.e("лог", "соединение установлено");
            }
            catch(Exception ex){
                Log.e("лог", "ошибка");
            }
        }
    }
}