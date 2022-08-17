package userInterface;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ScrollBar {
	private final Rectangle scrollBar;
	private double x;
	private double y = 0;
	private final double width = 5;
	private double height = 30;
	private Scene scene;
	private int value = 0;
	
	//*** should change this so don't need to pass in stage, scene.
	public ScrollBar(Scene scene, Group textRoot) {
		this.scrollBar = new Rectangle(width, height);
		this.x = Math.ceil(scene.getWidth() - this.width);
		this.scene = scene;
		initScrollBar();
	}
	
	private void initScrollBar() {
		scrollBar.setLayoutX(x);
		scrollBar.setLayoutY(y);
		scrollBar.setArcHeight(8);
		scrollBar.setArcWidth(8);
		scrollBar.setFill(Color.DARKGRAY);
	}
	
	public void setYPos(double yPos) {
		if (outOfBounds(yPos)) {
			if (this.y > yPos) scrollBar.setTranslateY(0);
			else scrollBar.setTranslateY(Math.ceil(scene.getHeight()-this.height));
		} else {
			scrollBar.setTranslateY(yPos);
		}
	}
	
	public void alignRight(double windowWidth) {
		double xPos = Math.ceil(scene.getWidth()-this.width);
		scrollBar.setTranslateX(xPos - scrollBar.getLayoutX());
		this.x = xPos;
	}
	
	private boolean outOfBounds(double yPos) {
		return Math.ceil(yPos + this.height) > scene.getHeight() || yPos < 0;
	}
	
	public double getY() {
		return this.y;
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
		this.height = height;
	}
	
}
