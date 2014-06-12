package hha.main;

import hha.aiml.BotEmotion;
import hha.aiml.Jcseg;
import hha.aiml.Robot;
import hha.robot.R;
import hha.util.Caller;
import hha.util.ChatMsgEntity;
import hha.util.ChatMsgViewAdapter;
import hha.util.music.Player;
import hha.xf.Data;
import hha.xf.NetRobot;
import hha.xf.Reader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lilele.automatic.AuTomatic;

public class MainActivity extends Activity implements Runnable {

	private AuTomatic mAuTomatic;
	private Toast mToast;
	private TextView text = null;
	Caller call = null;

	public Robot getBot() {
		return bot;
	}

	public NetRobot getNetbot() {
		return netbot;
	}

	public Reader getReader() {
		return reader;
	}

	public Player getPlayer() {
		return player;
	}

	public Button mainButton = null;
	public Button button = null;

	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	public void Welcome() {
		String ansString = "欢迎您，我是智能助手小X";
		text.setText(ansString + "\n");
		reader.start(ansString);
	}

	public void showTip(final String str) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mToast.setText(str);
				mToast.show();
			}
		});
	}

	private void playmusic(Data data) {
		String music_url = null;
		String music_title = data.name != null ? data.name : "";
		String music_singer = data.singer != null ? data.singer : "";
		try {
			music_url = hha.util.music.GetMusicUrl.getMusic(music_title,
					music_singer);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.playUrl(music_url);
	}

	public void ShowText(String t) {
		text.setText(text.getText() + t + "\n");
	}

	public void ShowTextOnUIThread(final String t) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				text.setText(text.getText() + t + "\n");
			}
		});
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();
		String date = String.valueOf(c.get(Calendar.YEAR)) + "-"
				+ String.valueOf(c.get(Calendar.MONTH)) + "-"
				+ c.get(c.get(Calendar.DAY_OF_MONTH));
		return date;
	}

	private ListView talkView;

	private String BotName = "小X";
	private String UserName = "用户";

	public void AddNewTalk(String user, String ans) {
		int MeId = R.layout.list_say_he_item;
		int HeId = R.layout.list_say_me_item;
		String date = getDate();
		if (UserName != null || UserName.equals("")) {
			ChatMsgEntity newMessage = new ChatMsgEntity(UserName, date, user,
					MeId);
			list.add(newMessage);
//			talkView.setAdapter(new hha.util.ChatMsgViewAdapter(this, list));
		}
		ChatMsgEntity newMessage2 = new ChatMsgEntity(BotName, date, ans, HeId);
		list.add(newMessage2);

		ChatMsgViewAdapter adapter = (ChatMsgViewAdapter) talkView.getAdapter();
//		if (adapter == null)
			talkView.setAdapter(new hha.util.ChatMsgViewAdapter(this, list));
//		else
//		{
//			adapter.setColl(list);
//			adapter.notifyDataSetChanged();
//		}
		talkView.setSelection(list.size()-1);
	}

	public void Show(final String userString, final String ansString) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AddNewTalk(userString,ansString);
				if (userString == null) {
					ShowText(ansString);
				} else {
					ShowText("\n用户:" + userString);
					ShowText(ansString);
				}
				audiom.setStreamVolume(AudioManager.STREAM_MUSIC, oldAudio,
						AudioManager.FLAG_PLAY_SOUND);
				reader.start(ansString);
			}
		});
	}

	public void GetReturnData(Data data) {
		try {
			if ((data.focus != null) && ("music".equals(data.focus))) {
				playmusic(data);
				String ansString = bot.Respond("OK");
				Show(data.rawtext, ansString);
				return;
			}
			if ((data.focus != null) && (data.operation != null)
					&& (data.name != null) && ("telephone".equals(data.focus))
					&& ("call".equals(data.operation))) {
				String ansString = bot.Respond("OK");
				Show(data.rawtext, ansString);
				if (call != null)
					call.callName(data.name);
				return;
			}
			if ((data.focus != null) && (data.operation != null)
					&& ("message".equals(data.focus))
					&& ("send".equals(data.operation))) {
				String ansString = bot.Respond("OK");
				Show(data.rawtext, ansString);
				return;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ShowTextOnUIThread("Error: " + e.getMessage());
		}

		String input = data.rawtext;
		input = Jcseg.chineseTranslate(input);

		String ansString = null;
		ansString = bot.Respond(input);
		ShowTextOnUIThread("分词: " + input + "bot answer: " + ansString);
		if ((!bot.CanFindAnswer()) && (data.content != null))
			ansString = data.content;

		Show(data.rawtext, ansString);

		String com = null;
		if ((com = bot.getCommand()) != null) {
			if ("Stop".equals(com)) {
				player.stop();
			}
		}

		mAuTomatic.setCountTime(0);
		mAuTomatic.setS_emotionStatus("高兴");
		mAuTomatic.setB_exit(false);
	}

	Robot bot; // 本地机器人
	NetRobot netbot; // 讯飞网络机器人
	Reader reader; // 讯飞语音合成器

	public String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

	private ArrayList<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();

	private ByteArrayOutputStream gossip;
	AssetManager am;
	Player player;
	AudioManager audiom;
	int oldAudio;

	PackageManager pm;
	ResolveInfo homeInfo;

	public void Exit() {
		ActivityInfo ai = homeInfo.activityInfo;
		Intent startIntent = new Intent(Intent.ACTION_MAIN);
		startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
		startActivitySafely(startIntent);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Exit();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	void startActivitySafely(Intent intent) {
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, R.string.unabletoopensoftware,
					Toast.LENGTH_SHORT).show();
		} catch (SecurityException e) {
			Toast.makeText(this, R.string.unabletoopensoftware,
					Toast.LENGTH_SHORT).show();
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BotEmotion.main = this;
		
		

		pm = getPackageManager();
		homeInfo = pm.resolveActivity(new Intent(Intent.ACTION_MAIN)
				.addCategory(Intent.CATEGORY_HOME), 0);

		setContentView(R.layout.maininterface);
		talkView = (ListView) findViewById(R.id.talkList);
		
		mainButton = (Button) findViewById(R.id.button_main);
		mainButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, MenuList.class);
				showTip("OK!!!!");
				MainActivity.this.startActivity(intent);
			}
		});

		audiom = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		oldAudio = audiom.getStreamVolume(AudioManager.STREAM_MUSIC);
		text = (TextView) findViewById(R.id.main_text);
		button = (Button) findViewById(R.id.button_speak);
		button.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN)// 判断按钮释放被释�?
				{
					oldAudio = audiom
							.getStreamVolume(AudioManager.STREAM_MUSIC);
					audiom.setStreamVolume(AudioManager.STREAM_MUSIC, 1,
							AudioManager.FLAG_PLAY_SOUND);

					int code = netbot.BeginSpeechUnderstand();
					if (code != 0) {
						text.setText("Error Code: " + code);
					}
				}
				if (event.getAction() == MotionEvent.ACTION_UP)// 判断按钮释放被释�?
				{
					int code = netbot.EndSpeechUnderstand();
					if (code != 0) {
						text.setText("Error Code: " + code);
					}
				}
				return false;
			}
		});

		this.mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);
		text.setText("欢迎\n");

		try {
			// 初始化本地机器人
			bot = new Robot(getAssets(), this);
			bot.BeginInit();

			new Thread(this).start();

			// text.setText(text.getText() + "正在启动语音合成引擎\n");
			// 初始化语音合成引擎
			Context context = getApplicationContext();
			reader = new Reader(context);

			// text.setText(text.getText() + "正在启动语音识别引擎\n");
			// 初始化语音识别引擎
			netbot = new NetRobot(this);
			netbot.InitXF();

			// text.setText(text.getText() + "正在初始化音乐播放器\n");
			// 初始化音乐Player
			player = new Player(new SeekBar(context));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			text.setText(e.toString());
		}
		// text.setText(bot.respond("welcome"));
		// 自主对话初始化
		mAuTomatic = new AuTomatic(this, getBot());
		mAuTomatic.setS_emotionStatus("高兴");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void run() {
		call = new Caller(MainActivity.this);
	}

}
