package com.magcomm.demos;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.iflytek.speech.ErrorCode;
import com.iflytek.speech.ISpeechModule;
import com.iflytek.speech.InitListener;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.SynthesizerListener;

/**
 * Created by lenovo on 15-10-10.
 */
public class TestXunfei extends Activity {
    private static final String TAG = "TestXunfei";
    private SpeechSynthesizer mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        SpeechUtility.getUtility(this).setAppid("5348db7b");
        // 初始化合成对象
        mTts = new SpeechSynthesizer(this, mTtsInitListener);
    }

    public void doRead(View v) {
        Log.i(TAG, "doRead is called ....");
        String demoStr = "你好你好你好你好，hello world, helloworld";
        int code = mTts.startSpeaking(demoStr, mTtsListener);
        if (code != 0) {
            showTip("start speak error : " + code);
        } else {
            showTip("start speak success.");
        }
    }

    /**
     * 初期化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {

        @Override
        public void onInit(ISpeechModule arg0, int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code == ErrorCode.SUCCESS) {

            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener.Stub() {
        @Override
        public void onBufferProgress(int progress) throws RemoteException {
            Log.d(TAG, "onBufferProgress :" + progress);
            showTip("onBufferProgress :" + progress);
        }

        @Override
        public void onCompleted(int code) throws RemoteException {
            Log.d(TAG, "onCompleted code =" + code);
            showTip("onCompleted code =" + code);
        }

        @Override
        public void onSpeakBegin() throws RemoteException {
            Log.d(TAG, "onSpeakBegin");
            showTip("onSpeakBegin");
        }

        @Override
        public void onSpeakPaused() throws RemoteException {
            Log.d(TAG, "onSpeakPaused.");
            showTip("onSpeakPaused.");
        }

        @Override
        public void onSpeakProgress(int progress) throws RemoteException {
            Log.d(TAG, "onSpeakProgress :" + progress);
            showTip("onSpeakProgress :" + progress);
        }

        @Override
        public void onSpeakResumed() throws RemoteException {
            Log.d(TAG, "onSpeakResumed.");
            showTip("onSpeakResumed");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTts.stopSpeaking(mTtsListener);
        // 退出时释放连接
        mTts.destory();
    }

    private void showTip(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, str);
            }
        });
    }

    /**
     * 参数设置
     * param
     *
     * @param
     * @return
     */
    private void setParam() {
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, "local");
        mTts.setParameter(SpeechSynthesizer.VOICE_NAME, "xiaoyan");
        mTts.setParameter(SpeechSynthesizer.SPEED, "50");
        mTts.setParameter(SpeechSynthesizer.PITCH, "50");
        mTts.setParameter(SpeechSynthesizer.VOLUME, "50");
    }
}
