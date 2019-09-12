package xyz.jinyuxin.simplepractice.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test01 {
  public static void main(String[] args) {
    //利用工具类Collections的reverse方法，将数组转成List之后反转。
    //再利用接口Collection的toArray方法，将List转成数组。但是要用toArray(T[] a)，指定返回的具体类型。
    String[] s = new String[]{
            "dog", "lazy", "a", "over", "jumps", "fox", "brown", "quick", "A"
    };

    Integer[] i = {1,2,3,4,5,6,7,8};
    //java.util.Arrays&ArrayList
    List<String> list = Arrays.asList(s);
    //转成java.util.ArrayList类型
    List<Integer> list1 = new ArrayList<>(Arrays.asList(i));
    Collections.reverse(list);
    Collections.reverse(list1);
    s = list.toArray(new String[0]);//没有指定类型的话会报错
    i = list1.toArray(new Integer[0]);
    System.out.println(Arrays.toString(s));
    System.out.println(Arrays.toString(i));
    char[] k = {'a', 'f', 'b', 'c', 'e', 'A', 'C', 'B'};
    System.out.println(Arrays.toString(k));// [a, f, b, c, e, A, C, B]
  }
}
