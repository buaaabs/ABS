package hha.aiml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

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
//		if ((str.length()!=0) && (str.charAt(0)<128))
//			return str;
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
