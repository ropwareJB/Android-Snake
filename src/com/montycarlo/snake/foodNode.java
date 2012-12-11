package com.montycarlo.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class foodNode extends Drawable{
	private float x, y;
	private Paint myPaint = new Paint();
	private final int myColour = Color.rgb(30, 240, 30);
	public foodNode(float newX, float newY){
		x = newX;
		y = newY;
		myPaint.setColor(myColour);
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public void setX(float newX){
		x = newX;
	}
	public void setY(float newY){
		y = newY;
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.drawRect(x, y, x+GameClient.nodeWidth, y + GameClient.nodeWidth, myPaint);
	}
	@Override
	public int getOpacity() {
		return 0;
	}
	@Override
	public void setAlpha(int arg0) {
	}
	@Override
	public void setColorFilter(ColorFilter arg0) {
	}
}
