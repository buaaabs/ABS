package bitoflife.chatterbean;

public class emotionLocator {
	
	
	
	private double MaxVRange=1000.0;
	private double MinVRange=0;
	private double tempVRange=0;
	
	private double MaxCRange=3000.0;
	private double MinCRange=500.0;
	private double tempCRange=0;
	
	private double MAX=10000.0;
	public emotionLocator()
	{
		
		
	}
	
	public String getLocation(double val,double happy,double anger)
	{
		tempCRange=(MinCRange*(MAX-val)+MaxCRange*val)/MAX;
		tempVRange=(MinVRange*(MAX-val)+MaxVRange*val)/MAX;
		double Score=happy*happy+anger*anger;
		if(Score<tempVRange*tempVRange)
		{
			return "困倦";
		}
		if(Score<tempCRange*tempCRange)
		{
			return "普通";
		}
		
		if(Math.abs(happy)>=Math.abs(anger))
		{
			if(happy<=0)
				return "忧伤";
			else
				return "快乐";
		}
		else
		{
			if(anger<=0)
				return "恐惧";
			else
				return "愤怒";
		}
	}
}
