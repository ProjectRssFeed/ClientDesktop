package cd.rssfeed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

import cd.rssfeed.model.Feed;
import cd.rssfeed.model.FeedSource;
import cd.rssfeed.view.ConfigurationController;
import cd.rssfeed.view.ConnectionController;
import cd.rssfeed.view.FeedOverviewController;
import cd.rssfeed.view.FeedSourceAddDialogController;
import cd.rssfeed.view.RootLayoutController;
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

    // Observable lists
    private ObservableList<FeedSource> feedSourceData = FXCollections.observableArrayList();
    private ObservableList<Feed> feedData = FXCollections.observableArrayList();

    // Core Settings
    private Configuration appConfig;
    private Loader loader;

    /**
     * Constructor
     */
    public MainApp() {
    	appConfig = new Configuration();
    	loader = new Loader(appConfig);

    	loadAllFeedData();
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

    public void loadAllFeedData() {
    	feedSourceData.clear();
    	loader.loadFeedSourcesAndFeeds(loadFeedSourceView(), loadFeedView());
    }

    public Function<ArrayList<FeedSource>, Void> loadFeedSourceView() {
    	return (feedSources) -> {
    		feedSourceData.clear();
    		feedSources.forEach(source->feedSourceData.add(source));

    		return null;
    	};
    }

    public Function<FeedSource, Void> loadFeedView() {
    	return (source) -> {
    		source.getFeedList().forEach(feed->feedData.add(feed));

    		return null;
    	};
    }

    public void loadFeedSource() {
    	feedSourceData.clear();
    	loader.loadFeedSourcesAndFeeds(loadFeedSourceView(), loadFeedView());
    }

    public void reloadFeedList() {
    	feedData.clear();
    	feedSourceData.forEach(feedSource->
    		feedSource.getFeedList().forEach(feed->
    			feedData.add(feed)));
    }

    public Function<Void, Void> reloadData() {
    	return (Void v) -> {
    		feedData.clear();
    		loadAllFeedData();

    		return null;
    	};
    }

    /**
     * Add new FeedSource and reload the view
     * @param newSource
     */
    public void addNewFeedSource(FeedSource newSource) {
    	loader.addNewFeedSource(newSource, reloadData());
    }

    /**
     * Remove a FeedSource and reload the view
     * @param removedSource
     */
    public void removeFeedSource(FeedSource removedSource) {
    	loader.removeFeedSource(removedSource, reloadData());
    }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("RSS Feed Aggregator");

        initRootLayout();

        showConnection();
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

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showConnection() {
    	try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Connection.fxml"));
            AnchorPane connection = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(connection);

            ConnectionController controller = loader.getController();
            controller.setMainApp(this);
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

    public boolean showSettingsDialog(ArrayList<String> conf) {
        try {
            // Load the FXML file and create a new stage for the pop-up dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Configuration.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Configuration");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the FeedSource into the controller.
            ConfigurationController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setConfiguration(conf);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setConfiguration(ArrayList<String> conf) {
    	appConfig.setServerURL(conf.get(0));
    	appConfig.setApiVersion(conf.get(1));
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Configuration getConfiguration() {
    	return appConfig;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
