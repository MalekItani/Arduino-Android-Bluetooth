package com.example.android.marsroverx;

import android.bluetooth.BluetoothDevice;

/**
 * Created by user on 5/12/2017.
 */

public class Btd {
    private String name, address;
    public BluetoothDevice bluetoothDevice;
    public Btd(String n, String a, BluetoothDevice blue){
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
