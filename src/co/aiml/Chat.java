package co.aiml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.lionsoul.jcseg.ASegment;
import org.lionsoul.jcseg.core.ADictionary;
import org.lionsoul.jcseg.core.DictionaryFactory;
import org.lionsoul.jcseg.core.IWord;
import org.lionsoul.jcseg.core.JcsegException;
import org.lionsoul.jcseg.core.JcsegTaskConfig;
import org.lionsoul.jcseg.core.SegmentFactory;

import bitoflife.chatterbean.AliceBot;

public class Chat 
{
	public static final String END = "bye";
	
	public static String input()
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("you say>");
		String input = "";
		try 
		{
			input = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}
	
	private static JcsegTaskConfig config;
	private static ADictionary dic;
	private static ASegment seg;
	
	private static void init() {
		config = new JcsegTaskConfig();
	    dic = DictionaryFactory
	    		.createDefaultDictionary(config);
	    for (String string : config.getLexiconPath()) {
	    	try {
				dic.loadFromLexiconDirectory(string);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    try {
			seg = (ASegment) SegmentFactory
					.createJcseg(JcsegTaskConfig.COMPLEX_MODE,
					new Object[]{config, dic});
		} catch (JcsegException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public static String chineseTranslate(String str) {
			try {
				seg.reset(new StringReader(str));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuffer newStr = new StringBuffer("");
			IWord word = null;
			try {
				while ( (word = seg.next()) != null ) {
					newStr.append(word.getValue()).append(" ");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			System.out.print("·Ö´Ê:"+newStr+"\n");
			return newStr.toString();
	}
	
	public static void main(String[] args) throws Exception
	{
		 init();
		 AliceBotMother mother = new AliceBotMother();		  
	     mother.setUp();
	     AliceBot bot = mother.newInstance();
	     
	     System.out.println("Alice>" + bot.respond("welcome"));
		 while(true)
		 {
			 String input = Chat.input();
			 input = chineseTranslate(input);
			 if(Chat.END.equalsIgnoreCase(input))
				 break;
			 
			 System.out.println("Alice>" + bot.respond(input));
		 }
	}
}
