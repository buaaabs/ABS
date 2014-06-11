package hha.aiml;

import hha.main.MainActivity;

public class BotEmotion {
	public static MainActivity main;
	
	static emotionLocator locator = new emotionLocator();
	static int vitality; // 活跃度 困倦-兴奋
	static int happiness; // 快乐度 忧伤-快乐
	static int confidence;// 置信度 不确定-确定
	static int mighty; // 强势度 恐惧-愤怒
	// 范围均是0~10000

	// 性格 情感变化率？ 0-1000%
	static int vitality_d;
	static int happiness_d;
	static int confidence_d;
	static int mighty_d;

	// 不确定性 百分比 0-1000%
	static int vitality_u;
	static int happiness_u;
	static int confidence_u;
	static int mighty_u;

	public static void init() {

		vitality = (int) (N_rand(5000.0, 50.0) + 0.5);
		happiness = (int) (N_rand(5000.0, 50.0) + 0.5);
		confidence = (int) (N_rand(5000.0, 50.0) + 0.5);
		mighty = (int) (N_rand(5000.0, 50.0) + 0.5);

		vitality_d = (int) (N_rand(80.0, 5.0) + 0.5);
		happiness_d = (int) (N_rand(80.0, 5.0) + 0.5);
		confidence_d = (int) (N_rand(80.0, 5.0) + 0.5);
		mighty_d = (int) (N_rand(80.0, 5.0) + 0.5);

		vitality_u = (int) (N_rand(15.0, 9.0) + 0.5);
		happiness_u = (int) (N_rand(15.0, 9.0) + 0.5);
		confidence_u = (int) (N_rand(15.0, 9.0) + 0.5);
		mighty_u = (int) (N_rand(15.0, 9.0) + 0.5);

	}
	
	public static void Update()
	{
		double dv = (5000 - vitality) * N_rand(80.0, 5.0) * 0.01;
		double dh = (5000 - happiness) * N_rand(80.0, 5.0) * 0.01;
		double dm = (5000 - mighty) * N_rand(80.0, 5.0) * 0.01;
		vitality += dv;
		happiness += dh;
		mighty += dm;
	}
	
	public static void UpdateEmotion()
	{
		vitality_d = (int) (N_rand(80.0, 5.0) + 0.5);
		happiness_d = (int) (N_rand(80.0, 5.0) + 0.5);
		confidence_d = (int) (N_rand(80.0, 5.0) + 0.5);
		mighty_d = (int) (N_rand(80.0, 5.0) + 0.5);

		vitality_u = (int) (N_rand(15.0, 9.0) + 0.5);
		happiness_u = (int) (N_rand(15.0, 9.0) + 0.5);
		confidence_u = (int) (N_rand(15.0, 9.0) + 0.5);
		mighty_u = (int) (N_rand(15.0, 9.0) + 0.5);
		
		Robot bot = main.getBot();
		String ans = locator.getLocation(vitality, happiness, mighty);
		bot.setProperty("Emotion", ans);
	}
	
	public static double sigmoid_d(double x)
	{
		double e_x = Math.exp(x-4);
		return Math.log(1+e_x)*e_x;
	}

	public static int normalization(int value) {
		if (value < 0)
			return 0;
		if (value > 10000)
			return 10000;
		return value;
	}

	public static int getVitality() {
		return vitality;
	}

	public static void setVitality(int vitality) {
		BotEmotion.vitality = normalization(vitality);
	}

	public static void changeVitality(int vitality) {
		BotEmotion.vitality += normalization(vitality) * vitality_d * 0.01;
		BotEmotion.vitality += normalization(vitality) * vitality_u
				* N_rand(0, 1) * 0.01;
		BotEmotion.vitality = normalization(BotEmotion.vitality);
	}

	public static int getHappiness() {
		return happiness;
	}

	public static void setHappiness(int happiness) {
		BotEmotion.happiness = normalization(happiness);
	}

	public static void changeHappiness(int happiness) {
		BotEmotion.happiness += normalization(happiness) * happiness_d * 0.01;
		BotEmotion.happiness += normalization(happiness) * happiness_u
				* N_rand(0, 1) * 0.01;
		BotEmotion.happiness = normalization(BotEmotion.happiness);
	}

	public static int getConfidence() {
		return confidence;
	}

	public static void setConfidence(int confidence) {
		BotEmotion.confidence = normalization(confidence);
	}

	public static void changeConfidence(int confidence) {
		BotEmotion.confidence += normalization(confidence) * confidence_d
				* 0.01;
		BotEmotion.confidence += normalization(confidence) * confidence_u
				* N_rand(0, 1) * 0.01;
		BotEmotion.confidence = normalization(BotEmotion.confidence);
	}

	public static int getMighty() {
		return mighty;
	}

	public static void setMighty(int mighty) {
		BotEmotion.mighty = normalization(mighty);
	}

	public static void changeMighty(int mighty) {
		BotEmotion.mighty += normalization(mighty) * mighty_d * 0.01;
		BotEmotion.mighty += normalization(mighty) * mighty_u * N_rand(0, 1)
				* 0.01;
		BotEmotion.mighty = normalization(BotEmotion.mighty);
	}

	static java.util.Random r = new java.util.Random();
	// 正态分布随机数产生
	public static double N_rand(double miu, double sigma2) {
		
		return r.nextGaussian() * Math.sqrt(sigma2) + miu;
	}

	// 泊松分布随机数的产生，代码如下： （未测试）
	public static double P_rand(double Lamda) { // 泊松分布
		double x = 0, b = 1, c = Math.exp(-Lamda), u;
		do {
			u = Math.random();
			b *= u;
			if (b >= c)
				x++;
		} while (b >= c);
		return x;
	}
}
