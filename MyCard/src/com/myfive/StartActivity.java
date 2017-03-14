package com.myfive;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.myCard.MyActivity;

public class StartActivity extends Activity {
	private CheckBox mCheckBox_music;// �������ְ�ť��

	private MediaPlayer mp; // MediaPlayer����
	private AudioManager am;// AudioManager����
	
	
	

	private int max;// �������
	private int current;// ��ǰ����
	private int stepVolume;// һ�����ӵ�����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ���ر���
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ����ȫ��
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		// ���ú�����
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// ��������������
		InnerOnClickListener listener = new InnerOnClickListener();
		// ���ü�����
		findViewById(R.id.start).setOnClickListener(listener);

		// ��ȡ���ְ�ť���أ�
		mCheckBox_music = (CheckBox) findViewById(R.id.music);
		// ��music��ť���м�����

		mp = new MediaPlayer();
		try {
			// mp.setDataSource("/sdcard/music/mg_bg_new.mp3");// ����·��
			mp = MediaPlayer.create(this, R.raw.mg_bg);
			mp.prepare();// ����
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ��������
		am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		stepVolume = max / 8;
		mCheckBox_music.setOnCheckedChangeListener(new MusicListener());

	}

	private class InnerOnClickListener implements View.OnClickListener {

		// MyActivity main;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// ������intent���󣬲����ü���Ե����
			Intent intent = new Intent(StartActivity.this, MyActivity.class);
			// ����Activity
			startActivity(intent);
		}
	}

	class MusicListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				mp.start();// ��ʼ����
				// �����ֽ��м�����
				mp.setOnCompletionListener(new OnCompletionListener() {
					// ѭ�����֣�
					@Override
					public void onCompletion(MediaPlayer mp) {

						try {
							mp.start();

						} catch (IllegalStateException e) {
							e.printStackTrace();
						}
					}
				});
				Toast.makeText(StartActivity.this, "��������", Toast.LENGTH_SHORT)
						.show();
			} else {
				mp.stop();// �رձ������֣�
				Toast.makeText(StartActivity.this, "�ر�����", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	protected void onPause() {
		/**
		 * ������ϷһֱΪ����
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onPause();
	}

}
