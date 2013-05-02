package edu.upenn.cis350.fruitninja;

import java.util.ArrayList;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends Activity {
	// request code used in creating the new Activity
	public static final int MainActivity_ID = 1;
	public static final int LeaderboardActivity_ID = 4;
	
	public static final String PREFS_NAME = "MyPrefsFile";
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		getPlayTime();
		//Background Music for main screen
		MediaPlayer menuBackgroundMusic = MediaPlayer.create(MenuActivity.this, R.raw.fruitninjamusic);
		menuBackgroundMusic.start();
	}     
	 
 	public void onStartButtonClick(View v) {
 	    // create an Intent using the current Activity and the Class to be created
 	    Intent i = new Intent(this, MainActivity.class);
 	    // pass the Intent to the Activity, using the specified request code
 	    startActivityForResult(i, MainActivity_ID);
	} 	
 	
 	public void onLeaderboardClick(View v) {
 	    // create an Intent using the current Activity and the Class to be created
 	    Intent i = new Intent(this, LeaderboardActivity.class);
 
 	    // pass the Intent to the Activity, using the specified request code
 	    startActivityForResult(i, LeaderboardActivity_ID);
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
 				getPlayTime();
 			break;
 		}  
 	}
}
