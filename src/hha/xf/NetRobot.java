package hha.xf;

import hha.main.MainActivity;
import android.content.Context;
import android.os.RemoteException;

import com.iflytek.speech.ErrorCode;
import com.iflytek.speech.ISpeechModule;
import com.iflytek.speech.InitListener;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechUnderstander;
import com.iflytek.speech.SpeechUnderstanderListener;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.UnderstanderResult;

public class NetRobot {
	
	Context mainContext;
	MainActivity mainActivity;
	SpeechUnderstander su = null;
	
	public NetRobot(MainActivity main) {
		// TODO Auto-generated constructor stub
		mainActivity = main;
		mainContext = main.getApplicationContext();
	}
	
	public int BeginSpeechUnderstand()
	{
		setParam();
		return su.startUnderstanding(sul);
	}
	
	public int EndSpeechUnderstand() {
		return su.stopUnderstanding(sul);
	}
	
	private InitListener il = new InitListener() {
		@Override
		public void onInit(ISpeechModule arg0, int code) {
			// TODO Auto-generated method stub
			if (code == ErrorCode.SUCCESS) {
				mainActivity.button.setEnabled(true);
				mainActivity.Welcome();	
			}
		}
	};

	private SpeechUnderstanderListener sul = new SpeechUnderstanderListener.Stub() {

		@Override
		public void onVolumeChanged(int v) throws RemoteException {
			mainActivity.showTip("onVolumeChanged:" + v);
		}

		@Override
		public void onError(int errorCode) throws RemoteException {
			mainActivity.showTip("onError Code:" + errorCode);
		}

		@Override
		public void onEndOfSpeech() throws RemoteException {
			mainActivity.showTip("onEndOfSpeech");
		}

		@Override
		public void onBeginOfSpeech() throws RemoteException {
			mainActivity.showTip("onBeginOfSpeech");
		}

		@Override
		public void onResult(final UnderstanderResult result)
				throws RemoteException {
			mainActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (null != result) {
						SaxParseService saxParseService = new SaxParseService();
						Data data = null;
						try {
							String string = result.getResultString();
							data = saxParseService.getData(string);
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mainActivity.GetReturnData(data);
					}
				}
			});
		}
	};

	public void InitXF() {
		if (SpeechUtility.getUtility(mainContext).queryAvailableEngines() != null
				&& SpeechUtility.getUtility(mainContext).queryAvailableEngines().length > 0) {
			mainActivity.showTip("讯飞语音可以使用");
		}
		else {
			return;
		}

		SpeechUtility.getUtility(mainContext).setAppid("53227870");

		su = new SpeechUnderstander(mainContext, il);
//		mainActivity.mToast = Toast.makeText(c, "", Toast.LENGTH_LONG);		
	}

	private void setParam() {
		// TODO Auto-generated method stub
		su.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
		su.setParameter(SpeechConstant.LANGUAGE, "zh-cn");
		su.setParameter(SpeechConstant.VAD_BOS, "4000");
		su.setParameter(SpeechConstant.VAD_EOS, "1000");

		String param = "asr_ptt=0";
		su.setParameter(SpeechConstant.PARAMS, param);
	}

}
