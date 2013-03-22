package edu.upenn.cis350.fruitninja;

import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;

//Class for a GameObject(fruit/sushi/baby/etc) -- maybe later on shapes can be inheriting from this
public class GameObject extends ShapeDrawable {
	
	protected int x; //X coordinate of top left corner
	protected int y; //Y coordinate of top left corner
	protected double xspeed; //Speed in X direction
	protected double yspeed; //Speed in Y direction
	protected int width;  //Width
	protected int height; //Height
	
	public GameObject(int x, int y, int width, int height, int xspeed, int yspeed){
		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.width = width;
		this.height = height;
		this.setBounds(x, y, x + width, y + height);
	}
	
	
	//Changes x and y coordinates by adding speed on every draw call
	@Override
	public void draw(Canvas canvas) {
		x+=xspeed;
		y-=yspeed;
		this.setBounds(x,y,x+width,y+height);
		//canvas.drawBitmap(Bitmap , matrix, paint)
		super.draw(canvas);
	}
	
	//Test intersection between an input point and the object
	public boolean intersect(int pointx, int pointy){
		return (pointx >= x && pointx <= x+width && pointy >= y && pointy <= y+height );
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
