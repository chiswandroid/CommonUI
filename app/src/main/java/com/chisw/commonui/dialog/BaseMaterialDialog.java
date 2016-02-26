package com.chisw.commonui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chisw.commonui.R;

public abstract class BaseMaterialDialog<Callback> extends DialogFragment {
    private static final String TAG = BaseMaterialDialog.class.getSimpleName();

    protected Callback callback;
    protected Decorate decorate;

    public interface Decorate {
        void decorate(View view, String fragmentTag);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (Callback) getActivity();
        } catch (ClassCastException exception) {
            throw new IllegalArgumentException(getContext().getString(R.string.noImplement));
        }
        try {
            decorate = (Decorate) getActivity();
        } catch (ClassCastException exception) {
            Log.d(TAG, exception.getMessage());
        }
        onParseArguments(getArguments());
        setStyle(STYLE_NO_FRAME, dialogTheme());
    }

    protected void onParseArguments(Bundle arguments) {
    }

    @StyleRes
    protected int dialogTheme() {
        return R.style.MaterialDialog;
    }

    @LayoutRes
    protected abstract int dialogLayoutResId();

    protected void decorateDialog(Dialog dialog) {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        decorateDialog(dialog);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(dialogLayoutResId(), container, false);
    }
}
