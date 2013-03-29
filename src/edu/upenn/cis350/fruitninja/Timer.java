package edu.upenn.cis350.fruitninja;

public class Timer {

		  private long startTime = 0;
		  private long endTime   = 0;

		  public void start(){
		    this.startTime = System.nanoTime();
		  }

		  public void end() {
		    this.endTime   = System.nanoTime();  
		  }

		  public long getStartTime() {
		    return this.startTime;
		  }

		  public long getEndTime() {
		    return this.endTime;
		  }

		  public long getTotalTime() {
		    return this.endTime - this.startTime;
		  }
		  
		  public double getElapsedTime(){
			  return (double) (System.nanoTime() - this.startTime)/1000000000;
		  }
}
