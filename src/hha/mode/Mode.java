package hha.mode;

import hha.aiml.Robot;
import hha.main.MainActivity;
import hha.util.PackageUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.lilele.automatic.AuTomatic;

public abstract class Mode {

	protected static List<Mode> SubClassList;

	public static void ModeInit(MainActivity main, AuTomatic auto) {
		SubClassList = new ArrayList<Mode>();

		List<String> ClassNameList = main.ListPackage("hha.mode.subclass");
		
		
		for (String className : ClassNameList) {
			try {
//				main.Show(null, className);
				Class tagClass = Class.forName(className);
				Constructor constructor = tagClass.getConstructor(
						MainActivity.class, AuTomatic.class);
				Object tag = constructor.newInstance(main, auto);
				SubClassList.add((Mode) tag);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	protected abstract String getModeName();

	protected int i_waitTime;// 等待时间，由正态分布产生
	

	protected MainActivity mainActivity = null;
	protected Robot bot = null;
	protected AuTomatic auto = null;

	public static void Switch(String str, int count) {
		for (Mode item : SubClassList) {
			if (item.isMode(str)) {
				item.Run(count);
				return;
			}
		}
		for (Mode item : SubClassList) {
			if (item.isMode("normal")) {
				item.Run(count);
				return;
			}
		}
	}

	public boolean isMode(String str) {
		return str.equals(getModeName());
	}

	public Mode(MainActivity mainActivity, AuTomatic auto) {
		super();
		this.mainActivity = mainActivity;
		this.auto = auto;
		bot = mainActivity.getBot();
	}

	public abstract void Run(int count);

	static java.util.Random r = new java.util.Random();

	// 正态分布随机数产生
	public static double N_rand(double miu, double sigma2) {

		return r.nextGaussian() * Math.sqrt(sigma2) + miu;
	}

	public static double N_rand(double miu, double sigma2, double min,
			double max) {
		double ans = r.nextGaussian() * Math.sqrt(sigma2) + miu;
		if (ans < min || ans > max)
			return N_rand(miu, sigma2, min, max);
		return ans;
	}
}
