package hha.mode.subclass;

import hha.main.MainActivity;
import hha.mode.Mode;
import hha.util.DataFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lilele.automatic.AuTomatic;

public class HealthyMode extends Mode {

	Random rand = new Random();
	JSONObject root;
	String[] userlist;
	String[] relationshiplist;
	String[] elementlist;
	String[] seniorlist;
	String[] historylist;
	
	public HealthyMode(MainActivity mainActivity, AuTomatic auto) {
		super(mainActivity, auto);
		// TODO Auto-generated constructor stub

		i_waitTime = (int) N_rand(30, 5, 0, 60);

		String jsondata = DataFileReader.ReadFile("healthy.json");
		if (jsondata == null) {
			jsondata = DataFileReader.ReadAsset("healthy.json");
			DataFileReader.WriteFile("healthy.json", jsondata);
		}

		try {
			root = new JSONObject(jsondata);
			userlist = JSONArrayToStringArray(root.getJSONArray("user"));
			relationshiplist = JSONArrayToStringArray(root.getJSONArray("relationship"));
			elementlist = JSONArrayToStringArray(root.getJSONArray("element"));
			seniorlist = JSONArrayToStringArray(root.getJSONArray("senior"));
			historylist = JSONArrayToStringArray(root.getJSONArray("history"));		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private String[] JSONArrayToStringArray(JSONArray array) throws JSONException
	{
		String[] strings = new String[array.length()];
		for (int i=0;i<array.length();i++)
			strings[i] = array.getString(i);
		return strings;
	}

	private List<String> element_ask = new ArrayList<String>();

	private void FindPropertyAndAsk(String str)
	{
		String prop = bot.getProperty(str);
		if (prop==null)
		{
			String ans = bot.Respond("ask_"+str);
			mainActivity.Show(null, ans);			
		}
	}
	
	public void CheckItem()
	{
		for (String item:userlist)
		{
			FindPropertyAndAsk(item);
		}
		
	}
	
	@Override
	public void Run(int count) {
		// TODO Auto-generated method stub
		if (i_waitTime == count) {
			
			
			
			i_waitTime = (int) N_rand(30, 5, 0, 60);
		}
	}

	@Override
	protected String getModeName() {
		// TODO Auto-generated method stub
		return "healthy";
	}
}
