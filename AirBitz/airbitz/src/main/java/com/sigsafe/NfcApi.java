package com.sigsafe;

import android.util.Log;

import java.io.IOException;

/**
 * Sigsafe 2015/02/22
 */

import android.nfc.tech.IsoDep;

public class NfcApi {

    private IsoDep mIsoDep;

    public NfcApi() {}

    public NfcApi(IsoDep isoDep) {
        mIsoDep = isoDep;
    }

    public void setIsoDep(IsoDep isoDep) {
        mIsoDep = isoDep;
    }

    public byte[] echo() throws IOException {

        byte[] ECHO = {
                (byte) 0x00, // CLA = 00 (first interindustry command set)
                (byte) 0xA4,
                (byte) 0x04,
                (byte) 0x0C,
                (byte) 0x06,
                (byte) 0x31,
                (byte) 0x35,
                (byte) 0x38,
                (byte) 0x34,
                (byte) 0x35,
                (byte) 0x46
        };
        byte[] result = mIsoDep.transceive(ECHO);
        Log.d("Sigsafe", byteArrayToHex(result));
        return result;
    }

    // TODO: for debugging only
    private String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }
}
