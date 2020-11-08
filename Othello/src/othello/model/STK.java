package othello.model;

public class STK {
	private Stone head;
	private int count;

	public STK() {
		head = null;
		count = 0;
	}

	public void push(int x, int y) {
		Stone temp = new Stone(x, y);

		temp.setNext(head);
		head = temp;

		++count;
		temp = null;
	}

	public Stone pop() {
		if (count == 0) {
			return null;
		}

		Stone value = head;

		head = head.getNext();

		--count;

		return value;
	}

	public Stone door() {
		return head;
	}

	public boolean isEmpty() {
		return count != 0;
	}

	public int length() {
		return count;
	}

	public void clean() {
		while (head != null)
			head = head.getNext();

		count = 0;
	}
}
