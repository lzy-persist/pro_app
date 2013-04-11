package org.nacao.searchapp.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.nacao.searchapp.R;
import org.nacao.searchapp.manage.AppTabWidge;
import org.nacao.searchapp.util.ExitApplication;
import org.nacao.searchapp.util.StringUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class IndustryPieStatistics extends AbstractChart{
	private Button pieButton;
	private Button barButton;
	private int[] cols = new int[]{Color.BLUE, Color.GREEN,Color.YELLOW,Color.MAGENTA, Color.RED, Color.DKGRAY};
	private Map<String,Double> values;
	private GraphicalView mChartView;
	private LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xy_chart);
		layout = (LinearLayout) findViewById(R.id.chart);
		pieButton = (Button)findViewById(R.id.statis_pie);
		barButton = (Button)findViewById(R.id.statis_bar);
		
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
		values = null;
		values = new HashMap<String, Double>();
	    initData(values);
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
	    renderer.setLabelsTextSize(12);
	    renderer.setLegendTextSize(12);
	    renderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
	    renderer.setMargins(new int[] { 20, 30, 15, 0 });
	    renderer.setStartAngle(90);
	    
	    mChartView = ChartFactory.getPieChartView(this, buildCategoryDataset("��ҵ�ֲ�ͳ��",values),renderer);
	    
	    renderer.setClickEnabled(true);
	    renderer.setSelectableBuffer(10);
	    mChartView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
				if (seriesSelection != null) {
					Toast.makeText(
							IndustryPieStatistics.this,
							"��Ӧ����������" + seriesSelection.getValue(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void getBar(){
		values=null;
		mChartView=null;
		String[] titles = new String[] { "������ҵ" };
		values = new HashMap<String, Double>();
	    initData(values);
	    int[] colors = new int[]{Color.GREEN} ;
	    List<double[]> vales = new ArrayList<double[]>();
	    
	    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
	    setChartSettings(renderer, "", "��ҵ", "����", 0,
	        12, 0, 2000, Color.DKGRAY, Color.LTGRAY);
	    
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
	    return new Intent(context, IndustryPieStatistics.class);
	}

	private void initData(Map<String, Double> values) {

		values.put("ũҵ", (double) 608);
		values.put("��ҵ", (double) 320);
		values.put("��ҵ", (double) 211);
		values.put("����", (double) 308);
		values.put("����", (double) 400);
		values.put("����ҵ", (double) 1300);
		values.put("����ҵ", (double) 380);
		values.put("����ҵ", (double) 516);
		values.put("����ҵ", (double) 605);
		values.put("����ҵ", (double) 610);
		values.put("����", (double) 1600);
/*		
 	 	values.put("ũ���֡����������ҵ", (double) 310);
		values.put("ú̿��ѡҵ", (double) 186);
		values.put("ʯ�ͺ���Ȼ������ҵ", (double) 200);
		values.put("��ɫ�������ѡҵ", (double) 210);
		values.put("��ɫ�������ѡҵ", (double) 232);
		values.put("�ǽ������ѡҵ", (double) 467);
		values.put("�������ѡҵ", (double) 678);
		values.put("ľ�ļ���Ĳ���ҵ", (double) 420);
		values.put("ʳƷ�ӹ�Ҳ", (double) 521);
		values.put("ʳƷ����ҵ", (double) 300);
		values.put("��������ҵ", (double) 200);
		values.put("�̲ݼӹ�ҵ", (double) 412);
		values.put("��֯ҵ", (double) 386);
		values.put("��װ��������ά��Ʒ����ҵ", (double) 280);
		values.put("Ƥ���Ƥ�����޼�����Ʒҵ", (double) 500);
		values.put("�Ҿ�����ҵ", (double) 300);
		values.put("����ҵ", (double) 300);
		values.put("����ҵ", (double) 300);
		values.put("����", (double) 300);
		values.put("����", (double) 300);
		values.put("�Ļ�����ҵ", (double) 300);*/
		
	}

	@Override
	public String getName() {
		return "��ҵ�ֲ�ͳ��";
	}

	@Override
	public String getDesc() {
		return "���չ��Ҿ�����ҵ��׼�������а�֤����������ҵ���֣���������Ӧ��ͳ��ͼ��";
	}
	
}
