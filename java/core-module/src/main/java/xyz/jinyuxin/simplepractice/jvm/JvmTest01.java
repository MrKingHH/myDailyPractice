package xyz.jinyuxin.simplepractice.jvm;

public class JvmTest01 {
  public static void main(String[] args) {
    long maxMemory = Runtime.getRuntime().maxMemory();
    long totalMemory = Runtime.getRuntime().totalMemory();
    System.out.println("虚拟机中试图使用的最大的内存是（最大分配）：" + maxMemory / (double) 1024 / 1024 + "MB");
    System.out.println("虚拟机的总内存(初始分配)：" + totalMemory / (double) 1024 / 1024 + "MB");
  }

}
