package edu.upenn.cis350.fruitninja;

import java.io.File;
import java.util.Arrays;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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
	public Bitmap[] pictures = new Bitmap[60];
	public Bitmap[] brownpictures = new Bitmap[60];
	public Bitmap[] explosions = new Bitmap[35];
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
       // new AsyncTimerTask().execute();
       // Bundle extras = getIntent().getExtras();
		//passedLevel = (Boolean)extras.get("NEWLEVEL");
        setContentView(R.layout.load);
        Button loading = (Button)findViewById(R.id.loading);
        loading.setEnabled(false);
       // initPics();
   
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                    initPics();
                    return null;
                } 

                @Override
                public void onPostExecute(Void result){
                   /* handler.post(new Runnable(){
                         @Override
                         public void run(){
                             setContentView(R.layout.main);
                         }
                    });*/
                	Button loading = (Button)findViewById(R.id.loading);
                	loading.setEnabled(true);
                    loading.setText("Tap to continue");
                }
           }.execute();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onContinueClick(View view){
    	new AsyncTimerTask().execute();
    	setContentView(R.layout.main);
    	
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
    
    protected void initPics(){
		BitmapFactory.Options o=new BitmapFactory.Options();
		o.inSampleSize = 8;
		o.inDither=false;                     //Disable Dithering mode
		
		String mDrawableName = "asteroidtest";
		int resID = getResources().getIdentifier(mDrawableName , "drawable", this.getPackageName());
		//Decode all the asteroid frames
		for (int i=1; i<=pictures.length; i++){
			if (i<10){
				mDrawableName = "asteroidtest0" + i;
				resID = getResources().getIdentifier(mDrawableName , "drawable", this.getPackageName());
				pictures[i-1] = BitmapFactory.decodeResource(getResources(), resID, o);
			}
			else{
				mDrawableName = "asteroidtest" + i;
				resID = getResources().getIdentifier(mDrawableName , "drawable", this.getPackageName());
				pictures[i-1] = BitmapFactory.decodeResource(getResources(), resID, o);
			}
			mDrawableName = "asteroidbrown" + i;
			resID = getResources().getIdentifier(mDrawableName , "drawable", this.getPackageName());
			brownpictures[i-1] = BitmapFactory.decodeResource(getResources(), resID, o);
			System.out.println(mDrawableName);
		}
		//Decode all explosion frames - brown ones not in yet
		for (int i=16; i<16+explosions.length; i++){
			mDrawableName = "explosion0" + i;
			resID = getResources().getIdentifier(mDrawableName , "drawable", this.getPackageName());
			explosions[i-16] = BitmapFactory.decodeResource(getResources(), resID, o);
			System.out.println(mDrawableName);
		}
	}
    
}


