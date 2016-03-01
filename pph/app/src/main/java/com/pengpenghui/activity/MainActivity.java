package com.pengpenghui.activity;
import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
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

import com.pengpenghui.domain.context.ContextCallback;
import com.pengpenghui.domain.context.MainController;
import com.pengpenghui.domain.entity.AdData;
import com.pengpenghui.domain.entity.User;
import com.pengpenghui.domain.service.nfc.NFCListener;
import com.pengpenghui.domain.service.nfc.NFCService;
import com.pengpenghui.ui.R;
import com.pengpenghui.ui.component.ChangeColorText;


public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener , ContextCallback{

	private GetBroDialog getBroDialog;
    private NFCService nfcModel;
    private PendingIntent pendingIntent;
	private MainController mainPageController;
	ViewPager mViewPager;
	FragmentPagerAdapter mAdapter;
	private AdFragment Fragment_ad;
	private PersonFragment Fragment_person;
	private MainFragment Fragment_main;
	private User userModel;
	List<Fragment> mTabs = new ArrayList<Fragment>();
	String[] mTitles = new String[] { "first Fragment !", "Second Fragment !","Third Fragment !" };
	List<ChangeColorText> mTabIndicators = new ArrayList<ChangeColorText>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		initDatas();
		mViewPager.setAdapter(mAdapter);  //设置Adapter，则每一页显示相应View
		initEvent();
	}

	/** 初始化所有事�? */
	private void initEvent() {
		//ViewPager在处理滑动事件的时�?�要用到OnPageChangeListener
		mViewPager.setOnPageChangeListener(this);
	}

	private void initDatas() {
		//广告页面
		Fragment_ad = new AdFragment();
		mTabs.add(Fragment_ad);
        //主界�?
		Fragment_main = new MainFragment();
		mTabs.add(Fragment_main);
		//个人信息页面
		Fragment_person = new PersonFragment();
		mTabs.add(Fragment_person);

		mainPageController = new MainController();

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
		if (!mainPageController.isUserLogin()){
			Toast.makeText(this,"您还未登录",Toast.LENGTH_SHORT).show();
		}

		if (!mainPageController.checkNFCDevice()){
			Toast.makeText(this,"您的设备不支持NFC",Toast.LENGTH_SHORT).show();
		}
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
//			mTabs.get(position).getView().setAlpha(1 - positionOffset);
//			mTabs.get(position + 1).getView().setAlpha(positionOffset);
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
		mainPageController.handleNFCEvent(getIntent(),this);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
		mainPageController.handleNFCEvent(intent, this);
    }

	@Override
	public void response(int state, Object object) {
		if (FAIL == state) {
			Toast.makeText(MainActivity.this, "" + object, Toast.LENGTH_SHORT).show();
			return;
		}
		if (getBroDialog!=null&&getBroDialog.isShowing()){
			getBroDialog.dismiss();
		}
		getBroDialog = new GetBroDialog(MainActivity.this, (AdData) object);
		getBroDialog.show();
	}
}