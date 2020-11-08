package othello.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import othello.model.Stone;
import othello.util.OthelloUtils;
import othello.AppMain;
import othello.model.OthelloUI;
import othello.model.Player;
import othello.model.STK;

public class OthelloPlayController {

	@FXML
	private Label turnLabel;
	@FXML
	private Label whiteLabel;
	@FXML
	private Label blackLabel;
	@FXML
	private Label winnerLabel;
	@FXML
	private GridPane othelloGrid;
	
	private OthelloUI othelloUi;
	
	private Stone stone = new Stone();
	
	private AppMain appMain;
	
	private Thread othelloThread = new Thread(()-> play());
	
	private ObservableList<Circle> truePlaces = FXCollections.observableArrayList();;
	
	Circle circle = new Circle(10.0f, Color.AQUAMARINE);
	
	Circle changeCircle;
	
	public OthelloPlayController() {
		
	}
	
	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}
	
	public OthelloPlayController getController() {
		return this;
	}
	
	@FXML
	private void initialize() {
		othelloUi = new OthelloUI();
		
		turnLabel.setText(othelloUi.getTurn());
		whiteLabel.setText(Integer.toString(othelloUi.getWhiteCount()));
		blackLabel.setText(Integer.toString(othelloUi.getBlackCount()));
		winnerLabel.setText(othelloUi.getWinner());
		
		othelloThread.start();
	}
	
	@FXML
	private void getColRow(MouseEvent event) {
		System.out.println("eventX: " + event.getX() + " eventY: " + event.getY());
		System.out.println("eventX: " + (int)event.getX() / 60 + " eventY: " + (int)event.getY() / 60);
		stone.setX((int)event.getY() / 60);
		stone.setY((int)event.getX() / 60);
		System.out.println("Stone x, y: " + stone.getX() + " " + stone.getY());
	}
	
	@FXML
	private void handleReset() {
		
	}
	
	@FXML
	private void handleHome() {
		othelloThread.stop();
		Platform.exit();
	}
	
	public Stone getStone() {
		return stone;
	}
	
	public GridPane getGridPane() {
		return othelloGrid;
	}
	
	private int x;
	private int y;
	private int turn = 0;
	private int wFlg = 0;
	private boolean pFlg = true;
	private Player curPlayer = null;
	
	public void play() {
		
		while(true) {
			curPlayer = OthelloUtils.takeTurn(turn);
			setTurn();
			setStoneCount();
			
			if(curPlayer.getStoneCount() == 0) {
				break;
			}
			
			searchTable();
			
			rightPlace();
			
			othelloUi.setTurn(curPlayer.toString());
			
			
			if(pFlg) {
				//warining
				System.out.print("\tplayer" + curPlayer.toString() + " can't put stone any where");
				
				if(++wFlg == 2) {
					break;
				}
			}
			else {
				wFlg = 0;
				
				input();
				
				changeStone();
			}
			
			//clear errorMessage
			//doSomething
			
			turn = (turn + 1) % 2;
		}
		
		if(OthelloUtils.p1.getStoneCount() > OthelloUtils.p2.getStoneCount())
			Platform.runLater(()->winnerLabel.setText(OthelloUtils.p1.toString() + " 승리"));
		else if(OthelloUtils.p1.getStoneCount() < OthelloUtils.p2.getStoneCount())
			Platform.runLater(()->winnerLabel.setText(OthelloUtils.p2.toString() + " 승리"));
		else
			Platform.runLater(()->winnerLabel.setText("Draw"));
	}
	
	public void setTurn() {
		Platform.runLater(()->turnLabel.setText(curPlayer.toString()));
	}
	
	public void setStoneCount() {
		Platform.runLater(()->whiteLabel.setText(Integer.toString(OthelloUtils.p1.getStoneCount())));
		Platform.runLater(()->blackLabel.setText(Integer.toString(OthelloUtils.p2.getStoneCount())));
	}
	
	public void input() {
		while (true) {
			x = stone.getX();
			y = stone.getY();
			
			// 입력 범위를 벗어났다
			if (x < 0 || y < 0 || x > 7 || y > 7) {
				System.out.println("wrongInput");
				continue;
			}
			// 놓을 수 있는 자리라
			if (OthelloUtils.inputTable[x][y]) {
				break;
			}

			System.out.println("false place");
		}
	}
	
	//돌을 놓을 수 있는 위치 세팅
	public void searchTable() {
		OthelloUtils.initInputTable();
		
		for (int x = 0; x < 8; ++x) {
			for (int y = 0; y < 8; ++y) {
				if(OthelloUtils.othelloTable[x][y] == curPlayer.getPlayer()) {
					checkDirect(x, y);
				}
			}
		}
	}
	
	//handle only Array
	public void checkDirect(int x, int y) {
		int tempX = x;
		int tempY = y;
		int oppositePlayer = curPlayer.getPlayer() == 1 ? 2 : 1;
		
		//direct 1
		if(tempX == 0 || tempY == 0) {}
		else if(OthelloUtils.othelloTable[--tempX][--tempY] == oppositePlayer)
			while(--tempX > -1 && --tempY > -1)
				if(OthelloUtils.othelloTable[tempX][tempY] == 0) {
					OthelloUtils.inputTable[tempX][tempY] = true;
					break;
				}
				else if(OthelloUtils.othelloTable[tempX][tempY] == curPlayer.getPlayer())
					break;
		
		tempX = x;
		tempY = y;
		//direct 2
		if(tempY == 0) {}
		else if(OthelloUtils.othelloTable[tempX][--tempY] == oppositePlayer)
			while(--tempY > -1)
				if(OthelloUtils.othelloTable[tempX][tempY] == 0) {
					OthelloUtils.inputTable[tempX][tempY] = true;
					break;
				}
				else if(OthelloUtils.othelloTable[tempX][tempY] == curPlayer.getPlayer())
					break;

		tempX = x;
		tempY = y;
		//direct 3
		if(tempX == 7 || tempY == 0) {}
		else if(OthelloUtils.othelloTable[++tempX][--tempY] == oppositePlayer)
			while(++tempX < 8 && --tempY > -1)
				if(OthelloUtils.othelloTable[tempX][tempY] == 0) {
					OthelloUtils.inputTable[tempX][tempY] = true;
					break;
				}
				else if(OthelloUtils.othelloTable[tempX][tempY] == curPlayer.getPlayer())
					break;
		
		tempX = x;
		tempY = y;
		//direct 4
		if(tempX == 7) {}
		else if(OthelloUtils.othelloTable[++tempX][tempY] == oppositePlayer)
			while(++tempX < 8)
				if(OthelloUtils.othelloTable[tempX][tempY] == 0) {
					OthelloUtils.inputTable[tempX][tempY] = true;
					break;
				}
				else if(OthelloUtils.othelloTable[tempX][tempY] == curPlayer.getPlayer())
					break;
		
		tempX = x;
		tempY = y;
		//direct 5
		if(tempX == 7 || tempY == 7) {}
		else if(OthelloUtils.othelloTable[++tempX][++tempY] == oppositePlayer)
			while(++tempX < 8 && ++tempY < 8)
				if(OthelloUtils.othelloTable[tempX][tempY] == 0) {
					OthelloUtils.inputTable[tempX][tempY] = true;
					break;
				}
				else if(OthelloUtils.othelloTable[tempX][tempY] == curPlayer.getPlayer())
					break;
		
		tempX = x;
		tempY = y;
		//direct 6
		if(tempY == 7) {}
		else if(OthelloUtils.othelloTable[tempX][++tempY] == oppositePlayer)
			while(++tempY < 8)
				if(OthelloUtils.othelloTable[tempX][tempY] == 0) {
					OthelloUtils.inputTable[tempX][tempY] = true;
					break;
				}
				else if(OthelloUtils.othelloTable[tempX][tempY] == curPlayer.getPlayer())
					break;
		
		tempX = x;
		tempY = y;
		//direct 7
		if(tempX == 0 || tempY == 7) {}
		else if(OthelloUtils.othelloTable[--tempX][++tempY] == oppositePlayer)
			while(--tempX > -1 && ++tempY < 8)
				if(OthelloUtils.othelloTable[tempX][tempY] == 0) {
					OthelloUtils.inputTable[tempX][tempY] = true;
					break;
				}
				else if(OthelloUtils.othelloTable[tempX][tempY] == curPlayer.getPlayer())
					break;
		
		tempX = x;
		tempY = y;
		//direct 8
		if(tempX == 0) {}
		else if(OthelloUtils.othelloTable[--tempX][tempY] == oppositePlayer)
			while(--tempX > -1)
				if(OthelloUtils.othelloTable[tempX][tempY] == 0) {
					OthelloUtils.inputTable[tempX][tempY] = true;
					break;
				}
				else if(OthelloUtils.othelloTable[tempX][tempY] == curPlayer.getPlayer())
					break;
	}
	
	public void rightPlace() {
		while(!truePlaces.isEmpty()) {System.out.println("대기중이애오!");}
		
		pFlg = true;
		for(int x = 0; x < 8; ++x)
			for(int y = 0; y < 8; ++y)
				if(OthelloUtils.inputTable[x][y]) {
					pFlg = false;
					final int tempx = x;
					final int tempy = y;
					Circle circle = new Circle(10.0f, Color.AQUAMARINE);
					truePlaces.add(circle);
					Platform.runLater(()->othelloGrid.add(circle, tempy, tempx));
				}
	}
	
	STK tempStack = new STK();
	STK stack = new STK();
	
	public void changeStone() {
		final int tempx = x;
		final int tempy = y;
		final Color col = curPlayer.getPlayer() == 1 ? Color.WHITE : Color.BLACK;
		Platform.runLater(()-> {
			changeCircle = new Circle(20.0f, col);
			changeCircle.setStroke(Color.BLACK);
			changeCircle.setStrokeWidth(1);
			othelloGrid.add(changeCircle, tempy, tempx);
		});
		
		//to handle Array, change x and y
		int tempX = x;
		int tempY = y;
		Stone temp;
		
		OthelloUtils.othelloTable[tempX][tempY] = curPlayer.getPlayer();
		OthelloUtils.inputTable[tempX][tempY] = false;

		int oppositePlayer = curPlayer.getPlayer() == 1 ? 2 : 1;
		int player = curPlayer.getPlayer();
		
		tempStack.clean();		
		//direct 1
		if(tempX == 0 || tempY == 0) {}
		else if(OthelloUtils.othelloTable[--tempX][--tempY] == oppositePlayer)
			while(tempX > -1 && tempY > -1) {
				if(OthelloUtils.othelloTable[tempX][tempY] == 0)
					break;
				else if(OthelloUtils.othelloTable[tempX][tempY] == player) {
					while(tempStack.isEmpty()) {
						temp = tempStack.pop();
						stack.push(temp.getX(), temp.getY());
					}
					break;
				}
				tempStack.push(tempX, tempY);
				--tempX;
				--tempY;
			}

		tempStack.clean();		
		tempX = x;
		tempY = y;
		//direct 2
		if(tempY == 0) {}
		else if(OthelloUtils.othelloTable[tempX][--tempY] == oppositePlayer)
			while(tempY > -1) {
				if(OthelloUtils.othelloTable[tempX][tempY] == 0)
					break;
				else if(OthelloUtils.othelloTable[tempX][tempY] == player) {
					while(tempStack.isEmpty()) {
						temp = tempStack.pop();
						stack.push(temp.getX(), temp.getY());
					}
					break;
				}
				tempStack.push(tempX, tempY);
				--tempY;
			}

		tempStack.clean();		
		tempX = x;
		tempY = y;
		//direct 3
		if(tempX == 7 || tempY == 0) {}
		else if(OthelloUtils.othelloTable[++tempX][--tempY] == oppositePlayer)
			while(tempX < 8 && tempY > -1) {
				if(OthelloUtils.othelloTable[tempX][tempY] == 0)
					break;
				else if(OthelloUtils.othelloTable[tempX][tempY] == player) {
					while(tempStack.isEmpty()) {
						temp = tempStack.pop();
						stack.push(temp.getX(), temp.getY());
					}
					break;
				}
				tempStack.push(tempX, tempY);
				++tempX;
				--tempY;
			}
		
		tempStack.clean();		
		tempX = x;
		tempY = y;
		//direct 4
		if(tempX == 7) {}
		else if(OthelloUtils.othelloTable[++tempX][tempY] == oppositePlayer)
			while(tempX < 8) {
				if(OthelloUtils.othelloTable[tempX][tempY] == 0)
					break;
				else if(OthelloUtils.othelloTable[tempX][tempY] == player) {
					while(tempStack.isEmpty()) {
						temp = tempStack.pop();
						stack.push(temp.getX(), temp.getY());
					}
					break;
				}
				tempStack.push(tempX, tempY);
				++tempX;
			}
		
		tempStack.clean();		
		tempX = x;
		tempY = y;
		//direct 5
		if(tempX == 7 || tempY == 7) {}
		else if(OthelloUtils.othelloTable[++tempX][++tempY] == oppositePlayer)
			while(tempX < 8 && tempY < 8) {
				if(OthelloUtils.othelloTable[tempX][tempY] == 0)
					break;
				else if(OthelloUtils.othelloTable[tempX][tempY] == player) {
					while(tempStack.isEmpty()) {
						temp = tempStack.pop();
						stack.push(temp.getX(), temp.getY());
					}
					break;
				}
				tempStack.push(tempX, tempY);
				++tempX;
				++tempY;
			}
		
		tempStack.clean();		
		tempX = x;
		tempY = y;
		//direct 6
		if(tempY == 7) {}
		else if(OthelloUtils.othelloTable[tempX][++tempY] == oppositePlayer)
			while(tempY < 8) {
				if(OthelloUtils.othelloTable[tempX][tempY] == 0)
					break;
				else if(OthelloUtils.othelloTable[tempX][tempY] == player) {
					while(tempStack.isEmpty()) {
						temp = tempStack.pop();
						stack.push(temp.getX(), temp.getY());
					}
					break;
				}
				tempStack.push(tempX, tempY);
				++tempY;
			}
		
		tempStack.clean();		
		tempX = x;
		tempY = y;
		//direct 7
		if(tempX == 0 || tempY == 7) {}
		else if(OthelloUtils.othelloTable[--tempX][++tempY] == oppositePlayer)
			while(tempX > -1 && tempY < 8) {
				if(OthelloUtils.othelloTable[tempX][tempY] == 0)
					break;
				else if(OthelloUtils.othelloTable[tempX][tempY] == player) {
					while(tempStack.isEmpty()) {
						temp = tempStack.pop();
						stack.push(temp.getX(), temp.getY());
					}
					break;
				}
				tempStack.push(tempX, tempY);
				--tempX;
				++tempY;
			}
		
		tempStack.clean();		
		tempX = x;
		tempY = y;
		//direct 8
		if(tempX == 0) {}
		else if(OthelloUtils.othelloTable[--tempX][tempY] == oppositePlayer)
			while(tempX > -1) {
				if(OthelloUtils.othelloTable[tempX][tempY] == 0)
					break;
				else if(OthelloUtils.othelloTable[tempX][tempY] == player) {
					while(tempStack.isEmpty()) {
						temp = tempStack.pop();
						stack.push(temp.getX(), temp.getY());
					}
					break;
				}
				tempStack.push(tempX, tempY);
				--tempX;
			}
		
		int count = 0;
		while(stack.isEmpty()) {
			++count;
			temp = stack.pop();
			final int tempxx = temp.getX();
			final int tempyy = temp.getY();
			
			Platform.runLater(()-> {
				changeCircle = new Circle(20.0f, col);
				changeCircle.setStroke(Color.BLACK);
				changeCircle.setStrokeWidth(1);
				othelloGrid.add(changeCircle, tempyy, tempxx);
			});
			OthelloUtils.othelloTable[temp.getX()][temp.getY()] = curPlayer.getPlayer();
		}
		
		if(curPlayer.getPlayer() == 1) {
			OthelloUtils.p1.addStone(count + 1);
			OthelloUtils.p2.loseStone(count);
		}
		else {
			OthelloUtils.p2.addStone(count + 1);
			OthelloUtils.p1.loseStone(count);
		}
		
		initRightPlace();
	}
	
	/**
	 * 먼저 그린 표시 없애기
	 */
	public void initRightPlace() {
		Platform.runLater(() -> {
			System.out.println(truePlaces.size());
			othelloGrid.getChildren().removeAll(truePlaces);
			truePlaces.clear();
		});
	}
	
	public Circle getCircleByRowColumnIndex (final int row, final int column, GridPane gridPane) {
	    Node result = null;
	    ObservableList<Node> childrens = gridPane.getChildren();

	    for (Node node : childrens) {
	        if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }

	    return (Circle)result;
	}

}
