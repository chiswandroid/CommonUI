package com.chisw.commonui.dialog;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chisw.commonui.R;

public class EzDialog extends BaseMaterialDialog<EzDialog.OnEzDialogCallback> {
    private static final String BUILDER = "Builder";

    public interface OnEzDialogCallback {
        void onEzPositiveClick(String fragmentTag);

        void onEzNegativeClick(String fragmentTag);
    }

    private Builder builder;

    @Override
    protected void onParseArguments(Bundle arguments) {
        builder = getArguments().getParcelable(BUILDER);
        if (builder == null) {
            throw new IllegalArgumentException(getContext().getString(R.string.illegalBuilder));
        }
    }

    @Override
    protected void decorateDialog(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(builder.cancelOnTouchOutside);
    }

    @LayoutRes
    @Override
    protected int dialogLayoutResId() {
        return R.layout.dialog_simple;
    }

    @Override
    protected int dialogTheme() {
        return builder.dialogTheme != 0 ? builder.dialogTheme : super.dialogTheme();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final TextView tvDialogTitle = (TextView) view.findViewById(R.id.tvDialogTitle);
        final TextView tvDialogContent = (TextView) view.findViewById(R.id.tvDialogContent);
        final Button btOk = (Button) view.findViewById(R.id.btOk);
        final Button btCancel = (Button) view.findViewById(R.id.btCancel);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Typeface robotoMedium = Typeface.createFromAsset(getResources().getAssets(), "fonts/robotoMedium.ttf");
            tvDialogTitle.setTypeface(robotoMedium);
            btOk.setTypeface(robotoMedium);
            btCancel.setTypeface(robotoMedium);
        }
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onEzPositiveClick(getTag());
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onEzNegativeClick(getTag());
            }
        });
        tvDialogTitle.setText(builder.title);
        tvDialogContent.setText(builder.content);
        if (decorate != null) {
            final String tag = getTag();
            decorate.decorate(tvDialogTitle, tag);
            decorate.decorate(tvDialogContent, tag);
            decorate.decorate(btOk, tag);
            decorate.decorate(btCancel, tag);
        }
    }

    public static class Builder implements Parcelable {
        private String title;
        private String content;
        private boolean cancelOnTouchOutside;
        @StyleRes
        private int dialogTheme;

        public Builder() {
        }

        protected Builder(Parcel in) {
            title = in.readString();
            content = in.readString();
            cancelOnTouchOutside = in.readByte() != 0;
            dialogTheme = in.readInt();
        }

        public static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };

        public Builder setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
            this.cancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setDialogTheme(int dialogTheme) {
            this.dialogTheme = dialogTheme;
            return this;
        }

        public EzDialog build() {
            final EzDialog dialog = new EzDialog();
            final Bundle arg = new Bundle();
            arg.putParcelable(BUILDER, this);
            dialog.setArguments(arg);
            return dialog;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(content);
            dest.writeByte((byte) (cancelOnTouchOutside ? 1 : 0));
            dest.writeInt(dialogTheme);
        }

    }
}
