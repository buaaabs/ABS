package hha.mode.subclass;

import hha.aiml.Jcseg;
import hha.main.MainActivity;
import hha.mode.Mode;
import hha.util.DataFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.lilele.automatic.AuTomatic;

public class TestMode extends Mode {
	List<String> testData = new ArrayList<String>();
	public TestMode(MainActivity mainActivity, AuTomatic auto) {
		super(mainActivity, auto);
		// TODO Auto-generated constructor stub
		try {
			InputStream is = DataFileReader.am.open("test.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str = null;
			while ((str = br.readLine())!=null)
			{
				testData.add(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected String getModeName() {
		// TODO Auto-generated method stub
		return "test";
	}

	@Override
	public void Run(int UserCount,int RobotCount) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		String input = null;
		for(String s : testData)
		{
			input = Jcseg.chineseTranslate(s);
			sb.append("User: "+input + "\n" + bot.Respond(input)+ "\n");
		}
		mainActivity.ShowTextOnUIThread(sb.toString());
		bot.setProperty("mode", "normal");
	}

}
