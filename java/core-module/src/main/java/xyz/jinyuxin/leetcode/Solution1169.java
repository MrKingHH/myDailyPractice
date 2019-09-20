package xyz.jinyuxin.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Solution1169 {

  public static void main(String[] args) {
    //交易名称 时间 金额 城市
    //交易金额超过 ¥1000
    //或者，它和另一个城市中同名的另一笔交易相隔不超过 60 分钟（包含 60 分钟整）就可能无效
    String[] transactions1 = {"alice,20,800,mtv", "alice,50,100,beijing"};
    String[] transactions2 = {"alice,20,800,mtv", "alice,50,1200,mtv"};
    String[] transactions3 = {
        "bob,689,1910,barcelona", "alex,696,122,bangkok", "bob,710,1726,barcelona",
        "bob,720,596,bangkok", "chalicefy,217,669,barcelona", "bob,175,221,amsterdam"};
    String[] transactions4 = {
        "bob,689,1910,barcelona", "alex,696,122,bangkok", "bob,832,1726,barcelona",
        "bob,820,596,bangkok", "chalicefy,217,669,barcelona", "bob,175,221,amsterdam"};
    String[] transactions5 = {
        "bob,627,1973,amsterdam", "alex,387,885,bangkok", "alex,355,1029,barcelona",
        "alex,587,402,bangkok", "chalicefy,973,830,barcelona", "alex,932,86,bangkok",
        "bob,188,989,amsterdam"};
    List<String> list1 = invalidTransactions(transactions1);
    List<String> list2 = invalidTransactions(transactions2);
    List<String> list3 = invalidTransactions(transactions3);
    List<String> list4 = invalidTransactions(transactions4);
    List<String> list5 = invalidTransactions(transactions5);
  }

  //
  //   1169.查询无效交易
  //   解题思路：
  //   （1）先判断金额是否超过1000，超过1000必然无效，将其添加进存储无效交易的Set<String>集合。
  //   （2）不管金额是否超额，我们都要将当前交易记录下来，我们采用一个map来记录。map类型为map<string,list<string>>。
  //   我们将交易名称作为key，整个交易作为value。如果没有这个key,我们就得为这个key实例化一个list<string>,并用当前的交易来初始化这个list,再将其放到map里。
  //   （3）我们将map里面的符合当前key的之前的所有交易找到。逐一将之前的所有交易的城市与当前交易的城市是否是同一个城市，如果满足统一城市，则比较交易时间，如果交易时间
  //   间隔小于等于60分钟，那么就将这两个交易放到set<string>中。
  public static List<String> invalidTransactions(String[] transactions) {
    List<String> result = new ArrayList<String>();
    //用于存放无效的交易
    HashSet<String> set = new HashSet<String>();
    int len = transactions.length;
    //把交易名称作为key 整个字符串作为value，value可能有多个 所以用List<String>
    HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    //遍历每一个交易
    for (int i = 0; i < len; i++) {
      String[] strArr = transactions[i].split(",");

      //首先判断金额是否超额
      if (Integer.parseInt(strArr[2]) > 1000) {
        set.add(transactions[i]);
      }

      //如果map里面没有这个key的话，那就实例化一个List<String>
      if (!map.containsKey(strArr[0])) {
        List<String> list = new ArrayList<String>();
        list.add(transactions[i]);
        map.put(strArr[0], list);
      } else {
        //如果存在这个key，那么就把value放到这个key的value列表中
        map.get(strArr[0]).add(transactions[i]);
        //加入后要判断当前简易是否跟同名 不同城市的交易时间差
        //得到当前交易名的所有交易列表
        List<String> temp = map.get(strArr[0]);
        //得到刚刚被插入列表的那个交易
        String[] str1 = temp.get(temp.size() - 1).split(",");
        //判断当前交易与之前交易的不同城市的交易时间差
        for (int j = 0; j < temp.size() - 1; j++) {
          String[] str2 = temp.get(j).split(",");
          if (str1[3].equals(str2[3])) {
            continue;
          } else {
            int time1 = Integer.parseInt(str1[1]);
            int time2 = Integer.parseInt(str2[1]);
            if (Math.abs(time1 - time2) <= 60) {
              set.add(temp.get(j));
              set.add(temp.get(temp.size() - 1));
            }
          }
        }
      }
    }
    result.addAll(set);
    return result;
  }

}
