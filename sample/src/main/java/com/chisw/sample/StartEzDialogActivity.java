package com.chisw.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chisw.commonui.dialog.BaseMaterialDialog;
import com.chisw.commonui.dialog.EzDialog;

public class StartEzDialogActivity extends AppCompatActivity implements
        EzDialog.OnEzDialogCallback, BaseMaterialDialog.Decorate {
    private static final String TAG = StartEzDialogActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_dialog);
    }

    public void showSimpleDialog(View v) {
        EzDialog.Builder builder = new EzDialog.Builder();
        builder.setTitle(getString(R.string.temp_title))
                .setContent(getString(R.string.temp_content))
                .setCancelOnTouchOutside(true)
                .build()
                .show(getSupportFragmentManager(), "EzDialog");
    }

    @Override
    public void onEzPositiveClick(String fragmentTag) {
        Toast.makeText(getApplicationContext(), "Agree", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEzNegativeClick(String fragmentTag) {
        Toast.makeText(getApplicationContext(), "Disagree", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void decorate(View view, String fragmentTag) {
        Log.d(TAG, fragmentTag);
        switch (view.getId()) {
            case R.id.btCancel:
                ((Button) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                break;
            case R.id.btOk:
                ((Button) view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                break;
        }
    }
}
