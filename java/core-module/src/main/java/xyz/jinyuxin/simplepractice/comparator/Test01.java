package xyz.jinyuxin.simplepractice.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test01 {

  public static void main(String[] args) {
    List<Person> personList = new ArrayList<>();
    Person p1 = new Person.Builder().setId(1).setName("jinyuxin").setAge(24).build();
    Person p2 = new Person.Builder().setId(2).setName("yujinxin").setAge(26).build();
    Person p3 = new Person.Builder().setId(3).setName("JINXINYU").setAge(26).build();
    Person p4 = new Person.Builder().setId(3).setName("DENGTAO").setAge(26).build();
    Person p5 = new Person.Builder().setId(3).setName("jinxinyu").setAge(24).build();
    personList.add(p1);
    personList.add(p2);
    personList.add(p3);
    personList.add(p4);
    personList.add(p5);
    //按照年龄降序
    Comparator<Person> byAgeDesc = Comparator.comparing(Person::getAge).reversed();
    //按照名字升序,忽略大小写，JINXINYU和jinxinyu应该相同
    Comparator<Person> byName = Comparator.comparing(Person::getName, String.CASE_INSENSITIVE_ORDER);
    
//    //按照名字升序,考虑大小写，JINXINYU应该在jinxinyu前面
//    Comparator<Person> byName = Comparator.comparing(Person::getName);
    //按照id升序
    Comparator<Person> byId = Comparator.comparing(Person::getId);

    //将上面定义的三个排序组合起来
    Comparator<Person> finalComparator = byId.thenComparing(byName).thenComparing(byAgeDesc);

    //忽略大小写的预期结果是
    //1 jinyuxin 24
    //2 yujinxin 26
    //3 DENGTAO 26
    //3 jinxinyu 26
    //3 jinxinyu 24

    List<Person> result = personList.stream().sorted(finalComparator).collect(Collectors.toList());
    for (Person p : result) {
      System.out.println(p.id+","+p.name+","+p.age);
    }
  }
}

class Person {
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
