package hha.mode.subclass;

import hha.main.MainActivity;
import hha.mode.Mode;

import com.lilele.automatic.AuTomatic;

import android.os.Message;

public class NomalMode extends Mode {

	
	public NomalMode(MainActivity mainActivity, AuTomatic auto) {
		super(mainActivity, auto);
		// TODO Auto-generated constructor stub
		i_waitTime = (int) N_rand(30,5,0,60);
	}

	protected String s_emotionStatus; // 情感状态
	@Override
	public void Run(int UserCount,int RobotCount) {
		// TODO Auto-generated method stub
		s_emotionStatus = bot.getProperty("Emotion");
		
		if (UserCount == i_waitTime) {
			String ansString = bot.Respond("AUTO_"+s_emotionStatus);
			mainActivity.Show("AUTO_"+s_emotionStatus, ansString);
			i_waitTime = (int) N_rand(30,5,0,60);
		}
		
		if (UserCount == 180) {
			String ansString = bot.Respond("AUTO_免打扰");
			auto.setB_exit(true);
			mainActivity.Show(null, ansString);
			i_waitTime = (int) N_rand(30,5,0,60);
		}
	}
	
	
	// 正态分布产生随机数，期望45，方差5，范围【30，60】
		public static int productGussianTime() {
				java.util.Random random = new java.util.Random();
				double double_res = Math.sqrt(25) * random.nextGaussian() + 45;
				int int_res = (int) double_res;
				if (int_res >= 30 && int_res <= 60) {
					return int_res;
				} else {
					return 40;
				}
			}


	@Override
	protected String getModeName() {
		// TODO Auto-generated method stub
		return "normal";
	}

}
