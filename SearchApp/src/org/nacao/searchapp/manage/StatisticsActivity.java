package org.nacao.searchapp.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nacao.searchapp.R;
import org.nacao.searchapp.statistics.AreaPieStatistics;
import org.nacao.searchapp.statistics.IndustryPieStatistics;
import org.nacao.searchapp.statistics.StatisticsChart;
import org.nacao.searchapp.util.DateProcess;
import org.nacao.searchapp.util.DisplayTool;
import org.nacao.searchapp.util.ExitApplication;
import org.nacao.searchapp.util.StringUtil;
import org.nacao.searchapp.util.Variable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StatisticsActivity extends Activity implements OnItemClickListener{
	private ListView lv;
	private StatisticsChart[] charts = new StatisticsChart[] {
			new AreaPieStatistics(),new IndustryPieStatistics()};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics_layout);
		lv = (ListView)findViewById(R.id.statislist);
		
		lv.setOnItemClickListener(this);
		lv.setAdapter(new SimpleAdapter(this, getListValues(), android.R.layout.simple_list_item_2, new String[]{StatisticsChart.NAME,StatisticsChart.DESC}, new int[]{android.R.id.text1,android.R.id.text2}));
	
		Log.v("searchapp", "手机号码为"+DisplayTool.getPhoneNum(StatisticsActivity.this)+"的用户,"+DateProcess.getSysTime()+"：查看机构的统计信息图表，登录用户为："+Variable.user.getUserName()+"。");

		ExitApplication.getInstance().addActivity(this);
	}

	private List<Map<String, String>> getListValues() {
		List<Map<String, String>> values = new ArrayList<Map<String, String>>();
		for (int i = 0; i < charts.length; i++) {
			Map<String, String> v = new HashMap<String, String>();
			v.put(StatisticsChart.NAME, charts[i].getName());
			v.put(StatisticsChart.DESC, charts[i].getDesc());
			values.add(v);
		}
		return values;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = charts[position].execute(this);
		
		startActivity(intent);
	}
	
	
	public void homeback(View view){
		ImageView homeback = (ImageView)findViewById(R.id.homeback);
		Intent inte = new Intent(this, AppTabWidge.class);
		inte.putExtra("select", "1");
		startActivity(inte);
		this.finish();
	}

}
