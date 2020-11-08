package othello.model;

public class Stone {
	private int x;
	private int y;
	private Stone next;
	
	public Stone() {
		this.x = 9;
		this.y = 9;
		next = null;
	}
	public Stone(int x, int y) {
		this.x = x;
		this.y = y;
		next = null;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Stone getNext() {
		return next;
	}
	
	public void setNext(Stone next) {
		this.next = next;
	}
	
	public void setX(int x) {
		this.x = x;
	};
	public void setY(int y) {
		this.y = y;
	};
}
