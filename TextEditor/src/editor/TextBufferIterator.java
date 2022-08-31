package editor;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TextBufferIterator implements Iterator<BufferNode> {
	BufferNode sentinel;
	BufferNode next;
	
	public TextBufferIterator(BufferNode sentinel) {
		this.sentinel = sentinel;
		this.next = sentinel.getNext();
	}
	
	@Override
	public boolean hasNext() {
		return !next.isSentinel();
	}

	@Override
	public BufferNode next() {
		if (!hasNext()) throw new NoSuchElementException("No next element.");
		BufferNode temp = next;
		this.next = next.getNext();
		return temp;
	}

}
