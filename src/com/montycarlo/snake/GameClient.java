package com.montycarlo.snake;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;

public class GameClient extends View implements OnTouchListener{
	private Paint myPaint;
	private Paint textPaint;
	private int greyedColour = Color.rgb(235, 235, 235);
	private Snake mySnake;
	private Timer moveTimer;
	private float vx, vy;
	private boolean gameover;
	private int highscore;
	private ArrayList<foodNode> myFood;
	private SharedPreferences prefs;
	public static int nodeW;
	public static int nodeH;
	public static int myWidth;
	public static int myHeight;
	public static float nodeWidth;
	public static final int nodeCount = 25;
	private final String scoreKey = "com.montycarlo.snake.highscore";
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
		textPaint= new Paint();
		highscore = 0;
		vx = 0;
		vy = nodeWidth;
		myFood = new ArrayList<foodNode>();
		addFood(1);
		restart();
	}
	public void setSharedPrefs(SharedPreferences newSharedPref){
		prefs = newSharedPref;
		highscore = prefs.getInt(scoreKey, 0);
	}
	public void addFood(int count){
		foodNode newFood;
		float[] newPos;
		for(int i=0;i<count;i++){
			newPos = randPos();
			newFood = new foodNode(newPos[0], newPos[1]);
			myFood.add(newFood);
		}
	}
	public float[] randPos(){
		float sx, sy;
		sx = ((int) Math.floor(Math.random() * nodeW)) * nodeWidth;
		sy = ((int) Math.floor(Math.random() * nodeH)) * nodeWidth;
		return new float[]{sx, sy};
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
		if(gameover) {
			restart();
			onResume();
		}
		float[] vxs = {0, nodeWidth, 0, -nodeWidth};
		float[] vys = {nodeWidth, 0, -nodeWidth, 0};
		int ind = 0;
		if(vx == 0 && vy>0) ind = 0;
		else if(vx > 0 && vy == 0) ind = 1;
		else if(vx == 0 && vy < 0) ind = 2;
		else if(vx < 0 && vy == 0) ind = 3;
		if(event.getX() > myWidth/2) ind--;
		else ind++;
		if(ind<0) ind += vxs.length;
		ind %= vxs.length;
		vx = vxs[ind];
		vy = vys[ind];
		return super.onTouchEvent(event);
	}
	private void gameTick(){
		mySnake.shift(vx, vy);
		testGameOver();
		int[] pos = mySnake.getHeadPosIndex();
		for(foodNode n : myFood) testEat(n, pos);
		this.postInvalidate();
	}
	private void testEat(foodNode node, int[] pos){
		if(node.getindX() == pos[0] && node.getindY() == pos[1]){
			if(myFood.size()>1) myFood.remove(node);
			else{
				float[] newPos = randPos();
				node.setX(newPos[0]);
				node.setY(newPos[1]);
			}
			mySnake.addNodes(1);
		}
	}
	private void testGameOver(){
		if(mySnake.testEatSelf()){
			moveTimer.cancel();
			gameover = true;
			if(highscore<mySnake.getLength()) {
				highscore = mySnake.getLength();
				prefs.edit().putInt(scoreKey, highscore).commit();
			}
		}
	}
	private void restart(){
		mySnake = new Snake(5);
		gameover = false;
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
		textPaint.setTextSize(20);
		textPaint.setARGB(150, 0, 0, 0);
		for(foodNode f : myFood) f.draw(canvas);
		canvas.drawText("H: " + highscore + ", S:" + mySnake.getLength(), 5, 25, textPaint);
	}
}
