package othello.model;

public class Player {
	private int player;
	private int stoneCount;
	private String character;
	
	public Player() { }
	
	public Player(int player, String character) {
		this.player = player;
		stoneCount = 2;
		this.character = character;
	}
	//1 or 2
	public int getPlayer() {
		return player;
	}
	//2
	public int getStoneCount() {
		return stoneCount;
	}
	
	public void loseStone(int count) {
		stoneCount -= count;
	}
	
	public void addStone(int count) {
		stoneCount += count;
	}
	
	public String toString() {
		return character;
	}
}
