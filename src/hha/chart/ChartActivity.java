package hha.chart;


import hha.robot.R;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChartActivity extends Activity{

	private View chartView = null;
	private LinearLayout line = null;
	private Button button = null;
	private TextView title = null;
	private int[] dates = null;
	private double[] values = null;
	private String xLaString = null;
	private String yLaString = null;
	private String titleString = null;
	
	private OnClickListener myClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String[] items = new String[]{"sb1","sb2","sb3","sb4"};
			
			AlertDialog ad = new AlertDialog.Builder(ChartActivity.this)
	        .setTitle("其他显示图")
	        .setItems(items, null).create();
			ad.show();
			
			ad.getListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Toast.makeText(ChartActivity.this, String.valueOf(arg2), Toast.LENGTH_SHORT).show();
				}
			});
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.my_chart);
		
		Intent intent = getIntent();
		dates = intent.getIntArrayExtra("date");
		values = intent.getDoubleArrayExtra("value");
		xLaString = intent.getStringExtra("x");
		yLaString = intent.getStringExtra("y");
		titleString = intent.getStringExtra("title");
		
		line = (LinearLayout)findViewById(R.id.chartLinearLayout);
		button = (Button)findViewById(R.id.more);
		button.setOnClickListener(myClickListener);
		title = (TextView)findViewById(R.id.title);
		title.setText("健康情况统计图");
		
		chartView = getLineChartView();
		//ViewGroup.LayoutParams vGroup = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//chartView.setLayoutParams(vGroup);
		line.addView(chartView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	}
	
	private View getLineChartView(){
		View view = ChartFactory.getLineChartView(this, getLineDataset(), getLineRenderer());
		return view;	
	}

	private XYMultipleSeriesRenderer getLineRenderer() {
		// TODO Auto-generated method stub
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setChartTitle(titleString);
		//renderer.setBackgroundColor(Color.YELLOW);
		//renderer.setApplyBackgroundColor(true);
		
		//renderer.setGridColor(Color.GRAY);
		//renderer.setMarginsColor(Color.GRAY);
		renderer.setXTitle(xLaString);
		renderer.setXLabelsColor(Color.WHITE);
		renderer.setYTitle(yLaString);
		renderer.setYLabelsColor(0,Color.WHITE);
		renderer.setShowGrid(true);
	    renderer.setXLabelsAlign(Align.RIGHT);
	    renderer.setYLabelsAlign(Align.RIGHT);
	    renderer.setZoomButtonsVisible(true);
		//renderer.setAxesColor(Color.BLUE);
		renderer.setMargins(new int[]{25,30,20,0});
		
		XYSeriesRenderer  series = new XYSeriesRenderer();
		series.setPointStyle(PointStyle.SQUARE);
		series.setFillPoints(true);
		series.setColor(Color.BLUE);
		
		renderer.addSeriesRenderer(series);
		
		return renderer;
	}

	private XYMultipleSeriesDataset getLineDataset() {
		// TODO Auto-generated method stub
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		//Random random = new Random();
		XYSeries series = new XYSeries(yLaString+"值");
		for(int i=0;i<dates.length;++i){
			series.add(dates[i], values[i]);
		}
		dataset.addSeries(series);
		return dataset;
	}
	
	
}

