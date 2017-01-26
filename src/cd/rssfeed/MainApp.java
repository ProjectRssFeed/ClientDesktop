package cd.rssfeed;

import java.io.IOException;

import cd.rssfeed.model.Feed;
import cd.rssfeed.model.FeedSource;
import cd.rssfeed.view.FeedOverviewController;
import cd.rssfeed.view.FeedSourceAddDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<FeedSource> feedSourceData = FXCollections.observableArrayList();
    private ObservableList<Feed> feedData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
    	feedSourceData.add(new FeedSource("FeedSouce1", "localhost", "FeedSource test"));
    	feedData.add(new Feed("Feed1", "localhost", "Feed test"));
    }

    /**
     * Return Observable List of FeedSource
     * @return
     */
    public ObservableList<FeedSource> getFeedSourceData() {
    	return feedSourceData;
    }

    public ObservableList<Feed> getFeedData() {
    	return feedData;
    }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("RSS Feed Aggregator");

        initRootLayout();

        showFeedOverview();
	}

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from FXML file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the feed overview inside the root layout.
     */
    public void showFeedOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FeedOverview.fxml"));
            AnchorPane feedOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(feedOverview);

            FeedOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showFeedSourceAddDialog(FeedSource feedSource) {
        try {
            // Load the FXML file and create a new stage for the pop-up dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FeedSourceAddDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Feed Source");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the FeedSource into the controller.
            FeedSourceAddDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setFeedSource(feedSource);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
