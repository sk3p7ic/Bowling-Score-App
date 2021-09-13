package sk3p7ic.bsa.app.main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sk3p7ic.bsa.app.display.PlayersView;

public class BowlingScoreApp extends Application {
  public static void main(String[] args) {
    launch(args); // Start the javafx application
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Bowling Score Calculator"); // Set the title to default
    primaryStage.setResizable(false); // Prevent the user from being able to resize windows
    getPlayerNamesView(primaryStage); // Gather the names of the players for the game
  }

  /**
   * Gathers the names of the players for the application and goes to the PlayersView once the names are gathered.
   * @param primaryStage The stage that will be used to display the windows in the application.
   */
  private void getPlayerNamesView(Stage primaryStage) {
    primaryStage.setTitle("Bowling Score Calculator -- Player Info Screen");
    VBox mainBox = new VBox(); // Used to store the elements for the info screen
    mainBox.setAlignment(Pos.CENTER);
    TextField numPlayers = new TextField(); // Stores the number of players in the game
    numPlayers.setPrefColumnCount(3);
    TextArea playerNames = new TextArea(); // Stores the names of players in the game
    Button submitBtn = new Button("Submit");
    mainBox.getChildren().addAll(
        new HBox(new Label("Enter number of players: "), numPlayers),
        new VBox(new Label("Enter names of players, each on a new line:"), playerNames),
        submitBtn
    );
    mainBox.setSpacing(10.0f);
    mainBox.setPadding(new Insets(20));
    submitBtn.setOnAction(event -> { // When the user clicks the button
      try {
        int numberOfPlayers = Integer.parseInt(numPlayers.getText()); // Attempt to get the number of players
        String[] namesOfPlayers = playerNames.getText().split("\n"); // Split the names into an array of names
        if (namesOfPlayers.length != numberOfPlayers)
          throw new Exception("Number of names (" + namesOfPlayers.length + ") does not match number of players (" +
              numberOfPlayers + ").");
        else if (numberOfPlayers == 0)
          throw new Exception("Number of players cannot equal zero.");
        PlayersView playersView = new PlayersView(namesOfPlayers);
        primaryStage.setTitle("Bowling Score Calculator");
        primaryStage.setScene(new Scene(playersView.generatePlayerFrames())); // Show the PlayersView
        primaryStage.show();
      } catch (Exception e) {
        mainBox.getChildren().add(new Label("Error: " + e.getMessage()));
      }
    });
    primaryStage.setScene(new Scene(mainBox)); // Show the player info screen
    primaryStage.show();
  }
}
