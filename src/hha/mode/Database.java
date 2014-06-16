package hha.mode;

import hha.aiml.Robot;
import hha.main.MainActivity;
import hha.mode.subclass.HealthyMode;
import hha.xhb.SQLHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		helper = new SQLHelper(main);
	}

	private void Creating(PArray atts, String pclass) {
		for (int i = 0; i < atts.list.length; i++) {

			helper.createTable(atts.list[i], new String[] { "time", "value" },
					new String[] { "varchar(30)", "double" });
			helper.add("Main", new String[] { "name", "unit", "fre", "class" },
					new Object[] { "dbt_" + atts.list[i], atts.unit[i],
							atts.fre, pclass });
		}
	}

	public void InitDatabase() {
		HealthyMode hmode = (HealthyMode) Mode.getMode("healthy");
		helper.createTable("data", new String[] { "key", "value" },
				new String[] { "varchar(50)", "varchar(200)" });

		helper.createTable("relationship", new String[] { "name1", "name2",
				"relation" }, new String[] { "varchar(20)", "varchar(20)",
				"varchar(20)" });

		Creating(hmode.getElement(), "element");
		Creating(hmode.getSenior(), "senior");
	}

	public void UpdateData(String key, String value) {
		
	}

	public String UpdateData(String key) {
		return null;
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
		return list;
	}
}
