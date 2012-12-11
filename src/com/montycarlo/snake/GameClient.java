package com.montycarlo.snake;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;

public class GameClient extends View implements OnTouchListener{
	private Paint myPaint;
	private int greyedColour = Color.rgb(235, 235, 235);
	private Snake mySnake;
	private Timer moveTimer;
	private float vx, vy;
	private ArrayList<foodNode> myFood;
	public static int nodeW;
	public static int nodeH;
	public static int myWidth;
	public static int myHeight;
	public static float nodeWidth;
	public static final int nodeCount = 25;
	public GameClient(Context context, AttributeSet attributes){
		super(context, attributes);
		DisplayMetrics myMetrics = getContext().getResources().getDisplayMetrics();
		myWidth = myMetrics.widthPixels;
		myHeight = myMetrics.heightPixels;
		nodeWidth = (float)myWidth/(float)nodeCount;
		nodeW = nodeCount;
		nodeH = (int) (myHeight / nodeWidth);
		setMinimumWidth(myWidth);
		setMinimumHeight(myHeight);
		myPaint = new Paint();
		mySnake = new Snake(5);
		vx = 0;
		vy = nodeWidth;
		myFood = new ArrayList<foodNode>();
		addFood(1);
	}
	public void addFood(int count){
		foodNode newFood;
		int sx, sy;
		for(int i=0;i<count;i++){
			sx = (int) Math.floor(Math.random() * nodeW);
			sy = (int) Math.floor(Math.random() * nodeH);
			newFood = new foodNode(sx * nodeWidth, sy * nodeWidth);
			myFood.add(newFood);
		}
	}
	public class GameTick extends TimerTask {
		@Override
		public void run() {
			gameTick();
		}
	}
	public void onResume(){
		moveTimer = new Timer();
		moveTimer.schedule(new GameTick(), 500, 100);
	}
	public void onStop(){
		moveTimer.cancel();
	}
	public boolean onTouch(View v, MotionEvent event){
		if(event.getAction() != MotionEvent.ACTION_DOWN) return true;
		float[] vxs = {0, nodeWidth, 0, -nodeWidth};
		float[] vys = {nodeWidth, 0, -nodeWidth, 0};
		int ind = 0;
		if(vx == 0 && vy>0) ind = 0;
		else if(vx > 0 && vy == 0) ind = 1;
		else if(vx == 0 && vy < 0) ind = 2;
		else if(vx < 0 && vy == 0) ind = 3;
		if(event.getY() > myHeight/2) ind--;
		else ind++;
		if(ind<0) ind += vxs.length;
		ind %= vxs.length;
		vx = vxs[ind];
		vy = vys[ind];
		return super.onTouchEvent(event);
	}
	private void gameTick(){
		mySnake.shift(vx, vy);
		this.postInvalidate();
	}
	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(getContext().getResources().getDisplayMetrics().widthPixels, getContext().getResources().getDisplayMetrics().heightPixels);
	}
	@Override protected void onDraw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		myPaint.setColor(greyedColour);
		for(int i=0;i<nodeW;i++){
			for(int q=0;q<nodeH;q++){
				if(((i + q) % 2) == 0) continue;
				canvas.drawRect(i*nodeWidth, q*nodeWidth, i*nodeWidth+nodeWidth, q*nodeWidth+nodeWidth, myPaint);
			}
		}
		mySnake.draw(canvas);
		for(foodNode f : myFood) f.draw(canvas);
	}
}
