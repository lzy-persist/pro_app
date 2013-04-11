package org.nacao.app.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nacao.app.R;
import org.nacao.app.adater.EnterinfosAadpter;
import org.nacao.app.httpclient.CustomerHttpClient;
import org.nacao.app.httpclient.HttpConnectionCallback;
import org.nacao.app.httpclient.HttpConnectionUtil;
import org.nacao.app.httpclient.UriAPI;
import org.nacao.app.model.EnterpriseInfo;
import org.nacao.app.util.Variable;
import org.nacao.app.view.PullDownView;
import org.nacao.app.view.PullDownView.OnPullDownListener;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceList extends Activity implements OnPullDownListener,OnItemClickListener {
	private static SlidingDrawer mdrawer;
	private static ImageView mbutton;
	private ImageView search;
	private RelativeLayout layout = null;
	private TextView mtext;
	private ListView inforList = null;
	private PullDownView mPullDownView;
	private EnterinfosAadpter adapter = null;
	private List<EnterpriseInfo> enterList = null;
	private TextView data_count = null;
	private TextView select_cond = null;
	/** Handler What加载数据完毕 **/
	private static final int WHAT_DID_LOAD_DATA = 0;
	/** Handler What更新数据完毕 **/
	private static final int WHAT_DID_REFRESH = 1;
	/** Handler What更多数据完毕 **/
	private static final int WHAT_DID_MORE = 2;
	/** Handler What数据加载完毕 **/
	private static final int WHAT_DID_OVER = 3;
	private int pageSize = 10;
	private int pageIndex = 1;
	private int totalCount = 0;
	private int isOnLine = 0;
	private int alarmCount = 0;
	private int offLine = 0;
	/** 去请求后台数据的次数 */
	private int requestCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.device_list);
		initPullView();
		loadData();
	}

	private void initPullView() {
		
		mPullDownView = (PullDownView) findViewById(R.id.ent_list);
		mPullDownView.setOnPullDownListener(this);
		inforList = mPullDownView.getListView();
		inforList.setOnItemClickListener(this);
		adapter = new EnterinfosAadpter(enterList, this);
		inforList.setAdapter(adapter);
		// 设置可以自动获取更多 滑到最后一个自动获取 改成false将禁用自动获取更多
		mPullDownView.enableAutoFetchMore(true, 1);
		// 隐藏 并禁用尾部
		mPullDownView.setHideFooter();
		// 显示并启用自动获取更多
		mPullDownView.setShowFooter();
		// 隐藏并且禁用头部刷新
		mPullDownView.setHideHeader();
		// 显示并且可以使用头部刷新
		mPullDownView.setShowHeader();
	}


	private void loadData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				GetDeviceListAsy asy = new GetDeviceListAsy();
				asy.execute("0");
			}
		}).start();
	}


	private class GetDeviceListAsy extends AsyncTask<String, String, String>
			implements HttpConnectionCallback {
		private boolean flag = false;
		private String total="0";

		@Override
		protected String doInBackground(String... pars) {// 处理后台数据请求
			HttpConnectionUtil util = new HttpConnectionUtil();
			DefaultHttpClient httpClient = CustomerHttpClient.getHttpClient();
			Map<String, String> parms = new HashMap<String, String>();
			parms.put("method", "jgdmList");
			util.setClient(httpClient);
			util.syncConnect(UriAPI.serviceHttp, parms,
					HttpConnectionUtil.HttpMethod.POST, GetDeviceListAsy.this,
					0);
			return pars[0];
		}

		@Override
		protected void onPreExecute() {// 任务执行之前开始调用此方法
			super.onPreExecute();

		}

		@Override
		protected void onPostExecute(String result) {// 返回的结果 与uI相关都在这个里面更新
			super.onPostExecute(result);
			System.out.println("result is:"+result);
			if (requestCount == 3) {
				requestCount=0;
				Toast.makeText(DeviceList.this, "当前网络不好，请检查网络！", 3000).show();
				return;
			}
			if (flag) {
				return;
			}
			
			if (result == "0") {// 第一次加载数据
				Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
				msg.obj = enterList;
				msg.sendToTarget();
			} else if (result == "1") {// 刷新数据
//				select_cond.setText("全部目标");
				mPullDownView.RefreshComplete();//线程安全
				Message msg = mUIHandler.obtainMessage(WHAT_DID_REFRESH);
				msg.obj = enterList;
				msg.sendToTarget();
			} else if (result == "2") {// 加载更多
				mPullDownView.notifyDidMore();
				Message msg = mUIHandler.obtainMessage(WHAT_DID_MORE);
				msg.obj = enterList;
				msg.sendToTarget();
			}
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
		}

		@Override
		public void execute(String response, int kind) {
			try {
				if (response == "2" || response == "1") {
					requestCount++;
					flag = true;
					if (requestCount == 3) {
						return;
					}
					GetDeviceListAsy asy = new GetDeviceListAsy();
					asy.execute("0");
					return;
				}
				flag = false;
				isOnLine = 0;
				offLine = 0;
				alarmCount = 0;
				JSONArray json = new JSONArray(response);
				
				enterList = new ArrayList<EnterpriseInfo>();
				
				for (int i = 0; i < json.length(); i++) {
					EnterpriseInfo ent = new EnterpriseInfo();
					JSONObject obj = json.getJSONObject(i);
					ent.setCodeId(obj.getString("codeId"));
					ent.setCodeCn(obj.getString("codeCn"));
					ent.setWorkUnit(obj.getString("workUnit"));
					ent.setIndustryId(obj.getString("industryId"));
					ent.setAddressname(obj.getString("addressname"));
					ent.setFinishdate(obj.getString("finishdate"));
					enterList.add(ent);
				}
				System.out.println("This callback data size:"+enterList.size());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	private Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DID_LOAD_DATA: {
				if (msg.obj != null) {
					enterList = (List<EnterpriseInfo>) msg.obj;
					showEnterpriseList();
					if (totalCount <= pageIndex * pageSize) {
						mPullDownView.enableAutoFetchMore(false, 1);
					}
				}
				mPullDownView.showInfo();
				break;
			}
			case WHAT_DID_REFRESH: {
				enterList = (List<EnterpriseInfo>) msg.obj;
				if (msg.obj != null) {
					showEnterpriseList();
					adapter.notifyDataSetChanged();
					mPullDownView.enableAutoFetchMore(true, 1);
					mPullDownView.refushOVer();
					if (totalCount <= pageIndex * pageSize) {
						mPullDownView.enableAutoFetchMore(false, 1);
					}
					// 告诉它更新完毕
				}
				// 告诉它更新完毕
				break;
			}
			case WHAT_DID_MORE: {
				List<EnterpriseInfo> ents = (List<EnterpriseInfo>) msg.obj;
				if (ents != null) {
					for (EnterpriseInfo c : ents) {
						if (enterList.contains(c)) {
							continue;
						}
						enterList.add(c);
					}
					adapter.notifyDataSetChanged();
				}
				break;
			}
			case WHAT_DID_OVER: {
				mPullDownView.enableAutoFetchMore(false, 1);
			}
			}
		}
	};

	private void showEnterpriseList() {
		adapter = new EnterinfosAadpter(enterList, this);
		inforList.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
/*		//点击查看详细信息
		EnterpriseInfo ent = (EnterpriseInfo) inforList.getAdapter().getItem(position);
		Intent inte = new Intent(DeviceList.this,EntinfosActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("codeid", ent.getCodeId());
		inte.putExtras(b);
		startActivityForResult(inte,1);*/
	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				GetDeviceListAsy asy = new GetDeviceListAsy();
				asy.execute("1");
			}
		}).start();
	}

	@Override
	public void onMore() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 告诉它获取更多完毕 这个事线程安全的 可看源代码
				if (totalCount > (pageIndex - 1) * pageSize) {
					// 没有更新的数据了
					Message msg = mUIHandler.obtainMessage(WHAT_DID_OVER);
					msg.sendToTarget();
				} else {
					GetDeviceListAsy asy = new GetDeviceListAsy();
					asy.execute("2");// 加载更多数据
				}
			}
		}).start();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (Variable.closeFlag) {
			Message msg = new Message();
			msg.what = 1;
//			DeviceList.handle.sendMessage(msg);
			return true;
		} else if (KeyEvent.KEYCODE_BACK == keyCode) {
			Intent inte = new Intent(this, LoginActivity.class);
			inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(inte);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void devBack(View view){
		ImageView back = (ImageView)findViewById(R.id.back);

		Intent inte = new Intent(getApplicationContext(),LoginActivity.class);
		startActivityForResult(inte, 1);
		this.finish();
	}
		
	public void findJgdm(View view){
		ImageView back = (ImageView)findViewById(R.id.enterlist);

		Intent inten = new Intent(getApplicationContext(), AllEnters.class);
		startActivity(inten);
	}

}
