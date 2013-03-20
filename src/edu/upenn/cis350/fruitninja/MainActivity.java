package edu.upenn.cis350.fruitninja;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	public int color;
	public int thickness;
	
	public int scoreNumber;
	
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

    	pbview.clear = true;
    	pbview.invalidate();
    }
    
    public void onincSpeedClick(View view){
    	SlicingView pbview = (SlicingView)findViewById(R.id.SlicingView);

    	pbview.incSpeed();
    	pbview.invalidate();
    }
    
    public void ondecSpeedClick(View view){
    	SlicingView pbview = (SlicingView)findViewById(R.id.SlicingView);

    	pbview.decSpeed();
    	pbview.invalidate();
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
    
    public void setColor(int c){
    	color = c;
    }
    
    public int getColor(){
    	return color;
    }
    
    public void setThickness(int t){
    	thickness = t;
    }
    
    public int getThickness(){
    	return thickness;
    }
    
}
