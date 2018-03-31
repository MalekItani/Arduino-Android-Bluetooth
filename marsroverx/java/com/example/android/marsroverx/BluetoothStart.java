package com.example.android.marsroverx;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

import io.github.controlwear.virtual.joystick.android.JoystickView;

import static android.R.attr.angle;
import static android.R.attr.radius;
import static android.R.attr.value;
import static com.example.android.marsroverx.BtdAdapter.device;
import static com.example.android.marsroverx.BtdAdapter.device;
import static com.example.android.marsroverx.R.id.drive;
import static com.example.android.marsroverx.R.id.spin;


public class BluetoothStart extends AppCompatActivity {
    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    public static final String TAG = BluetoothStart.class.getName();
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static BluetoothSocket Bts = null;
    private OutputStream connect = null;
    private boolean enabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_start);
        Task task = new Task();
            task.execute(mBluetoothAdapter);


        JoystickView joy = (JoystickView) findViewById(R.id.spin);
        joy.setButtonColor(Color.parseColor("#00FFFF"));
        joy.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override

            public void onMove(int angle, int strength) {
                if (enabled) {
                    write(new byte[]{'W', Byte.valueOf("" + strength), Byte.valueOf("" + (int) (angle / 10)),'~'});
                }
            }
        });

        Button drive = (Button) findViewById(R.id.drive);
        drive.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    write(Data.enab);
                    enabled = true;
                }
                else if(event.getActionMasked() == MotionEvent.ACTION_UP)
                {
                    write(Data.disab);
                    enabled = false;
                }
                return false;
            }
        });
        Button spinRight = (Button) findViewById(R.id.sideArmSpin);
        spinRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    write(Data.spin);
                }
                else if(event.getActionMasked() == MotionEvent.ACTION_UP)
                {
                    write(Data.haltSpin);
                }
                return false;
            }
        });
        Button spinLeft = (Button) findViewById(R.id.sideArmSpinReverse);
        spinLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    write(Data.spinReverse);
                }
                else if(event.getActionMasked() == MotionEvent.ACTION_UP)
                {
                    write(Data.haltSpin);
                }
                return false;
            }
        });
        }
    public void openBluetoothIntent(View view) {
        Intent intent = new Intent(this, BluetoothList.class);
        startActivity(intent);
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
        if(Bts !=null) {
            try {
                connect.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "Exception at write function!");
            }
        }
        else{
            Toast.makeText(getBaseContext(), "No connection made yet!", Toast.LENGTH_SHORT).show();
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
