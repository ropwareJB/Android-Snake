package com.montycarlo.snake;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Snake extends Drawable {
	private Paint myPaint = new Paint();
	private int myColour = Color.rgb(20, 20, 20);
	private List<SnakeNode> myNodes;
	public Snake(int w){
		myPaint.setColor(myColour);
		myNodes = new ArrayList<SnakeNode>();
		addNodes(w);
	}
	public void addNodes(int count){
		if(count <= 0) return;
		SnakeNode head;
		if(myNodes.size() == 0) {
			myNodes.add(head = new SnakeNode(0, 0));
			head.setPaint(myPaint);
			count--;
		}else head = myNodes.get(0);
		for(int i=0;i<count;i++) {
			SnakeNode stack = new SnakeNode(head.getX(), head.getY());
			myNodes.add(stack);
			stack.setPaint(myPaint);
		}
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		for(SnakeNode a : myNodes) a.draw(canvas);
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
	public void shift(float x, float y){
		if(myNodes.size() == 0) return;
		SnakeNode head = myNodes.get(0);
		for(int i=myNodes.size()-1;i>0;i--) {
			SnakeNode a = myNodes.get(i);
			SnakeNode b = myNodes.get(i-1);
			a.setPos(b.getX(), b.getY());
			a.wrap((int)(GameClient.nodeW * GameClient.nodeWidth), (int)(GameClient.nodeH * GameClient.nodeWidth));
		}
		head.shift(x, y);
		head.wrap((int)(GameClient.nodeW * GameClient.nodeWidth), (int)(GameClient.nodeH * GameClient.nodeWidth));
	}
}
