package com.pengpenghui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pengpenghui.domain.controller.MainPageController;
import com.pengpenghui.domain.entity.UserModel;
import com.pengpenghui.pph_interface.ViewInterface;

/**
 * Created by 肖文浩 on 2015/7/18.
 */
public class Activity_Reperson extends Activity{
    private EditText et_re_person_ops;
    private EditText et_re_person_nps;
    private EditText et_re_person_rps;
    private EditText et_re_person_name;
    private Button regi;
    private String oldps;
    private String newps;
    private String rnewps;
    private String name;
    private MainPageController mainPageController;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reinformation);
        findView();
        setListener();
        initData();
    }
    private void findView(){
        //et_re_person_name = (EditText)findViewById(R.id.re_person_name);
        et_re_person_ops = (EditText)findViewById(R.id.re_person_ops);
        et_re_person_nps = (EditText)findViewById(R.id.re_person_nps);
        et_re_person_rps = (EditText)findViewById(R.id.re_preson_rnps);
        regi = (Button)findViewById(R.id.regi);
    }
    private void setListener(){
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldps = et_re_person_ops.getText().toString();
                String newps = et_re_person_nps.getText().toString();
                String rnewps = et_re_person_rps.getText().toString();
                String name = et_re_person_name.getText().toString();
                if (oldps.isEmpty()||newps.isEmpty()||rnewps.isEmpty()){
                    Toast.makeText(Activity_Reperson.this,"密码修改输入框为空",Toast.LENGTH_SHORT).show();
                }else{
                    if (oldps.equals(userModel.getPassWord())){
                        if (newps.equals(rnewps)){
                            mainPageController.changePassword(oldps,newps);
                        }else{
                            Toast.makeText(Activity_Reperson.this,"密码不一致",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Activity_Reperson.this,"原密码错误",Toast.LENGTH_SHORT).show();
                    }
                }
                if(!name.isEmpty()){
                    mainPageController.changeName(name);
                }else{
                    Toast.makeText(Activity_Reperson.this,"昵称修输入框为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initData(){
        userModel = UserModel.getInstance();
        mainPageController = new MainPageController(this, new ViewInterface() {
            @Override
            public void requestSuccessfully(String msg, String data) {
                switch (msg){
                    case "changeps":userModel.setPassWord(newps);break;
                    case "changename":userModel.setNickName(name);break;
                }
            }

            @Override
            public void requestUnSuccessfully(String msg, String data) {

            }

            @Override
            public void requestError(String msg, String data) {

            }
        });
    }

}
