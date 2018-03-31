package com.example.android.arduinobluetoothled;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static com.example.android.arduinobluetoothled.BtdAdapter.device;


public class BluetoothStart extends AppCompatActivity {
    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    public static final String TAG = BluetoothStart.class.getName();
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static BluetoothSocket Bts = null;
    private OutputStream connect = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_start);
        Task task = new Task();
            task.execute(mBluetoothAdapter);
    }
    public void openBluetoothIntent(View view){
        Intent intent = new Intent(this, BluetoothList.class);
        startActivity(intent);
    }

    public void redOn(View view){if(connect == null){ Toast toast=Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT); toast.show(); return;}
        write(Data.redOn); view.startAnimation(buttonClick);
    }
    public void yellowOn(View view){if(connect == null){ Toast toast=Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT); toast.show(); return;}
        write(Data.yellowOn); view.startAnimation(buttonClick);
    }
    public void greenOn(View view){if(connect == null){ Toast toast=Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT); toast.show(); return;}
        write(Data.greenOn); view.startAnimation(buttonClick);
    }
    public void allOn(View view){if(connect == null){ Toast toast=Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT); toast.show(); return;}
        write(Data.allOn); view.startAnimation(buttonClick);
    }
    public void redOff(View view){if(connect == null){ Toast toast=Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT); toast.show(); return;}
        write(Data.redOff); view.startAnimation(buttonClick);
    }
    public void yellowOff(View view){if(connect == null){ Toast toast=Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT); toast.show(); return;}
        write(Data.yellowOff); view.startAnimation(buttonClick);
    }
    public void greenOff(View view){if(connect == null){ Toast toast=Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT); toast.show(); return;}
        write(Data.greenOff); view.startAnimation(buttonClick);
    }
    public void allOff(View view){if(connect == null){ Toast toast=Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT); toast.show(); return;}
        write(Data.allOff); view.startAnimation(buttonClick);
    }

    private class Task extends AsyncTask<BluetoothAdapter, Void, Void> {
        @Override
        protected Void doInBackground(BluetoothAdapter... mBluetoothAdapters) {
                try {
                    Bts = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    Bts.connect();
                    connect = Bts.getOutputStream();
                } catch (IOException e) {
                    Log.e(TAG, "Exception at doInBackground ...");
                }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
            progressBar.setVisibility(View.GONE);
            if (!Bts.isConnected()) {
                Toast.makeText(getBaseContext(), "Could not connect to selected device", Toast.LENGTH_SHORT).show();
            } else {
                Toast toast = Toast.makeText(getBaseContext(), "Connected!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Toast toast = Toast.makeText(getBaseContext(),"One moment please...", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private void write(byte[] bytes){
        try{
            connect.write(bytes);
        }
        catch (IOException e){
            Log.e(TAG,"Exception at write function!");
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        try {
            Bts.close();
        }
        catch (IOException e) {
            Log.e(TAG, "Exception at Destory");
        }
    }
}
