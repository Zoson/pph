package com.pengpenghui.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.context.MainController;
import com.pengpenghui.ui.R;

/**
 * Created by zoson on 15-9-2.
 */
public class RenameDialog extends Dialog {
    private EditText et_rename;
    private Button bt_get_bro;
    private Button bt_cancel;
    private MainController mainPageController;
    private Context context;

    public RenameDialog(Context context, int theme) {
        super(context, theme);
    }

    public RenameDialog(Context context,MainController mainPageController) {
        super(context);
        setContentView(R.layout.activity_dialog_rename);
        this.context = context;
        this.mainPageController = mainPageController;
        findView();
        setListener();
    }
    private void findView(){
        et_rename = (EditText)findViewById(R.id.et_rename);
        bt_cancel = (Button)findViewById(R.id.bt_cancel);
        bt_get_bro = (Button)findViewById(R.id.bt_get_bro);
    }
    private void setListener(){
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RenameDialog.this.dismiss();
            }
        });
        bt_get_bro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPageController.changeName(et_rename.getText().toString(), new ContextCallback() {
                    @Override
                    public void response(int state, Object object) {

                    }
                });
                dismiss();
            }
        });
    }
}
