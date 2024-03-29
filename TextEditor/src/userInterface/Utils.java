package userInterface;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Utils {
	public static double getTextWidth(Text t) {
		return Math.ceil(t.getLayoutBounds().getWidth());
	}
	
	//cursor height based on font height, 
	//which javafx considers the same across all characters of a font
	public static double getFontHeight(Font font) {
		Text t = new Text("a");
		t.setFont(font);
		return Math.ceil(t.getLayoutBounds().getHeight());
	}
}
