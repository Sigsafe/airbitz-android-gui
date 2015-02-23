package com.sigsafe;

import android.nfc.*;
import android.nfc.tech.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.io.IOException;
import android.util.Log;


public class NfcActivity extends Activity {

    private NfcAdapter mNfcAdapter;
    private NfcApi mNfcApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mNfcApi = new NfcApi();

        if (mNfcAdapter == null) {
            handleNFCUnsupported();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            handleNFCDisabled();
            return;
        }

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = IsoDep.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    IsoDep isoDep = IsoDep.get(tag);
                    try {
                        isoDep.connect();
                        mNfcApi.setIsoDep(isoDep);
                        byte[] result = mNfcApi.echo();
                    }
                    catch(IOException e){
                        // TODO
                    }
                    break;
                }
            }
        }
    }

    private void handleNFCUnsupported() {
        // TODO
    }

    private void handleNFCDisabled() {
        // TODO
    }
}