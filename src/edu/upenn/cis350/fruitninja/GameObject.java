package edu.upenn.cis350.fruitninja;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;

//Class for a GameObject
public class GameObject extends ShapeDrawable {
	
	protected int x; 			//X coordinate of top left corner
	protected int y; 			//Y coordinate of top left corner
	protected double xspeed; 	//Speed in X direction
	protected double yspeed; 	//Speed in Y direction
	protected int width;  		//Width
	protected int height; 		//Height
	protected Bitmap picture;
	protected Bitmap[] pictures;
	protected Bitmap[] explosions;
	protected int index;
	protected boolean exploded;
	
	public GameObject(int x, int y, int width, int height, int xspeed, int yspeed, SlicingView sv){
		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.width = width;
		this.height = height;
		index = (int)(Math.random()*120);
		this.setBounds(x, y, x + width, y + height);
		pictures = new Bitmap[60];
		explosions = new Bitmap[35];
		exploded = false;
	}
	
	//Changes x and y coordinates by adding speed on every draw call
	@Override
	public void draw(Canvas canvas) {
		
		if(exploded){
			System.out.println(index);
			this.setBounds(x,y,x+width,y+height);
			Rect boundRect = new Rect(x,y,x+width,y+height);
			canvas.drawBitmap(explosions[index/2], null, boundRect, this.getPaint());
			if(index < 35) {index++;}
		}
		else{
			x+=xspeed;
			y-=yspeed;
			this.setBounds(x,y,x+width,y+height);
			Rect boundRect = new Rect(x,y,x+width,y+height);
			canvas.drawBitmap(pictures[index/2], null, boundRect, this.getPaint());
			index++;
			if(index >= 120){index=0;}
		}
	}
	
	//Test intersection between an input point and the object
	public boolean intersect(int pointx, int pointy){
		if(!exploded){
			if(pointx >= x && pointx <= x+width && pointy >= y && pointy <= y+height ){
				exploded = true;
				index = 0;
				return true;
			}
			else {
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public void setPicsExps(Bitmap[] pics, Bitmap[] exps){
		pictures = pics;
		explosions = exps;
	}

	public boolean isExploded(){
		return(index >= 35 && exploded);
	}
	
	//Setters for the object fields
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setSpeedX(double xspeed){
		this.xspeed = xspeed;
	}
	
	public void setSpeedY(double yspeed){
		this.yspeed = yspeed;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	//Getters for the object fields
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public double getSpeedX(){
		return xspeed;
	}
	
	public double getSpeedY(){
		return yspeed;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
