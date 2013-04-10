package edu.upenn.cis350.fruitninja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreScreenActivity extends Activity {

	public static final int MenuActivity_ID = 3;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		
		Bundle extras = getIntent().getExtras();
		int misses = (Integer)extras.get("MISSES");
		int hits = (Integer)extras.get("HITS");
		double time = (Double)extras.get("PLAYTIME");
		TextView hitratio = (TextView)findViewById(R.id.hits);
		hitratio.setText("Hits/Misses: " + hits + "/" + misses);
		TextView hitpercent = (TextView)findViewById(R.id.hitsPercent);
		hitpercent.setText("Hits/Total(%): " + (hits/(misses+hits) * 100) + "%" );
		TextView playtime = (TextView)findViewById(R.id.levelTime);
		playtime.setText("Time taken to complete level: " + (int)(time/60) + " minutes " + Math.round(time%60) + " seconds" );
	}     
	
    public void onMainMenuClick(View view){
 	    // create an Intent using the current Activity 
	    // and the Class to be created
 	    Intent i = new Intent(this, MenuActivity.class);
 
 	    // pass the Intent to the Activity, 
 	    // using the specified request code
 	    startActivityForResult(i, MenuActivity_ID);
    }
	
}
