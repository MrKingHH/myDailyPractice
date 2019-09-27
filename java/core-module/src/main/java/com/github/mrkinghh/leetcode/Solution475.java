package com.github.mrkinghh.leetcode;

import java.util.HashSet;
import java.util.Set;

public class Solution475 {

  /**
   * 475. 供暖器：冬季已经来临。 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。
   * 现在，给出位于一条水平线上的房屋和供暖器的位置，找到可以覆盖所有房屋的最小加热半径。
   * 所以，你的输入将会是房屋和供暖器的位置。你将输出供暖器的最小加热半径。
   * 解题思路：
   * （1）对于每个房屋，要么用前面的暖气，要么用后面的，二者取近的，得到距离；
   * （2）对于所有的房屋，选择最大的上述距离。
   */
  public static int findRadius(int[] houses, int[] heaters) {
    if (houses.length == 0 || heaters.length == 0) {
      return 0;
    }

    int len1 = houses.length;
    int len2 = heaters.length;
    Set<Integer> set = new HashSet<Integer>();
    for (int i = 0; i < len1; i++) {
      for (int j = 0; j < len2; j++) {
        //如果供暖器在该房屋或者后面
        if (heaters[j] >= houses[i]) {
          int back = heaters[j] - houses[i];
        } else { //如果供暖器在该房屋前面
          int front = houses[i] - heaters[j];
        }

      }
    }

    return 0;
  }

}
