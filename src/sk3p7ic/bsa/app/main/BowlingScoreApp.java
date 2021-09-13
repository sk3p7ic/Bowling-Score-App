package sk3p7ic.bsa.app.main;

import sk3p7ic.bsa.calculator.main.Player;

import java.util.ArrayList;

public class BowlingScoreApp {
  public static void main(String[] args) {
    //int[] bowls = new int[]{8, 2, 5, 4, 9, 0, 10, 0, 10, 0, 5, 5, 5, 3, 6, 3, 9, 1, 9, 1, 10};
    int[][] games = new int[][]{
        new int[]{8, 2, 5, 4, 9, 0, 10, 0, 10, 0, 5, 5, 5, 3, 6, 3, 9, 1, 9, 1, 10},
        new int[]{10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 10, 10},
        new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
        new int[]{1, 9, 2, 8, 3, 7, 4, 6, 5, 5, 6, 4, 7, 3, 8, 2, 9, 1, 3, 7, 5}
    };
    String playerName = "Joshua Ibrom";
    for (int[] bowls : games) {
      try {
        testPlayer(playerName, bowls);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void testPlayer(String playerName, int[] bowls) {
    Player player = new Player(playerName);
    ArrayList<Integer> frameThrows = new ArrayList<>();
    for (int i = 0; i <= 18; i++) {
      if (i % 2 != 0) continue;
      if (i <= 16) {
        frameThrows.add(bowls[i]);
        frameThrows.add(bowls[i + 1]);
      } else {
        frameThrows.add(bowls[i]);
        frameThrows.add(bowls[i + 1]);
        frameThrows.add(bowls[i + 2]);
      }
      int[] results = player.addBowls(frameThrows);
      for (int result : results) System.out.print(result + " ");
      System.out.println("\n");
    }
  }
}
