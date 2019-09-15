package xyz.jinyuxin.simplepractice.jvm;

public class JvmTest02 {
  public static void main(String[] args) {
    String str = "OOM";
    while (true){
      str += str + "OutOfMemoryError";//在堆中不停的实例化对象
    }
  }
}
