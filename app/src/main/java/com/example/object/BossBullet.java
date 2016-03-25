package com.example.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.example.mybeatplane.R;

public class BossBullet extends Bullet{
	private Bitmap bullet;
	public BossBullet(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.harm = 1;
	}

	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		speed = -20;	
		object_x = arg1 - object_width / 2;
		object_y = arg2 - object_height;
	}

	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bullet = BitmapFactory.decodeResource(resources, R.drawable.bossbullet);
		object_width = bullet.getWidth();
		object_height = bullet.getHeight();
	}

	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if (isAlive) {
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width,object_y + object_height);
			canvas.drawBitmap(bullet, object_x, object_y, paint);
			canvas.restore();
			logic();
		}
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!bullet.isRecycled()){
			bullet.recycle();
		}
	}

	@Override
	public void logic() {
		if (object_y <= screen_height) {
			object_y -= speed;
		} else {
			isAlive = false;
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
		isAlive = false;
		return true;
	}
}

