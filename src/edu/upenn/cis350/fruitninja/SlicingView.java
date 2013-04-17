package edu.upenn.cis350.fruitninja;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.TextView;

public class SlicingView extends View{
	
	public MainActivity m;
	private final Bitmap mBitmapFromSdcard;
	private final int EXP_ID;
	private final int TRMB_ID;
	
	public SlicingView(Context c) throws IOException{
		super(c);
		m = (MainActivity) c;
		mBitmapFromSdcard = BitmapFactory.decodeResource(getResources(), R.drawable.spacebg);
		sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		AssetFileDescriptor afd = m.getAssets().openFd("explosion.wav");
		AssetFileDescriptor afd2 = m.getAssets().openFd("sadTrombone.wav");
		EXP_ID = sp.load(afd, 0);
		TRMB_ID = sp.load(afd2, 0);
		init();
	}

	public SlicingView(Context c, AttributeSet a) throws IOException{
		super(c, a);
		m = (MainActivity) c;
		mBitmapFromSdcard = BitmapFactory.decodeResource(getResources(), R.drawable.spacebg);
		sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		AssetFileDescriptor afd = m.getAssets().openFd("explosion.wav");
		AssetFileDescriptor afd2 = m.getAssets().openFd("sadTrombone.wav");
		EXP_ID = sp.load(afd, 0);
		TRMB_ID = sp.load(afd2, 0);
		init();
	}
	
	private double gravity;
	public boolean clear;
	public Paint paintBrush;
	public Path p;
	public ArrayList<Path> strokes;
	public ArrayList<Paint> strokesPaint;
	private SoundPool sp;
	public int levelNum = 0;
	public int[] levelScores = {100, 150, 250, 350, 400};
	public int[] levelTimes = {30, 40, 80, 120, 200};
	public double[] levelDifficulty = {.6, .7, .8, .9, 1};
	
	//Contains all game objects 
	private ArrayList<GameObject> gameobjs;
	
	//Multiplier for speed - used in difficulty settings
	public double speedMult = 1.0;
	
	//Multiplier for size - used in difficulty settings
	public double sizeMult = 2.5;
	
	int c = Color.RED;
	
	protected void init(){
		gravity = .5;
		clear = false;
		strokes = new ArrayList<Path>();
		strokesPaint = new ArrayList<Paint>();
		
		int xEnter1 = (int)(Math.random()*1200);
		int yEnter1 = (int)(Math.random()*550);
		int xSpeed1 = (int)(speedMult*Math.random()*-20);
		int ySpeed1 = (int)(speedMult*Math.random()*50+15);
		
		gameobjs = new ArrayList<GameObject>();
		GameObject square = new GameObject(0, 100, (int)(50*sizeMult), (int)(50*sizeMult), 7, 10, this);
		GameObject squareTwo = new GameObject(xEnter1, 540, (int)(100*sizeMult), (int)(100*sizeMult), xSpeed1, ySpeed1, this);
		
		square.getPaint().setColor(Color.RED);
		squareTwo.getPaint().setColor(Color.BLUE);
		gameobjs.add(square);
		gameobjs.add(squareTwo);
		
		m.levelNumber = levelNum;
		
		//Initialize the Timer
		m.t.start();
		m.playTimer.start();
	}
	
	//Draw the view
	protected void onDraw(Canvas canvas){

        canvas.drawBitmap(mBitmapFromSdcard, 0, 0, null);
		
        //newGameObject();
        if(m.t.getElapsedTime() % 2.5 < .15){
        	newGameObject();
        }
        
		for(GameObject go : gameobjs){
			//Decrease Y speed by the amount of gravity
			go.setSpeedY(go.getSpeedY()-(gravity*levelDifficulty[levelNum]));
			go.draw(canvas);
		}
		
		//Object goes off screen --> remove it, increase number of misses
		for (int i = 0; i < gameobjs.size(); i++){
			if(gameobjs.get(i).x < -100 || gameobjs.get(i).x > 1600 || gameobjs.get(i).y < -100 
					|| gameobjs.get(i).y > 800){
				gameobjs.remove(gameobjs.get(i));
				m.misses++;
			}
		}
		
		for (int i = 0; i < strokes.size(); i++){
			canvas.drawPath(strokes.get(i), strokesPaint.get(i));
		}

	    if (clear){
	    	canvas.drawBitmap(mBitmapFromSdcard, 0, 0, null);
	    	strokesPaint.clear();
	    	strokes.clear();
	    	clear = false;
	    	init();
	    	invalidate();
	    }
	    
	    /*if m.misses reaches 5 LOSE GAME - to be implemented
	    if (m.misses >= 5){
	    	
	    }*/
	    
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
			paintBrush.setColor(Color.WHITE);                  // set the color
		    paintBrush.setStrokeWidth(2);        // set the size
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
			for (int i = 0; i < gameobjs.size(); i++){
				if(gameobjs.get(i).intersect(x, y)){
					m.hits++;
					
					if(gameobjs.get(i).width < 33 * speedMult){
						m.scoreNumber += 25;		//score increases by 10 for every target hit
					}
					else if(gameobjs.get(i).width >= 33 * speedMult && gameobjs.get(i).width < 45 * speedMult){
						m.scoreNumber += 15;
					}
					else{
						m.scoreNumber += 10;
					}
					
					ScoreView sView = (ScoreView) m.findViewById(R.id.ScoreView);
					sView.invalidate();
					gameobjs.remove(gameobjs.get(i));
					i--;
					sp.play(EXP_ID, 0.5f, 0.5f, 0, 0, 1);
					newGameObject();
					
				}
			}
			
			if(levelNum == 4){
				m.showScoreScreen();
			}
			//CHECK FOR LEVEL COMPLETION
			if(m.scoreNumber >= levelScores[levelNum] && m.t.getElapsedTime() < levelTimes[levelNum]){
				levelNum++;
				m.levelNumber = levelNum;
				
				m.showScoreScreen();
			}
			
			if(m.scoreNumber < levelScores[levelNum] && m.t.getElapsedTime() >= levelTimes[levelNum]){
				sp.play(TRMB_ID, 0.5f, 0.5f, 0, 0, 1);
				m.scoreNumber = 0;
				//Save the three lines below for further testing purposes //
				//incSize();
				//decSpeed();
				//m.t.start();
				m.showScoreScreen();
			}
			
			
			invalidate();
			return true;
		}
		else if (e.getAction() == MotionEvent.ACTION_UP){
			strokes.clear();
		}
		return false;
	}
	
	//create a new game object in a random location and add it to the list of game objects
	public GameObject newGameObject(){
		double random = Math.random();
		GameObject square;
		int xSpeed = 1;
		int ySpeed = 1;
		int size = (int)(Math.random()*50+25);
		
		//Left Side
		if(random < .3){
			int yPos = (int) (550 * Math.random());
			
			xSpeed = (int)(speedMult*Math.random()*15 + 10);
			ySpeed = (int)(speedMult*Math.random()*20 + (yPos/45)*levelDifficulty[levelNum]);
			
			
			
			square = new GameObject(0, yPos, (int)(size*sizeMult), (int)(size*sizeMult), xSpeed, ySpeed, this);
		}
		
		//Top Side
		else if(random < .5 && random >= .3){
			int xPos = (int)(Math.random()*1200);
			if(xPos > 600){
				xSpeed = (int)(speedMult*Math.random()*-15);
			}
			else{
				xSpeed = (int)(speedMult*Math.random()*15);
			}
			ySpeed = (int)(speedMult*Math.random()*1*levelDifficulty[levelNum]);
			square = new GameObject(xPos, 0, (int)(size*sizeMult), (int)(size*sizeMult), xSpeed, -ySpeed, this);
		}
		
		//Right Side
		else if(random < .8 && random >= .5){
			int yPos = (int) (550 * Math.random());
			xSpeed = (int)(speedMult*Math.random()*-10 - 2);
			ySpeed = (int)(speedMult*Math.random()*15 + (yPos/39)*levelDifficulty[levelNum]);
			
			square = new GameObject(1200, yPos, (int)(size*sizeMult), (int)(size*sizeMult), xSpeed, ySpeed, this);
		}
		
		//Bottom Side
		else{
			int xPos = (int)(Math.random()*1200);
			if(xPos > 600){
				xSpeed = (int)(speedMult*Math.random()*-5);
			}
			else{
				xSpeed = (int)(speedMult*Math.random()*5);
			}
			ySpeed = (int)(speedMult*Math.random()*10 + (1.2-speedMult)*40*levelDifficulty[levelNum]);
			square = new GameObject(xPos, 550, (int)(size*sizeMult), (int)(size*sizeMult), xSpeed, ySpeed, this);
		}
		
		int[] colors = {Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.DKGRAY};
		int color = (int) Math.floor(Math.random()*5);
		square.getPaint().setColor(colors[color]);
		
		gameobjs.add(square);
		return square;
	}
}

