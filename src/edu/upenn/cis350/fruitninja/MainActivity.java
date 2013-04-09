package edu.upenn.cis350.fruitninja;

import java.io.File;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	public int color;
	public int thickness;
	
	public int scoreNumber;
	public String bgFile;
	public Timer t = new Timer();
	public Timer playTimer = new Timer();
	
	protected String getBgFile(){
		return new File(getFilesDir(), "spaceBg.jpg").getAbsolutePath();
	}
	
	protected int getScoreNumber(){
		return scoreNumber;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onClearButtonClick(View view){
    	SlicingView pbview = (SlicingView)findViewById(R.id.SlicingView);
    	ScoreView sView = (ScoreView)findViewById(R.id.ScoreView);

    	pbview.clear = true;
    	scoreNumber = 000;
    	pbview.invalidate();
    	sView.invalidate();
    }
    
    public void onincSpeedClick(View view){
    	SlicingView pbview = (SlicingView)findViewById(R.id.SlicingView);

    	pbview.incSpeed();
    	pbview.invalidate();
    }
    
    public void ondecSpeedClick(View view){
    	SlicingView pbview = (SlicingView)findViewById(R.id.SlicingView);
    	ScoreView sView = (ScoreView)findViewById(R.id.ScoreView);

    	pbview.decSpeed();
    	pbview.invalidate();
    	sView.invalidate();
    }
    
    public void onincSizeClick(View view){
    	SlicingView pbview = (SlicingView)findViewById(R.id.SlicingView);

    	pbview.incSize();
    	pbview.invalidate();
    }
    
    public void ondecSizeClick(View view){
    	SlicingView pbview = (SlicingView)findViewById(R.id.SlicingView);

    	pbview.decSize();
    	pbview.invalidate();
    }
    
    public void onMainMenuClick(View view){
    	updateScore();
    	updateTime();
    	Intent i = new Intent();
    	
    	// put the number of clicks into the Intent
    	//i.putExtra(�NUM_CLICKS", num_clicks);
    	setResult(RESULT_OK, i);
    	// ends this Activity
    	finish(); 

    }
    
    protected void onStop(){
        super.onStop();
        updateScore();
        updateTime();
     }
    
    protected void updateScore(){
    	 // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        int currHighScore = settings.getInt("highScore", 0);
        if (scoreNumber > currHighScore){
     	   editor.putInt("highScore", scoreNumber);
        	
     	   // Commit the edits!
     	   editor.commit();
        }   
    }
    
    protected void updateTime(){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        int oldPlayTime = settings.getInt("playTime", 0);
        System.out.println("Play time: " + playTimer.getElapsedTime());
     	editor.putInt("playTime", (int)(oldPlayTime + playTimer.getElapsedTime()) );	
 	   // Commit the edits!
 	   editor.commit(); 
    }
}
