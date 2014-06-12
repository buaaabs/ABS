package hha.aiml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public class NetAiml {

	String ip;
	int port = 10010;
	Socket socket;

	PrintStream ps;
	BufferedReader br;
	InputStream input;
	OutputStream output;
	Set learned = new HashSet();
	

	public NetAiml(String _ip) {
		ip = _ip;
		learned.add(0);
	}

	public NetAiml(String _ip, int _port) {
		ip = _ip;
		port = _port;
		learned.add(0);
	}
	public void Close()
	{
		try {
			ps.println("END");
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean Connect() {
		try {
			socket = new Socket(ip, port);
			input = socket.getInputStream();
			output = socket.getOutputStream();
			br = new BufferedReader(new InputStreamReader(input,"gbk"));
			ps = new PrintStream(output);
			ps.println("BEGIN");
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public void SendCommand(String str) {
		ps.println(str);
	}

	public void SendArg(String arg) {
		ps.println("BEGIN{");
		ps.println(arg);
		ps.println("}END");
	}

	
	
	public String GetNetAiml(String match) {

		StringBuilder sb = new StringBuilder();
		String is = null;
		String k;
		try {
			SendCommand("FindByString");
			SendArg(match);
			
			
			while (!(k = br.readLine()).equals("END")) {
				sb.append(k);
			}
			
			int i = Integer.parseInt(sb.toString());
			if (!learned.contains(i))
			{
				SendCommand("Find");
				SendArg(String.valueOf(i));
				
				sb.deleteCharAt(0);
				while (!(k = br.readLine()).equals("END")) {
					sb.append(k);
				}
				
				is = sb.toString();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}

}
