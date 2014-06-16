package hha.util;

import java.util.Date;

public class DateCal {
	
	public static double getDay(Date d1 , Date d2){
		return (d1.getTime()-d2.getTime())*1.0/(3600*24*1000.0);
	}
}
