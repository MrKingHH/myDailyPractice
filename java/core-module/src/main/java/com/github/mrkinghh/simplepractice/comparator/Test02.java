package com.github.mrkinghh.simplepractice.comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test02 {
  public static void main(String[] args) {
    List<Student> list = new ArrayList<>();
    Student t1 = new Student.Builder().setId(1)
            .setAge(20).setScore(95)
            .setName("DT").build();
    Student t2 = new Student.Builder().setId(2)
            .setAge(23).setScore(92)
            .setName("CZ").build();
    Student t3 = new Student.Builder().setId(3)
            .setAge(22).setScore(93)
            .setName("LD").build();
    Student t4 = new Student.Builder().setId(4)
            .setAge(25).setScore(98)
            .setName("LD").build();
    list.add(t1);
    list.add(t2);
    list.add(t3);
    list.add(t4);

    //将list转成array
    Student[] test = list.toArray(new Student[0]);

    //将array转成list
    List<Student> list1 = new ArrayList<>(Arrays.asList(test));

    Collections.sort(list);
  }

}

