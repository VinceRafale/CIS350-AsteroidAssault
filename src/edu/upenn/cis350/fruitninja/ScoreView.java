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
	
	protected ShapeDrawable square;
	protected ShapeDrawable square1;
	protected ShapeDrawable square2;
	protected ShapeDrawable square3;
	protected ShapeDrawable square4;
	
	protected Paint thin;
	protected Paint thick;
	protected Paint scoreLabel;
	
	protected void init(){
		
		//thin
		thin = new Paint();
		thin.setColor(Color.BLACK);
		thin.setTextAlign(Paint.Align.LEFT);
		thin.setTypeface(Typeface.SANS_SERIF);
		thin.setTextSize(20);
		
		//THICK
		thick = new Paint();
		thick.setColor(Color.BLACK);
		thick.setTextAlign(Paint.Align.LEFT);
		thick.setTypeface(Typeface.SANS_SERIF);
		thick.setTextSize(25);
		
		//Score (the word "Score: ")
		scoreLabel = new Paint();
		scoreLabel.setColor(Color.RED);
		scoreLabel.setTextAlign(Paint.Align.LEFT);
		scoreLabel.setTypeface(Typeface.SANS_SERIF);
		scoreLabel.setTextSize(25);
		
		m.setColor(Color.WHITE);
		m.setThickness(2);
	}
	
	protected void onDraw(Canvas canvas){
		canvas.drawColor(Color.BLACK);
		
		String scoreValue = Integer.toString(m.scoreNumber);
		
		canvas.drawText("thin", 160, 30, thin);
		canvas.drawText("THICK", 200, 30, thick);
		canvas.drawText("Score: " + scoreValue, 160, 60, scoreLabel);		
		//canvas.drawText("Score: " + m.t.getElapsedTime(), 160, 60, scoreLabel);		
	}
	
	public boolean onTouchEvent(MotionEvent e){
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int)e.getX();
			int y = (int)e.getY();
			 if (x > 160 && x < 200 && y > 0 && y < 30){
				Log.v("tag", "thin");
				m.setThickness(2);
				return true;
			}else if (x > 200 && x < 270 && y > 0 && y < 30){
				Log.v("tag", "THICK");
				m.setThickness(6);
				return true;
			}
		}
		return false;
	}
	
}
