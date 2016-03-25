package com.example.object;

import android.content.res.Resources;
import android.graphics.Canvas;

public class EnemyPlane extends GameObject{
	protected int score;
	protected int blood;
	protected int bloodVolume;
	protected boolean isExplosion;
	protected boolean isVisible;
	public EnemyPlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
	}

	@Override
	public void initial(int arg0,float arg1,float arg2){
		
	}

	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub
		if (object_y < screen_height) {
			object_y += speed;
		} 
		else {
			isAlive = false;
		}
		if(object_y + object_height > 0){
			isVisible = true;
		}
		else{
			isVisible = false;
		}
	}

	public void attacked(int harm) {
		// TODO Auto-generated method stub
		blood -= harm;
		if (blood <= 0) {
			isExplosion = true;
		}
	}

	@Override
	public boolean isCollide(GameObject obj) {

		if (object_x <= obj.getObject_x()
				&& object_x + object_width <= obj.getObject_x()) {
			return false;
		}

		else if (obj.getObject_x() <= object_x
				&& obj.getObject_x() + obj.getObject_width() <= object_x) {
			return false;
		}

		else if (object_y <= obj.getObject_y()
				&& object_y + object_height <= obj.getObject_y()) {
			return false;
		}

		else if (obj.getObject_y() <= object_y
				&& obj.getObject_y() + obj.getObject_height() <= object_y) {
			return false;
		}
		return true;
	}

	public boolean isCanCollide() {
		// TODO Auto-generated method stub
		return isAlive && !isExplosion && isVisible;
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	public void setScore(int score) {
		// TODO Auto-generated method stub
		this.score = score;
	}
	public int getBlood() {
		// TODO Auto-generated method stub
		return blood;
	}
	public void setBlood(int blood) {
		// TODO Auto-generated method stub
		this.blood = blood;
	}
	public int getBloodVolume() {
		// TODO Auto-generated method stub
		return bloodVolume;
	}
	public void setBloodVolume(int bloodVolume) {
		// TODO Auto-generated method stub
		this.bloodVolume = bloodVolume;
	}
	public boolean isExplosion() {
		// TODO Auto-generated method stub
		return isExplosion;
	}
}

