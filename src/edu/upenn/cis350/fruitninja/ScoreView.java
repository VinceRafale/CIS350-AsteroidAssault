package edu.upenn.cis350.fruitninja;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ScoreView extends View{

	public MainActivity m;
	public String[] visualfeedback = {"Good Job!", "Awesome!", "Clutch!", "Well Done!", "Amazing!", "Good Work!",
			  "Fantastic!", "Nice!", "Unstoppable!"};
	
	public ScoreView(Context c){
		super(c);
		m = (MainActivity) c;
		init();
	}

	public ScoreView(Context c, AttributeSet a){
		super(c, a);
		m = (MainActivity) c;
		init();
	}
	
	public Paint paintBrush;
	public int test = 0;
	
	protected Paint encouragement;
	protected Paint scoreLabel;
	
	protected void init(){	

		m.setColor(Color.WHITE);
		m.setThickness(2);

		//visual feedback
		encouragement = new Paint();
		encouragement.setColor(Color.WHITE);
		encouragement.setTextAlign(Paint.Align.LEFT);
		encouragement.setTypeface(Typeface.SANS_SERIF);
		encouragement.setTextSize(20);
		
		//Score (the word "Score: ")
		scoreLabel = new Paint();
		scoreLabel.setColor(Color.RED);
		scoreLabel.setTextAlign(Paint.Align.LEFT);
		scoreLabel.setTypeface(Typeface.SANS_SERIF);
		scoreLabel.setTextSize(25);
	}
	
	protected void onDraw(Canvas canvas){
		//	canvas.drawColor(Color.BLACK);
		String scoreValue = Integer.toString(m.scoreNumber);
		
		int randomIndex = (int) (Math.random()*visualfeedback.length);
		
		canvas.drawText(visualfeedback[randomIndex], 160, 30, encouragement);
		canvas.drawText("Score: " + scoreValue, 160, 60, scoreLabel);		
		//canvas.drawText("Score: " + m.t.getElapsedTime(), 160, 60, scoreLabel);		
	}
	
	public boolean onTouchEvent(MotionEvent e){
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int)e.getX();
			int y = (int)e.getY();
		}
		return false;
	}
	
}
