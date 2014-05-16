package hha.aiml;

import hha.main.MainActivity;

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
	MainActivity main = null;
	private ByteArrayOutputStream gossip;

	public Robot(AssetManager am,MainActivity main) {
		// TODO Auto-generated constructor stub
		this.am = am;
		this.main = main;
	}

	public void InitRobot() {
		// /初始化分词系统
		try {
			BotEmotion.init();
			
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

			// 初始化机器人
			gossip = new ByteArrayOutputStream();

			AliceBotParser parser = new AliceBotParser();

			bot = parser.parse(
					am.open("context.xml", AssetManager.ACCESS_BUFFER),
					am.open("splitters.xml", AssetManager.ACCESS_BUFFER),
					am.open("substitutions.xml", AssetManager.ACCESS_BUFFER),
					am.open("idiom.aiml", AssetManager.ACCESS_BUFFER));

			bitoflife.chatterbean.Context bot_context = bot.getContext();
			bot_context.outputStream(gossip);
			main.showTip("Bot Bootup");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			main.showTip("Bot IOException");
			e.printStackTrace();
		} catch (AliceBotParserConfigurationException e) {
			// TODO Auto-generated catch block
			main.showTip("Bot AliceBotParserConfigurationException");
			e.printStackTrace();
		} catch (AliceBotParserException e) {
			main.Show("Error", e.getMessage());
			// TODO Auto-generated catch block
			main.showTip("Bot AliceBotParserException");
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
		
		if (!isInitDone())
		{
			return "";
		}
		
		String output = "";
		String ansString = bot.respond(str);
		bitoflife.chatterbean.Context context = bot.getContext();

		output = (String) context.property("predicate.CanNotFind");
		context.property("predicate.CanNotFind", "null");
		command = (String) context.property("predicate.Command");
		context.property("predicate.Command", "null");

		if ("True".equals(output))
			canfind = false;
		else
			canfind = true;

		return ansString;
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
