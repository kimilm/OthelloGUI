package othello;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import othello.view.OthelloOverviewController;

public class AppMain extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Othello");
		this.primaryStage = primaryStage;

		initRootLayout();

		showOthelloOverview();
	}
	
	public void initRootLayout() {
		try {
			rootLayout = FXMLLoader.load(getClass().getResource("view/RootLayout.fxml"));

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showOthelloOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/OthelloOverview.fxml"));

			VBox othelloOverview = loader.load();

			rootLayout.setCenter(othelloOverview);

			OthelloOverviewController controller = loader.getController();
			controller.setAppMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUI(String flag, Object data) {
		switch (flag) {
		case "turn":
			break;
		case "count":
			break;
		case "warning":
			break;
		case "winner":
			break;
	}
	}
	
	public BorderPane getBorderpane() {
		return rootLayout;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main (String [] args) {
		launch(args);
	}
}
