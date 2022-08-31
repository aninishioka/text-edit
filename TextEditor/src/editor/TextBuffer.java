package editor;

import java.util.ArrayList;

import java.util.List;

import javafx.geometry.VPos;
import javafx.scene.text.Text;

public class TextBuffer implements Iterable<BufferNode> {
	private BufferNode sentinel;
	private BufferNode curPos;
	private List<BufferNode> linePointers;
	
	
	public TextBuffer() {
		sentinel = new BufferNode(new Text());
		sentinel.setPrev(sentinel);
		sentinel.setNext(sentinel);
		sentinel.setAsSentinel();
		
		curPos = sentinel;
		linePointers  = new ArrayList<>();
		linePointers.add(sentinel);
	}
	
	public void addNode(BufferNode newNode, BufferNode prev) {
		BufferNode next = prev.getNext();
		
		newNode.setPrev(prev);
		newNode.setNext(next);
		
		prev.setNext(newNode);
		next.setPrev(newNode);
	}
	
	public void addChar(Text t) {
		BufferNode newNode = new BufferNode(t);
		addNode(newNode, getCurPos());
		setCurPos(newNode);
	}
	
	public void addChar(char c) {
		addChar(new Text(""+c));
	}
	
	public void delChar() {
		BufferNode cur = getCurPos();
		BufferNode p = cur.getPrev();
		delChar(cur);
		setCurPos(p);
	}
	
	public void delChar(BufferNode bn) {
		BufferNode p = bn.getPrev();
		BufferNode n = bn.getNext();
		p.setNext(n);
		n.setPrev(p);
	}
	
	public BufferNode getCurPos() {
		return curPos;
	}
	
	public Text getCurTextObject() {
		return curPos.getTextObject();
	}
	
	public void setCurPos(BufferNode node) {
		curPos = node;
	}
	
	//for testing purposes
	public BufferNode getSentinel() {
		return sentinel;
	}
	
	public boolean isEmpty() {
		return sentinel.getNext() == sentinel;
	}
	
	public BufferNode getFirst() {
		if (isEmpty()) return null;
		else return sentinel.getNext();
	}
	
	public BufferNode getLast() {
		if (isEmpty()) return null;
		else return sentinel.getPrev();
	}

	@Override
	public TextBufferIterator iterator() {
		return new TextBufferIterator(sentinel);
	}
	
	public int getNumLines() {
		return linePointers.size();
	}
	
	public void addNewLinePointer(BufferNode n) {
		if (n.getString().length() == 0) return;
		if (n.getString().charAt(0) != ' ' && n.getString().charAt(0) != '\r') {
			BufferNode dummy = new BufferNode(new Text(""), true);
			
			Text t = dummy.getTextObject();
			t.setTextOrigin(VPos.TOP);
			t.setX(n.getX());
			t.setY(n.getY());
			
			addNode(dummy, n.getPrev());
		
			linePointers.add(dummy);
		} else {
			linePointers.add(n);
		}
	}
	
	public void clearLinePointers() {
		linePointers.clear();
		linePointers.add(sentinel);
	}
	
	public void moveCurPosRight() {
		if (this.getCurPos() == this.getLast()) return;
		else this.setCurPos(this.getCurPos().getNext());
	}
	
	public void moveCurPosLeft() {
		if (this.curIsSentinel()) return;
		else this.setCurPos(this.getCurPos().getPrev());
	}
	
	public boolean curIsSentinel() {
		return this.getCurPos() == this.sentinel;
	}
	
	public void setCurToSentinel() {
		setCurPos(sentinel);
	}
	
	public List<BufferNode> getLinePointers() {
		return this.linePointers;
	}
	
	public BufferNode getLinePointer(int lineNum) {
		if (isEmpty()) return null;
		return this.linePointers.get(lineNum);
	}
}
