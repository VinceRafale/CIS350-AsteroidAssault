package edu.upenn.cis350.fruitninja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreScreenActivity extends Activity {
	public static final int MainActivity_ID = 1;
	private boolean passedLevel;
	
	public static final int MenuActivity_ID = 3;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		
		Bundle extras = getIntent().getExtras();
		int misses = (Integer)extras.get("MISSES");
		int hits = (Integer)extras.get("HITS");
		double time = (Double)extras.get("PLAYTIME");
		passedLevel = (Boolean)extras.get("PASSED");
		Button nextLevel = (Button)findViewById(R.id.nextLevel);
		if (!passedLevel){
			nextLevel.setText("Retry Level");
		}
		else{
			nextLevel.setText("Next Level");
		}
		TextView hitratio = (TextView)findViewById(R.id.hits);
		hitratio.setText("Hits/Misses: " + hits + "/" + misses);
		TextView hitpercent = (TextView)findViewById(R.id.hitsPercent);
		hitpercent.setText("Hits/Total(%): " + Math.round((double)(hits*100.0/(misses+hits)))  + "%" );
		TextView playtime = (TextView)findViewById(R.id.levelTime);
		playtime.setText("Time taken to complete level: " + (int)(time/60) + " minutes " + Math.round(time%60) + " seconds" );
	}     
	
    public void onMainMenuClick(View view){
 	    // create an Intent using the current Activity 
	    // and the Class to be created
    	
 	    Intent i = new Intent(this, MenuActivity.class);
 	    //Finish current Activity
 	    finish();
 	    // pass the Intent to the Activity, 
 	    // using the specified request code
 	    startActivityForResult(i, MenuActivity_ID);
    }
    
    public void onNextLevelClick(View view){
 	    // create an Intent using the current Activity 
	    // and the Class to be created
 	    Intent i = new Intent(this, MainActivity.class);
 	    i.putExtra("NEWLEVEL", passedLevel);
 
 	    //Finish current activity
 	    finish();
 	    // pass the Intent to the Activity, 
 	    // using the specified request code
 	   //startActivityForResult(i, MainActivity_ID);
    }
	
}
