package com.example.object;

import java.util.Random;
import com.example.mybeatplane.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class SmallPlane extends EnemyPlane{
	private static int currentCount = 0;
	private Bitmap smallPlane;
	public static int sumCount = 8;
	public SmallPlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.score = 100;
	}

	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		bloodVolume = 1;
		blood = bloodVolume;
		Random ran = new Random();
		speed = ran.nextInt(8) + 8 * arg0;	
		object_x = ran.nextInt((int)(screen_width - object_width));
		object_y = -object_height * (currentCount*2 + 1);
		currentCount++;
		if(currentCount >= sumCount){
			currentCount = 0;
		}
	}

	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		smallPlane = BitmapFactory.decodeResource(resources, R.drawable.small);
		object_width = smallPlane.getWidth();
		object_height = smallPlane.getHeight()/3;
	}

	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){

			if(!isExplosion){
				if(isVisible){
					canvas.save();
					canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
					canvas.drawBitmap(smallPlane, object_x, object_y,paint);
					canvas.restore();
				}	
				logic();
			}
			else{
				int y = (int) (currentFrame * object_height);
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(smallPlane, object_x, object_y - y,paint);
				canvas.restore();
				currentFrame++;
				if(currentFrame >= 3){
					currentFrame = 0;
					isExplosion = false;
					isAlive = false;
				}
			}
		}
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!smallPlane.isRecycled()){
			smallPlane.recycle();
		}
	}
}
