package edu.upenn.cis350.fruitninja;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends Activity {
	// request code used in creating the new Activity
	public static final int MainActivity_ID = 1;
	
	public static final String PREFS_NAME = "MyPrefsFile";
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		getHighScore();
		getPlayTime();
	}     
	 
 	public void onStartButtonClick(View v) {
 	    // create an Intent using the current Activity 
	    // and the Class to be created
 	    Intent i = new Intent(this, MainActivity.class);
 
 	    // pass the Intent to the Activity, 
 	    // using the specified request code
 	    startActivityForResult(i, MainActivity_ID);
	 } 	
 	
 	protected void getHighScore(){
 		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		TextView hiScoreView = (TextView)findViewById(R.id.highScore);
		int highScore = settings.getInt("highScore", 0);
		
	    hiScoreView.setText("High Score: " + highScore);
 	}
 	
 	protected void getPlayTime(){
 		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		TextView playTimeView = (TextView)findViewById(R.id.playTime);
		int playTime = settings.getInt("playTime", 0);
	    playTimeView.setText("Total Play Time: " + playTime);
 	}
 	
 	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
 		super.onActivityResult(requestCode, resultCode, intent);
 		// the requestCode lets us know which Activity it was
 		switch(requestCode) {
 			case 1:
 				getHighScore();
 				getPlayTime();
 			break;
 		}  
 	}

		

}
