package com.pengpenghui.ui;
import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import com.pengpenghui.domain.controller.MainPageController;
import com.pengpenghui.pph_interface.NFCListener;
import com.pengpenghui.domain.entity.NFCModel;
import com.pengpenghui.pph_interface.ViewInterface;
import com.pengpenghui.ui.component.ChangeColorText;
import com.pengpenghui.ui.component.GetBroDialog;


public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener , NFCListener{

	private GetBroDialog getBroDialog;
    private NFCModel nfcModel;
    private PendingIntent pendingIntent;
	private MainPageController mainPageController;
	ViewPager mViewPager;
	FragmentPagerAdapter mAdapter;
	List<Fragment> mTabs = new ArrayList<Fragment>();
	String[] mTitles = new String[] { "first Fragment !", "Second Fragment !","Third Fragment !" };
	List<ChangeColorText> mTabIndicators = new ArrayList<ChangeColorText>();
	private boolean tryStartNfc = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		initDatas();
		mViewPager.setAdapter(mAdapter);  //设置Adapter，则每一页显示相应View
		initEvent();
		if(tryStartNfc){
			startNfc();
		}

	}

    private void startNfc(){
        nfcModel = new NFCModel(this,this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }
	/** 初始化所有事�? */
	private void initEvent() {
		//ViewPager在处理滑动事件的时�?�要用到OnPageChangeListener
		mViewPager.setOnPageChangeListener(this);
	}

	private void initDatas() {
		//广告页面
		Fragment_Ad Fragment_ad = new Fragment_Ad();
		mTabs.add(Fragment_ad);
        //主界�?
		Fragment_Main Fragment_main = new Fragment_Main();
		mTabs.add(Fragment_main);
		//个人信息页面
		Fragment_Person Fragment_person = new Fragment_Person();
		mTabs.add(Fragment_person);

		mainPageController = new MainPageController(this, new ViewInterface() {
			@Override
			public void requestSuccessfully(String msg, String data) {
				switch (msg){
					case "0":
						getBroDialog = new GetBroDialog(MainActivity.this,mainPageController);
						getBroDialog.show();
						break;
					case "1":
						Toast.makeText(MainActivity.this,"领取成功",Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void requestUnSuccessfully(String msg, String data) {

			}

			@Override
			public void requestError(String msg, String data) {

			}
		});

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()){
		@Override
		public int getCount() {
			return mTabs.size();
		}
		@Override
		public Fragment getItem(int position) {
			return mTabs.get(position);
		}
	};
		
		
	}

	private void initView() {
		mViewPager = (ViewPager)findViewById(R.id.vp);

		ChangeColorText one = (ChangeColorText) findViewById(R.id.id_indicator_one);
		ChangeColorText two = (ChangeColorText) findViewById(R.id.id_indicator_two);
		ChangeColorText three = (ChangeColorText) findViewById(R.id.id_indicator_three);
		
		mTabIndicators.add(one);
		mTabIndicators.add(two);
		mTabIndicators.add(three);
		

		//循环遍历mTabIndicators里面的元素，每个元素的�?�放入item中�??
		for (View item : mTabIndicators) {  
			item.setOnClickListener(this);
		}
		
		one.setIconAlpha(1.0f);  //将第�?个颜色加�?
	}

	
	
	@Override
	public void onClick(View v) {
		if (v instanceof ChangeColorText) {
			
			resetOtherTabs();   //将所有的Tab设为透明
			ChangeColorText current = (ChangeColorText)v;  //将特定的Tab颜色加深
			current.setIconAlpha(1.0f);
			
			//设置当前显示的页�?
			mViewPager.setCurrentItem(mTabIndicators.indexOf(current),false);
		}
	}

	/** 
	 * 重置其他的TabIndicator的颜�? ，设为�?�明
	 * 
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicators.size(); i++) {
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}
	
	/**
	 * onPageScrolled(int arg0,float arg1,int arg2)，当页面在滑动的时�?�会调用此方法，在滑动被停止之前，此方法回一直得到调用�??
	 * 其中三个参数的含义分别为�?
	 * arg0 :当前页面，及你点击滑动的页面
	 * arg1:当前页面偏移的百分比
	 * arg2:当前页面偏移的像素位�?  
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		
		if (positionOffset > 0) {
			ChangeColorText left = mTabIndicators.get(position);
			ChangeColorText right = mTabIndicators.get(position + 1);
			
			//切换时，图标的�?�明度转�?
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
			
			// 以下代码是切换Fragment中View的�?�明度，不需要可以注释掉
			mTabs.get(position).getView().setAlpha(1 - positionOffset);
			mTabs.get(position + 1).getView().setAlpha(positionOffset);
		}
	}
	
	/**
	 * 此方法是在状态改变的时�?�调用，其中arg0这个参数
	 * 有三种状态（0�?1�?2）�?�arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做�?
	 * 当页面开始滑动的时�?�，三种状�?�的变化顺序为（1�?2�?0�?
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	/**
	 * onPageSelected(int arg0) �?   
	 * 此方法是页面跳转完后得到调用，arg0是你当前选中的页面的Position（位置编号）
	 */
	@Override
	public void onPageSelected(int arg0) {
	}
    @Override
    protected void onResume() {
        super.onResume();
		if (tryStartNfc){
			nfcModel.endbleForegroundDispatch();
		}

    }

    public void getnfc(String code){
        Toast.makeText(this,code,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
		if (tryStartNfc){
			nfcModel.disableForegroundDispatch();
		}
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        nfcModel.getNfcIntent(intent);
    }
    @Override
    public void getNfcInfo(String code) {
        //Toast.makeText(this,code,Toast.LENGTH_SHORT).show();
		if (getBroDialog!=null&&getBroDialog.isShowing()){
			return;
		}
		mainPageController.fromNfcTagToGetAd("1234");
    }
}