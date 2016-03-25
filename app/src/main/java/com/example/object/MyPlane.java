package com.example.object;

import java.util.ArrayList;
import java.util.List;
import com.example.factory.GameObjectFactory;
import com.example.interfaces.IMyPlane;
import com.example.mybeatplane.R;
import com.example.view.MainView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class MyPlane extends GameObject implements IMyPlane{
	private float middle_x;
	private float middle_y;
	private long startTime;
	private long endTime;
	private boolean isChangeBullet;
	private Bitmap myplane;
	private Bitmap myplane2;
	private List<Bullet> bullets;
	private MainView mainView;
	private GameObjectFactory factory;
	public MyPlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
		this.speed = 8;
		isChangeBullet = false;
		factory = new GameObjectFactory();
		bullets = new ArrayList<Bullet>();
		changeButtle();
	}
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void setScreenWH(float screen_width, float screen_height) {
		super.setScreenWH(screen_width, screen_height);
		object_x = screen_width/2 - object_width/2;
		object_y = screen_height - object_height;
		middle_x = object_x + object_width/2;
		middle_y = object_y + object_height/2;
	}

	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		myplane = BitmapFactory.decodeResource(resources, R.drawable.myplane);
		myplane2 = BitmapFactory.decodeResource(resources, R.drawable.myplaneexplosion);
		object_width = myplane.getWidth() / 2;
		object_height = myplane.getHeight();
	}

	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			int x = (int) (currentFrame * object_width);
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
			canvas.drawBitmap(myplane, object_x - x, object_y, paint);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 0;
			}
		}
		else{
			int x = (int) (currentFrame * object_width);
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y
					+ object_height);
			canvas.drawBitmap(myplane2, object_x - x, object_y, paint);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 1;
			}
		}
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		for(Bullet obj:bullets){	
			obj.release();
		}
		if(!myplane.isRecycled()){
			myplane.recycle();
		}
		if(!myplane2.isRecycled()){
			myplane2.recycle();
		}
	}

	@Override
	public void shoot(Canvas canvas,List<EnemyPlane> planes) {
		// TODO Auto-generated method stub	

		for(Bullet obj:bullets){	
			if(obj.isAlive()){
				for(EnemyPlane pobj:planes){

					if( pobj.isCanCollide()){
						if(obj.isCollide((GameObject)pobj)){
							 pobj.attacked(obj.getHarm());
							if(pobj.isExplosion()){
								mainView.addGameScore(pobj.getScore());
								if(pobj instanceof SmallPlane){
									mainView.playSound(2);
								}
								else if(pobj instanceof MiddlePlane){
									mainView.playSound(3);
								}
								else if(pobj instanceof BigPlane){
									mainView.playSound(4);
								}
								else{
									mainView.playSound(5);
								}
							}			
							break;
						}
					}
				}
				obj.drawSelf(canvas);
			}
		}
	}

	@Override
	public void initButtle() {
		// TODO Auto-generated method stub
		for(Bullet obj:bullets){	
			if(!obj.isAlive()){
				obj.initial(0,middle_x, middle_y);
				break;
			}
		}
	}

	@Override
	public void changeButtle() {
		// TODO Auto-generated method stub
		bullets.clear();
		if(isChangeBullet){
			for(int i = 0;i < 4;i++){
				MyBullet2 bullet = (MyBullet2) factory.createMyBullet2(resources);
				bullets.add(bullet);
			}
		}
		else{
			for(int i = 0;i < 4;i++){
				MyBullet bullet = (MyBullet) factory.createMyBullet(resources);
				bullets.add(bullet);
			}
		}
	}

	public void isBulletOverTime(){
		if(isChangeBullet){
			endTime = System.currentTimeMillis();	
			if(endTime - startTime > 15000){
				isChangeBullet = false;
				startTime = 0;
				endTime = 0;
				changeButtle();
			}
		}
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	@Override
	public boolean isChangeBullet() {
		return isChangeBullet;
	}
	@Override
	public void setChangeBullet(boolean isChangeBullet) {
		this.isChangeBullet = isChangeBullet;
	}
	@Override
	public float getMiddle_x() {
		return middle_x;
	}
	@Override
	public void setMiddle_x(float middle_x) {
		this.middle_x = middle_x;
		this.object_x = middle_x - object_width/2;
	}
	@Override
	public float getMiddle_y() {
		return middle_y;
	}
	@Override
	public void setMiddle_y(float middle_y) {
		this.middle_y = middle_y;
		this.object_y = middle_y - object_height/2;
	}	
}
