package edu.upenn.cis350.fruitninja;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends Activity {
	// request code used in creating the new Activity
	public static final int MainActivity_ID = 1;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}     
	 
 	public void onStartButtonClick(View v) {
 	    // create an Intent using the current Activity 
	    // and the Class to be created
 	    Intent i = new Intent(this, MainActivity.class);
 
 	    // pass the Intent to the Activity, 
 	    // using the specified request code
 	    startActivityForResult(i, MainActivity_ID);
	 } 	
		

}
