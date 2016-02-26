package com.chisw.commonui.dialog;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chisw.commonui.R;
import com.chisw.commonui.utils.TypefaceUtils;

public class OkCancelDialog extends BaseMaterialDialog<OkCancelDialog.OnSimpleDialogCallback> {
    private static final String BUILDER = "Builder";

    public interface OnSimpleDialogCallback {
        void onOk();

        void onCancel();
    }

    private Builder builder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = getArguments().getParcelable(BUILDER);
        assert builder != null;
        initIfNeeded();
    }

    @Override
    protected void decorateDialog(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(builder.cancelOnTouchOutside);
    }

    @LayoutRes
    @Override
    protected int dialogLayoutResId() {
        return R.layout.d_simple;
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
        tvDialogTitle.setText(builder.title);
        tvDialogContent.setText(builder.content);
        btOk.setText(builder.okButtonText);
        btCancel.setText(builder.cancelButtonText);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            TypefaceUtils.clearCache();
        }
    }

    private void initIfNeeded() {
        if (builder.okButtonText == null) {
            builder.okButtonText = getString(R.string.ok);
        }
        if (builder.cancelButtonText == null) {
            builder.cancelButtonText = getString(R.string.cancel);
        }
    }

    public static class Builder implements Parcelable {
        private String title;
        private String content;
        private String okButtonText;
        private String cancelButtonText;
        private boolean cancelOnTouchOutside;

        public Builder() {
        }

        protected Builder(Parcel in) {
            title = in.readString();
            content = in.readString();
            okButtonText = in.readString();
            cancelButtonText = in.readString();
            cancelOnTouchOutside = in.readByte() != 0;
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

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
            this.cancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCancelButtonText(String cancelButtonText) {
            this.cancelButtonText = cancelButtonText;
            return this;
        }

        public Builder setOkButtonText(String okButtonText) {
            this.okButtonText = okButtonText;
            return this;
        }

        public OkCancelDialog build() {
            final OkCancelDialog okCancelDialog = new OkCancelDialog();
            final Bundle bundle = new Bundle();
            bundle.putParcelable(BUILDER, this);
            okCancelDialog.setArguments(bundle);
            return okCancelDialog;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(content);
            dest.writeString(okButtonText);
            dest.writeString(cancelButtonText);
            dest.writeByte((byte) (cancelOnTouchOutside ? 1 : 0));
        }
    }
}
