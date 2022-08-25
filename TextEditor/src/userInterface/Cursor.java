package userInterface;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Cursor {
	public final Rectangle cursor;
	public final static int width = 1;
	public double height;
	public final double LEFT_RIGHT_MARGIN = 0;
	public final double TOP_BOTTOM_MARGIN = 0;
	public double xPos = LEFT_RIGHT_MARGIN;
	public double yPos = TOP_BOTTOM_MARGIN;

	
	public Cursor(double height) {
		cursor = new Rectangle(width, height);
		this.height = height;
		
		initPos();
		initAnimation();
	}
	
	private void initPos() {
		cursor.setX(LEFT_RIGHT_MARGIN);
		cursor.setY(TOP_BOTTOM_MARGIN);
	}
	
	private void initAnimation() {
		FadeTransition fadeOut = new FadeTransition(Duration.millis(1));
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0);
		
		FadeTransition fadeIn = new FadeTransition(Duration.millis(1));
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1.0);
		
		PauseTransition pause = new PauseTransition(Duration.millis(500));
		PauseTransition pauseTwo = new PauseTransition(Duration.millis(500));
		
		SequentialTransition seq = new SequentialTransition(cursor, fadeIn, pause, fadeOut, pauseTwo);
		seq.setCycleCount(Animation.INDEFINITE);
		
		seq.play();
	}
	
	public Rectangle getCursor() {
		return cursor;
	}
	
	public double getXPos() {
		return xPos;
	}
	
	public double getYPos() {
		return yPos;
	}
	
	public void setPos(double x, double y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	public void updatePos(double x, double y) {
		cursor.setTranslateX(x);
		cursor.setTranslateY(y);
		
		setPos(x, y);
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public void setHeight(double height) {
		cursor.setHeight(height);
		this.height = height;
	}
}
