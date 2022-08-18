package userInterface;

import java.util.LinkedList;
import java.util.List;
import editor.EditHistory;
import editor.EditType;
import editor.HistoryNode;
import editor.TextBuffer;
import editor.TextBufferIterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UserInterface {
	final private Stage stage;
	final private Group root;
	final private Group textRoot;
	final private Scene scene;
	final private Cursor cursor;
	final private TextBuffer tb;
	final private static double INITIAL_WIDTH = 500;
	final private static int INITIAL_HEIGHT = 500;
	final private static int INITIAL_FONT_SIZE = 12;
	final private static int X_MARGIN = 5;
	final private static int Y_MARGIN = 0;
	final private static String INITIAL_FONT_NAME = "Verdana";
	private double width = INITIAL_WIDTH;
	private double height = INITIAL_HEIGHT;
	private Font font = new Font(INITIAL_FONT_NAME, INITIAL_FONT_SIZE);
	private String fontName = INITIAL_FONT_NAME;
	private int fontSize = INITIAL_FONT_SIZE;
	private final EditHistory history;
	private final ScrollBar scrollBar;
	private boolean scrollEngaged = false;
	
	
	public UserInterface(Stage stage, TextBuffer tb) {
		this.stage = stage;
		this.root = new Group();
		this.scene = new Scene(root, width, height);
		this.textRoot = new Group();
		this.cursor = initCursor();
		this.tb = tb;
		this.scrollBar = initScrollBar();
		this.history = new EditHistory();
		
		initInterface();
		
	}
	
	private void initInterface() {
		drawBackground();
		drawCursor();
		drawText();
		drawScrollBar();
		setEventHandlers();
		stage.show();
	}

	private void drawBackground() {
		stage.setScene(scene);
	}
	
	private void drawText() {
		root.getChildren().add(textRoot);
		textRoot.setLayoutX(X_MARGIN);
	}
	
	private Cursor initCursor() {
		return new Cursor(Utils.getFontHeight(font));
	}
	
	private void drawCursor() {
		root.getChildren().add(cursor.getCursor());
	}
	
	private void drawScrollBar() {
		root.getChildren().add(scrollBar.getBar());
		setScrollBar();
	}

	private ScrollBar initScrollBar() {
		return new ScrollBar(scene);
	}
	
	private void setEventHandlers() {
		scene.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				handleTextInputEvent(event);
				event.consume();
			}
			
		});
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				handleSpecialKeyEvent(event);
				event.consume();
			}
			
		});
		
		scrollBar.getBar().addEventHandler(MouseEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				scrollEngaged = true;
				event.consume();
			}
			
		});
		
		scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (scrollEngaged) {
					scrollBar.setYPos(event.getY());
				}
				event.consume();
			}
			
		});
		
		//add functionality for moving cursor with mouse
		scrollBar.getBar().addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (scrollEngaged) {
					scrollEngaged = false;
				} else {
					
				}
				event.consume();
			}
			
		});
		
		stage.heightProperty().addListener(windowResizeListener);
		stage.widthProperty().addListener(windowResizeListener);
		
	}
	
	public void handleTextInputEvent(KeyEvent event) {
		if (event.getCharacter().length() == 1 && !event.isShortcutDown()) {
			Text t = new Text(event.getCharacter());
			
			history.recordEdit(EditType.ADD_CHAR, t);
			history.clearRedoHistory();
			
			addText(t);
			updateView();
		} 
	}
	
	public void addText(Text t) {
		t.setTextOrigin(VPos.TOP);
		t.setFont(font);
		
		tb.addChar(t);
		textRoot.getChildren().add(t);
	}
	
	
	private void setText() {
		TextBufferIterator iterator = tb.iterator();
		double curX = X_MARGIN;
		double curY = Y_MARGIN;
		List<Text> word = new LinkedList<>();
		double wordWidth = 0;
		
		while (iterator.hasNext()) {
			Text t = iterator.next().getValue();
			
			if (t.getText().charAt(0) == ' ' || t.getText().charAt(0) == '\r') {
				
				if (t.getText().charAt(0) == ' ') {
					t.setX(curX);
					t.setY(curY);
					curX += Math.ceil(Utils.getTextWidth(t));
				} else if (t.getText().charAt(0) == '\r') {
					curX = X_MARGIN;
					curY += Utils.getFontHeight(font);
					t.setX(curX);
					t.setY(curY);
				}

				word.clear();
				wordWidth = 0;
			} else {
				wordWidth += Math.ceil(Utils.getTextWidth(t));
				word.add(t);
				
				if (!fitsCurLine(curX, Utils.getTextWidth(t)) && wordWidth <= getMaxTextX() && word.get(0).getX() != X_MARGIN) {
					curX = X_MARGIN;
					curY += Math.ceil(Utils.getFontHeight(font));
					
					for (Text c : word) {
						c.setX(curX);
						c.setY(curY);
						curX += Math.ceil(Utils.getTextWidth(c));
					}
				} else {
					if (!fitsCurLine(curX, Utils.getTextWidth(t))) {
						curX = X_MARGIN;
						curY += Math.ceil(Utils.getFontHeight(font));
					}
					t.setX(curX);
					t.setY(curY);
					
					curX += Math.ceil(Utils.getTextWidth(t));
				}
			}
		}
		
	}
	
	private boolean fitsCurLine(double curX, double textWidth) {
		return curX + textWidth <= getMaxTextX();
	}
	
	public void setCursor() {
		if (tb.isEmpty()) {
			cursor.updatePos(X_MARGIN, Y_MARGIN);
			return;
		}
		Text t = tb.getCurPos().getValue();
		cursor.updatePos(t.getX()+Utils.getTextWidth(t), t.getY());
	}

	public void handleSpecialKeyEvent(KeyEvent event) {
		if (event.getCode() == KeyCode.BACK_SPACE) {
			handleBackspace(event);
			history.clearRedoHistory();
		} else if (event.isShortcutDown()) {
			handleShortCut(event);
		} else if (event.getCharacter().length() == 0) {
			handleArrowInputs(event);
		}
	}
	
	public void handleShortCut(KeyEvent event) {
		KeyCombination incFont = new KeyCodeCombination(KeyCode.ADD, KeyCombination.META_DOWN);
		KeyCombination incFont60Keeb = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.META_DOWN);
		KeyCombination decFont = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.META_DOWN);
		KeyCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCombination.META_DOWN);
		KeyCombination redo = new KeyCodeCombination(KeyCode.Y, KeyCombination.META_DOWN);
		KeyCombination copy = new KeyCodeCombination(KeyCode.C, KeyCombination.META_DOWN);
		KeyCombination paste = new KeyCodeCombination(KeyCode.V, KeyCombination.META_DOWN);
		KeyCombination cut = new KeyCodeCombination(KeyCode.X, KeyCombination.META_DOWN);
		
		if (incFont.match(event) || incFont60Keeb.match(event)) {
			changeFontSize("inc");
		} else if (decFont.match(event)) {
			changeFontSize("dec");
		} else if (undo.match(event)) {
			handleUndo();
		} else if (redo.match(event)) {
			handleRedo();
		} else if (copy.match(event) || cut.match(event)) {
			
		} else if (paste.match(event)) {
			
		}
	}
	
	private void changeFontSize(String incOrDec) {
		if (incOrDec == "inc") fontSize += 4;
		else if (incOrDec == "dec" && fontSize > 4) fontSize -= 4;
		
		font = new Font(fontName, fontSize);
		
		TextBufferIterator iterator = tb.iterator();
		
		while (iterator.hasNext()) {
			Text t = iterator.next().getValue();
			t.setFont(font);
		}
		updateView();
		setScrollBar();
		cursor.setHeight(Utils.getFontHeight(font));
	}
	
	public void handleBackspace(KeyEvent event) {
		if (textRootEmpty()) return;
		deleteText();
		Text toDelete = tb.getCurTextObject();
		updateView();
		history.recordEdit(EditType.DEL_CHAR, toDelete);
	}
	
	public void deleteText() {
		if (textRootEmpty()) return;
		
		Text toDelete = tb.getCurTextObject();
		tb.delChar();
		
		textRoot.getChildren().remove(toDelete);
	}
	
	public void handleUndo() {
		if (history.undoHistoryEmpty()) return;
		HistoryNode action = history.undo();
		if (action.getType() == EditType.ADD_CHAR) {
			if (textRootEmpty()) return;
			deleteText();
			history.recordUndo(action);
		} else if (action.getType() == EditType.DEL_CHAR) {
			Text t = action.getText();
			addText(t);
		}
		updateView();
	}
	
	public void handleRedo() {
		if (history.redoHistoryEmpty()) return;
		
		HistoryNode action = history.redo();
		
		if (action.getType() == EditType.ADD_CHAR) {
			Text t = action.getText();
			addText(t);
		} else if (action.getType() == EditType.DEL_CHAR) {
			deleteText();
		}
		updateView();
 	}
	
	public boolean textRootEmpty() {
		return textRoot.getChildren().size() == 0;
	}
	
	public void handleArrowInputs(KeyEvent event) {
		if (event.getCode() == KeyCode.RIGHT) {
			
		} else if (event.getCode() == KeyCode.LEFT) {
			
		} else if (event.getCode() == KeyCode.UP) {
			
		} else if (event.getCode() == KeyCode.DOWN) {
			
		}
	}
	
	private void updateView() {
		setText();
		setCursor();
		setScrollBar();
		
	}
	
	private void setScrollBar() {
		setScrollBarVisibility();
		resizeScrollBar();
	}
	
	private void setScrollBarVisibility() {
		if (tb.isEmpty() || getTextRootSize() <= scene.getHeight()) {
			scrollBar.setVisibility(false);
		} else {
			scrollBar.setVisibility(true);
		}
	}
	
	private void resizeScrollBar() {
		if (!scrollBar.getVisibility()) return;
		double scrollBarSize = Math.ceil(scene.getHeight() / getTextRootSize() * scene.getHeight());
		scrollBar.setHeight(scrollBarSize);
	}
	
	ChangeListener<Number> windowResizeListener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
			if (oldVal.toString() == "NaN") return;
			resizeWindow();
			updateView();
			scrollBar.alignRight(newVal.doubleValue());
		}
	};
	
	private void resizeWindow() {
		this.height = scene.getHeight();
		this.width = scene.getWidth();
	}
	
	
	private double getMaxTextX() {
		return this.width - (X_MARGIN*2);
	}
	
	private double getTextRootSize() {
		if (tb.isEmpty()) return 0;
		else return tb.getLast().getValue().getY() + Utils.getFontHeight(font);
	}
}
