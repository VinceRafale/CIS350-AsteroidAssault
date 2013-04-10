package edu.upenn.cis350.fruitninja;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LeaderboardActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaderboard);
		getHighScore();
	}     
 	
 	protected void getHighScore(){
 		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
 		ArrayList<Integer> highscores = new ArrayList<Integer>(10);
 		for(int i=0; i<10; i++){
 			highscores.add(settings.getInt("highScore" + (i+1), 0));
 		}
 		ListView highscoreList = (ListView)findViewById(R.id.highscores);
 		ArrayAdapter<Integer> aa = new ArrayAdapter<Integer>(this,android.R.layout.simple_list_item_1, highscores);
 		highscoreList.setAdapter(aa);

 	}
 	
    public void onMainMenuClick(View view){
    	Intent i = new Intent();
    	
    	// put the number of clicks into the Intent
    	setResult(RESULT_OK, i);
    	// ends this Activity
    	finish(); 

    }
}

