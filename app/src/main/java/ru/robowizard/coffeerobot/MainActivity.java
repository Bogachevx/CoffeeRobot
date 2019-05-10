package ru.robowizard.coffeerobot;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

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
        tcpClient.init("192.168.1.36", 48569);
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
        TCPCommunicator.writeToSocket(Drink,UIHandler,this);
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
        // TODO Auto-generated method stub
        final String theMessage=message;
        try {
            runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            //EditText editTextFromServer =(EditText)findViewById(R.id.editTextFromServer);
                            //editTextFromServer.setText(theMessage);
                            Toast.makeText(getApplicationContext(), theMessage, Toast.LENGTH_SHORT).show();

                        }
                    });

                } catch (Exception e) {
                e.printStackTrace();
        }


    }


    @Override
    public void onTCPConnectionStatusChanged(boolean isConnectedNow) {
        // TODO Auto-generated method stub
        if(isConnectedNow)
        {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    //dialog.hide();
                    Toast.makeText(getApplicationContext(), "Connected to server", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
