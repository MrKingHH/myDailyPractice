package xyz.jinyuxin.simplepractice.java8;

public class Java8Test01 {

  public static void main(String[] args) {
    Formula formula = a -> a + 1;

    System.out.println(formula.sqrt(9));
    System.out.println(formula.calculate(9));

  }

}

