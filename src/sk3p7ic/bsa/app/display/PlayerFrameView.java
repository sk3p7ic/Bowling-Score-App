package sk3p7ic.bsa.app.display;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sk3p7ic.bsa.calculator.main.Player;

import java.util.ArrayList;

public class PlayerFrameView {
  private final String playerName;
  private TextField[] bowlFields;
  private Label[] scores;
  private VBox mainPlayerFrameViewBox;
  private HBox playerFramesBox;
  private Button submitBtn;

  private Player player;

  private final int NORMAL_BORDERWIDTH = 3;
  private final int DOUBLE_BORDERWIDTH = NORMAL_BORDERWIDTH * 2;

  private PlayerFrameView() { playerName = ""; }

  public PlayerFrameView(String playerName) {
    this.playerName = playerName;
    bowlFields = new TextField[21]; // Create the array
    for (int i = 0; i < bowlFields.length; i++) { // Initialize all the TextFields
      bowlFields[i] = new TextField();
      bowlFields[i].setText("0");
      bowlFields[i].setPrefColumnCount(2);
      bowlFields[i].setDisable(true);
    }
    // Re-enable first two TextFields for the user
    bowlFields[0].setDisable(false);
    bowlFields[1].setDisable(false);
    scores = new Label[10];
    for (int i = 0; i < scores.length; i++) { // Initialize all the Labels
      scores[i] = new Label(""); // Empty label without text
    }
    mainPlayerFrameViewBox = new VBox();
    mainPlayerFrameViewBox.setPadding(new Insets(20));
    mainPlayerFrameViewBox.setSpacing(5.0f);
    playerFramesBox = new HBox();
    playerFramesBox.setAlignment(Pos.CENTER_RIGHT);
    submitBtn = new Button("Calculate & Advance Frame");
    submitBtn.setOnAction(event -> {
      try {
        calculateScores();
      } catch (Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
      }
    });
    mainPlayerFrameViewBox.getChildren().addAll(playerFramesBox, submitBtn);
    mainPlayerFrameViewBox.setAlignment(Pos.BASELINE_RIGHT);

    player = new Player(playerName);
  }

  private VBox createFrame(int frameNumber) {
    VBox frameBox = new VBox();
    Label frameNumberLabel = new Label(Integer.toString(frameNumber));
    // Create the borders for the label.
    frameNumberLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
            new BorderWidths(NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH, DOUBLE_BORDERWIDTH, NORMAL_BORDERWIDTH))));
    frameNumberLabel.setMaxWidth(Double.MAX_VALUE);
    frameNumberLabel.setAlignment(Pos.CENTER);
    frameBox.getChildren().add(frameNumberLabel);
    HBox scoreEntriesBox = new HBox();
    bowlFields[frameNumber * 2 - 2].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, new BorderWidths(0, 0, 0, NORMAL_BORDERWIDTH))));
    bowlFields[frameNumber * 2 - 1].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, new BorderWidths(0, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH))));
    scoreEntriesBox.getChildren().addAll(bowlFields[frameNumber * 2 - 2], bowlFields[frameNumber * 2 - 1]);
    if (frameNumber == 10) {
      bowlFields[frameNumber * 2].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
              CornerRadii.EMPTY, new BorderWidths(0, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH, 0))));
      scoreEntriesBox.getChildren().add(bowlFields[frameNumber * 2]);
    }
    frameBox.getChildren().add(scoreEntriesBox);
    scores[frameNumber - 1].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, new BorderWidths(0, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH))));
    scores[frameNumber - 1].setMaxWidth(Double.MAX_VALUE);
    scores[frameNumber - 1].setAlignment(Pos.CENTER);
    frameBox.getChildren().add(scores[frameNumber - 1]);
    return frameBox;
  }

  public VBox createPlayerFrames() {
    Label playerNameLabel = new Label(playerName + ":");
    playerNameLabel.setMaxHeight(Double.MAX_VALUE);
    playerNameLabel.setMaxWidth(Double.MAX_VALUE);
    playerNameLabel.setAlignment(Pos.CENTER_LEFT);
    playerNameLabel.setPadding(new Insets(0, 10, 0, 0));
    playerNameLabel.setFont(Font.font(20.0f));
    playerFramesBox.getChildren().add(playerNameLabel);
    for (int i = 1; i <= 10; i++) {
      playerFramesBox.getChildren().add(createFrame(i));
    }
    return mainPlayerFrameViewBox;
  }

  public void calculateScores() {
    ArrayList<Integer> results = new ArrayList<>();
    for (int i = 0; i < player.getCurrentFrameIndex() + 2; i++) {
      try {
        TextField bowl = bowlFields[i];
        results.add(Integer.valueOf(bowl.getText()));
      } catch (Exception e) {
        System.out.println("The program encountered an error: " + e.getMessage());
        return; // Stop the calculation
      }
    }
    int[] currentScores = player.addBowls(results);
    for (int i = 0; i < currentScores.length; i++) {
      scores[i].setText(Integer.toString(currentScores[i]));
    }
    int currentFrameIndex = player.getCurrentFrameIndex();
    // Enable entry on the next two fields
    if (currentFrameIndex <= 18) {
      bowlFields[currentFrameIndex].setDisable(false);
      bowlFields[currentFrameIndex + 1].setDisable(false);
    }
    if (currentFrameIndex == 18) bowlFields[currentFrameIndex + 2].setDisable(false);
    // Disable entry on previous fields
    bowlFields[currentFrameIndex - 1].setEditable(false);
    bowlFields[currentFrameIndex - 2].setEditable(false);
    if (currentFrameIndex > 18) bowlFields[bowlFields.length - 1].setEditable(false);
  }
}
