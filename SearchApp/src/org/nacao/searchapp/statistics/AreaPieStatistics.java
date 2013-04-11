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
	    
	    mChartView = ChartFactory.getPieChartView(this, buildCategoryDataset("�����ֲ�ͳ��",values),renderer);

	    renderer.setClickEnabled(true);
	    renderer.setSelectableBuffer(10);
	    mChartView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					Toast.makeText(
							AreaPieStatistics.this,
							"��Ӧ����������" + seriesSelection.getValue(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void getBar(){
		mChartView=null;
		String[] titles = new String[] { "�����ֲ�" };
		values = new HashMap<String, Double>();
	    initData(values);
	    int[] colors = new int[]{Color.GREEN} ;
	    List<double[]> vales = new ArrayList<double[]>();
	    
	    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
	    setChartSettings(renderer, "", "����", "����", 0,
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

		values.put("����", (double) 608);
		values.put("���", (double) 320);
		values.put("�ӱ�", (double) 211);
		values.put("ɽ��", (double) 310);
		values.put("����", (double) 186);
		values.put("����", (double) 200);
		values.put("����", (double) 210);
		values.put("����", (double) 232);
		values.put("����", (double) 678);
		values.put("����", (double) 420);
		values.put("�㶫", (double) 800);
/*		values.put("����", (double) 300);
		values.put("�Ϻ�", (double) 467);
		values.put("�㽭", (double) 521);
		values.put("����", (double) 200);
		values.put("����", (double) 412);
		values.put("ɽ��", (double) 386);
		values.put("����", (double) 280);
		values.put("����", (double) 300);
		values.put("����", (double) 356);
		values.put("����", (double) 300);
		values.put("����", (double) 200);
		values.put("�Ĵ�", (double) 280);
		values.put("����", (double) 200);
		values.put("����", (double) 200);
		values.put("����", (double) 300);
		values.put("����", (double) 200);
		values.put("����", (double) 223);
		values.put("�ຣ", (double) 180);
		values.put("����", (double) 160);
		values.put("�½�", (double) 140);
		*/
	}

	public String getName() {
		return "�����ֲ�ͳ��";
	}

	public String getDesc() {
		return "ȫ��ʡ����������Ϊ46�������ģ�����46���������ϱ����������������Ӧ��ͳ��ͼ��";
	}

}
