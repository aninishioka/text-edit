package userInterface;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ScrollBar {
	private final Rectangle scrollBar;
	private final double INIT_Y = 0;
	private final double WIDTH = 5;
	private final double INIT_HEIGHT = 30;
	private Scene scene;
	private int value = 0;
	
	//*** should change this so don't need to pass in stage, scene.
	public ScrollBar(Scene scene) {
		this.scrollBar = new Rectangle(WIDTH, INIT_HEIGHT);
		this.scene = scene;
		initScrollBar();
	}
	
	private void initScrollBar() {
		scrollBar.setLayoutX(Math.ceil(scene.getWidth() - this.WIDTH));
		scrollBar.setLayoutY(INIT_Y);
		scrollBar.setArcHeight(8);
		scrollBar.setArcWidth(8);
		scrollBar.setFill(Color.DARKGRAY);
	}
	
	//***should be able to get down to just 2 conditions by using math.min & math.max
	public void setYPos(double yPos) {
		if (scrollBar.getY() > yPos) {
			scrollBar.setTranslateY(Math.max(0, yPos));
		} else {
			scrollBar.setTranslateY(Math.min(Math.ceil(scene.getHeight()-scrollBar.getHeight()), yPos));
		}
		/*if (outOfBounds(yPos)) {
			if (scrollBar.getY() > yPos) scrollBar.setTranslateY(0);
			else scrollBar.setTranslateY(Math.ceil(scene.getHeight()-scrollBar.getHeight()));
		} else {
			scrollBar.setTranslateY(yPos);
		}*/
	}
	
	public void alignRight(double windowWidth) {
		double xPos = Math.ceil(scene.getWidth()-this.WIDTH);
		scrollBar.setTranslateX(xPos - scrollBar.getLayoutX());
	}
	
	/*private boolean outOfBounds(double yPos) {
		return Math.ceil(yPos + scrollBar.getHeight()) > scene.getHeight() || yPos < 0;
	}*/
	
	public double getY() {
		return scrollBar.getTranslateY();
	}
	
	public Rectangle getBar() {
		return scrollBar;
	}
	
	public void setValue() {
		
	}
	
	public double getValue() {
		return value;
	}
	
	public void updateValue() {
		
	}
	
	public void setHeight(double height) {
		scrollBar.setHeight(height);
	}
	
	public void setVisibility(boolean b) {
		scrollBar.setVisible(b);
	}
	
	public boolean getVisibility() {
		return scrollBar.isVisible();
	}
	
}
