package com.github.mrkinghh.leetcode;

public class SolutionLCP1 {

  //用一个for循环
  public static int game(int[] guess, int[] answer) {
    int len = guess.length;
    int num = 0;
    for (int i = 0; i < len; i++) {
      if (guess[i] == answer[i]) {
        num++;
      }
    }
    return num;
  }
}
