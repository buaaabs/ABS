package hha.util.music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GetMusicUrl {
	
	public static String HttpGet(String str) {

        StringBuffer sb=new StringBuffer();  
		try {  

            InputStream is=new URL(str).openStream();  

            BufferedReader br=new BufferedReader(new InputStreamReader(is));  

            String str1=null;  
            
            while((str1=br.readLine())!=null){  
                sb.append(str1);  
            }  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return sb.toString();  
	}
	
	public static String executeHttpGet(String str) {
		
		        String result = null;
		
		        URL url = null;
		
		        HttpURLConnection connection = null;
		
		        InputStreamReader in = null;
		
		        try {
		
		            url = new URL(str);
		
		            connection = (HttpURLConnection) url.openConnection();
		
		            in = new InputStreamReader(connection.getInputStream());
		
		            BufferedReader bufferedReader = new BufferedReader(in);
		
		            StringBuffer strBuffer = new StringBuffer();
		
		            String line = null;
		
		            while ((line = bufferedReader.readLine()) != null) {
		
		                strBuffer.append(line);
		
		            }
		
		            result = strBuffer.toString();
		
		        } catch (Exception e) {
		
		            e.printStackTrace();
		
		        } finally {
		
		            if (connection != null) {
		
		                connection.disconnect();
		
		            }
		
		            if (in != null) {
		
		                try {
		
		                    in.close();
		
		                } catch (IOException e) {
		
		                    e.printStackTrace();
		
		                }
		
		            }
		
		 
		
		        }
		
		        return result;
		
		    }

	static ArrayList<MusicUrlData> data = null;
	public static String getMusic(String name,String singer) throws UnsupportedEncodingException {
		String url = null;
		String urlName = URLEncoder.encode(name, "utf-8");
		String urlSinger = URLEncoder.encode(singer, "utf-8");
		url = "http://box.zhangmen.baidu.com/x?op=12&count=1&title="
				+urlName+"$$"+urlSinger+"$$$$";
		//url = "http://www.baidu.com/";
		String exe_string = HttpGet(url);		
	//	return exe_string;
		try {
			 data = SaxParseService.getData(exe_string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (data != null)
		{
		String music_url = new String(data.get(0).encodeString+data.get(0).decodeString);
		return music_url;
		}else return "";
	}
	
	
}
