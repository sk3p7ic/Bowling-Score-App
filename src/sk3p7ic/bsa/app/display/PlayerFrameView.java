package sk3p7ic.bsa.app.display;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerFrameView {
  private final String playerName;
  private TextField[] bowlFields;
  private VBox mainPlayerFrameViewBox;
  private HBox playerFramesBox;
  private Button submitBtn;

  private PlayerFrameView() { playerName = ""; }

  public PlayerFrameView(String playerName) {
    this.playerName = playerName;
    bowlFields = new TextField[21]; // Create the array
    for (TextField bowl : bowlFields) { // Initialize all the TextFields
      bowl = new TextField();
      bowl.setPrefColumnCount(2);
      bowl.setDisable(true);
    }
    // Re-enable first two TextFields for the user
    bowlFields[0].setDisable(false);
    bowlFields[1].setDisable(false);
    mainPlayerFrameViewBox = new VBox();
    playerFramesBox = new HBox();
    submitBtn = new Button("Calculate & Advance Frame");
    mainPlayerFrameViewBox.getChildren().addAll(playerFramesBox, submitBtn);
  }
}
