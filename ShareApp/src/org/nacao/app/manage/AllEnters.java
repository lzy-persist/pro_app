package org.nacao.app.manage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nacao.app.R;
import org.nacao.app.adater.EnterpriseAadpter;
import org.nacao.app.httpclient.CustomerHttpClient;
import org.nacao.app.httpclient.HttpConnectionCallback;
import org.nacao.app.httpclient.HttpConnectionUtil;
import org.nacao.app.httpclient.UriAPI;
import org.nacao.app.model.EnterpriseInfo;
import org.nacao.app.util.StringUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AllEnters extends Activity implements
		ExpandableListView.OnGroupClickListener,
		ExpandableListView.OnChildClickListener,OnItemClickListener {
	private int pageSize = 10;
	private int pageIndex = 1;
	private int page = 1;
	private int pageCount = 0;
	private ImageView back = null;
	private ImageView first, next, prev, last;
	private EditText deviceNumber = null;
	private EditText codeValue = null;
	private ImageView search_device = null;
	private TextView curentPage, totalPage;
	private ListView all_device_list = null;
	private ExpandableListView expListView = null;
	private String selectGroup = null;
	private List<EnterpriseInfo> enterList = null;
	private EnterpriseAadpter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.device_manager);
		findView();
		
		Intent ite = this.getIntent();
		String jgdm = (String)ite.getSerializableExtra("jgdm");
		String jgmc = (String)ite.getSerializableExtra("jgmc");
		
		SearchDeviceAsy asy = new SearchDeviceAsy();
		asy.execute(StringUtil.convertNull(jgdm),StringUtil.convertNull(jgmc));
	}

	private void findView() {
		all_device_list = (ListView) findViewById(R.id.device_list);
		back = (ImageView)findViewById(R.id.dmback);
		first = (ImageView) findViewById(R.id.first);
		prev = (ImageView) findViewById(R.id.prev);
		last = (ImageView) findViewById(R.id.last);
		next = (ImageView) findViewById(R.id.next);
		totalPage = (TextView) findViewById(R.id.count);
		curentPage = (TextView) findViewById(R.id.curentPage);
		search_device = (ImageView) findViewById(R.id.search_device);
		search_device.setOnClickListener(new SearchDevice());
		deviceNumber = (EditText) findViewById(R.id.number_value);
		codeValue = (EditText)findViewById(R.id.code_value);
		first.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pageIndex = 1;
				String keyword = deviceNumber.getText().toString();
				String keyvalue = codeValue.getText().toString();
				SearchDeviceAsy asy = new SearchDeviceAsy();
				asy.execute(keyword,keyvalue);
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pageIndex++;
				if (pageIndex > page) {
					pageIndex = page;
				}
				String keyword = deviceNumber.getText().toString();
				String keyvalue = codeValue.getText().toString();
				SearchDeviceAsy asy = new SearchDeviceAsy();
				asy.execute(keyword,keyvalue);
			}
		});
		prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pageIndex--;
				if (pageIndex < 1) {
					pageIndex = 1;
				}
				String keyword = deviceNumber.getText().toString();
				String keyvalue = codeValue.getText().toString();
				SearchDeviceAsy asy = new SearchDeviceAsy();
				asy.execute(keyword,keyvalue);
			}
		});
		last.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pageIndex = page;
				String keyword = deviceNumber.getText().toString();
				String keyvalue = codeValue.getText().toString();
				SearchDeviceAsy asy = new SearchDeviceAsy();
				asy.execute(keyword,keyvalue);
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		all_device_list.setOnItemClickListener(this);
	}

	private void initAdapter() {
//		if (enterList != null && enterList.size() > 0) {
			curentPage.setText(pageIndex + "/" + page);
			totalPage.setText(pageCount + "条");
			adapter = new EnterpriseAadpter(enterList,this);
			all_device_list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
//		}
	}


	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {

		return false;
	}

	private class SearchDeviceAsy extends AsyncTask<String, String, String>
			implements HttpConnectionCallback {
		private int index = 0;

		@Override
		public void execute(String response, int kind) {
			try {
				if (response == "2" || response == "1") {
					index++;
					String keyword = deviceNumber.getText().toString();
					String keyvalue = codeValue.getText().toString();
					if (index < 3) {
						SearchDeviceAsy asy = new SearchDeviceAsy();
						asy.execute(keyword,keyvalue);
					}
					return;
				}
				index = 0;
				JSONObject json = new JSONObject(response);
				pageCount = json.getInt("Total");
				page = ((pageCount % pageSize) > 0 ? (pageCount / pageSize) + 1
						: pageCount / pageSize);
				JSONArray arry = json.getJSONArray("Rows");
				enterList = new ArrayList<EnterpriseInfo>();
				for (int i = 0; i < arry.length(); i++) {
					EnterpriseInfo ent = new EnterpriseInfo();
					JSONObject obj = (JSONObject) arry.get(i);
					ent.setCodeId(obj.getString("codeId"));
					ent.setCodeCn(obj.getString("codeCn"));
					enterList.add(ent);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			HttpConnectionUtil util = new HttpConnectionUtil();
			DefaultHttpClient httpClient = CustomerHttpClient.getHttpClient();
			Map<String, String> parms = new HashMap<String, String>();
			
			parms.put("codeid", params[0]);// params[0]
			try {
				parms.put("codecn", URLEncoder.encode(params[1],"GBK"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			parms.put("method", "jgdmPage");
			parms.put("pageNo", pageIndex + "");
			parms.put("pageSize", pageSize + "");
			util.setClient(httpClient);
			util.syncConnect(UriAPI.serviceHttp, parms,
					HttpConnectionUtil.HttpMethod.POST, SearchDeviceAsy.this, 0);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(index==0){
				initAdapter();
			}
		}
	}

	class SearchDevice implements OnClickListener {
		@Override
		public void onClick(View v) {
			String keyword = deviceNumber.getText().toString();
			String keyvalue = codeValue.getText().toString(); 
			if (!StringUtil.isEmpty(keyword) && keyword.length() != 9){
				Toast.makeText(AllEnters.this, "需输入9位的机构代码!", 3000).show();
				return;
			}
			
			InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );     
			if (imm.isActive( ) ) {     
				imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );    
			}    
				
			SearchDeviceAsy asy = new SearchDeviceAsy();
			//初始页码
			pageIndex=1;
			asy.execute(keyword,keyvalue);

		}
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		EnterpriseInfo ent = (EnterpriseInfo) all_device_list.getAdapter().getItem(position);
		Intent inte = new Intent(this,EntinfosActivity.class);
		Bundle bun = new Bundle();
		bun.putSerializable("codeid", ent.getCodeId());
		inte.putExtras(bun);
		startActivityForResult(inte, 1);
	}
	
}
