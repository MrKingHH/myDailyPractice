package xyz.jinyuxin.findjob.hw.a20190911;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 修改机票并排序
 * 输入：
 * 3
 * cz7132,a1,zhangsan
 * cz7132,a2,zhaosi
 * cz7156,a2,wangwu
 * 2
 * cz7132,a1,cz7156,a2
 * cz7156,a2,cz7156,a3
 * 输出：
 * cz7132,a2,zhaosi
 * cz7156,a2,zhangsan
 * cz7156,a3,wangwu
 */
public class Solution3 {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String n = sc.nextLine();
    int intn = Integer.parseInt(n);
    List<Ticket> list = new ArrayList<>(intn);
    //输入N个航班
    for (int i = 0; i < intn; i++) {
      String line = sc.nextLine();
      String[] strs = line.split(",");
      Ticket temp = new Ticket(strs[0], strs[1], strs[2]);
      list.add(temp);
    }
    //输入调整的航班，输入一行，调整一行
    String m = sc.nextLine();
    int intm = Integer.parseInt(m);
    for (int i = 0; i < intm; i++) {
      String line = sc.nextLine();
      //得到cz7132 A1  cz7156 A2
      String[] strs = line.split(",");

      //不能每次来都从头开始找
      for (int j = 0; j < list.size(); j++) {
        Ticket ticket = list.get(j);
        //找到老的机票
        if (ticket.hangban.equals(strs[0]) && ticket.zuoweihao.equals(strs[1]) && !ticket.modify) {
          //将老机票拿出来，做更改，航班号和座位号改成新的
          ticket.hangban = strs[2];
          ticket.zuoweihao = strs[3];
          ticket.modify = true;
          break;
        }
      }
    }

    Collections.sort(list);
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i).hangban + ","
              + list.get(i).zuoweihao + "," + list.get(i).chengkeming);
    }

  }

  static class Ticket implements Comparable<Ticket> {

    String hangban;
    String zuoweihao;
    String chengkeming;
    boolean modify = false;

    public Ticket(String hangban, String zuoweihao, String chengkeming) {
      this.hangban = hangban;
      this.zuoweihao = zuoweihao;
      this.chengkeming = chengkeming;
    }

    //先按照航班号升序 再按座位号升序
    @Override
    public int compareTo(Ticket o) {
      if (this.hangban.compareTo(o.hangban) < 0) {
        return -1;
      } else if (this.hangban.compareTo(o.hangban) > 0) {
        return 1;
      } else if (this.zuoweihao.compareTo(o.zuoweihao) < 0) {
        return -1;
      } else if (this.zuoweihao.compareTo(o.zuoweihao) > 0) {
        return 1;
      } else {
        return 0;
      }
    }
  }

}

