package com.example.object;

import java.util.Random;
import com.example.mybeatplane.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class MiddlePlane extends EnemyPlane{
	private static int currentCount = 0;
	private Bitmap middlePlane;
	public static int sumCount = 4;
	public MiddlePlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.score = 1000;
	}

	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		bloodVolume = 15;
		blood = bloodVolume;
		Random ran = new Random();
		speed = ran.nextInt(2) + 6 * arg0;	
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
		middlePlane = BitmapFactory.decodeResource(resources, R.drawable.middle);
		object_width = middlePlane.getWidth();
		object_height = middlePlane.getHeight()/4;
	}

	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			if(!isExplosion){
				if(isVisible){
					canvas.save();
					canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
					canvas.drawBitmap(middlePlane, object_x, object_y,paint);
					canvas.restore();
				}	
				logic();
			}
			else{
				int y = (int) (currentFrame * object_height);
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(middlePlane, object_x, object_y - y,paint);
				canvas.restore();
				currentFrame++;
				if(currentFrame >= 4){
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
		if(!middlePlane.isRecycled()){
			middlePlane.recycle();
		}
	}
}
