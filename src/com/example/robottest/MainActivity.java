package com.example.robottest;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.speech.ErrorCode;
import com.iflytek.speech.ISpeechModule;
import com.iflytek.speech.InitListener;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechUnderstander;
import com.iflytek.speech.SpeechUnderstanderListener;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.UnderstanderResult;


public class MainActivity extends Activity {

	private Toast mToast;	
	private TextView text = null;
	private Button button = null;
	private SpeechUnderstander su = null;
	
	private InitListener il = new InitListener(){

		@Override
		public void onInit(ISpeechModule arg0, int code) {
			// TODO Auto-generated method stub
			if (code == ErrorCode.SUCCESS) {
				button.setEnabled(true);
        	}
		}
		
	};
	private void showTip(final String str) {
	// TODO Auto-generated method stub
	runOnUiThread(new Runnable() {		
		@Override
		public void run() {
			mToast.setText(str);
			mToast.show();
		}
		});		
	}
	private SpeechUnderstanderListener sul = new SpeechUnderstanderListener.Stub() {
        
        @Override
        public void onVolumeChanged(int v) throws RemoteException {
            showTip("onVolumeChanged："	+ v);
        }
        
		@Override
        public void onError(int errorCode) throws RemoteException {
			showTip("onError Code："	+ errorCode);
        }
        
        @Override
        public void onEndOfSpeech() throws RemoteException {
			showTip("onEndOfSpeech");
        }
        
        @Override
        public void onBeginOfSpeech() throws RemoteException {
			showTip("onBeginOfSpeech");
        }

		@Override
		public void onResult(final UnderstanderResult result) throws RemoteException {
	       	runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (null != result) {
			            	// 显示
							text.setText(result.getResultString());
			            } else {
			                text.setText("识别结果不正确。");
			            }	
					}
				});
		}
    };
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (TextView)findViewById(R.id.main_text);
		button = (Button)findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setParam();		
				int code = su.startUnderstanding(sul);
				if(code!=0){
					text.setText("Error Code: "+code);
				}
			}
			
		});
		if(SpeechUtility.getUtility(this).queryAvailableEngines()!=null 
				&& SpeechUtility.getUtility(this).queryAvailableEngines().length>0)
		{
			text.setText("可以使用讯飞服务");
		}
		
		SpeechUtility.getUtility(MainActivity.this).setAppid("53227870");
		
//	    sr = new SpeechRecognizer(this,il);
//		sr.setParameter(SpeechConstant.PARAMS, "asr_ptt=1");
		
		su = new SpeechUnderstander(this, il);
		this.mToast = Toast.makeText(this, "", Toast.LENGTH_LONG);

		
		
	}

	private void setParam() {
		// TODO Auto-generated method stub
		su.setParameter(SpeechConstant.ENGINE_TYPE,"cloud" );
		su.setParameter(SpeechConstant.LANGUAGE, "zh-cn");
		su.setParameter(SpeechConstant.VAD_BOS,"4000");
		su.setParameter(SpeechConstant.VAD_EOS, "1000");

		String param = "asr_ptt=0";
		su.setParameter(SpeechConstant.PARAMS, param);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
