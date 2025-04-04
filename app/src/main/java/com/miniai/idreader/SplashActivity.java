package com.miniai.idreader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miniai.idsdk.IdReader;

import java.io.InputStream;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends Activity implements IdReader.ICallbackInitialize {
    TextView textViewStatus = null;
    ProgressBar progressBarInitialize = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textViewStatus = findViewById(R.id.text_status);
        progressBarInitialize = findViewById(R.id.progress_initialize);

        _initializeSDK();
    }

    private void _initializeSDK() {
        textViewStatus.setVisibility(TextView.VISIBLE);
        progressBarInitialize.setVisibility(TextView.VISIBLE);

        byte[] pLicense = readLicense();
        if (pLicense == null){
            OnFailed("Failed to read license file.");
            return;
        }

        IdReader.GetInstance().initialize(this, pLicense, this);
    }

    private byte[] readLicense() {
        try  {
            InputStream inputStream = getResources().openRawResource(R.raw.license);
            int nLen = inputStream.available();
            byte[] ret = new byte[nLen];
            inputStream.read(ret);
            return ret;
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public void OnSuccess() {
        //Toast.makeText(this, "Success to initialize", Toast.LENGTH_LONG).show();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void OnFailed(String message) {
        textViewStatus.setText(message);
        progressBarInitialize.setVisibility(TextView.INVISIBLE);
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}