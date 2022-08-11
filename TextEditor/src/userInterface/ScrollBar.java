package userInterface;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ScrollBar {
	private final Rectangle scrollBar;
	private double x;
	private double y = 0;
	private final double width = 10;
	private double height = 30;
	private Scene scene;
	
	public ScrollBar(Scene scene) {
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
	
	public void setPos(double yPos) {
		if (outOfBounds(yPos)) {
			if (this.y > yPos) scrollBar.setTranslateY(0);
			else scrollBar.setTranslateY(Math.ceil(scene.getHeight()-this.height));
		}
		scrollBar.setTranslateX(x);
		scrollBar.setTranslateY(y);
	}
	
	private boolean outOfBounds(double yPos) {
		return Math.ceil(yPos + this.height) > scene.getHeight();
	}
	
	public double getY() {
		return this.y;
	}
	
	public Rectangle getBar() {
		return scrollBar;
	}
}