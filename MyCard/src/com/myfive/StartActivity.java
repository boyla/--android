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
	private CheckBox mCheckBox_music;// 创建音乐按钮；

	private MediaPlayer mp; // MediaPlayer引用
	private AudioManager am;// AudioManager引用
	
	
	

	private int max;// 最大音量
	private int current;// 当前音量
	private int stepVolume;// 一次增加的音量

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		// 设置横屏；
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// 创建监听器对象
		InnerOnClickListener listener = new InnerOnClickListener();
		// 配置监听器
		findViewById(R.id.start).setOnClickListener(listener);

		// 获取音乐按钮开关；
		mCheckBox_music = (CheckBox) findViewById(R.id.music);
		// 对music按钮进行监听；

		mp = new MediaPlayer();
		try {
			// mp.setDataSource("/sdcard/music/mg_bg_new.mp3");// 设置路径
			mp = MediaPlayer.create(this, R.raw.mg_bg);
			mp.prepare();// 缓冲
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 调用声音
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
			// 创建建intent对象，并配置激活对的组件
			Intent intent = new Intent(StartActivity.this, MyActivity.class);
			// 激活Activity
			startActivity(intent);
		}
	}

	class MusicListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				mp.start();// 开始播放
				// 对音乐进行监听；
				mp.setOnCompletionListener(new OnCompletionListener() {
					// 循环音乐；
					@Override
					public void onCompletion(MediaPlayer mp) {

						try {
							mp.start();

						} catch (IllegalStateException e) {
							e.printStackTrace();
						}
					}
				});
				Toast.makeText(StartActivity.this, "开启音乐", Toast.LENGTH_SHORT)
						.show();
			} else {
				mp.stop();// 关闭背景音乐；
				Toast.makeText(StartActivity.this, "关闭音乐", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	protected void onPause() {
		/**
		 * 设置游戏一直为横屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onPause();
	}

}
