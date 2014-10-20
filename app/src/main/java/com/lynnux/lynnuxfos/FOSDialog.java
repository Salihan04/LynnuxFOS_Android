package com.lynnux.lynnuxfos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class FOSDialog extends ProgressDialog {

    private TextView loadingText;

    private String title;

    public FOSDialog(Context context) {
        super(context);
    }

    public FOSDialog(Context context, int theme) {
        super(context, theme);
    }

    private FOSDialog(Context context, String title) {
        super(context);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setDimAmount(0.5f);


        setContentView(R.layout.loading_progress);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,0);

        loadingText = (TextView) findViewById(R.id.loading_text);
        setupDialog(title);
    }

    public static FOSDialog showProgress(Context context, String title) {
        FOSDialog dialog = new FOSDialog(context, title);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    private void setupDialog(String title) {
        loadingText.setText(title);
    }
}
