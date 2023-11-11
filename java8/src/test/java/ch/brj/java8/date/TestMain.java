package ch.brj.java8.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class TestMain {

    public static void main(String[] args) {
        TestMain ut = new TestMain();
        ut.testLocalDate();
        ut.testInstant();
        ut.testFormat();
        ut.testMonth();
    }

    public void testInstant() {

        Instant heute = Instant.now();
        System.out.println(heute);

        Instant vorgestern = heute.minus(2, ChronoUnit.DAYS);
        System.out.println(vorgestern);

    }

    public void testLocalDate() {

        LocalDate heute = LocalDate.now();
        System.out.println(heute);

        LocalDate vorgestern = heute.minus(2, ChronoUnit.DAYS);
        System.out.println(vorgestern);

    }

    public void testFormat() {

        LocalDate heute = LocalDate.now();
        System.out.println(heute);

        System.out.println(heute.format(DateTimeFormatter.ISO_DATE));
        System.out.println(heute.format(DateTimeFormatter.ISO_ORDINAL_DATE));
        System.out.println(heute.format(DateTimeFormatter.ISO_WEEK_DATE));

        DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        System.out.println(heute.format(formatter1));

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.MMM.yyyy");
        System.out.println(heute.format(formatter2));
    }

    public void test1() {

        LocalDate heute = LocalDate.now();
        System.out.println(heute);

        System.out.println(heute.lengthOfMonth());
        System.out.println(heute.lengthOfYear());
    }

    public void testMonth() {

        LocalDate heute = LocalDate.now();

        Month month = heute.getMonth();
        System.out.println(month);
        System.out.println(month.firstDayOfYear(false));
        System.out.println(month.maxLength());

        Month nextMonth = month.plus(1);
        System.out.println(nextMonth);
        System.out.println(nextMonth.firstDayOfYear(false));
        System.out.println(nextMonth.maxLength());
    }

}
