package com.example.interfaces;

import java.util.List;

import com.example.object.EnemyPlane;

import android.graphics.Canvas;

public interface IMyPlane {
	public float getMiddle_x();
	public void setMiddle_x(float middle_x);
	public float getMiddle_y();
	public void setMiddle_y(float middle_y);
	public boolean isChangeBullet();
	public void setChangeBullet(boolean isChangeBullet);

	public void shoot(Canvas canvas,List<EnemyPlane> planes);

	public void initButtle();

	public void changeButtle();
}
