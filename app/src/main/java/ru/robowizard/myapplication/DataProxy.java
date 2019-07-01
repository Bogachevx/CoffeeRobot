package ru.robowizard.myapplication;

import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DataProxy {
    private static DataProxy instance;

    //Singleton
    public static DataProxy getInstance() {
        if (instance == null) {
            instance = new DataProxy();
        }
        return instance;
    }

    private DataProxy(){}

    public void sendAsync(final String message, final String ip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {

                    InetAddress serv_addr = InetAddress.getByName(ip);

                    DatagramSocket sock = new DatagramSocket();

                    byte [] buf = (message).getBytes();

                    DatagramPacket pack = new DatagramPacket(buf, message.length(), serv_addr, 52000);

                    sock.send(pack);

                    sock.close();

                    for (int i=0; i<buf.length;i++) buf[i]=0;
                    Log.i("UDP", "Send OK");
                }
                catch (Exception e){
                    Log.d("UDP", "Error: " + e);
                }
            }
        }).start();

    }

}