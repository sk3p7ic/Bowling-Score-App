package sk3p7ic.bsa.calculator.main;

import java.util.List;

public class Player {
  private int[] playerFrames; // Stores the throws for the frames
  private int[] frameScores; // Stores the scores for the frames
  private int currentFrame; // Stores the current frame's index
  private final String playerName; // The name of the player

  private Player() { playerName = ""; } // Used to prevent instantiation via no-arg constructor

  /**
   * Creates a new player object with a blank game.
   * @param playerName The name of the player the object is for.
   */
  public Player(String playerName) {
    this.playerName = playerName;
    playerFrames = new int[21]; // (9*2)+3 <-- 9 normal 2-throw frames and a 3-throw 10th frame
    frameScores = new int[10]; // 10 frames
    currentFrame = 0;
  }

  public String getPlayerName() {
    return playerName;
  }

  private void nextFrame() {
    currentFrame += 2; // 0, 2, 4, 6, ... (Because there's 2 throws per normal frame)
  }

  /**
   * Calculates the current scores for each frame.
   */
  private void calculateScore() {
    int currentScore = 0;
    for (int i = 0; i < Math.min(currentFrame, 18); i++) {
      if (i % 2 != 0) continue; // Pass if not the first throw in a frame
      if (playerFrames[i] == 10) { // Strike
        if (playerFrames[i + 2] == 10) currentScore += 10 + playerFrames[i + 2] + playerFrames[i + 4];
        else currentScore += 10 + playerFrames[i + 2] + playerFrames[i + 3];
      } else if (playerFrames[i] + playerFrames[i + 1] == 10) { // Spare
        currentScore += 10 + playerFrames[i + 2];
      } else {
        currentScore += playerFrames[i] + playerFrames[i + 1];
      }
      frameScores[i / 2] = currentScore;
    }
    if (currentFrame == 18) {
      if (playerFrames[18] == 10) {
        currentScore += 10 + playerFrames[19] + playerFrames[20];
      } else if (playerFrames[18] + playerFrames[19] == 10) {
        currentScore += 10 + playerFrames[20];
      } else {
        currentScore += playerFrames[18] + playerFrames[19];
      }
      frameScores[9] = currentScore;
    }
  }

  /**
   * Takes in a list of new throws and calculates the score with these throws added.
   * @param bowls A list of the throws for the frame.
   * @return An array containing the scores for each frame.
   */
  public int[] addBowls(List<Integer> bowls) {
    for (int i = bowls.size() - 1; i > currentFrame - 1; i--) { // Add the new throws to the end of playerFrames array
      playerFrames[i] = bowls.get(i);
    }
    calculateScore(); // Calculate the new scores
    nextFrame(); // Advance the game by one frame
    return frameScores; // Return the scores
  }
}
