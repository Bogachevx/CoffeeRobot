package ru.robowizard.coffeerobot;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Handler;
import android.view.View;

public class MainActivity extends AppCompatActivity implements TCPListener{

    private TCPCommunicator tcpClient;
    private boolean isFirstLoad=true;
    private Handler UIHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        ConnectToServer();
    }

    private void ConnectToServer() {
        tcpClient = TCPCommunicator.getInstance();
        TCPCommunicator.addListener(this);
        tcpClient.init("192.168.1.36", 49152);
    }

    public void btnSendClick(View view)
    {
        String Drink;
        switch (view.getId())
        {
            case R.id.buttonAmericano:
            {
                Drink = "Americano";
                break;
            }
            case  R.id.buttonCappuccino:
            {
                Drink = "Cappuccino";
                break;
            }
            case R.id.buttonEspresso:
            {
                Drink = "Espresso";
                break;
            }
            case  R.id.buttonLatte:
            {
                Drink = "Latte";
                break;
            }
            default:
            {
                Drink = "null";
                break;
            }
        }
        setupDialog();
        TCPCommunicator.writeToSocket(Drink,UIHandler,this);
    }

    private ProgressDialog dialog;
    private void setupDialog() {
        dialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Making");
        dialog.setMessage("Please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setContentView(R.layout.activity_main);
        if(!isFirstLoad)
        {
            TCPCommunicator.closeStreams();
            ConnectToServer();
        }
        else
            isFirstLoad=false;
    }

    @Override
    public void onTCPMessageRecieved(String message) {
        final String theMessage=message;
        try {
            runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            dialog.hide();
                            Toast.makeText(getApplicationContext(), theMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                e.printStackTrace();
        }
    }

    @Override
    public void onTCPConnectionStatusChanged(boolean isConnectedNow) {
        if(isConnectedNow)
        {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Connected to server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
