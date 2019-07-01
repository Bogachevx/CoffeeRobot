package ru.robowizard.coffeerobot;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements TCPListener{

    private TCPCommunicator tcpClient;
    private boolean isFirstLoad=true;
    private Handler UIHandler = new Handler();

    public static final String APP_PREFERENCES = "IPPORT";
    public String IP = "192.168.1.36";
    public int PORT = 49152;
    private SharedPreferences mSettings;

    private EditText ip;
    private EditText port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        generateNoteOnSD();
        ConnectToServer();
    }

    private int secretcounter = 0;
    public void secretButtonClicked(View view)
    {
        secretcounter++;
        if (secretcounter == 5)
        {
            Toast.makeText(getApplicationContext(), "SECRET BUTTON", Toast.LENGTH_SHORT).show();
        }
    }

    private void ConnectToServer() {
        tcpClient = TCPCommunicator.getInstance();
        TCPCommunicator.addListener(this);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        tcpClient.init(IP, PORT);
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
        dialog.setTitle("Идёт приготовление напитка");
        dialog.setMessage("Ожидайте...");
        dialog.setIndeterminate(true);
        dialog.show();
    }

    public void generateNoteOnSD() {
        try {

            File root = new File(Environment.getExternalStorageDirectory(), "CoffeeRobot");
            if (!root.exists()) {
                boolean mkdir = root.mkdir();
                File gpxfile = new File(root, "ini.txt");
                gpxfile.createNewFile();
                FileWriter writer = new FileWriter(gpxfile);

                writer.append("192.168.0.2\n49152\n");
                writer.flush();
                writer.close();
                Toast.makeText(getApplicationContext(), "First init", Toast.LENGTH_SHORT).show();
            } else
            {
                File file = new File(root, "ini.txt");
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String lineData = bufferedReader.readLine();
                IP = lineData;
                lineData = bufferedReader.readLine();
                PORT = Integer.valueOf(lineData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

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
                            if (dialog.isShowing())
                                dialog.hide();
                            //Toast.makeText(getApplicationContext(), "Your welcome!", Toast.LENGTH_SHORT).show();
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
