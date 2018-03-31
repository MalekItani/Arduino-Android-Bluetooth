package com.example.android.blindapp;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by user on 5/18/2017.
 */

public class Btd extends ArrayAdapter {
    public static BluetoothDevice device = null;
    public static final String LOG_TAG = MainActivity.class.getName();
    public Btd(Context context, ArrayList<Bt> list) {
        super(context, 0, list);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final Bt currentDevice = (Bt) getItem(position);
        Button name = (Button) listItemView.findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                device = currentDevice.getBluetoothDevice();
                getContext().startActivity(intent);
            }
        });
        name.setText(currentDevice.getName());
        return listItemView;
    }
}
