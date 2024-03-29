package build;

import java.io.Serializable;

import editor.TextBuffer;
import javafx.scene.text.Font;

public class FileData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final TextBuffer tb;
	private final Font font;
	
	public FileData(TextBuffer tb, Font font) {
		this.tb = tb;
		this.font = font;
	}
	
	public TextBuffer getTextBuffer() {
		return tb;
	}
	
	public Font getFont() {
		return font;
	}
}
