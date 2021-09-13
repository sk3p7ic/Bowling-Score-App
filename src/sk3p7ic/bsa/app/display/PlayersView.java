package sk3p7ic.bsa.app.display;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PlayersView {
  ArrayList<PlayerFrameView> players; // Stores the frames for each player
  VBox playerFrameViews; // Contains all the VBoxes with the PlayerFrameViews

  /**
   * Initializes values.
   */
  public PlayersView() {
    players = new ArrayList<>();
    playerFrameViews = new VBox();
  }

  /**
   * Creates new player objects and initializes values.
   * @param playerNames The names of the players who will have PlayerFrameViews.
   */
  public PlayersView(String[] playerNames) {
    this(); // Run the default constructor
    for (String name : playerNames) {
      players.add(new PlayerFrameView(name));
    }
  }

  /**
   * Adds a player to the display.
   * @param playerName The name of the player to create a new PlayerFrameView for.
   */
  public void addPlayer(String playerName) {
    players.add(new PlayerFrameView(playerName));
  }

  /**
   * Generates the PlayerFrameViews and displays them.
   * @return A VBox containing all of the PlayerFrameView objects.
   */
  public VBox generatePlayerFrames() {
    for (PlayerFrameView player : players) playerFrameViews.getChildren().add(player.createPlayerFrames());
    return playerFrameViews;
  }
}
