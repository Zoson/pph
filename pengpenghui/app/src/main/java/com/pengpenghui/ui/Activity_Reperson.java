package com.pengpenghui.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pengpenghui.domain.controller.MainPageController;
import com.pengpenghui.domain.entity.UserModel;
import com.pengpenghui.pph_interface.ViewInterface;
import com.pengpenghui.ui.component.RenameDialog;
import com.pengpenghui.util.EnvironmentData;
import com.pengpenghui.util.StaticData;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 肖文浩 on 2015/7/18.
 */
public class Activity_Reperson extends Activity{
    private EditText et_re_person_ops;
    private EditText et_re_person_nps;
    private EditText et_re_person_rps;
    private EditText et_re_person_name;
    private Button regi;
    private Button rein;
    private String oldps;
    private String newps;
    private String rnewps;
    private String name;
    private ImageView personPicture;
    private TextView personName;
    private TextView phoneNum;
    private MainPageController mainPageController;
    private UserModel userModel;
    private String[] items = {"选择本地图片", "拍照"};
    private String file;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

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
        personPicture= (ImageView) findViewById(R.id.userpic);
        personName= (TextView)findViewById(R.id.uesrname_show);
        phoneNum= (TextView)findViewById(R.id.phone);
        regi = (Button)findViewById(R.id.regi);
        rein  = (Button)findViewById(R.id.bn_re_rein);
    }
    private void setListener(){
        rein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldps = et_re_person_ops.getText().toString();
                String newps = et_re_person_nps.getText().toString();
                String rnewps = et_re_person_rps.getText().toString();
                //String name = et_re_person_name.getText().toString();
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

            }
        });
        personName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RenameDialog renameDialog = new RenameDialog(Activity_Reperson.this, mainPageController);
                renameDialog.setTitle("修改昵称");
                renameDialog.show();
            }
        });
        personPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }
    private void initData(){
        userModel = UserModel.getInstance();
        personName.setText(userModel.getNickName());
        personPicture.setImageBitmap(userModel.getBitmap());
        phoneNum.setText(userModel.getId());
        mainPageController = new MainPageController(this, new ViewInterface() {
            @Override
            public void requestSuccessfully(String msg, String data) {
                switch (msg){
                    case "changeps":userModel.setPassWord(newps);break;
                    case "changename":personName.setText(userModel.getNickName());
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

    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo_bit = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo_bit);

            personPicture.setImageDrawable(drawable);
            userModel.setBitmap(photo_bit);
            saveBitmap(photo_bit);
            if(EnvironmentData.checkSDCard())
                file = Environment.getExternalStorageDirectory()+StaticData.IMAGE_DIR+StaticData.IMAGE_FILE_NAME2;
            else
                file = StaticData.IMAGE_DIR+StaticData.IMAGE_FILE_NAME2;
        }
        mainPageController.changPicture(file);
    }


    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
                                break;
                            case 1:

                                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                if (EnvironmentData.checkSDCard()) {
                                    String path = Environment.getExternalStorageDirectory()+StaticData.IMAGE_DIR;
                                    File file = new File(path);
                                    if(!file.exists()){
                                        file.mkdir();
                                    }
                                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(path,StaticData.IMAGE_FILE_NAME)));
                                }
                                else {
                                    String path = "/pph";
                                    File file = new File(path);
                                    if(!file.exists()){
                                        file.mkdir();
                                    }
                                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(StaticData.IMAGE_DIR,StaticData.IMAGE_FILE_NAME)));
                                }
                                startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (EnvironmentData.checkSDCard()) {
                        File tempFile = new File(Environment.getExternalStorageDirectory()+StaticData.IMAGE_DIR + StaticData.IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        File tempFile = new File(StaticData.IMAGE_DIR + StaticData.IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;

            }
        }
    }
    private void saveBitmap(Bitmap bmp){
        String filePath;
        if (EnvironmentData.checkSDCard()){
            filePath = Environment.getExternalStorageDirectory()+StaticData.IMAGE_DIR+StaticData.IMAGE_FILE_NAME2;
            try {
                FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory()+StaticData.IMAGE_DIR+StaticData.IMAGE_FILE_NAME2);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            filePath = StaticData.IMAGE_DIR+StaticData.IMAGE_FILE_NAME2;
            try{
                FileOutputStream out = new FileOutputStream(StaticData.IMAGE_DIR+StaticData.IMAGE_FILE_NAME2);
                bmp.compress(Bitmap.CompressFormat.PNG,90,out);
                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
