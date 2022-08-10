package editor;

import javafx.scene.text.Text;

public class HistoryNode {
	private EditType editType;
	private Text text = null;
	private boolean increase = false;
	
	public HistoryNode(EditType type, Text t) {
		this.editType = type;
		this.text = t;
	}
	
	public HistoryNode(EditType type, boolean increase) {
		this.editType = type;
		this.increase = increase;
	}
	
	public EditType getType() {
		return editType;
	}
	
	public Text getText() {
		return text;
	}
 }
