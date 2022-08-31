package editor;

import javafx.scene.text.Text;

public class BufferNode {
		private Text value;
		private String string;
		private BufferNode prev;
		private BufferNode next;
		private boolean isDummy = false;
		private boolean isSentinel = false;
		
		public BufferNode(Text value) {
			this.value = value;
			this.string = value.getText();
		}
		
		public BufferNode(Text value, boolean isDummy) {
			this.value = value;
			this.isDummy = isDummy;
		}
		
		public void setAsSentinel() {
			this.isSentinel = true;
		}
		
		public Text getTextObject() {
			return this.value;
		}
		
		public double getX() {
			return this.value.getX();
		}
		
		public double getY() {
			return this.value.getY();
		}
		
		public BufferNode getPrev() {
			return prev;
		}
		
		public boolean hasNext() {
			return !next.isSentinel;
		}
		
		public BufferNode getNext() {
			return next;
		}
		
		public void setPrev(BufferNode p) {
			prev = p;
		}
		
		public void setNext(BufferNode n) {
			next = n;
		}
		
		public String getString() {
			return this.value.getText();
		}
		
		public boolean isDummy() {
			return this.isDummy;
		}
		
		public boolean isSentinel() {
			return this.isSentinel;
		}
	}