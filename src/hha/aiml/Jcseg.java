package hha.aiml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lionsoul.jcseg.ASegment;
import org.lionsoul.jcseg.core.ADictionary;
import org.lionsoul.jcseg.core.DictionaryFactory;
import org.lionsoul.jcseg.core.IWord;
import org.lionsoul.jcseg.core.JcsegException;
import org.lionsoul.jcseg.core.JcsegTaskConfig;
import org.lionsoul.jcseg.core.SegmentFactory;

public class Jcseg {

	private static JcsegTaskConfig config;
	private static ADictionary dic;
	private static ASegment seg;
	private static Pattern findChinese;
	public static boolean isInitDone = false;
	public static void init(InputStream prop) {
		config = new JcsegTaskConfig(prop);
		dic = DictionaryFactory.createDefaultDictionary(config);
	}

	public static void initDic(InputStream input) {
		try {
			dic.loadFromLexiconDirectory(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void initSeg() {
		try {
//			findChinese = Pattern.compile("^[\u4E00-\u9FA5].*");
			findChinese = Pattern.compile("^[a-zA-Z0-9#\\$].*");
			seg = (ASegment) SegmentFactory.createJcseg(
					JcsegTaskConfig.COMPLEX_MODE, new Object[] { config, dic });
			isInitDone = true;
		} catch (JcsegException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String chineseTranslate(String str) {
		if ((!isInitDone) || (str ==null))
			return str;
		str = str.toUpperCase();
		Matcher m = findChinese.matcher(str);
		if (m.matches())
		{
			return str;
		}
		try {
			seg.reset(new StringReader(str));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer newStr = new StringBuffer("");
		IWord word = null;
		try {
			while ((word = seg.next()) != null) {
				newStr.append(word.getValue()).append(" ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.print("分词:" + newStr + "\n");
		return newStr.toString();
	}

}
