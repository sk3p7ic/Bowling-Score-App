package sk3p7ic.bsa.app.display;

import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlayersView {
  ArrayList<PlayerFrameView> players;
  VBox playerFrameViews;

  public PlayersView() {
    players = new ArrayList<>();
    playerFrameViews = new VBox();
  }

  public PlayersView(@NotNull String[] playerNames) {
    this(); // Run the default constructor
    for (String name : playerNames) {
      players.add(new PlayerFrameView(name));
    }
  }

  public void addPlayer(String playerName) {
    players.add(new PlayerFrameView(playerName));
  }

  public VBox generatePlayerFrames() {
    for (PlayerFrameView player : players) playerFrameViews.getChildren().add(player.createPlayerFrames());
    return playerFrameViews;
  }
}
