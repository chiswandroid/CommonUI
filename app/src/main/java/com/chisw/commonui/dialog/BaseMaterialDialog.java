package com.chisw.commonui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.chisw.commonui.R;

public abstract class BaseMaterialDialog<Callback> extends DialogFragment {

    protected Callback callback;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, dialogTheme());
        try {
            callback = (Callback) getActivity();
            assert callback != null;
        } catch (Exception exception) {
            throw new IllegalArgumentException(getContext().getString(R.string.noImplement));
        }
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
