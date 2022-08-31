package userInterface;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Cursor {
	public final Rectangle cursor;
	public final static int WIDTH = 1;
	public double height;
	public final double INIT_X = 0;
	public final double INIT_Y = 0;

	
	public Cursor(double height) {
		cursor = new Rectangle(WIDTH, height);
		this.height = height;
		
		initPos();
		initAnimation();
	}
	
	private void initPos() {
		cursor.setX(INIT_X);
		cursor.setY(INIT_Y);
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
		return cursor.getTranslateX();
	}
	
	public double getYPos() {
		return cursor.getTranslateY();
	}
	
	public void updatePos(double x, double y) {
		cursor.setTranslateX(x);
		cursor.setTranslateY(y);
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public void setHeight(double height) {
		cursor.setHeight(height);
		this.height = height;
	}
}
