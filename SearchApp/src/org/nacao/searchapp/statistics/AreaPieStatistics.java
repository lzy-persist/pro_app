package org.nacao.searchapp.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.nacao.searchapp.R;
import org.nacao.searchapp.util.ExitApplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AreaPieStatistics extends AbstractChart{
	private Button pieButton;
	private Button barButton;
	private int[] cols = new int[]{Color.BLUE, Color.GREEN,Color.YELLOW,Color.MAGENTA, Color.RED, Color.DKGRAY};
	private Map<String,Double> values=new HashMap<String, Double>();
	private GraphicalView mChartView;
	private LinearLayout layout;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xy_chart);
		layout = (LinearLayout) findViewById(R.id.chart);
		pieButton = (Button)findViewById(R.id.statis_pie);
		barButton = (Button)findViewById(R.id.statis_bar);
	    initData(values);
	    
		getPie();
		
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		pieButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				layout.removeAllViews();
				getPie();
				layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			}
		});
		
		barButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				layout.removeAllViews();
				getBar();
				layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			}
		});
				
		ExitApplication.getInstance().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(mChartView!=null){
			mChartView.repaint();
		}
	}

	private  void getPie(){
		mChartView=null;
	    int[] colors = new int[values.size()] ;
//	    int temp=0;
	    Random rd = new Random();
	    for(int i=0;i<values.size();i++){
/*	    	int k = rd.nextInt(cols.length);
	    	while(temp==k){
	    		k=rd.nextInt(cols.length);
	    	}
    		temp=k;
*/    		
	    	if(i>=cols.length){
	    		colors[i]=Color.argb(rd.nextInt(50)+1, rd.nextInt(80)+1, rd.nextInt(100)+1, rd.nextInt(80)+1);
	    	}else{
	    		colors[i]=cols[i];
	    	}
	    }
	    
	    
	    DefaultRenderer renderer = buildCategoryRenderer(colors);
	    renderer.setZoomButtonsVisible(true);
	    renderer.setZoomEnabled(true);
	    renderer.setChartTitleTextSize(16);
	    renderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
	    renderer.setMargins(new int[] { 20, 30, 15, 0 });
	    
	    mChartView = ChartFactory.getPieChartView(this, buildCategoryDataset("地区分布统计",values),renderer);

	    renderer.setClickEnabled(true);
	    renderer.setSelectableBuffer(10);
	    mChartView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					Toast.makeText(
							AreaPieStatistics.this,
							"对应机构数量：" + seriesSelection.getValue(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void getBar(){
		mChartView=null;
		String[] titles = new String[] { "地区分布" };
		values = new HashMap<String, Double>();
	    initData(values);
	    int[] colors = new int[]{Color.GREEN} ;
	    List<double[]> vales = new ArrayList<double[]>();
	    
	    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
	    setChartSettings(renderer, "", "地区", "数量", 0,
	        12, 0, 1000, Color.DKGRAY, Color.LTGRAY);
	    
	    renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
	    renderer.setXLabels(1);
	    renderer.setYLabels(10);
	    double start = 0.5;
	    int i=0;
	    double[] db = new double[values.size()];
	    for(Entry<String, Double> ev : values.entrySet()){
    		renderer.addXTextLabel(start, ev.getKey());
    		db[i]=ev.getValue();
    		i++;
    		start+=1;
	    }
	    vales.add(db);
	    
	    renderer.setXLabelsAlign(Align.LEFT);
	    renderer.setYLabelsAlign(Align.LEFT);
	    renderer.setPanEnabled(true, false);
	     renderer.setZoomEnabled(true);
	    renderer.setZoomRate(1.1f);
	    renderer.setBarSpacing(0.5f);
	    mChartView = ChartFactory.getBarChartView(this, buildBarDataset(titles,vales), renderer,Type.STACKED);
	    
	}
	
	@Override
	public Intent execute(Context context) {
	    return new Intent(context, AreaPieStatistics.class);
	}

	private void initData(Map<String, Double> values) {

		values.put("北京", (double) 608);
		values.put("天津", (double) 320);
		values.put("河北", (double) 211);
		values.put("山西", (double) 310);
		values.put("内蒙", (double) 186);
		values.put("吉林", (double) 200);
		values.put("辽宁", (double) 210);
		values.put("云南", (double) 232);
		values.put("江苏", (double) 678);
		values.put("安徽", (double) 420);
		values.put("广东", (double) 800);
/*		values.put("福建", (double) 300);
		values.put("上海", (double) 467);
		values.put("浙江", (double) 521);
		values.put("江西", (double) 200);
		values.put("河南", (double) 412);
		values.put("山东", (double) 386);
		values.put("湖北", (double) 280);
		values.put("湖南", (double) 300);
		values.put("重庆", (double) 356);
		values.put("广西", (double) 300);
		values.put("海南", (double) 200);
		values.put("四川", (double) 280);
		values.put("贵州", (double) 200);
		values.put("云南", (double) 200);
		values.put("陕西", (double) 300);
		values.put("西藏", (double) 200);
		values.put("甘肃", (double) 223);
		values.put("青海", (double) 180);
		values.put("宁夏", (double) 160);
		values.put("新疆", (double) 140);
		*/
	}

	public String getName() {
		return "地区分布统计";
	}

	public String getDesc() {
		return "全国省市区共划分为46个分中心，按照46个分中心上报的数据情况给出对应的统计图！";
	}

}
