package editor;

import javafx.scene.text.Text;

public class BufferNode {
		private Text value;
		private String string;
		private BufferNode prev;
		private BufferNode next;
		
		public BufferNode(Text value) {
			this.value = value;
			this.string = value.getText();
		}
		
		public Text getValue() {
			return value;
		}
		
		public BufferNode getPrev() {
			return prev;
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
		
		public String getTextValue() {
			return string;
		}
	}