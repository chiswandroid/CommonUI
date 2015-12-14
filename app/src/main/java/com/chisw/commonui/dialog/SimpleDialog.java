package com.chisw.commonui.dialog;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chisw.commonui.R;
import com.chisw.commonui.utils.TypefaceUtils;

public class SimpleDialog extends BaseMaterialDialog<SimpleDialog.OnSimpleDialogCallback> {

    public interface OnSimpleDialogCallback {
        void onOk();

        void onCancel();
    }

    private static final String TITLE = "Title";
    private static final String CONTENT = "Content";
    private static final String OK = "Ok";
    private static final String CANCEL = "Cancel";

    public static SimpleDialog initInstance(String title, String content, String ok, String cancel) {
        final SimpleDialog simpleDialog = new SimpleDialog();
        final Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(CONTENT, content);
        bundle.putString(OK, ok);
        bundle.putString(CANCEL, cancel);
        simpleDialog.setArguments(bundle);
        return simpleDialog;
    }

    public static SimpleDialog initInstance(String title, String message) {
        return initInstance(title, message, OK, CANCEL);
    }

    private String title;
    private String message;
    private String titleButtonOk;
    private String titleButtonCancel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(TITLE);
        message = getArguments().getString(CONTENT);
        titleButtonOk = getArguments().getString(OK);
        titleButtonCancel = getArguments().getString(CANCEL);
    }

    @LayoutRes
    protected int dialogLayoutResId() {
        return R.layout.dialog_simple;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(dialogLayoutResId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final TextView tvDialogTitle = (TextView) view.findViewById(R.id.tvDialogTitle);
        final TextView tvDialogContent = (TextView) view.findViewById(R.id.tvDialogContent);
        final Button btOk = (Button) view.findViewById(R.id.btOk);
        final Button btCancel = (Button) view.findViewById(R.id.btCancel);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            TypefaceUtils.setTypeface(tvDialogTitle, TypefaceUtils.ROBOTO_MEDIUM);
            TypefaceUtils.setTypeface(btOk, TypefaceUtils.ROBOTO_MEDIUM);
            TypefaceUtils.setTypeface(btCancel, TypefaceUtils.ROBOTO_MEDIUM);
        }
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onOk();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCancel();
            }
        });
        tvDialogTitle.setText(title);
        tvDialogContent.setText(message);
        btOk.setText(titleButtonOk);
        btCancel.setText(titleButtonCancel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            TypefaceUtils.clearCache();
        }
    }
}
