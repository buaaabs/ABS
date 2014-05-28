package hha.aiml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetAiml {

	String ip;
	int port = 10010;
	Socket socket;

	PrintStream ps;
	BufferedReader br;

	public NetAiml(String _ip) {
		ip = _ip;
	}

	public NetAiml(String _ip, int _port) {
		ip = _ip;
		port = _port;
	}

	public void Connect() {
		try {
			socket = new Socket(ip, port);
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			br = new BufferedReader(new InputStreamReader(input));
			ps = new PrintStream(output);
			ps.println("BEGIN");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void Close() {
		ps.println("END");

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

		char[] buf = new char[1024 * 8];
		StringBuilder sb = new StringBuilder();
		int k;
		try {
			Connect();
			SendCommand("FindByString");
			SendArg(match);
			Close();
			
			while ((k = br.read(buf)) != -1) {
				sb.append(buf,0,k);
			}
			
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

}
