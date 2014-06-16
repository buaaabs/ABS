package hha.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import android.content.res.AssetManager;

public class DataFileReader {

	public static AssetManager am;

	public static String ReadAsset(String path) {
		try {
			return InputStreamToString(am
					.open(path, AssetManager.ACCESS_BUFFER));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String ReadFile(String path) {
		File file = new File(path);
		StringBuffer sb = new StringBuffer();
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);

			byte[] buf = new byte[1024];

			while ((fis.read(buf)) != -1) {
				sb.append(new String(buf));
				buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	public static InputStream ReadFileAsStream(String path) {
		File file = new File(path);
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fis;
	}

	public static void WriteFile(String path, String str) {
		try {
			FileOutputStream out = new FileOutputStream(path);
			PrintStream p = new PrintStream(out);
			p.print(str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static InputStream StringToInputStream(String in) {
		ByteArrayInputStream is = null;
		try {
			is = new ByteArrayInputStream(in.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}

	public static String InputStreamToString(InputStream in) {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		try {
			for (int n; (n = in.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return out.toString();
	}
}
