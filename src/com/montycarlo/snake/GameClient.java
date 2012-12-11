package com.montycarlo.snake;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import  android.view.View;

public class GameClient extends View {
	private Paint myPaint;
	private int greyedColour = Color.rgb(235, 235, 235);
	private Snake mySnake;
	private Timer moveTimer;
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
	}
	public class GameTick extends TimerTask {
		@Override
		public void run() {
			gameTick();
		}

	}

	public void onStart(){
		moveTimer = new Timer();
		moveTimer.schedule(new GameTick(), 500, 200);
	}
	private void gameTick(){
		mySnake.shift(0, nodeWidth);
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
	}
}
