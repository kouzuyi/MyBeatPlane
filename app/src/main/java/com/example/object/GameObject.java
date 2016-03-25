package com.example.object;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

abstract public class GameObject {
	protected int currentFrame;
	protected int speed;
	protected float object_x;
	protected float object_y;
	protected float object_width;
	protected float object_height;
	protected float screen_width;
	protected float screen_height;
	protected boolean isAlive;
	protected Paint paint;
	protected Resources resources;

	public GameObject(Resources resources) {
		this.resources = resources;
		this.paint = new Paint();
	}

	public void setScreenWH(float screen_width, float screen_height) {
		this.screen_width = screen_width;
		this.screen_height = screen_height;
	}

	public void initial(int arg0,float arg1,float arg2){}

	protected abstract void initBitmap();

	public abstract void drawSelf(Canvas canvas);

	public abstract void release();

	public boolean isCollide(GameObject obj) {	
		return true;
	}

	public void logic(){}

	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public float getObject_x() {
		return object_x;
	}
	public void setObject_x(float object_x) {
		this.object_x = object_x;
	}
	public float getObject_y() {
		return object_y;
	}
	public void setObject_y(float object_y) {
		this.object_y = object_y;
	}
	public float getObject_width() {
		return object_width;
	}
	public void setObject_width(float object_width) {
		this.object_width = object_width;
	}
	public float getObject_height() {
		return object_height;
	}
	public void setObject_height(float object_height) {
		this.object_height = object_height;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
