package xyz.jinyuxin.simplepractice.jvm;

/**
 * Heap test.
 */
public final class JvmTest02 {

  /**
   * Test
   * @param args main args
   */
  public static void main(final String[] args) {
    StringBuilder str = new StringBuilder("OOM");
    while (true) {
      str.append(str).append("OutOfMemoryError"); //在堆中不停的实例化对象
    }
  }

  /***/
  private JvmTest02() {
  }
}
