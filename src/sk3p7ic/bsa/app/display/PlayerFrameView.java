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
  private final String playerName; // Stores the player's name
  private TextField[] bowlFields; // Stores the results that the user enters
  private Label[] scores; // Stores the scores for each frame
  private VBox mainPlayerFrameViewBox; // The overall VBox that all elements are contained in
  private HBox playerFramesBox; // The HBox containing the frame elements

  private Player player; // The player instance used for calculations

  // Used to easily change the widths of borders
  private final int NORMAL_BORDERWIDTH = 3;
  private final int DOUBLE_BORDERWIDTH = NORMAL_BORDERWIDTH * 2;

  private PlayerFrameView() { playerName = ""; } // Used to prevent instantiation via no-arg constructor

  /**
   * Creates a new object with a given player name.
   * @param playerName The name of the player.
   */
  public PlayerFrameView(String playerName) {
    this.playerName = playerName;
    bowlFields = new TextField[21]; // Create the array
    for (int i = 0; i < bowlFields.length; i++) { // Initialize all the TextFields
      bowlFields[i] = new TextField();
      bowlFields[i].setText("0");
      bowlFields[i].setPrefColumnCount(2); // Used to keep the field's width small
      bowlFields[i].setDisable(true); // Used to ensure that the player cannot enter data into later fields
    }
    // Re-enable first two TextFields for the user
    bowlFields[0].setDisable(false);
    bowlFields[1].setDisable(false);
    scores = new Label[10];
    for (int i = 0; i < scores.length; i++) { // Initialize all the Labels
      scores[i] = new Label("0");
    }
    mainPlayerFrameViewBox = new VBox();
    mainPlayerFrameViewBox.setPadding(new Insets(20));
    mainPlayerFrameViewBox.setSpacing(5.0f);
    playerFramesBox = new HBox();
    playerFramesBox.setAlignment(Pos.CENTER_RIGHT);
    Button submitBtn = new Button("Calculate & Advance Frame");
    submitBtn.setOnAction(event -> {
      try {
        calculateScores();
      } catch (Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
      }
    });
    mainPlayerFrameViewBox.getChildren().addAll(playerFramesBox, submitBtn);
    mainPlayerFrameViewBox.setAlignment(Pos.BASELINE_RIGHT);

    player = new Player(playerName); // Create a new Player object for calculating the score
  }

  /**
   * Creates a properly-formatted frame for the game.
   * @param frameNumber The number of the frame.
   * @return A VBox containing a frame with the given frame number.
   */
  private VBox createFrame(int frameNumber) {
    VBox frameBox = new VBox();
    Label frameNumberLabel = new Label(Integer.toString(frameNumber)); // Get the frame number and make it a Label
    // Create the borders for the label.
    frameNumberLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
            new BorderWidths(NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH, DOUBLE_BORDERWIDTH, NORMAL_BORDERWIDTH))));
    frameNumberLabel.setMaxWidth(Double.MAX_VALUE);
    frameNumberLabel.setAlignment(Pos.CENTER);
    frameBox.getChildren().add(frameNumberLabel);
    HBox scoreEntriesBox = new HBox();
    // Create the borders for the TextField objects that will be in this frame
    bowlFields[frameNumber * 2 - 2].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, new BorderWidths(0, 0, 0, NORMAL_BORDERWIDTH))));
    bowlFields[frameNumber * 2 - 1].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, new BorderWidths(0, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH))));
    scoreEntriesBox.getChildren().addAll(bowlFields[frameNumber * 2 - 2], bowlFields[frameNumber * 2 - 1]);
    if (frameNumber == 10) { // Create the borders for the third TextField object if this is the tenth frame
      bowlFields[frameNumber * 2].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
              CornerRadii.EMPTY, new BorderWidths(0, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH, 0))));
      scoreEntriesBox.getChildren().add(bowlFields[frameNumber * 2]);
    }
    frameBox.getChildren().add(scoreEntriesBox);
    // Create the borders for the Label that will display the scores to the user
    scores[frameNumber - 1].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY, new BorderWidths(0, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH, NORMAL_BORDERWIDTH))));
    scores[frameNumber - 1].setMaxWidth(Double.MAX_VALUE);
    scores[frameNumber - 1].setAlignment(Pos.CENTER);
    frameBox.getChildren().add(scores[frameNumber - 1]);
    return frameBox; // Return the frame that was created
  }

  /**
   * Creates the frames for and returns the main VBox containing all elements.
   * @return A VBox containing the player's name, frames, and a button to advance the game.
   */
  public VBox createPlayerFrames() {
    Label playerNameLabel = new Label(playerName + ":"); // Displays the player's name
    playerNameLabel.setMaxHeight(Double.MAX_VALUE);
    playerNameLabel.setMaxWidth(Double.MAX_VALUE);
    playerNameLabel.setAlignment(Pos.CENTER_LEFT);
    playerNameLabel.setPadding(new Insets(0, 10, 0, 0));
    playerNameLabel.setFont(Font.font(20.0f));
    playerFramesBox.getChildren().add(playerNameLabel);
    for (int i = 1; i <= 10; i++) {
      playerFramesBox.getChildren().add(createFrame(i)); // Create each of the frames and add them to an HBox
    }
    return mainPlayerFrameViewBox; // Return the main VBox for the player
  }

  /**
   * Gathers the info that the user has entered, calculates the scores, and displays them in the score labels in each
   * frame.
   */
  public void calculateScores() {
    ArrayList<Integer> results = new ArrayList<>(); // Stores the results that the user has entered for a frame
    // Gather only data from beginning to current frame (current index +2 if normal frame, +3 if tenth frame)
    for (int i = 0; i < player.getCurrentFrameIndex() + ((player.getCurrentFrameIndex() == 18) ? 3 : 2); i++) {
      try {
        TextField bowl = bowlFields[i]; // Get the TextField containing a throw's result
        results.add(Integer.parseInt(bowl.getText())); // Attempt to parse the text into an int
      } catch (Exception e) {
        System.out.println("[ERROR]: " + e.getMessage());
        return; // Stop the calculation
      }
    }
    int[] currentScores = player.addBowls(results); // Add the bowls, run the calculations, and get scores
    for (int i = 0; i < currentScores.length; i++) { // Display the scores to the user
      scores[i].setText(Integer.toString(currentScores[i]));
    }
    int currentFrameIndex = player.getCurrentFrameIndex(); // Get the current frame index now that it has been changed
    // Enable entry on the next two fields
    if (currentFrameIndex <= 18) {
      bowlFields[currentFrameIndex].setDisable(false);
      bowlFields[currentFrameIndex + 1].setDisable(false);
    }
    // If the tenth frame, enable text entry for the third TextField
    if (currentFrameIndex == 18) bowlFields[currentFrameIndex + 2].setDisable(false);
    // Disable entry on previous fields
    bowlFields[currentFrameIndex - 1].setEditable(false); // Use setEditable to prevent greyed-out look
    bowlFields[currentFrameIndex - 2].setEditable(false);
    // If past the tenth frame (calculations over), disable editing on the third TextField
    if (currentFrameIndex > 18) bowlFields[bowlFields.length - 1].setEditable(false);
  }
}
