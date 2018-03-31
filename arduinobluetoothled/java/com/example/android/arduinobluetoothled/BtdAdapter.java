package com.example.android.arduinobluetoothled;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import static android.R.attr.start;

/**
 * Created by user on 5/12/2017.
 */

public class BtdAdapter extends ArrayAdapter {
    public static BluetoothDevice device = null;
    public static final String LOG_TAG = MainActivity.class.getName();
    public BtdAdapter(Context context, ArrayList<Btd> list) {
        super(context, 0, list);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final Btd currentDevice = (Btd) getItem(position);
        Button name = (Button) listItemView.findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BluetoothStart.class);
                device = currentDevice.getBluetoothDevice();
                getContext().startActivity(intent);
                }
        });
        name.setText(currentDevice.getName());
        return listItemView;
    }
}
