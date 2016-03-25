package com.example.object;

import com.example.mybeatplane.R;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class MissileGoods extends GameGoods{
	public MissileGoods(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub
		bmp = BitmapFactory.decodeResource(resources, R.drawable.missile_goods);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}
