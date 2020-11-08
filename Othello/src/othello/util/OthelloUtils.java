package othello.util;

import java.util.Arrays;
import othello.model.Player;

public class OthelloUtils {
	public static Player p1 = new Player(1, "백");
	public static Player p2 = new Player(2, "흑");
	
	public static int [][] othelloTable = {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 2, 0, 0, 0},
			{0, 0, 0, 2, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0} };
	
	public static boolean [][] inputTable = {
			{false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false},
			{false, false, false, false, false, false, false, false} };
	
	public static void initInputTable() {
		for(boolean [] row : inputTable)
			Arrays.fill(row, false);
	}
	
	public static Player takeTurn(int turn) {
		return turn == 0 ? p1 : p2;
	}
}
