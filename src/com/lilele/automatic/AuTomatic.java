package com.lilele.automatic;

import java.util.Timer;
import java.util.TimerTask;

import bitoflife.chatterbean.aiml.Random;
import android.R.integer;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import hha.aiml.Robot;
import hha.main.MainActivity;
import hha.mode.Mode;

public class AuTomatic {

	private MainActivity mainActivity;
	private String[] waitTime = { "5s", "10s", "20s", "30s", "60s", "2m" };
	private Timer mtimer;
	private boolean isTimerRun = false;

	private int UserCountTime;
	private int RobotCountTime;
	private Robot mbot;
	private int memotionInt;
	private int i_waitTime;// 等待时间，由正态分布产生
	private String s_emotionStatus; // 情感状态
	private boolean b_exit;

	public boolean isB_exit() {
		return b_exit;
	}

	public void setB_exit(boolean b_exit) {
		this.b_exit = b_exit;
	}

	public String getS_emotionStatus() {
		return s_emotionStatus;
	}

	public void setS_emotionStatus(String s_emotionStatus) {
		this.s_emotionStatus = s_emotionStatus;
	}

	public int emotionValue() {
		return memotionInt;
	}

	public Robot getMbot() {
		return mbot;
	}

	public void setMbot(Robot mbot) {
		this.mbot = mbot;
	}



	public int getUserCountTime() {
		return UserCountTime;
	}

	public void setUserCountTime(int userCountTime) {
		UserCountTime = userCountTime;
	}

	public int getRobotCountTime() {
		return RobotCountTime;
	}

	public void setRobotCountTime(int robotCountTime) {
		RobotCountTime = robotCountTime;
	}

	public AuTomatic(MainActivity mainAct, Robot bot) {

		mainActivity = mainAct;
		b_exit = false;
		UserCountTime = 0;
		RobotCountTime = 0;
		this.memotionInt = 0;
		i_waitTime = 40;
		s_emotionStatus = "普通";
		this.setMbot(bot);
		Mode.ModeInit(mainAct, this);
		start();
	}

	public void destroy() {
		if (isTimerRun == true) {
			isTimerRun = false;
			mtimer.cancel();
		}
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			String ansString = "";
			switch (what) {
			case 1: // 普通
				Toast.makeText(mainActivity, "普通", 1).show();
				if (mbot != null) {
					ansString = mbot.Respond("1");
					mainActivity.Show(null, ansString);
				}
				break;
			case 2:// 高兴
				Toast.makeText(mainActivity, "高兴", 1).show();
				if (mbot != null) {

					ansString = mbot.Respond("2");
					mainActivity.Show(null, ansString);
				}
				break;
			case 3:// 困倦
				Toast.makeText(mainActivity, "困倦", 1).show();
				if (mbot != null) {
					ansString = mbot.Respond("3");
					mainActivity.Show(null, ansString);
				}
				break;
			case 4:// 忧伤
				Toast.makeText(mainActivity, "忧伤", 1).show();
				if (mbot != null) {
					ansString = mbot.Respond("4");
					mainActivity.Show(null, ansString);
				}
				break;
			case 5:// 恐惧
				Toast.makeText(mainActivity, "恐惧", 1).show();
				if (mbot != null) {
					ansString = mbot.Respond("5");
					mainActivity.Show(null, ansString);
				}
				break;
			case 6:// 愤怒
				Toast.makeText(mainActivity, "愤怒", 1).show();
				if (mbot != null) {
					ansString = mbot.Respond("6");
					mainActivity.Show(null, ansString);
				}
				break;
			case 7:// 软件自动退出
				Toast.makeText(mainActivity, "软件免打扰模式", 1).show();
				if (mbot != null) {
					ansString = mbot.Respond("7");
					mainActivity.Show(null, ansString);
					RobotCountTime = 0;
					b_exit = true;
				}
				break;

			default:
				break;
			}
		};
	};

	public void start() {
		if (isTimerRun == false) {
			isTimerRun = true;
			mtimer = new Timer(true);
			mtimer.schedule(new MyTimerTask(), 10000, 1000);
		}
	}

	private class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			if (!mbot.isInitDone())
				return;

			mbot.getBot().getEmotion().Update();

			UserCountTime++;
			RobotCountTime++;
			if (RobotCountTime == 8 && b_exit == true) {
				programExit();
			}

			String mode = mbot.getProperty("mode");
			Mode.Switch(mode, UserCountTime,RobotCountTime);
			/*
			 * if (countTime == i_waitTime) { Message msg = Message.obtain(); if
			 * (s_emotionStatus.equals("普通")) { msg.what = 1; } else if
			 * (s_emotionStatus.equals("高兴")) { msg.what = 2; } else if
			 * (s_emotionStatus.equals("困倦")) { msg.what = 3; } else if
			 * (s_emotionStatus.equals("忧伤")) { msg.what = 4; } else if
			 * (s_emotionStatus.equals("恐惧")) { msg.what = 5; } else if
			 * (s_emotionStatus.equals("愤怒")) { msg.what = 6; }
			 * 
			 * myHandler.sendMessage(msg); }
			 * 
			 * // if (countTime == 65) { // Message msg = Message.obtain(); //
			 * msg.what = 6; // myHandler.sendMessage(msg); // } if (countTime
			 * == 180) { Message msg = Message.obtain(); msg.what = 7;
			 * myHandler.sendMessage(msg); }
			 */
		}

	}

	// 正态分布产生随机数，期望45，方差5，范围【30，60】
	private int productGussianTime() {
		java.util.Random random = new java.util.Random();
		double double_res = Math.sqrt(25) * random.nextGaussian() + 45;
		int int_res = (int) double_res;
		if (int_res >= 30 && int_res <= 60) {
			return int_res;
		} else {
			return 40;
		}
	}

	private void programExit() {
		mainActivity.Exit();
	}

}
