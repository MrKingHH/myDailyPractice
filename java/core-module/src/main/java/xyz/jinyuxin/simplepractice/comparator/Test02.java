package xyz.jinyuxin.simplepractice.comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test02 {
  public static void main(String[] args) {
    List<Test> list = new ArrayList<>();
    Test t1 = new Test.Builder().setId(1)
            .setAge(20).setScore(95)
            .setName("DT").build();
    Test t2 = new Test.Builder().setId(2)
            .setAge(23).setScore(92)
            .setName("CZ").build();
    Test t3 = new Test.Builder().setId(3)
            .setAge(22).setScore(93)
            .setName("LD").build();
    Test t4 = new Test.Builder().setId(4)
            .setAge(25).setScore(98)
            .setName("LD").build();
    list.add(t1);
    list.add(t2);
    list.add(t3);
    list.add(t4);

    //将list转成array
    Test[] test = list.toArray(new Test[0]);

    //将array转成list
    List<Test> list1 = new ArrayList<>(Arrays.asList(test));

    Collections.sort(list);
  }

}

class Test implements Comparable<Test> {
  public final int id;
  public int age;
  public int score;
  public String name;

  public Test(int id, int age, int score, String name) {
    this.id = id;
    this.age = age;
    this.score = score;
    this.name = name;
  }

  /**
   * this.id - o.id ->按照id升序
   * o.id - this.id ->按照id降序
   * this.age - o.age -> 按照age升序
   * o.age - this.age -> 按照age降序
   * this.score - o.score -> 按照score升序
   * o.score-this.score 按照降序
   * 总结就是：this在前 按照字段升序，this在后，按照字段降序
   * <p>
   * //按照名字长度排序
   * this.name.length() - o.name.length()
   * <p>
   * //按照字典排序
   * //升序
   * this.name.compareTo(o.name) <0 return -1
   * //降序
   * this.name.compareTo(o.name) <0 return 1
   */
  @Override
  public int compareTo(Test o) {
    //按照字母升序
    if (this.name.compareTo(o.name) < 0) {
      return -1;
    } else if (this.name.compareTo(o.name) > 0) {
      return 1;
    } else {
      return 0;
    }
  }

  public static class Builder {
    private int id = -1;
    private int age = -1;
    private int score = -1;
    private String name = null;

    public Builder setId(int id) {
      this.id = id;
      return this;
    }

    public Builder setAge(int age) {
      this.age = age;
      return this;
    }

    public Builder setScore(int score) {
      this.score = score;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Test build() {
      return new Test(id, age, score, name);
    }
  }
}
