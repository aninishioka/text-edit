package tests;

import editor.TextBuffer;
import editor.BufferNode;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextBufferTests {
	private static TextBuffer tb;
	private static BufferNode sentinel;
	
	@BeforeEach
	public void setUp() {
		tb = new TextBuffer();
		sentinel = tb.getSentinel();
	}

	//Sentinel's next and previous pointers should point to itself on initialization.
	@Test
	void Initialize_textbuffer_sanity_check() {
		assertEquals(sentinel.getValue(), null);
		assertEquals("Sentinel.next should point at self", sentinel.getNext(), sentinel);
		assertEquals("Sentinel.prev should point at self",sentinel.getPrev(), sentinel);
		assertEquals("CurPos should be sentinel", tb.getCurPos(), sentinel);
	}
	
	//Calling addChar should:
	//1. set new node's next to node curPos's next was previously pointing to
	//2. set next pointer of curPos to point to our new node
	//3. set new node's previous to curPos
	//4. change curPos's prev to new node iff curPos is sentinel. otherwise, should be unchanged.
	//5. set curPos to new node.
	@Test
	void Add_single_char_to_length_zero_textbuffer() {
		tb.addChar("a");
		
		BufferNode cur = tb.getCurPos();
		
		assertEquals(cur.getValue(), "a");
		assertEquals(cur.getPrev(), sentinel);
		assertEquals(cur.getNext(), sentinel);
		assertEquals(sentinel.getPrev(), cur);
		assertEquals(sentinel.getNext(), cur);
		
	}
	
	@Test
	void Delete_single_char_from_length_one_textbuffer() {
		tb.addChar("a");
		tb.delChar();
		
		BufferNode cur = tb.getCurPos();
		
		assertEquals("CurPos should be sentinel", cur, sentinel);
		assertEquals("Sentinel.next should point at self", sentinel.getNext(), sentinel);
		assertEquals("Sentinel.prev should point at self",sentinel.getPrev(), sentinel);
	}
	
	@Test
	void Delete_single_char_from_length_zero_textbuffer() {
		tb.delChar();
		
		BufferNode cur = tb.getCurPos();
		
		assertEquals("CurPos should be sentinel", cur, sentinel);
		assertEquals("Sentinel.next should point at self", sentinel.getNext(), sentinel);
		assertEquals("Sentinel.prev should point at self",sentinel.getPrev(), sentinel);
	}
	
	 @Test
	 void Add_char_to_middle_of_textbuffer() {
		 tb.addChar("a");
		 BufferNode a = tb.getCurPos();
		 tb.addChar("b");
		 BufferNode b = tb.getCurPos();
		 tb.addChar("c");
		 BufferNode c = tb.getCurPos();
		 
		 tb.setCurPos(b);
		 tb.addChar("d");
		 BufferNode d = tb.getCurPos();
		 
		 BufferNode[] order = {sentinel,a,b,d,c,sentinel};
		 
		 BufferNode runner = sentinel.getNext();
		 
		 for (int i = 1; i < order.length-1; i++) {
			 assertEquals(runner, order[i]);
			 
			 assertEquals(runner.getPrev(), order[i-1]);
			 
			 assertEquals(runner.getNext(), order[i+1]);
			 
			 runner = runner.getNext();
		 }
		 
	 }

	 @Test
	 void Delete_char_from_middle_of_textbuffer() {
		 tb.addChar("a");
		 BufferNode a = tb.getCurPos();
		 tb.addChar("b");
		 BufferNode b = tb.getCurPos();
		 tb.addChar("c");
		 BufferNode c = tb.getCurPos();
		 
		 tb.setCurPos(b);
		 tb.delChar();
		 
		 BufferNode[] order = {sentinel,a,c,sentinel};
		 
		 BufferNode runner = sentinel.getNext();
		 
		 for (int i = 1; i < order.length-1; i++) {
			 assertEquals(runner, order[i]);

			 assertEquals(runner.getPrev(), order[i-1]);
			 
			 assertEquals(runner.getNext(), order[i+1]);
			 
			 runner = runner.getNext();
		 }
	 }
}
