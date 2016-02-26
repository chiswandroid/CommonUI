package com.chisw.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chisw.commonui.dialog.OkCancelDialog;

public class StartSimpleDialogActivity extends AppCompatActivity implements OkCancelDialog.OnSimpleDialogCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_dialog);
    }

    public void showDialog(View v) {
        OkCancelDialog.Builder builder = new OkCancelDialog.Builder();
        builder.setTitle(getString(R.string.temp_title))
                .setContent(getString(R.string.temp_content))
                .setCancelOnTouchOutside(true)
                .build()
                .show(getSupportFragmentManager(), "OkCancelDialog");
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
