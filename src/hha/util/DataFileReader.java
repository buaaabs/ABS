package hha.util;

import hha.main.MainActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;

import android.app.Activity;
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
		// File file = new File(path);
		// MainActivity.main.ShowTextOnUIThread(file.getAbsolutePath());
		// File f = Environment.getDataDirectory();
		//
		//
		// MainActivity.main.ShowTextOnUIThread(f.getAbsolutePath());
		CharBuffer cb = null;
		FileInputStream fis;
		try {
			fis = MainActivity.main.openFileInput(path);
			cb = CharBuffer.allocate(fis.available());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		String filecontent = null;
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(fis, "utf-8");
			try {
				if (cb != null) {
					isr.read(cb);
				}
				filecontent = new String(cb.array());
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filecontent;
	}

	public static InputStream ReadFileAsStream(String path) {
		FileInputStream fis = null;

		try {
			fis = MainActivity.main.openFileInput(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fis;
	}

	public static void WriteFile(String path, String str) {
		try {
			FileOutputStream out = MainActivity.main.openFileOutput(path,
					Activity.MODE_PRIVATE);
			OutputStreamWriter osw;
			osw = new OutputStreamWriter(out, "utf-8");
			try {
				osw.write(str);
				osw.flush();
				osw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		CharBuffer cb = null;
		try {
			cb = CharBuffer.allocate(in.available());
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"utf-8"));
			br.read(cb);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cb.toString();
	}
}
