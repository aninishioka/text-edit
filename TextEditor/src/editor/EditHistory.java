package editor;

import java.util.Deque;
import java.util.LinkedList;

import javafx.scene.text.Text;

//*** add capability to to undo/redo changes to font size

public class EditHistory {
	private final Deque<HistoryNode> undoStack;
	private final Deque<HistoryNode> redoStack;
	private final int maxMemory = 100;
	
	public EditHistory() {
		undoStack = new LinkedList<>();
		redoStack = new LinkedList<>();	
	}
	
	public void recordEdit(EditType type, Text t) {
		checkSize(undoStack);
		undoStack.addLast(new HistoryNode(type, t));
	}
	
	public void recordEdit(EditType type, boolean inc) {
		checkSize(undoStack);
		undoStack.addLast(new HistoryNode(type, inc));
	}
	
	public void recordUndo(HistoryNode undo) {
		checkSize(redoStack);
		redoStack.addLast(undo);
	}
	
	private void checkSize(Deque<HistoryNode> stack) {
		if (stack.size() == maxMemory) {
			stack.pollFirst();
		}
	}
	
	public HistoryNode undo() {
		return undoStack.pollLast();
	}
	
	public HistoryNode redo() {
		return redoStack.pollLast();
	}
	
	public boolean undoHistoryEmpty() {
		return undoStack.size() == 0;
	}
	
	public boolean redoHistoryEmpty() {
		return redoStack.size() == 0;
	}
	
	public void clearRedoHistory() {
		redoStack.clear();
	}
}
