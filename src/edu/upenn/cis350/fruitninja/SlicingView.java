package edu.upenn.cis350.fruitninja;

import java.util.ArrayList;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SlicingView extends View{
	
	public MainActivity m;
	
	public SlicingView(Context c){
		super(c);
		m = (MainActivity) c;
		init();
	}

	public SlicingView(Context c, AttributeSet a){
		super(c, a);
		m = (MainActivity) c;
		init();
	}
	
	private double gravity;
	public boolean clear;
	public Paint paintBrush;
	public Path p;
	public ArrayList<Path> strokes;
	public ArrayList<Paint> strokesPaint;
	
	//Contains all game objects 
	private ArrayList<GameObject> gameobjs;
	
	//Multiplier for speed - used in difficulty settings
	public double speedMult = 1.0;
	
	//Multiplier for size - used in difficulty settings
	public double sizeMult = 1.0;
	
	int c = Color.RED;
	
	protected void init(){
		gravity = .5;
		clear = false;
		strokes = new ArrayList<Path>();
		strokesPaint = new ArrayList<Paint>();
		
		int xEnter1 = (int)(Math.random()*1200);
		int yEnter1 = (int)(Math.random()*550);
		int xSpeed1 = (int)(speedMult*Math.random()*-20);
		int ySpeed1 = (int)(speedMult*Math.random()*40+10);
		
		gameobjs = new ArrayList<GameObject>();
		GameObject square = new GameObject(0, 100, (int)(50*sizeMult), (int)(50*sizeMult), 7, 10);
		GameObject squareTwo = new GameObject(xEnter1, 540, (int)(100*sizeMult), (int)(100*sizeMult), xSpeed1, ySpeed1);
		square.getPaint().setColor(Color.RED);
		squareTwo.getPaint().setColor(Color.BLUE);
		gameobjs.add(squareTwo);
		gameobjs.add(square);
	}
	
	protected void onDraw(Canvas canvas){

		for(GameObject go : gameobjs){
			//Decrease Y speed by the amount of gravity
			go.setSpeedY(go.getSpeedY()-gravity);
			go.draw(canvas);
		}
		
		for (int i = 0; i < strokes.size(); i++){
			canvas.drawPath(strokes.get(i), strokesPaint.get(i));
		}

	    if (clear){
	    	canvas.drawColor(Color.WHITE);
	    	strokesPaint.clear();
	    	strokes.clear();
	    	clear = false;
	    	init();
	    	invalidate();
	    }
	    invalidate();
	}
	
	//Go through all game objects and increase speed.
	//Also increase speed multiplier.
	public void incSpeed(){
		for(GameObject go : gameobjs){
			if(go.getSpeedX()>0){
				go.setSpeedX(go.getSpeedX()+1);
			}
			else{
				go.setSpeedX(go.getSpeedX()-1);
			}
			if(go.getSpeedY()>0){
				go.setSpeedY(go.getSpeedY()+1);
			}
			else{
				go.setSpeedY(go.getSpeedY()-1);
			}
		}
		speedMult+=0.1;
	}
	
	//Go through all game objects and decrease speed.
	//Also decrease speed multiplier.
	public void decSpeed(){
		for(GameObject go : gameobjs){
			if(go.getSpeedX()>0){
				go.setSpeedX(go.getSpeedX()-1);
			}
			else{
				go.setSpeedX(go.getSpeedX()+1);
			}
			if(go.getSpeedY()>0){
				go.setSpeedY(go.getSpeedY()-1);
			}
			else{
				go.setSpeedY(go.getSpeedY()+1);
			}
		}
		if (speedMult>0.1){
			speedMult-=0.1;
		}
	}
	
	//Go through all game objects and increase size.
	//Also increase size multiplier.
	public void incSize(){
		for(GameObject go : gameobjs){
			go.setWidth(go.getWidth()+10);
			go.setHeight(go.getHeight()+10);
		}
		sizeMult+=0.1;
	}
	
	//Go through all game objects and decrease size.
	//Also decrease size multiplier.
	public void decSize(){
		for(GameObject go : gameobjs){
			go.setWidth(go.getWidth()-10);
			go.setHeight(go.getHeight()-10);
		}
		if(sizeMult>0.1){
			sizeMult-=0.1;
		}
	}
	
	public boolean onTouchEvent(MotionEvent e){
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int)e.getX();
			int y = (int)e.getY();
			p = new Path();
			paintBrush = new Paint();
			paintBrush.setColor(m.getColor());                  // set the color
		    paintBrush.setStrokeWidth(m.getThickness());        // set the size
		    paintBrush.setDither(true);                    		// set the dither to true
		    paintBrush.setStyle(Paint.Style.STROKE);       		// set to STROKE
		    paintBrush.setStrokeJoin(Paint.Join.ROUND);    		// set the join to round you want
		    paintBrush.setStrokeCap(Paint.Cap.ROUND);      		// set the paintBrush cap to round too
		    paintBrush.setAntiAlias(true);                      // set anti alias so it smoothes
		    strokesPaint.add(paintBrush);
			strokes.add(p);
			p.moveTo(x, y);
			invalidate();
			return true;
		}else if (e.getAction() == MotionEvent.ACTION_MOVE){
			int x = (int)e.getX();
			int y = (int)e.getY();
			p.lineTo(x,y);
			
			//Tests every GameObject in the gameobjs list
			//If intersection is detected, removes that GameObject from the list
			for (GameObject go : gameobjs){
				if(go.intersect(x, y)){
					m.scoreNumber += 10;		//score increases by 10 for every target hit
					gameobjs.remove(go);
				}
			}
			invalidate();
			return true;
		}
		else if (e.getAction() == MotionEvent.ACTION_UP){
			strokes.clear();
		}
		return false;
	}
}
