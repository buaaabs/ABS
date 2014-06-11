package com.lilele.automatic;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import hha.aiml.Robot;
import hha.main.MainActivity;

public class AuTomatic {

	private MainActivity mainActivity;
    private String[] waitTime={"5s","10s","20s","30s","60s","2m"};
	private Timer mtimer;
    public boolean m_bISaid;
    private int countTime;
    private Robot mbot;
    private int memotionInt;
    
    public int emotionValue(){
    	return memotionInt;
    }
    
    
    
  
    
	
	public Robot getMbot() {
		return mbot;
	}

    
	public void setMbot(Robot mbot) {
		this.mbot = mbot;
	}

	public int getCountTime() {
		return countTime;
	}

	public void setCountTime(int countTime) {
		this.countTime = countTime;
	}

	public AuTomatic(MainActivity mainAct,Robot bot) {
		mainActivity = mainAct;
		mtimer =new Timer(true);
		m_bISaid=false;
		countTime=0;
		this.memotionInt=0;
		this.setMbot(bot);
		setTimeTask();
	}
	
	public void destroy(){
		mtimer.cancel();
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			String ansString="";
			switch (what) {
			case 5:
				Toast.makeText(mainActivity, "timer+5", 1).show();
				if (mbot!=null) {
					ansString=mbot.Respond("5s");
					mainActivity.Show(null, ansString);
				}
				break;
			case 10:
				Toast.makeText(mainActivity, "timer+10", 1).show();
				if (mbot!=null) {
					
					ansString=mbot.Respond("10s");
					mainActivity.Show(null, ansString);
				}
				break;
			case 20:
				Toast.makeText(mainActivity, "timer+20", 1).show();
				if (mbot!=null) {
					ansString=mbot.Respond("20s");
					mainActivity.Show(null, ansString);
				}
				break;
			case 30:
				Toast.makeText(mainActivity, "timer+30", 1).show();
				if (mbot!=null) {
					ansString=mbot.Respond("30s");
					mainActivity.Show(null, ansString);
				}
				break;
			case 60:
				Toast.makeText(mainActivity, "timer+60", 1).show();
				if (mbot!=null) {
					ansString=mbot.Respond("60s");
					mainActivity.Show(null, ansString);
				}
				break;
			case 120:
				Toast.makeText(mainActivity, "timer+120", 1).show();
				if (mbot!=null) {
					mbot.Respond("2m");
				}
				break;

			default:
				break;
			}
		};
	};

	private void setTimeTask() {
		mtimer.schedule(new MyTimerTask(), 10000,1000);
	}

	private class MyTimerTask extends TimerTask {

		@Override
		public void run() {
           
			countTime++;
			switch (countTime) {
			case 5:
			{
				Message msg = Message.obtain();
				msg.what = 5;
				memotionInt=1;
				myHandler.sendMessage(msg);
				break;
			}
			case 10:
			{
				Message msg = Message.obtain();
				msg.what = 10;
				memotionInt=2;
				myHandler.sendMessage(msg);
				break;
			}
			case 20:
			{
				Message msg = Message.obtain();
				msg.what = 20;
				memotionInt=3;
				myHandler.sendMessage(msg);
				break;
			}
			case 30:
			{
				Message msg = Message.obtain();
				msg.what = 30;
				memotionInt=4;
				myHandler.sendMessage(msg);
				break;
			}
			case 60:
			{
				Message msg = Message.obtain();
				msg.what = 60;
				memotionInt=5;
				myHandler.sendMessage(msg);
				break;
			}
			case 120:
			{
				Message msg = Message.obtain();
				msg.what = 120;
				memotionInt=6;
				myHandler.sendMessage(msg);
				break;
			}
			default:
				break;
			}
		
		}

	}

}
