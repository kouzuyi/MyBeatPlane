package com.example.view;

import java.util.ArrayList;
import java.util.List;

import com.example.constant.ConstantUtil;
import com.example.factory.GameObjectFactory;
import com.example.mybeatplane.R;
import com.example.object.BigPlane;
import com.example.object.BossPlane;
import com.example.object.BulletGoods;
import com.example.object.EnemyPlane;
import com.example.object.GameObject;
import com.example.object.MiddlePlane;
import com.example.object.MissileGoods;
import com.example.object.MyPlane;
import com.example.object.SmallPlane;
import com.example.sounds.GameSoundPool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MainView extends BaseView{
	private int missileCount;
	private int middlePlaneScore;
	private int bigPlaneScore;
	private int bossPlaneScore;
	private int missileScore;
	private int bulletScore;
	private int sumScore;
	private int speedTime;
	private float bg_y;
	private float bg_y2;
	private float play_bt_w;
	private float play_bt_h;	 
	private float missile_bt_y;		 	
	private boolean isPlay;
	private boolean isTouchPlane;
	private Bitmap background;
	private Bitmap background2;
	private Bitmap playButton;
	private Bitmap missile_bt;
	private MyPlane myPlane;
	private BossPlane bossPlane;
	private List<EnemyPlane> enemyPlanes;
	private MissileGoods missileGoods;
	private BulletGoods bulletGoods;
	private GameObjectFactory factory;
	public MainView(Context context,GameSoundPool sounds) {
		super(context,sounds);
		// TODO Auto-generated constructor stub
		isPlay = true;
		speedTime = 1;
		factory = new GameObjectFactory();
		enemyPlanes = new ArrayList<EnemyPlane>();
		myPlane = (MyPlane) factory.createMyPlane(getResources());
		myPlane.setMainView(this);
		for(int i = 0;i < SmallPlane.sumCount;i++){

			SmallPlane smallPlane = (SmallPlane) factory.createSmallPlane(getResources());
			enemyPlanes.add(smallPlane);
		}
		for(int i = 0;i < MiddlePlane.sumCount;i++){

			MiddlePlane middlePlane = (MiddlePlane) factory.createMiddlePlane(getResources());
			enemyPlanes.add(middlePlane);
		}
		for(int i = 0;i < BigPlane.sumCount;i++){

			BigPlane bigPlane = (BigPlane) factory.createBigPlane(getResources());
			enemyPlanes.add(bigPlane);
		}

		bossPlane = (BossPlane)factory.createBossPlane(getResources());
		bossPlane.setMyPlane(myPlane);
		enemyPlanes.add(bossPlane);

		missileGoods = (MissileGoods) factory.createMissileGoods(getResources());

		bulletGoods = (BulletGoods) factory.createBulletGoods(getResources());
		thread = new Thread(this);	
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceCreated(arg0);
		initBitmap();
		for(GameObject obj:enemyPlanes){			
			obj.setScreenWH(screen_width,screen_height);
		}
		missileGoods.setScreenWH(screen_width, screen_height);
		bulletGoods.setScreenWH(screen_width, screen_height);
		myPlane.setScreenWH(screen_width,screen_height);
		myPlane.setAlive(true);
		if(thread.isAlive()){
			thread.start();
		}
		else{
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(arg0);
		release();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			isTouchPlane = false;
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_DOWN){
			float x = event.getX();
			float y = event.getY();
			if(x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h){
				if(isPlay){
					isPlay = false;
				}		
				else{
					isPlay = true;	
					synchronized(thread){
						thread.notify();
					}
				}
				return true;
			}

			else if(x > myPlane.getObject_x() && x < myPlane.getObject_x() + myPlane.getObject_width() 
					&& y > myPlane.getObject_y() && y < myPlane.getObject_y() + myPlane.getObject_height()){
				if(isPlay){
					isTouchPlane = true;
				}
				return true;
			}

			else if(x > 10 && x < 10 + missile_bt.getWidth() 
					&& y > missile_bt_y && y < missile_bt_y + missile_bt.getHeight()){
				if(missileCount > 0){
					missileCount--;
					sounds.playSound(5, 0);
					for(EnemyPlane pobj:enemyPlanes){
						if(pobj.isCanCollide()){
							pobj.attacked(100);
							if(pobj.isExplosion()){
								addGameScore(pobj.getScore());
							}			
						}
					}	
				}
				return true;
			}
		}

		else if(event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1){

			if(isTouchPlane){
				float x = event.getX();
				float y = event.getY();
				if(x > myPlane.getMiddle_x() + 20){
					if(myPlane.getMiddle_x() + myPlane.getSpeed() <= screen_width){
						myPlane.setMiddle_x(myPlane.getMiddle_x() + myPlane.getSpeed());
					}					
				}
				else if(x < myPlane.getMiddle_x() - 20){
					if(myPlane.getMiddle_x() - myPlane.getSpeed() >= 0){
						myPlane.setMiddle_x(myPlane.getMiddle_x() - myPlane.getSpeed());
					}				
				}
				if(y > myPlane.getMiddle_y() + 20){
					if(myPlane.getMiddle_y() + myPlane.getSpeed() <= screen_height){
						myPlane.setMiddle_y(myPlane.getMiddle_y() + myPlane.getSpeed());
					}		
				}
				else if(y < myPlane.getMiddle_y() - 20){
					if(myPlane.getMiddle_y() - myPlane.getSpeed() >= 0){
						myPlane.setMiddle_y(myPlane.getMiddle_y() - myPlane.getSpeed());
					}
				}
				return true;
			}	
		}
		return false;
	}

	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		playButton = BitmapFactory.decodeResource(getResources(),R.drawable.play);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
		background2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_02);
		missile_bt = BitmapFactory.decodeResource(getResources(), R.drawable.missile_bt);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		play_bt_w = playButton.getWidth();
		play_bt_h = playButton.getHeight()/2;
		bg_y = 0;
		bg_y2 = bg_y - screen_height;
		missile_bt_y = screen_height - 10 - missile_bt.getHeight();
	}

	public void initObject(){
		for(EnemyPlane obj:enemyPlanes){	

			if(obj instanceof SmallPlane){
				if(!obj.isAlive()){
					obj.initial(speedTime,0,0);
					break;
				}	
			}

			else if(obj instanceof MiddlePlane){
				if(middlePlaneScore > 10000){
					if(!obj.isAlive()){
						obj.initial(speedTime,0,0);
						break;
					}	
				}
			}

			else if(obj instanceof BigPlane){
				if(bigPlaneScore >= 25000){
					if(!obj.isAlive()){
						obj.initial(speedTime,0,0);
						break;
					}	
				}
			}

			else{
				if(bossPlaneScore >= 80000){
					if(!obj.isAlive()){
						obj.initial(0,0,0);
						break;
					}
				}
			}
		}

		if(missileScore >= 30000){
			if(!missileGoods.isAlive()){
				missileGoods.initial(0,0,0);
				missileScore = 0;
			}
		}

		if(bulletScore >= 20000){
			if(!bulletGoods.isAlive()){
				bulletGoods.initial(0,0,0);
				bulletScore = 0;
			}
		}

		if(bossPlane.isAlive())
			bossPlane.initButtle();
		myPlane.isBulletOverTime();
		myPlane.initButtle();

		if(sumScore >= speedTime*100000 && speedTime < 6){
			speedTime++;	
		}
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		for(GameObject obj:enemyPlanes){			
			obj.release();
		}
		myPlane.release();
		bulletGoods.release();
		missileGoods.release();
		if(!playButton.isRecycled()){
			playButton.recycle();
		}
		if(!background.isRecycled()){
			background.recycle();
		}
		if(!background2.isRecycled()){
			background2.recycle();
		}
		if(!missile_bt.isRecycled()){
			missile_bt.recycle();
		}
	}

	@Override
	public void drawSelf() {
		// TODO Auto-generated method stub
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.save();

			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, bg_y, paint);
			canvas.drawBitmap(background2, 0, bg_y2, paint);
			canvas.restore();	

			canvas.save();
			canvas.clipRect(10, 10, 10 + play_bt_w,10 + play_bt_h);
			if(isPlay){
				canvas.drawBitmap(playButton, 10, 10, paint);			 
			}
			else{
				canvas.drawBitmap(playButton, 10, 10 - play_bt_h, paint);
			}
			canvas.restore();

			if(missileCount > 0){
				paint.setTextSize(40);
				paint.setColor(Color.BLACK);
				canvas.drawBitmap(missile_bt, 10,missile_bt_y, paint);
				canvas.drawText("X "+String.valueOf(missileCount), 20 + missile_bt.getWidth(), screen_height - 25, paint);//��������
			}	

			if(missileGoods.isAlive()){
				if(missileGoods.isCollide(myPlane)){
					missileGoods.setAlive(false);
					missileCount++;
					sounds.playSound(6, 0);
				}
				else
					missileGoods.drawSelf(canvas);
			}

			if(bulletGoods.isAlive()){
				if(bulletGoods.isCollide(myPlane)){
					bulletGoods.setAlive(false);
					if(!myPlane.isChangeBullet()){
						myPlane.setChangeBullet(true);
						myPlane.changeButtle();
						myPlane.setStartTime(System.currentTimeMillis());
					}
					else{
						myPlane.setStartTime(System.currentTimeMillis());
					}
					sounds.playSound(6, 0);
				}
				else
					bulletGoods.drawSelf(canvas);
			}

			for(EnemyPlane obj:enemyPlanes){		
				if(obj.isAlive()){
					obj.drawSelf(canvas);					

					if(obj.isCanCollide() && myPlane.isAlive()){		
						if(obj.isCollide(myPlane)){			
							myPlane.setAlive(false);	
						}
					}
				}	
			}
			if(!myPlane.isAlive()){
				threadFlag = false;
				sounds.playSound(4, 0);
			}
			myPlane.drawSelf(canvas);
			myPlane.shoot(canvas,enemyPlanes);
			sounds.playSound(1, 0);

			if(missileCount > 0){
				paint.setTextSize(40);
				paint.setColor(Color.BLACK);
				canvas.drawBitmap(missile_bt, 10,missile_bt_y, paint);
				canvas.drawText("X "+String.valueOf(missileCount), 20 + missile_bt.getWidth(), screen_height - 25, paint);//��������
			}

			paint.setTextSize(30);
			paint.setColor(Color.rgb(235, 161, 1));
			canvas.drawText("Score :"+String.valueOf(sumScore), 30 + play_bt_w, 40, paint);
			canvas.drawText("Speed X "+String.valueOf(speedTime), screen_width - 150, 40, paint);
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void viewLogic(){
		if(bg_y > bg_y2){
			bg_y += 10;											
			bg_y2 = bg_y - background.getHeight();
		}
		else{
			bg_y2 += 10;											
			bg_y = bg_y2 - background.getHeight();
		}
		if(bg_y >= background.getHeight()){
			bg_y = bg_y2 - background.getHeight();
		}
		else if(bg_y2 >= background.getHeight()){
			bg_y2 = bg_y - background.getHeight();
		}
	}

	public void addGameScore(int score){
		middlePlaneScore += score;
		bigPlaneScore += score;
		bossPlaneScore += score;
		missileScore += score;
		bulletScore += score;
		sumScore += score;
	}

	public void playSound(int key){
		sounds.playSound(key, 0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (threadFlag) {	
			long startTime = System.currentTimeMillis();
			initObject();
			drawSelf();	
			viewLogic();
			long endTime = System.currentTimeMillis();	
			if(!isPlay){
				synchronized (thread) {  
				    try {  
				    	thread.wait();  
				    } catch (InterruptedException e) {  
				        e.printStackTrace();  
				    }  
				}  		
			}	
			try {
				if (endTime - startTime < 100)
					Thread.sleep(100 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message message = new Message();   
		message.what = 	ConstantUtil.TO_END_VIEW;
		message.arg1 = Integer.valueOf(sumScore);
		mainActivity.getHandler().sendMessage(message);
	}
}
