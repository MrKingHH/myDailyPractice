package xyz.jinyuxin.simplepractice.java8;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class Java8Test02 {
  public static void main(String[] args) {

    Clock clock = Clock.systemDefaultZone();
    long millis = clock.millis();
    System.out.println(clock.getZone());
    System.out.println(millis);//1568540792111
    //某一个特定的时间点也可以使用 Instant 类来表示，格林尼治时间？
    Instant instant = clock.instant();
    System.out.println(instant);

    //得到本地时间
    Date legacyDate = Date.from(instant); //2019-09-15T09:49:53.342Z
    System.out.println(legacyDate);//Sun Sep 15 17:49:53 CST 2019

    //输出所有区域标识符
    Set<String> set = ZoneId.getAvailableZoneIds();
    Iterator<String> iterator = set.iterator();
    int i = 0;
    while (iterator.hasNext()) {
      i++;
      String str = iterator.next();
      System.out.print(str + " ");
      if (i % 6 == 0) {
        System.out.println();
      }
    }

    //东京比上海快一个小时
    ZoneId zoneId1 = ZoneId.of("Asia/Shanghai");
    ZoneId zoneId2 = ZoneId.of("Asia/Tokyo");
    LocalTime localTime1 = LocalTime.now(zoneId1);
    LocalTime localTime2 = LocalTime.now(zoneId2);

    //后面的减前面的
    long hoursBetween = ChronoUnit.HOURS.between(localTime2, localTime1);
    long minutesBetween = ChronoUnit.MINUTES.between(localTime1, localTime2);
    //后面的是不是比前面的快，返回此处true
    System.out.println(localTime1.isBefore(localTime2));
    System.out.println(hoursBetween);
    System.out.println(minutesBetween);
    System.out.println("现在的时间是：" + localTime1);

    LocalDate today = LocalDate.now();//获取现在的日期
    System.out.println("今天的日期: " + today);//2019-09-15
    LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
    System.out.println("明天的日期: " + tomorrow);//2019-09-16
    LocalDate yesterday = tomorrow.minusDays(2);
    System.out.println("昨天的日期: " + yesterday);//2019-09-14

    //构造具体的某一天
    LocalDate independenceDay = LocalDate.of(2019, Month.SEPTEMBER, 15);
    DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
    System.out.println("今天是周几:" + dayOfWeek);//sunday


  }
}
