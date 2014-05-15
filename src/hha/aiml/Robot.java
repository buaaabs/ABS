package hha.aiml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.parser.AliceBotParser;
import bitoflife.chatterbean.parser.AliceBotParserConfigurationException;
import bitoflife.chatterbean.parser.AliceBotParserException;

public class Robot implements Runnable {

	AliceBot bot = null;
	AssetManager am = null;
	boolean canfind = false;
	String command = null;
	private ByteArrayOutputStream gossip;

	public Robot(AssetManager am) {
		// TODO Auto-generated constructor stub
		this.am = am;
	}

	public void InitRobot() {
		// /初始化分词系统
		try {
			InputStream prop = am.open("jcseg.properties",
					AssetManager.ACCESS_BUFFER);

			Jcseg.init(prop);
			prop.close();

			String[] strings = null;
			strings = am.list("lexicon");

			// InputStream[] inputStreams = new InputStream[strings.length];
			for (int i = 0; i < strings.length; i++) {
				InputStream is = am.open("lexicon/" + strings[i],
						AssetManager.ACCESS_BUFFER);
				Jcseg.initDic(is);
				is.close();
			}
			Jcseg.initSeg();

			// /初始化机器人
			gossip = new ByteArrayOutputStream();

			AliceBotParser parser = new AliceBotParser();

			bot = parser.parse(
					am.open("context.xml", AssetManager.ACCESS_BUFFER),
					am.open("splitters.xml", AssetManager.ACCESS_BUFFER),
					am.open("substitutions.xml", AssetManager.ACCESS_BUFFER),
					am.open("idiom.aiml", AssetManager.ACCESS_BUFFER));

			bitoflife.chatterbean.Context bot_context = bot.getContext();
			bot_context.outputStream(gossip);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AliceBotParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AliceBotParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isInitDone() {
		return bot != null;
	}

	public void BeginInit() {
		new Thread(this).start();
	}

	public String Respond(String str) {
		String output = "";

		bitoflife.chatterbean.Context context = (bot != null ? bot.getContext()
				: null);

		if (context != null) {

			output = (String) context.property("predicate.CanNotFind");
			context.property("predicate.CanNotFind", "null");
			command = (String) context.property("predicate.Command");
			context.property("predicate.Command", "null");
		}

		if ("True".equals(output))
			canfind = false;
		else
			canfind = true;

		return bot.respond(str);
	}

	public boolean CanFindAnswer() {
		return canfind;
	}

	public String getCommand() {
		return command;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		InitRobot();
	}
}
