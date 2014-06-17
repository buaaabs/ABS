package hha.chart;


import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import android.R.anim;
import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyChart {

	private Context context = null;
	/**
	 * 用于储存日期取用部分
	 */
	private int[] dates = null;
	/**
	 * 用于储存日期对应的值得部分
	 */
	private double[] values = null;
	/**
	 * x坐标轴的标题
	 */
	private String xLaString = "";
	/**
	 * y坐标轴的标题
	 */
	private String yLaString = "";
	/**
	 * 统计图的标题
	 */
	private String title = "";
	/**
	 * 设置统计图的标题
	 * @param title
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 设置x坐标轴标签
	 * @param x
	 */
	public void setXLaString(String x){
		this.xLaString = x;
	}
	
	/**
	 * 设置y坐标轴标签
	 * @param y
	 */
	public void setYLaString(String y){
		this.yLaString = y;
	}
	
	/**
	 * 构造函数
	 * @param context
	 */
	public MyChart(Context context){
		this.context = context;
	}
	
	/**
	 * 通过月份得到该月的天数
	 * @param month
	 * @return
	 */
	private int getDayNum(int month,int year) {
		switch (month+1) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
			
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			return getFebDays(year);
		default:
			return 30;
		}
	}
	
	/**
	 * 根据平年闰年得到二月份天数
	 * @param year
	 * @return
	 */
	private int getFebDays(int year){
		boolean flag = false;
		if(year % 400 == 0){
			flag = true;
		}else if(year % 4 != 0){
			flag = false;
		}else{
			if(year % 100 == 0){
				flag = false;
			}else{
				flag = true;
			}
		}
		
		if(flag) return 29;
		else return 28;
	}
	
	/**
	 * 传入时间,不指示按年或月或日或小时为时间轴，通过自动判断处理,若有两个日期相同，则抛出SameDateException异常
	 * @param dates
	 * @throws SameDateException 
	 */
	@SuppressWarnings("deprecation")
	public void setDate(final Date[] dates) throws SameDateException{
		for(int i =0;i<dates.length - 1;++i){
			for(int j =i+1;j<dates.length;++j){
				if(dates[i].equals(dates[j])){
					throw new SameDateException();
				}
			}
		}
		this.dates = new int[dates.length];
		if(dates.length <= 1){
			for(int i= 0;i<dates.length;++i){
				this.dates[i] = dates[i].getDate();
			}
		}
		int key = -1;
//		if(xLaString.equals(""))	xLaString = "小时";
//		if(dates[0].getDate() != dates[1].getDate()){
//			key = 0;
//			Toast.makeText(context, "天", Toast.LENGTH_SHORT).show();
//			if(xLaString.equals("")) xLaString = "天";
//		}else if(dates[0].getMonth() != dates[1].getMonth()){
//			key = 1;
//			if(xLaString.equals("")) xLaString = "月";
//		}else if(dates[0].getYear() != dates[1].getYear()){
//			key = 2;
//			if(xLaString.equals("")) xLaString = "年";
//		}
		
		int end = dates.length-1;
		int year = dates[0].getYear()-dates[end].getYear();
		if(year > 1 || year < -1){
			key = 2;
			if(xLaString.equals("")) xLaString = "年";
		}else{
			int month = dates[0].getMonth()-dates[end].getMonth();
			if(month > 1 || month < -1){
				key = 1;
				if(xLaString.equals("")) xLaString = "月";
			}else{
				int day = dates[0].getDate()-dates[end].getDate();
				if(day > 1 || day < -1){
					key = 0;
					if(xLaString.equals("")) xLaString = "天";
				}else{
					key = -1;
					if(xLaString.equals("")) xLaString = "小时";
				}
			}
		}
		
		
		switch (key) {
		case -1:
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getHours();
				if(i>0 && this.dates[i] < this.dates[i-1]){
					this.dates[i] += 24;
				}
			}
			break;
		case 0:
			int days = getDayNum(dates[0].getMonth(),dates[0].getYear());
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getDate();
				if(i>0 && this.dates[i] < this.dates[i-1]){
					this.dates[i] += days;
				}
			}
			break;
		case 1:
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getMonth();
				if(i>0 && this.dates[i] < this.dates[i-1]){
					this.dates[i] += 12;
				}
			}
			break;
		case 2:
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getYear();
			}
			break;

		default:
			int dayss = getDayNum(dates[0].getMonth(),dates[0].getYear());
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getDate();
				if(i>0 && this.dates[i] < this.dates[i-1]){
					this.dates[i] += dayss;
				}
			}
			break;
		}
	}
	
	/**
	 * 设置时间，传入String key值标识了以什么为横轴，其中“y”表示年，“m”表示月，
	 * “d”表示天，“h”表示小时,如果非以上标识，按天来处理，
	 * 若有两个日期相同，则抛出SameDateException异常
	 * @param  dates
	 * @param  key
	 * @throws SameDateException 
	 */
	@SuppressWarnings("deprecation")
	public void setDate(final Date[] dates,final String key) throws SameDateException{
		for(int i =0;i<dates.length - 1;++i){
			for(int j =i+1;j<dates.length;++j){
				if(dates[i].equals(dates[j])){
					throw new SameDateException();
				}
			}
		}
		
		this.dates = new int[dates.length];
		if(key.equals("y") || key.equals("Y")){
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getYear();
			}
			if(xLaString.equals("")) xLaString = "年";
		}else if(key.equals("m") || key.equals("m")){
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getMonth();
				if(i>0 && this.dates[i] < this.dates[i-1]){
					this.dates[i] += 12;
				}
			}
			if(xLaString.equals("")) xLaString = "月";
		}else if(key.equals("d") || key.equals("d")){
			int days = getDayNum(dates[0].getMonth(),dates[0].getYear());
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getDate();
				if(i>0 && this.dates[i] < this.dates[i-1]){
					this.dates[i] += days;
				}
			}
			if(xLaString.equals("")) xLaString = "天";
		}else if(key.equals("h") || key.equals("h")){
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getHours();
				if(i>0 && this.dates[i] < this.dates[i-1]){
					this.dates[i] += 24;
				}
			}
			if(xLaString.equals("")) xLaString = "小时";
		}else{
			int dayss = getDayNum(dates[0].getMonth(),dates[0].getYear());
			for(int i =0;i<dates.length;++i){
				this.dates[i] = dates[i].getDate();
				if(i>0 && this.dates[i] < this.dates[i-1]){
					this.dates[i] += dayss;
				}
			}
			if(xLaString.equals("")) xLaString = "天";
		}
	}
	
	/**
	 * 传入对应的数据
	 * @param values
	 */
	public void setValue(final double[] values) {
		this.values = new double[values.length];
		for(int i=0;i<values.length;++i){
			this.values[i] = values[i];
		}
	}
	
	
	/**
	 * 开启一个新的activity，如果之前没有setDate，则抛出NotSetDateException异常，
	 * 如果之前没有setValue，则抛出NotSetValueException异常，如果日期数量与数据数量不同
	 * 抛出DateNotMatchValueException异常
	 * @throws NotSetDateException
	 * @throws NotSetValueException
	 * @throws DateNotMatchValueException
	 */
	public void startActivity() throws NotSetDateException, NotSetValueException, DateNotMatchValueException{
		
	    if(dates == null){
	    	throw new NotSetDateException();
	    }
	    if(values == null){
	    	throw new NotSetValueException();
	    }
	    if(dates.length != values.length){
	    	throw new DateNotMatchValueException();
	    }
	    
	    //Date_Value_Pair_Format format = new Date_Value_Pair_Format(dates, values);
	    
	    Intent intent = new Intent(context,ChartActivity.class);
	    intent.putExtra("date", dates);
	    intent.putExtra("value", values);
	    intent.putExtra("x", xLaString);
	    intent.putExtra("y", yLaString);
	    intent.putExtra("title", title);
	    context.startActivity(intent);
	}
	
	/**
	 * 将日期和数值打包后排序的类
	 * @author hyf
	 *
	 */
	private class Date_Value_Pair_Format{
		private d_v[] pairs = null;
		
		/**
		 * 用于排序使用
		 * @author hyf
		 *
		 */
		private class d_v implements Comparable<d_v>{
			public Date da;
			public double d;
		
			@Override
			public int compareTo(d_v another) {
				// TODO Auto-generated method stub
				if(this.da.compareTo(another.da) > 0){
					return 1;
				}else if(this.da.compareTo(another.da) < 0){
					return -1;
				}else {
					if(this.d > another.d){
						return 1;
					}else if(this.d == another.d){
						return 0;
					}else{
						return -1;
					}
				}
			}
		}
		
		public Date_Value_Pair_Format(Date[] dates,double[] ds){
			this.pairs = new d_v[dates.length];
			for (int i = 0; i < ds.length; i++) {
				this.pairs[i] = new d_v();
				this.pairs[i].da = dates[i];
				this.pairs[i].d = ds[i];
			}
			
			Arrays.sort(this.pairs);
		}
		
		/**
		 * 获得规则的时间
		 * @return
		 */
		public Date[] getFormatDates(){
			Date[] dd = new Date[pairs.length];
			for(int i =0;i<pairs.length;++i){
				dd[i] = pairs[i].da;
			}
			return dd;
		}
		
		/**
		 * 获得规则的数值
		 * @return
		 */
		public double[] getFormatValues(){
			double[] dd = new double[pairs.length];
			for (int i = 0; i < dd.length; i++) {
				dd[i] = pairs[i].d;
			}
			return dd;
		}
		
	}
}

