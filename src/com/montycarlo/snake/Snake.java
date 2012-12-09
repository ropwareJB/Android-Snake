package com.montycarlo.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class Snake extends Drawable {
	Paint myPaint = new Paint();
	int myColour = Color.rgb(0, 220, 50);
	int x, y;
	int w;
	public Snake(int w){
		this.w = w;
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		myPaint.setColor(myColour);
		canvas.drawRect(x, y, x+w, y+w*5, myPaint);
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub
	}
	public void shift(int x, int y){
		this.x += x;
		this.y += y;
	}
}
