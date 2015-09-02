package com.pengpenghui.ui;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;



import com.pengpenghui.util.Constant;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.RenrenShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.RenrenSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;


/**
 * Created by 文浩 on 2015/7/19.
 */
public class Activity_Share extends Activity {

    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(Constant.DESCRIPTOR);

    //优惠价格
    private int disccount_share;
    //优惠券类型
    private String type_share="元优惠券";
    //优惠提供方
    private String address_share;
    //开始时间
    private String startDate;
    private String  endDate;
    private Button button;
    private TextView type;
    private TextView price;
    private TextView address;
    private TextView startDate_share;
    private TextView endDate_share;
    private RelativeLayout relativeLayout_sharet;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.yiuhui_share);
        price = (TextView)findViewById(R.id.youhui_price_share);
        type = (TextView)findViewById(R.id.youhui_style_share);
        address = (TextView)findViewById(R.id.youhui_address_share);
        startDate_share = (TextView)findViewById(R.id.start_data_share);
        endDate_share = (TextView)findViewById(R.id.end_data_share);
        //这里修改优惠券的颜色
        relativeLayout_sharet = (RelativeLayout)findViewById(R.id.youhui_bg_share);
        //relativeLayout.setBackground();
        //youhuibg1到youhuibg4

        //测试
        disccount_share=10;
        String price00=disccount_share+"";
        type_share="元优惠券";
        address_share ="晓键书店";
        startDate="2015.06.07";
        endDate  ="2015.09.10";
        price.setText(price00);
        setting(price00, type_share, address_share, startDate, endDate);

        configPlatforms();

        setShareContent();
        setButton();


    }
    public void setButton(){

        button = (Button) findViewById(R.id.getddItem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("hehe", "____________________________________");
                mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.RENREN);
                mController.openShare(Activity_Share.this, false);


            }
        });

    }

    public void setting(String pri,String typ,String add,String  data1,String data2)
    {
        price.setText(pri);
        type.setText(typ);
        address.setText(add);
        startDate_share.setText(data1);
        endDate_share.setText(data2);
    }
    /**
     * 配置分享平台参数</br>
     */
    private void configPlatforms() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        // 添加人人网SSO授权
        RenrenSsoHandler renrenSsoHandler = new RenrenSsoHandler(this,
                "201874", "28401c0964f04a72a14c812d6132fcef",
                "3bf66e42db1e4fa9829b955cc300b737");
        mController.getConfig().setSsoHandler(renrenSsoHandler);

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qqSsoHandler.addToSocialSDK();
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();
//        // 添加QQ、QZone平台
//        addQQQZonePlatform();
        UMWXHandler wxHandler = new UMWXHandler(this, "wx2719f4889dc36f09", "7ce63f14fa3e168ee98c6d677accde39");
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈平台
        UMWXHandler wxCircleHandler = new UMWXHandler(this, "wx2719f4889dc36f09", "7ce63f14fa3e168ee98c6d677accde39");
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();


    }
    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @return
     */
    public void setShareContent(){

//      新浪分享
        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent.setShareContent(Constant.SOCIAL_CONTENT +Constant.SOCIAL_LINK);
        sinaContent.setShareImage(new UMImage(this, Constant.SOCIAL_IMAGE));
        sinaContent.setTitle(Constant.SOCIAL_TITLE);
        mController.setShareMedia(sinaContent);



//      人人网分享
        RenrenShareContent renrenShareContent = new RenrenShareContent();
        renrenShareContent.setShareContent(Constant.SOCIAL_CONTENT + Constant.SOCIAL_LINK);
        renrenShareContent.setShareImage(new UMImage(this, Constant.SOCIAL_IMAGE));
        renrenShareContent.setTitle(Constant.SOCIAL_TITLE);
        mController.setShareMedia(renrenShareContent);



//      QQ分享
        QQShareContent qqShareContent = new QQShareContent();
        //设置分享文字
        qqShareContent.setShareContent(Constant.SOCIAL_CONTENT);
        //设置分享title
        qqShareContent.setTitle(Constant.SOCIAL_TITLE);
        //设置分享图片
        qqShareContent.setShareImage(new UMImage(this, Constant.SOCIAL_IMAGE));
        //设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl(Constant.SOCIAL_LINK);
        mController.setShareMedia(qqShareContent);



//      QQ空间分享
        QZoneShareContent qzone = new QZoneShareContent();
        //设置分享文字
        qzone.setShareContent(Constant.SOCIAL_CONTENT);
        //设置分享title
        qzone.setTitle(Constant.SOCIAL_TITLE);
        //设置分享图片
        qzone.setShareImage(new UMImage(this, Constant.SOCIAL_IMAGE));
        //设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl(Constant.SOCIAL_LINK);
        mController.setShareMedia(qzone);


//      微信分享
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(Constant.SOCIAL_CONTENT);
        //设置分享title
        weixinContent.setTitle(Constant.SOCIAL_TITLE);
        //设置分享图片
//        weixinContent.setShareImage(new UMImage(this, Constant.SOCIAL_IMAGE));
        //设置点击分享内容的跳转链接
        weixinContent.setTargetUrl(Constant.SOCIAL_LINK);
        mController.setShareMedia(weixinContent);


        //     设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(Constant.SOCIAL_CONTENT);
        //设置分享title
        circleMedia.setTitle(Constant.SOCIAL_TITLE);
        //设置分享图片
//        circleMedia.setShareImage(new UMImage(this, Constant.SOCIAL_IMAGE));
        //设置点击分享内容的跳转链接
        circleMedia.setTargetUrl(Constant.SOCIAL_LINK);
        mController.setShareMedia(circleMedia);



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** 使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }






}
