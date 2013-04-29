package edu.upenn.cis350.fruitninja;

import java.io.File;
import java.util.Arrays;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Chronometer;

public class MainActivity extends Activity {
	
	public static final int ScoreScreenActivity_ID = 2;
	public static final String PREFS_NAME = "MyPrefsFile";
	public int color;
	public int thickness;
	public int hits = 0;
	public int misses = 0;
	public int scoreNumber;
	public int levelNumber;
	public String bgFile;
	public Timer t = new Timer();
	public Timer playTimer = new Timer();
	//public boolean newLevel = false;
	
	protected String getBgFile(){
		return new File(getFilesDir(), "spaceBg.jpg").getAbsolutePath();
	}
	
	protected int getScoreNumber(){
		return scoreNumber;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncTimerTask().execute();
       // Bundle extras = getIntent().getExtras();
		//passedLevel = (Boolean)extras.get("NEWLEVEL");
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
    	//scoreNumber = 000;
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
        int minHighScore = settings.getInt("highScore10", -1);
        if (scoreNumber > minHighScore){
     	   editor.putInt("highScore10", scoreNumber);
        	
     	   // Commit the edits!
     	   editor.commit();
        }   
        sortScores();
    }
    
    //Sorts the scores in the database 
    protected void sortScores(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        int[] highscores = new int[10];
        for(int i = 0; i < 10; i++){
        	highscores[i] = settings.getInt("highScore" + (i+1), 0);
        }
        Arrays.sort(highscores);
        for(int i = 0; i < 10; i++){
        	editor.putInt("highScore" + (i+1), highscores[9-i]);
        }
        editor.commit();
    }
    
    protected void updateTime(){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        int oldPlayTime = settings.getInt("playTime", 0);
     	editor.putInt("playTime", (int)(oldPlayTime + playTimer.getElapsedTime()) );	
 	   // Commit the edits!
 	   editor.commit(); 
    }
    
    public void showScoreScreen(boolean passedLevel) {
    	updateTime();
 	    // create an Intent using the current Activity 
	    // and the Class to be created
 	    Intent i = new Intent(this, ScoreScreenActivity.class);
 	    i.putExtra("PLAYTIME", playTimer.getElapsedTime());
 	    i.putExtra("HITS", hits);
 	    i.putExtra("MISSES", misses);
 	    i.putExtra("PASSED", passedLevel);
 	    // pass the Intent to the Activity, 
 	    // using the specified request code
 	    startActivityForResult(i, ScoreScreenActivity_ID);
	 } 	
    
    public void setColor(int col){
    	color = col;
    }
    
    public void setThickness(int thick){
    	thickness = thick;
    }
    
    //Timer
    class AsyncTimerTask extends AsyncTask<String, Void, String> {
    	
    	protected String doInBackground(String... inputs){
    		String reply = "Loading";
    		return reply;
    		
    	}
    	
    	protected void onPostExecute(String result){
    		Chronometer timer = (Chronometer) findViewById(R.id.timertext);
    		timer.setTextColor(Color.RED);
    		timer.start();
    	}
    	
    }
    
}


