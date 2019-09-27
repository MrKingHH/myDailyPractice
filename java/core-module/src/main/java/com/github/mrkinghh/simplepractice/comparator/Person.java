package com.github.mrkinghh.simplepractice.comparator;

public class Person {
  int id;
  String name;
  int age;

  public Person(int id, String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }


  public static class Builder {
    private int id;
    private String name;
    private int age;

    public Builder() {
    }

    public Builder setId(int id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setAge(int age) {
      this.age = age;
      return this;
    }

    public Person build() {
      return new Person(id, name, age);
    }
  }

}
