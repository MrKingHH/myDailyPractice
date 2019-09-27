package com.github.mrkinghh.simplepractice.comparator;

public class Student implements Comparable<Student> {
  public final int id;
  public int age;
  public int score;
  public String name;

  public Student(int id, int age, int score, String name) {
    this.id = id;
    this.age = age;
    this.score = score;
    this.name = name;
  }

  //   * this.id - o.id ->按照id升序
  //   * o.id - this.id ->按照id降序
  //   * this.age - o.age -> 按照age升序
  //   * o.age - this.age -> 按照age降序
  //   * this.score - o.score -> 按照score升序
  //   * o.score-this.score 按照降序
  //   * 总结就是：this在前 按照字段升序，this在后，按照字段降序
  //   * <p>
  //   * //按照名字长度排序
  //   * this.name.length() - o.name.length()
  //   * <p>
  //   * //按照字典排序
  //   * //升序
  //   * this.name.compareTo(o.name) <0 return -1
  //   * //降序
  //   * this.name.compareTo(o.name) <0 return 1
  @Override
  public int compareTo(Student o) {
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

    public Student build() {
      return new Student(id, age, score, name);
    }
  }
}
