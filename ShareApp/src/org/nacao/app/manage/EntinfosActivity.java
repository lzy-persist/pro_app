package org.nacao.app.manage;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.nacao.app.R;
import org.nacao.app.httpclient.CustomerHttpClient;
import org.nacao.app.httpclient.HttpConnectionCallback;
import org.nacao.app.httpclient.HttpConnectionUtil;
import org.nacao.app.httpclient.UriAPI;
import org.nacao.app.model.Jgdm;
import org.nacao.app.util.StringUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EntinfosActivity extends Activity {
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
	
	private TableLayout tableInfo;
	private ImageView back;
	private Jgdm jgdmInfo;
	private String codeid;
	private TextView errInfos;
	private int requestCount = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent inte = this.getIntent();
		codeid=(String) inte.getSerializableExtra("codeid");
		
		setContentView(R.layout.entinfos);
		
		back = (ImageView)findViewById(R.id.entback);
		tableInfo = (TableLayout)findViewById(R.id.table_info);
		errInfos = (TextView)findViewById(R.id.err_infos);
		tableInfo.setStretchAllColumns(true);

		jgdmInfoAsy asy = new jgdmInfoAsy();
		asy.execute("0");
		
	}

	
	
	private class jgdmInfoAsy extends AsyncTask<String, String, String> implements HttpConnectionCallback{
		private boolean flag = false;
		@Override
		public void execute(String response, int kind) {
			try {
				if (response == "2" || response == "1") {
					requestCount++;
					flag=true;
					if (requestCount == 3) {
						return;
					}
					
					jgdmInfoAsy asy = new jgdmInfoAsy();
					asy.execute("0");
					return;
				}
				
				JSONObject json = new JSONObject(response);
				String jgdm = json.getString("jgdm");
				if(jgdm.equals("null")){
					Toast.makeText(EntinfosActivity.this, "没有查询到给定条件的数据!", 3000).show();
					
					finish();
				}else{
					flag=false;
					jgdmInfo = new Jgdm();
					jgdmInfo = jsonToBean(jgdmInfo,json);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			HttpConnectionUtil util = new HttpConnectionUtil();
			DefaultHttpClient httpClient = CustomerHttpClient.getHttpClient();
			Map<String, String> parms = new HashMap<String, String>();
			
			parms.put("codeid", codeid);
			parms.put("method", "jgdmInfo");
			
			util.setClient(httpClient);
			util.syncConnect(UriAPI.serviceHttp, parms,
					HttpConnectionUtil.HttpMethod.POST, jgdmInfoAsy.this, 1);
			return params[0];
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (requestCount == 3) {
				requestCount=0;
				Toast.makeText(EntinfosActivity.this, "当前网络不好，请检查网络！", 3000).show();
				return;
			}
			if (flag) {
				return;
			}
			System.out.println("result：："+result);
			Message msg = myHandler.obtainMessage(1);
			msg.obj = jgdmInfo;
			msg.sendToTarget();
		}
	
	}
	
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				if(msg.obj!=null){
					jgdmInfo = (Jgdm)msg.obj;
					errInfos.setHeight(0);
					showInfo();
				}else{
					errInfos.setText("没有检索到给定条件的数据!");
				}
				break;
			default:
				break;
			}
			
		}
		
	};
	
	private void showInfo(){
		//展示
		TableRow tr1 = new TableRow(this);
		TextView t11 = new TextView(this);t11.setGravity(Gravity.RIGHT);
		t11.setTextSize(10f);
		t11.setText("机构代码：");
		tr1.addView(t11);
		TextView t12 = new TextView(this);t12.setTextColor(Color.BLUE);
		t12.setTextSize(10f);
		t12.setText(jgdmInfo.getJgdm());
		tr1.addView(t12);
		tableInfo.addView(tr1,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow tr2 = new TableRow(this);
		TextView t21 = new TextView(this);t21.setGravity(Gravity.RIGHT);
		t21.setTextSize(10f);
		t21.setText("机构名称：");
		tr2.addView(t21);
		TextView t22 = new TextView(this);t22.setTextColor(Color.BLUE);
		t22.setTextSize(10f);t22.setWidth(WC);
		t22.setText(jgdmInfo.getJgmc());
		tr2.addView(t22);
		tableInfo.addView(tr2,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow tr3 = new TableRow(this);
		TextView t31 = new TextView(this);t31.setTextSize(10f);t31.setGravity(Gravity.RIGHT);
		t31.setText("经营范围：");
		tr3.addView(t31);
		TextView t32 = new TextView(this);t32.setTextSize(10f);t32.setTextColor(Color.BLUE);
		t32.setText(jgdmInfo.getJyfw());t32.setWidth(WC);
		tr3.addView(t32);
		tableInfo.addView(tr3,new TableLayout.LayoutParams(WC, WC));
		//---------------------
		TableRow tr5 = new TableRow(this);
		TextView t51 = new TextView(this);t51.setTextSize(10f);t51.setGravity(Gravity.RIGHT);
		t51.setText("机构地址：");
		tr5.addView(t51);
		TextView t52 = new TextView(this);t52.setTextSize(10f);t52.setTextColor(Color.BLUE);
		t52.setText(jgdmInfo.getJgdz());t52.setWidth(WC);
		tr5.addView(t52);
		tableInfo.addView(tr5,new TableLayout.LayoutParams(WC, WC));
		//---------------------
		TableRow tr4 = new TableRow(this);
		TextView t41 = new TextView(this);t41.setTextSize(10f);t41.setGravity(Gravity.RIGHT);
		t41.setText("行政区划：");
		tr4.addView(t41);
		TextView t42 = new TextView(this);t42.setTextSize(10f);t42.setTextColor(Color.BLUE);
		t42.setText(jgdmInfo.getXzqhmc());
		tr4.addView(t42);
		TableRow tr40 = new TableRow(this);
		TextView t43 = new TextView(this);t43.setTextSize(10f);t43.setGravity(Gravity.RIGHT);
		t43.setText("行政区划编码：");
		tr40.addView(t43);
		TextView t44 = new TextView(this);t44.setTextSize(10f);t44.setTextColor(Color.BLUE);
		t44.setText(jgdmInfo.getXzqh());
		tr40.addView(t44);
		tableInfo.addView(tr4,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(tr40,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow tr6 = new TableRow(this);
		TextView t61 = new TextView(this);t61.setTextSize(10f);t61.setGravity(Gravity.RIGHT);
		t61.setText("法定代表人姓名：");
		tr6.addView(t61);
		TextView t62 = new TextView(this);t62.setTextSize(10f);t62.setTextColor(Color.BLUE);
		t62.setText(jgdmInfo.getFddbr());
		tr6.addView(t62);

		TableRow tr60 = new TableRow(this);

		TextView t63 = new TextView(this);t63.setTextSize(10f);t63.setGravity(Gravity.RIGHT);
		t63.setText("证件号码：");
		tr60.addView(t63);
		TextView t64 = new TextView(this);t64.setTextSize(10f);t64.setTextColor(Color.BLUE);
		t64.setText(jgdmInfo.getZjhm());
		tr60.addView(t64);
		tableInfo.addView(tr6,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(tr60,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow tr7 = new TableRow(this);
		TextView t71 = new TextView(this);t71.setTextSize(10f);t71.setGravity(Gravity.RIGHT);
		t71.setText("旧经济行业：");
		tr7.addView(t71);
		TextView t72 = new TextView(this);t72.setTextSize(10f);t72.setTextColor(Color.BLUE);
		t72.setText(jgdmInfo.getJjhy());
		tr7.addView(t72);
		TableRow tr70 = new TableRow(this);
		
		TextView t73 = new TextView(this);t73.setTextSize(10f);t73.setGravity(Gravity.RIGHT);
		t73.setText("新经济行业(仅供参考)：");
		tr70.addView(t73);
		TextView t74 = new TextView(this);t74.setTextSize(10f);t74.setTextColor(Color.BLUE);
		t74.setText(jgdmInfo.getNjjhy());
		tr70.addView(t74);
		tableInfo.addView(tr7,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(tr70,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow tr8 = new TableRow(this);
		TextView t81 = new TextView(this);t81.setTextSize(10f);t81.setGravity(Gravity.RIGHT);
		t81.setText("经济类型：");
		tr8.addView(t81);
		TextView t82 = new TextView(this);t82.setTextSize(10f);t82.setTextColor(Color.BLUE);
		t82.setText(jgdmInfo.getJjlx());
		tr8.addView(t82);
		TableRow tr80 = new TableRow(this);
		
		TextView t83 = new TextView(this);t83.setTextSize(10f);t83.setGravity(Gravity.RIGHT);
		t83.setText("机构类型：");
		tr80.addView(t83);
		TextView t84 = new TextView(this);t84.setTextSize(10f);t84.setTextColor(Color.BLUE);
		t84.setText(jgdmInfo.getJglx());
		tr80.addView(t84);
		tableInfo.addView(tr8,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(tr80,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow tr9 = new TableRow(this);
		TextView t91 = new TextView(this);t91.setTextSize(10f);t91.setGravity(Gravity.RIGHT);
		t91.setText("注册日期：");
		tr9.addView(t91);
		TextView t92 = new TextView(this);t92.setTextSize(10f);t92.setTextColor(Color.BLUE);
		t92.setText(""+jgdmInfo.getZcrq());
		tr9.addView(t92);
		
		TableRow tr90 = new TableRow(this);
		TextView t93 = new TextView(this);t93.setTextSize(10f);t93.setGravity(Gravity.RIGHT);
		t93.setText("注册号：");
		tr90.addView(t93);
		TextView t94 = new TextView(this);t94.setTextSize(10f);t94.setTextColor(Color.BLUE);
		t94.setText(jgdmInfo.getZch());
		tr90.addView(t94);
		tableInfo.addView(tr9,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(tr90,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow tra = new TableRow(this);
		TextView ta1 = new TextView(this);ta1.setTextSize(10f);ta1.setGravity(Gravity.RIGHT);
		ta1.setText("邮政编码：");
		tra.addView(ta1);
		TextView ta2 = new TextView(this);ta2.setTextSize(10f);ta2.setTextColor(Color.BLUE);
		ta2.setText(""+jgdmInfo.getYzbm());
		tra.addView(ta2);
		TableRow tra0 = new TableRow(this);
		
		TextView ta3 = new TextView(this);ta3.setTextSize(10f);ta3.setGravity(Gravity.RIGHT);
		ta3.setText("电话号码：");
		tra0.addView(ta3);
		TextView ta4 = new TextView(this);ta4.setTextSize(10f);ta4.setTextColor(Color.BLUE);
		ta4.setText(jgdmInfo.getDhhm());
		tra0.addView(ta4);
		tableInfo.addView(tra,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(tra0,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow trb = new TableRow(this);
		TextView tb1 = new TextView(this);tb1.setTextSize(10f);tb1.setGravity(Gravity.RIGHT);
		tb1.setText("办证日期：");
		trb.addView(tb1);
		TextView tb2 = new TextView(this);tb2.setTextSize(10f);tb2.setTextColor(Color.BLUE);
		tb2.setText(""+jgdmInfo.getBzrq());
		trb.addView(tb2);
		TextView tb3 = new TextView(this);tb3.setTextSize(10f);tb3.setGravity(Gravity.RIGHT);
		TableRow trb0 = new TableRow(this);
		tb3.setText("作废日期：");
		trb0.addView(tb3);
		TextView tb4 = new TextView(this);tb4.setTextSize(10f);tb4.setTextColor(Color.BLUE);
		tb4.setText(""+jgdmInfo.getZfrq());
		trb0.addView(tb4);
		tableInfo.addView(trb,new TableLayout.LayoutParams(WC, WC));
		tableInfo.addView(trb0,new TableLayout.LayoutParams(WC, WC));
		//---------------------
		TableRow trc = new TableRow(this);
		TextView tc1 = new TextView(this);tc1.setTextSize(10f);tc1.setGravity(Gravity.RIGHT);
		tc1.setText("注册资金：");
		trc.addView(tc1);
		TextView tc2 = new TextView(this);tc2.setTextSize(10f);tc2.setTextColor(Color.BLUE);
		tc2.setText(""+jgdmInfo.getZczj());
		trc.addView(tc2);
		
		TableRow trc0 = new TableRow(this);
		TextView tc3 = new TextView(this);tc3.setTextSize(10f);tc3.setGravity(Gravity.RIGHT);
		tc3.setText("货币种类：");
		trc0.addView(tc3);
		TextView tc4 = new TextView(this);tc4.setTextSize(10f);tc4.setTextColor(Color.BLUE);
		tc4.setText(""+jgdmInfo.getHbzl());
		trc0.addView(tc4);
		tableInfo.addView(trc,new TableLayout.LayoutParams(WC, WC));
		tableInfo.addView(trc0,new TableLayout.LayoutParams(WC, WC));
		//---------------------
		TableRow trd = new TableRow(this);
		TextView td1 = new TextView(this);td1.setTextSize(10f);td1.setGravity(Gravity.RIGHT);
		td1.setText("年检日期：");
		trd.addView(td1);
		TextView td2 = new TextView(this);td2.setTextSize(10f);td2.setTextColor(Color.BLUE);
		td2.setText(""+jgdmInfo.getNjrq());
		trd.addView(td2);
		TableRow trd0 = new TableRow(this);
		TextView td3 = new TextView(this);td3.setTextSize(10f);td3.setGravity(Gravity.RIGHT);
		td3.setText("职工人数：");
		trd0.addView(td3);
		TextView td4 = new TextView(this);td4.setTextSize(10f);td4.setTextColor(Color.BLUE);
		td4.setText(""+jgdmInfo.getZgrs());
		trd0.addView(td4);
		tableInfo.addView(trd,new TableLayout.LayoutParams(WC, WC));
		tableInfo.addView(trd0,new TableLayout.LayoutParams(WC, WC));
		//---------------------
		TableRow tre = new TableRow(this);
		TextView te1 = new TextView(this);te1.setTextSize(10f);te1.setGravity(Gravity.RIGHT);
		te1.setText("批准文号：");
		tre.addView(te1);
		TextView te2 = new TextView(this);te2.setTextSize(10f);te2.setTextColor(Color.BLUE);
		te2.setText(""+jgdmInfo.getPzwh());
		tre.addView(te2);
		TextView te3 = new TextView(this);te3.setTextSize(10f);te3.setGravity(Gravity.RIGHT);
		TableRow tre0 = new TableRow(this);
		te3.setText("年检期限：");
		tre0.addView(te3);
		TextView te4 = new TextView(this);te4.setTextSize(10f);te4.setTextColor(Color.BLUE);
		te4.setText(""+jgdmInfo.getNjqx());
		tre0.addView(te4);
		tableInfo.addView(tre,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(tre0,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow trf = new TableRow(this);
		TextView tf1 = new TextView(this);tf1.setTextSize(10f);tf1.setGravity(Gravity.RIGHT);
		tf1.setText("主管机构代码：");
		trf.addView(tf1);
		TextView tf2 = new TextView(this);tf2.setTextSize(10f);tf2.setTextColor(Color.BLUE);
		tf2.setText(""+jgdmInfo.getZgdm());tf2.setWidth(WC);
		trf.addView(tf2);
		TableRow trf0 = new TableRow(this);
		TextView tf3 = new TextView(this);tf3.setTextSize(10f);tf3.setGravity(Gravity.RIGHT);
		tf3.setText("批准日期：");
		trf0.addView(tf3);
		TextView tf4 = new TextView(this);tf4.setTextSize(10f);tf4.setTextColor(Color.BLUE);
		tf4.setText(""+jgdmInfo.getPwrq());
		trf0.addView(tf4);
		tableInfo.addView(trf,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(trf0,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow trg = new TableRow(this);
		TextView tg1 = new TextView(this);tg1.setTextSize(10f);tg1.setGravity(Gravity.RIGHT);
		tg1.setText("主管机构名称：");
		trg.addView(tg1);
		TextView tg2 = new TextView(this);tg2.setTextSize(10f);tg2.setTextColor(Color.BLUE);
		tg2.setText(""+jgdmInfo.getZgmc());tg2.setWidth(WC);
		trg.addView(tg2);
		TableRow trg0 = new TableRow(this);
		TextView tg3 = new TextView(this);tg3.setTextSize(10f);tg3.setGravity(Gravity.RIGHT);
		tg3.setText("批准机构代码：");
		trg0.addView(tg3);
		TextView tg4 = new TextView(this);tg4.setTextSize(10f);tg4.setTextColor(Color.BLUE);
		tg4.setText(""+jgdmInfo.getPzjgdm());
		trg0.addView(tg4);
		tableInfo.addView(trg,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(trg0,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow trh = new TableRow(this);
		TextView th1 = new TextView(this);th1.setTextSize(10f);th1.setGravity(Gravity.RIGHT);
		th1.setText("办证机构代码：");
		trh.addView(th1);
		TextView th2 = new TextView(this);th2.setTextSize(10f);th2.setTextColor(Color.BLUE);
		th2.setText(""+jgdmInfo.getBzjgdm());
		trh.addView(th2);
		TableRow trh0 = new TableRow(this);
		TextView th3 = new TextView(this);th3.setTextSize(10f);th3.setGravity(Gravity.RIGHT);
		th3.setText("批准机构名称：");
		trh0.addView(th3);
		TextView th4 = new TextView(this);th4.setTextSize(10f);th4.setTextColor(Color.BLUE);
		th4.setText(""+jgdmInfo.getPzjgmc());
		trh0.addView(th4);
		tableInfo.addView(trh,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(trh0,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow tri = new TableRow(this);
		TextView ti1 = new TextView(this);ti1.setTextSize(10f);ti1.setGravity(Gravity.RIGHT);
		ti1.setText("办证机构名称：");
		tri.addView(ti1);
		TextView ti2 = new TextView(this);ti2.setTextSize(10f);ti2.setTextColor(Color.BLUE);
		ti2.setText(""+jgdmInfo.getBzjgmc());
		tri.addView(ti2);
		TableRow tri0 = new TableRow(this);
		TextView ti3 = new TextView(this);ti3.setTextSize(10f);ti3.setGravity(Gravity.RIGHT);
		ti3.setText("外方投资国别或地区：");
		tri0.addView(ti3);
		TextView ti4 = new TextView(this);ti4.setTextSize(10f);ti4.setTextColor(Color.BLUE);
		ti4.setText(""+jgdmInfo.getWftzgb());
		tri0.addView(ti4);
		tableInfo.addView(tri,new TableLayout.LayoutParams(FP, WC));
		tableInfo.addView(tri0,new TableLayout.LayoutParams(FP, WC));
		//---------------------
		TableRow trj = new TableRow(this);
		TextView tj1 = new TextView(this);tj1.setTextSize(10f);tj1.setGravity(Gravity.RIGHT);
		tj1.setText("变更日期：");
		trj.addView(tj1);
		TextView tj2 = new TextView(this);tj2.setTextSize(10f);tj2.setTextColor(Color.BLUE);
		tj2.setText(""+jgdmInfo.getBgrq());
		trj.addView(tj2);
		tableInfo.addView(trj,new TableLayout.LayoutParams(FP, WC));
	}
	
	public static <T> T jsonToBean(Object obj,JSONObject json) throws Exception {
		Class<T> c = (Class<T>) obj.getClass();
		T t = c.cast(obj);
		Field field[] = c.getDeclaredFields();
		for (Field f : field) {

			String filed_name = f.getName();
			String filed_form_name = f.getName();
			filed_name = filed_name.substring(0, 1).toUpperCase()
					+ filed_name.substring(1);
			
			Method method = null;
                method = c.getMethod("set" + filed_name,String.class);
                method.invoke(t,StringUtil.convertNull(json.getString(filed_form_name)));

		}

		return t;
	}
	
	public void entback(View view){
		ImageView back = (ImageView)findViewById(R.id.entback);

		this.finish();
	}
	
	public void homeback(View view){
		ImageView homeback = (ImageView)findViewById(R.id.homeback);
		Intent inte = new Intent(this, AppTabWidge.class);
		inte.putExtra("select", "1");
		startActivity(inte);
		this.finish();
	}
}
