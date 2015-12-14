package com.chisw.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chisw.commonui.dialog.SimpleDialog;

public class StartSimpleDialogActivity extends AppCompatActivity implements SimpleDialog.OnSimpleDialogCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_dialog);
    }

    public void showDialog(View v) {
        SimpleDialog.initInstance("Use Google\'s location service?",
                "Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running.")
                .show(getSupportFragmentManager(), "Simple dialog");
    }

    @Override
    public void onOk() {
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
    }
}
