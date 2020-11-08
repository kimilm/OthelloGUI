package othello.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

public class OthelloUI {
	@FXML
	private final StringProperty turn;
	@FXML
	private final IntegerProperty blackCount;
	@FXML
	private final IntegerProperty whiteCount;
	@FXML
	private final StringProperty  winner; 
	
	public OthelloUI() {
		turn = new SimpleStringProperty("백");
		blackCount = new SimpleIntegerProperty(2);
		whiteCount = new SimpleIntegerProperty(2);
		winner = new SimpleStringProperty("");
	}

	public void setTurn(String turn) {
		this.turn.set(turn);
	}
	
	public String getTurn() {
		return turn.get();
	}
	
	public StringProperty getTurnProperty() {
		return turn;
	}

	public void setBlackCount(int count) {
		this.blackCount.set(count);
	}
	
	public int getBlackCount() {
		return blackCount.get();
	}
	
	public IntegerProperty getBlackCountProperty() {
		return blackCount;
	}

	public void setWhiteCount(int count) {
		this.whiteCount.set(count);
	}
	
	public int getWhiteCount() {
		return whiteCount.get();
	}
	
	public IntegerProperty getWhiteCountProperty() {
		return whiteCount;
	}

	public void setWinner(String winner) {
		this.winner.set(winner);
	}
	
	public String getWinner() {
		return winner.get();
	}
	
	public StringProperty getWinnerProperty() {
		return winner;
	}
}
