package com.montycarlo.snake;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class SnakeNode extends Drawable {
	private Paint myPaint;
	private float x, y;
	public SnakeNode(float x, float y){
		this.x = x;
		this.y = y;
	}
	public void setPaint(Paint aPaint){
		myPaint = aPaint;
	}
	@Override
	public void draw(Canvas canvas) {
		if(myPaint == null) return;
		canvas.drawRect(x, y, x+GameClient.nodeWidth, y+GameClient.nodeWidth, myPaint);
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
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public int getindX(){
		return Math.round(x / GameClient.nodeWidth);
	}
	public int getindY(){
		return Math.round(y / GameClient.nodeWidth);
	}
	public float[] getPos(){
		return new float[]{x, y};
	}
	public void shift(float x, float y){
		this.x += x;
		this.y += y;
	}
	public void setPos(float x, float y){
		this.x = x;
		this.y = y;
	}
	public void wrap(float maxX, float maxY){
		int myX = Math.round(x / GameClient.nodeWidth);
		int myY = Math.round(y / GameClient.nodeWidth);
		if(myX >= maxX) x = 0;
		else if(myX < 0) x = maxX * GameClient.nodeWidth - GameClient.nodeWidth;
		if(myY >= maxY) y = 0;
		else if(myY < 0) y = maxY * GameClient.nodeWidth - GameClient.nodeWidth;
	}
}
