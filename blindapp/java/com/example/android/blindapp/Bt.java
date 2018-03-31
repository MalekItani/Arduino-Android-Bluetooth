package com.example.android.blindapp;

import android.bluetooth.BluetoothDevice;

/**
 * Created by user on 5/18/2017.
 */

public class Bt {
    private String name, address;
    public BluetoothDevice bluetoothDevice;
    public Bt(String n, String a, BluetoothDevice blue){
        name = n;
        address=a;
        bluetoothDevice=blue;
    }
    public String getName(){
        return name;
    }

    public String getAddress() {
        return address;
    }
    public BluetoothDevice getBluetoothDevice(){
        return bluetoothDevice;
    }
}
