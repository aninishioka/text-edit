package editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.text.Text;

public class TextBuffer implements Iterable<BufferNode> {
	private BufferNode sentinel;
	private BufferNode curPos;
	private List<BufferNode> lines;
	
	
	public TextBuffer() {
		sentinel = new BufferNode(null);
		sentinel.setPrev(sentinel);
		sentinel.setNext(sentinel);
		
		curPos = sentinel;
		lines  = new ArrayList<>();
	}
	
	public void addChar(Text t) {
		BufferNode newNode = new BufferNode(t);
		
		BufferNode cur = getCurPos();
		
		BufferNode p = cur;
		BufferNode n = cur.getNext();
		
		
		newNode.setPrev(p);
		newNode.setNext(n);
		
		cur.setNext(newNode);
		n.setPrev(newNode);
		
		setCurPos(newNode);
	}
	
	public void delChar() {
		BufferNode cur = getCurPos();
		
		if (cur == sentinel) return;
		
		BufferNode p = cur.getPrev();
		BufferNode n = cur.getNext();
		p.setNext(n);
		n.setPrev(p);
		
		setCurPos(p);
	}
	
	public BufferNode getCurPos() {
		return curPos;
	}
	
	public Text getCurTextObject() {
		return curPos.getValue();
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
		return lines.size();
	}
	
	public void addNewLines(BufferNode n) {
		lines.add(n);
	}
	
	public void clearLines() {
		lines.clear();
	}
}
