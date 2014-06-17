package hha.mode;

import hha.main.MainActivity;
import hha.mode.subclass.HealthyMode;
import hha.xhb.SQLHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import bitoflife.chatterbean.Context;
import bitoflife.chatterbean.util.UserData;

public class Database {

	MainActivity main;
	Context context;
	SQLHelper helper;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public Database(MainActivity main, Context context) {
		super();
		this.main = main;
		this.context = context;
		
	}
	public static boolean equalpre(String str_s, String str_l) {
		return ((str_l.length() > str_s.length()) && (str_s.equals(str_l
				.substring(0,str_s.length()))));
	}
	public void SaveData()
	{
		Map<String,Object> map = context.getProperties();
		for (String key : map.keySet()) {
			
		    Object value = map.get(key);
		    if ((equalpre("predicate.",key)) && (value instanceof String))
		    {
		    	UpdateData(key,(String)value);
		    }

		}
	}
	
	public void getData()
	{
		Cursor c = helper.queryAll("data", null);
		int p_k = c.getColumnIndex("key");
		int p_v = c.getColumnIndex("value");
		while (c.moveToNext())
		{
			String key = c.getString(p_k);
			String value = c.getString(p_v);
			context.property(key, value);
		}
		c.close();
		
		HealthyMode hmode = (HealthyMode) Mode.getMode("healthy");
		Update(hmode.getSenior().list);
		Update(hmode.getElement().list);
		Update(hmode.getRelationship().list);
	}
	
	
	private void Update(String[] str)
	{
		for (String s : str)
		{
			Object o = getUserData(s);
			context.property("userdata."+s, o);
		}
	}
	
	public void InitDatabase() {
		helper = new SQLHelper(main);
		
	}

	public void UpdateData(String key, String value) {
		helper.AddOrUpdate("data",new String[]{"key","value"},new String[]{key,value});
	}

	public String UpdateData(String key) {
		Cursor c= helper.query("data", new String[]{"key"}, new String[]{key}, null);
		String s = null;
		while(c.moveToNext())
		{
			s = c.getString(c.getColumnIndex("key"));
			break;
		}
		c.close();
		return s;
	}

	public void AddUserData(String key, UserData data) {
		String time = sdf.format(data.date);
		helper.add("dbt_" + key, new String[] { "time", "value" },
				new Object[] { time, data.str });
	}

	public List<UserData> getUserData(String key) {
		Cursor c = helper.queryAll("dbt_" + key, null);
		int p_t = c.getColumnIndex("time");
		int p_v = c.getColumnIndex("value");
		List<UserData> list = new ArrayList<UserData>();
		while (c.moveToNext()) {
			try {
				Date date = sdf.parse(c.getString(p_t));
				String data = c.getString(p_v);
				UserData userdata = new UserData(data, date);
				list.add(userdata);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		c.close();
		return list;
	}
}
