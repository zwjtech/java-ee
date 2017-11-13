import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by zhangWeiJie on 2017/10/23.
 */
public class Java8FeaturesTest {

    @Test
    public void dateAPITest(){
        Clock clock = Clock.systemDefaultZone();
        long mills = clock.millis();
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);
    }
    @Test
    public void timeZoneTest(){
        System.out.println(ZoneId.getAvailableZoneIds());
// prints all available timezone ids

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());

// ZoneRules[currentStandardOffset=+01:00]
// ZoneRules[currentStandardOffset=-03:00]
    }

    @Test
    public void localTimeTest(){
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);

        System.out.println(now1.isBefore(now2));  // false

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);       // -3
        System.out.println(minutesBetween);     // -239

        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late);       // 23:59:59

        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.SHORT)
                        .withLocale(Locale.GERMAN);

        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println(leetTime);   // 13:37
    }

    @Test
    public void localDateTest(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);

        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println(dayOfWeek);    // FRIDAY

        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMAN);

        LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter);
        System.out.println(xmas);   // 2014-12-24
    }

    @Test
    public void localDateTimeTest(){
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek);      // WEDNESDAY

        Month month = sylvester.getMonth();
        System.out.println(month);          // DECEMBER

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);    // 1439

        Instant instant = sylvester
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014

        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern("MM dd, yyyy - HH:mm");

        LocalDateTime parsed = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter);
        String string = formatter.format(parsed);
        System.out.println(string);     // Nov 03, 2014 - 07:13
    }

    @Test
    public void interfaceDefaultImplTest(){
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return 100*a;
            }
        };
        double r1 = formula.calculate(10);
        double r2 = formula.sqrt(9);
        System.out.println(r1+","+r2);

    }

    @Test
    public void lambdaExpression(){
        //old
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

//        Collections.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String a, String b) {
//                return b.compareTo(a);
//            }
//        });
//        System.out.println(names);
//
//        //lambda
//        Collections.sort(names,(String a,String b)->{
//            return b.compareTo(a);
//        });
//        System.out.println(names);

        //simple
        Collections.sort(names,(String a,String b)->a.compareTo(b));

        //more simple
        Collections.sort(names,(a,b)->a.compareTo(b));
        System.out.println(names);
    }

    @Test
    public void getCpu(){
        int cpuNum = Runtime.getRuntime()
                .availableProcessors();
        System.out.println(cpuNum);
        File storeTasksFile = new File(System.getProperty("java.io.tmpdir"),
                "Store_Tasks_");

    }


}
