package com.example.android.arduinobluetoothled;

/**
 * Created by user on 5/16/2017.
 */

public interface Data {
    byte[] redOn = {'R'};
    byte[] yellowOn = {'Y'};
    byte[] greenOn = {'G'};
    byte[] allOn = {'N'};
    byte[] redOff = {'1'};
    byte[] yellowOff = {'2'};
    byte[] greenOff = {'3'};
    byte[] allOff = {'F'};
}
