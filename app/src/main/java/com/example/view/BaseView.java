package com.example.view;
import com.example.mybeatplane.MainActivity;
import com.example.sounds.GameSoundPool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BaseView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
	protected int currentFrame;
	protected float scalex;
	protected float scaley;
	protected float screen_width;
	protected float screen_height;
	protected boolean threadFlag;
	protected Paint paint;
	protected Canvas canvas;
	protected Thread thread;
	protected SurfaceHolder sfh;
	protected GameSoundPool sounds;
	protected MainActivity mainActivity;

	public BaseView(Context context,GameSoundPool sounds) {
		super(context);
		// TODO Auto-generated constructor stub
		this.sounds = sounds;
		this.mainActivity = (MainActivity) context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		screen_width = this.getWidth();
		screen_height = this.getHeight();
		threadFlag = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		threadFlag = false;
	}

	public void initBitmap() {}

	public void release() {}

	public void drawSelf() {}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	public void setThreadFlag(boolean threadFlag){
		this.threadFlag = threadFlag;
	}

}
