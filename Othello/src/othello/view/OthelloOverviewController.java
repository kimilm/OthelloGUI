package othello.view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import othello.AppMain;

public class OthelloOverviewController {

	private AppMain appMain;

	@FXML
	private void handleStart() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(appMain.getClass().getResource("view/OthelloPlay.fxml"));
			
			AnchorPane othelloPlay = loader.load();
			appMain.getBorderpane().setCenter(othelloPlay);
			OthelloPlayController controller = loader.getController();
			controller.setAppMain(appMain);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@FXML
	private void handleExit() {
		Platform.exit();
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}
}
