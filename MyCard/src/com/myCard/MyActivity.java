package com.myCard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.myfive.StartActivity;

public class MyActivity extends Activity {

	
	private boolean stop_music=true;
	
	MyView myView;
	String messString;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				messString = msg.getData().getString("data");
				showDialog();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 锁定横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		myView = new MyView(this, handler);
		setContentView(myView);
	}

	public void showDialog() {
		stop_music=false;
		AlertDialog.Builder mDialog = new Builder(MyActivity.this);
		mDialog.setMessage(messString);
		// mDialog.setPositiveButton("重新开始游戏",
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// reGame();
		// }
		// });
		// mDialog.setPositiveButton("返回",
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// Intent intent = new Intent();
		// }
		// });

		mDialog.setPositiveButton("重新开始游戏", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				reGame();
			}
		}).setNegativeButton("返回主界面", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent();
				intent.setClass(MyActivity.this, StartActivity.class);
				startActivity(intent);
			}
		}).setTitle("游戏结束").create().show();
	}
	
	public boolean returnstop_music(boolean stop_music)
	{
		return stop_music;
		
	}

	// 重新开始游戏
	public void reGame() {
		myView = new MyView(this, handler);
		setContentView(myView);
	}

}
