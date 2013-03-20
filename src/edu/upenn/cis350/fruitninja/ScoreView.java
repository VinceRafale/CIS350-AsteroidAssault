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
		square = new ShapeDrawable(new RectShape());
		square.setBounds(0,0,0+30,0+30);
		square1 = new ShapeDrawable(new RectShape());
		square1.setBounds(30,0,30+30,0+30);
		square2 = new ShapeDrawable(new RectShape());
		square2.setBounds(60,0,60+30,0+30);
		square3 = new ShapeDrawable(new RectShape());
		square3.setBounds(90,0,90+30,0+30);
		square4 = new ShapeDrawable(new RectShape());
		square4.setBounds(120,0,120+30,0+30);
		
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
		
		m.setColor(Color.BLACK);
		m.setThickness(2);
	}
	
	protected void onDraw(Canvas canvas){
		square.getPaint().setColor(Color.RED);
		square.draw(canvas);
		square1.getPaint().setColor(Color.YELLOW);
		square1.draw(canvas);
		square2.getPaint().setColor(Color.GREEN);
		square2.draw(canvas);
		square3.getPaint().setColor(Color.BLUE);
		square3.draw(canvas);
		square4.getPaint().setColor(Color.BLACK);
		square4.draw(canvas);
		
		String scoreValue = Integer.toString(m.scoreNumber);
		
		canvas.drawText("thin", 160, 30, thin);
		canvas.drawText("THICK", 200, 30, thick);
		canvas.drawText("Score: " + scoreValue, 160, 60, scoreLabel);		
	}
	
	public boolean onTouchEvent(MotionEvent e){
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int)e.getX();
			int y = (int)e.getY();
			if (x > 0 && x < 30 && y > 0 && y < 30){
				Log.v("tag", "RED"); 
				m.setColor(Color.RED);
				return true;
			}else if (x > 30 && x < 60 && y > 0 && y < 30){
				Log.v("tag", "YELLOW");
				m.setColor(Color.YELLOW);
				return true;
			}else if (x > 60 && x < 90 && y > 0 && y < 30){
				Log.v("tag", "GREEN");
				m.setColor(Color.GREEN);
				return true;
			}else if (x > 90 && x < 120 && y > 0 && y < 30){
				Log.v("tag", "BLUE");
				m.setColor(Color.BLUE);
				return true;
			}else if (x > 120 && x < 150 && y > 0 && y < 30){
				Log.v("tag", "BLACK");
				m.setColor(Color.BLACK);
				return true;
			}else if (x > 160 && x < 200 && y > 0 && y < 30){
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
