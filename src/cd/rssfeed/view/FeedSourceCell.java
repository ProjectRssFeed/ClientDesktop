package cd.rssfeed.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class FeedSourceCell {
    @FXML
    private VBox vBox;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label url;

    public FeedSourceCell()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FeedSourceCell.class.getResource("FeedSourceCellItem.fxml"));

        loader.setController(this);
        try
        {
            loader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(String title, String description, String url)
    {
        this.title.setText(title);
        this.description.setText(description);
        this.url.setText(url);
    }

    public VBox getBox()
    {
        return vBox;
    }
}
