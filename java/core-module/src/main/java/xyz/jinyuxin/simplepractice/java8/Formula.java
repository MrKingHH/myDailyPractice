package xyz.jinyuxin.simplepractice.java8;

public interface Formula {

  int calculate(int a);

  default double sqrt(int a) {
    return Math.sqrt(a);
  }

}
