package com.example.android.blindapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.android.blindapp.R.id.h;
import static com.example.android.blindapp.R.id.progress;

public class MainActivity extends AppCompatActivity {
    public AlphaAnimation click = new AlphaAnimation(1.0F, 0.3F);
    private static BluetoothSocket Bts = null;
    public static final String TAG = MainActivity.class.getName();
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public static final int REQUEST_ENABLE_BT = 1;
    private OutputStream os = null;
    private InputStream is = null;
    int dt1, dt2;
    private byte[] request_data = {'1'};
    int data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
        if (!bt.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        if (Btd.device != null) {
            Task task = new Task();
            task.execute(mBluetoothAdapter);
        } else {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
            progressBar.setVisibility(View.GONE);
        }

    }

    public void Bt(View view) {
        Intent intent = new Intent(MainActivity.this, BluetoothList.class);
        startActivity(intent);
    }

    private class Task extends AsyncTask<BluetoothAdapter, Void, Void> {
        @Override
        protected Void doInBackground(BluetoothAdapter... mBluetoothAdapters) {
            try {
                Bts = Btd.device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                Bts.connect();
                os = Bts.getOutputStream();
                is = Bts.getInputStream();
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
                Btd.device = null;
            } else {
                Toast toast = Toast.makeText(getBaseContext(), "Connected!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Toast toast = Toast.makeText(getBaseContext(), "One moment please...", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void send(byte[] bytes) {
        try {
            os.write(bytes);
        } catch (IOException e) {
            Log.e(TAG, "Exception at write function!");
        }
    }

    private void read() {
        try {
            data = is.read();
        } catch (IOException e) {
            Log.e(TAG, "Exception at read function");
        }
    }

    public void but(View view) {
        view.startAnimation(click);
        if (Btd.device != null) {
            send(request_data);
            read();
            dt1 = data + 5;
            dt2 = data - 5;
            TextView h = (TextView) findViewById(R.id.h);
            TextView hmin = (TextView) findViewById(R.id.hmin);
            TextView hmax = (TextView) findViewById(R.id.hmax);
            h.setText("" + data + " cm");
            hmin.setText("" + dt2 + " cm");
            hmax.setText("" + dt1 + " cm");
            ProgressBar progressBar=  (ProgressBar) findViewById(R.id.progress);
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(getBaseContext(), "Opening Maps...", Toast.LENGTH_SHORT).show();
            Intent intent =  new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_MAPS);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
                    }
        else {
            Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT).show();
        }
    }

    public void dc(View view) {
        if(Btd.device!= null) {
            try {
                Bts.close();
                Btd.device = null;
            } catch (IOException e) {
                //
            }
            Toast.makeText(getBaseContext(), "Device Disconnected", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getBaseContext(), "No Connection Established Yet", Toast.LENGTH_SHORT).show();
        }
    }
}
