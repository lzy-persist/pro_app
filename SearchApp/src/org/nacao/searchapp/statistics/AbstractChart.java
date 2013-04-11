package org.nacao.searchapp.statistics;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.nacao.searchapp.R;
import org.nacao.searchapp.manage.AppTabWidge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public abstract class AbstractChart extends Activity implements StatisticsChart {

	protected DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	protected CategorySeries buildCategoryDataset(String title,
			Map<String, Double> values) {
		CategorySeries series = new CategorySeries(title);
		if (values != null && values.size() > 0) {
			for (Entry<String, Double> vlues : values.entrySet()) {
				series.add(vlues.getKey(), (double) vlues.getValue());
			}
		}
		return series;
	}

	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(14);
		renderer.setChartTitleTextSize(16);
		renderer.setLabelsTextSize(12);
		renderer.setLegendTextSize(12);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
			List<double[]> values) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

	public void chartback(View view) {
		ImageView back = (ImageView) findViewById(R.id.entback);

		this.finish();
	}

	public void homeback(View view) {
		ImageView homeback = (ImageView) findViewById(R.id.homeback);
		Intent inte = new Intent(this, AppTabWidge.class);
		inte.putExtra("select", "1");
		startActivity(inte);
		this.finish();
	}
}
