package org.nacao.app.manage;

import java.util.ArrayList;
import java.util.List;

import org.nacao.app.R;
import org.nacao.app.util.DisplayTool;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class AppTabWidge extends TabActivity {
	private RadioGroup group;
	private TabHost tabHost;
	public static final String TAB_HOME = "tabHome";
	public static final String TAB_USER = "tab_user";
	public static final String TAB_SETTING = "tab_setting";
	private List<View> listViews;
	private int currIndex = 0;
	private ViewPager mPager;
	private static Activity act = null;
	private LocalActivityManager manager = null;
	private final Context context = AppTabWidge.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// »•µÙ±ÍÃ‚¿∏
		setContentView(R.layout.activity_main);
		act = this;
		String select = this.getIntent().getStringExtra("select");
		group = (RadioGroup) findViewById(R.id.main_radio1);
		RadioButton r_b1 = (RadioButton) findViewById(R.id.radio_button0);
		RadioButton r_b2 = (RadioButton) findViewById(R.id.radio_button1);
		tabHost = getTabHost();
		tabHost.addTab(tabHost
				.newTabSpec(TAB_HOME)
				.setIndicator(TAB_HOME)
				.setContent(
						new Intent(this, SearchActivity.class).putExtra(
								"tag_switch", "1")));//.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		tabHost.addTab(tabHost
				.newTabSpec(TAB_USER)
				.setIndicator(TAB_USER)
				.setContent(
						new Intent(this, UserinfoActivity.class).addFlags(
								Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra(
								"tag_switch", "2")));
		tabHost.addTab(tabHost
				.newTabSpec(TAB_SETTING)
				.setIndicator(TAB_SETTING)
				.setContent(
						new Intent(this, SettingsActivity.class).putExtra(
								"tag_switch", "3")));
		
//		 manager = new LocalActivityManager(this, false);
//		 manager.dispatchCreate(savedInstanceState);
		if (select.equals("1")) {
			r_b1.setChecked(true);
		} else if (select.equals("2")) {
			r_b2.setChecked(true);
			tabHost.setCurrentTab(1);
		}
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button0:
					tabHost.setCurrentTab(0);
					DisplayTool.setNormalScreen(AppTabWidge.this);
					break;
					
				case R.id.radio_button1:
					tabHost.setCurrentTab(1);
					DisplayTool.setNormalScreen(AppTabWidge.this);
					break;
				
				case R.id.radio_button3:
					tabHost.setCurrentTab(2);
					DisplayTool.setNormalScreen(AppTabWidge.this);
				default:
					break;
				}
			}
		});
//		 InitViewPager();
	}

	public static Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				DisplayTool.setNormalScreen(act);
				break;
			case 2:
				DisplayTool.setFullScreen(act);
				break;
			default:
				break;
			}
		}
	};

	private void InitViewPager() {

//		 mPager = (ViewPager) findViewById(R.id.vPager);

		listViews = new ArrayList<View>();

		MyPagerAdapter mpAdapter = new MyPagerAdapter(listViews);
		Intent intent = new Intent(this, SearchActivity.class);

		listViews.add(getView("1", intent));


		intent = new Intent(this, UserinfoActivity.class);

		listViews.add(getView("2", intent));

		intent = new Intent(this, SettingsActivity.class);
		listViews.add(getView("3", intent));
		mPager.setAdapter(mpAdapter);

		mPager.setCurrentItem(0);

		mPager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	public class MyPagerAdapter extends PagerAdapter {

		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {

			this.mListViews = mListViews;

		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {

			((ViewPager) arg0).removeView(mListViews.get(arg1));

		}

		@Override
		public void finishUpdate(View arg0) {

		}

		@Override
		public int getCount() {

			return mListViews.size();

		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {

			((ViewPager) arg0).addView(mListViews.get(arg1), 0);

			return mListViews.get(arg1);

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == (arg1);

		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {

			return null;

		}

		@Override
		public void startUpdate(View arg0) {

		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int arg0) {

			Animation animation = null;

			Intent intent = new Intent();
			RadioButton r_b1 = null;
			switch (arg0) {

			case 0:
				// text1.setTextColor(Color.GREEN);
				r_b1 = (RadioButton) findViewById(R.id.radio_button0);
				tabHost.setCurrentTab(0);
				break;

			case 1:
				r_b1 = (RadioButton) findViewById(R.id.radio_button1);
				tabHost.setCurrentTab(1);
				break;
			case 3:
				r_b1 = (RadioButton) findViewById(R.id.radio_button3);
				tabHost.setCurrentTab(2);
				break;

			}
			r_b1.setChecked(true);
			currIndex = arg0;

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}
	}

	private View getView(String id, Intent intent) {

		return manager.startActivity(id, intent).getDecorView();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (currIndex == 0) {
			Message msg = new Message();
			msg.what = 1;
//			DeviceList.handle.sendMessage(msg);
			return true;
		} else if (KeyEvent.KEYCODE_BACK == keyCode) {
			Intent inte = new Intent(this, LoginActivity.class);
			startActivity(inte);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
