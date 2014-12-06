package me.loki2302;

import org.junit.Test;

import java.time.*;

import static org.junit.Assert.assertEquals;

public class DateTimeTest {
    @Test
    public void canComputeTomorrowDate() {
        LocalDate date = LocalDate.of(2014, 12, 31).plusDays(1);
        assertEquals(2015, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
    }

    @Test
    public void canComputeDateDifference() {
        Period period = Period.between(
                LocalDate.of(2014, 12, 31),
                LocalDate.of(2015, 1, 1));
        assertEquals(1, period.getDays());
    }

    @Test
    public void canComputeTime2SecondsLater() {
        LocalTime time = LocalTime.of(12, 59, 59).plusSeconds(2);
        assertEquals(13, time.getHour());
        assertEquals(0, time.getMinute());
        assertEquals(1, time.getSecond());
    }

    @Test
    public void canComputeTimeDifference() {
        Duration duration = Duration.between(
                LocalTime.of(12, 59, 59),
                LocalTime.of(13, 0, 1));
        assertEquals(2, duration.getSeconds());
    }

    @Test
    public void canComputeTomorrowDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2014, 12, 31, 23, 59, 59).plusSeconds(2);
        assertEquals(2015, dateTime.getYear());
        assertEquals(1, dateTime.getMonthValue());
        assertEquals(1, dateTime.getDayOfMonth());
        assertEquals(0, dateTime.getHour());
        assertEquals(0, dateTime.getMinute());
        assertEquals(1, dateTime.getSecond());
    }

    @Test
    public void canComputeDateTimeDifference() {
        Duration duration = Duration.between(
                LocalDateTime.of(2014, 12, 31, 23, 59, 59),
                LocalDateTime.of(2015, 1, 1, 0, 0, 1));
        assertEquals(2, duration.getSeconds());
    }
}
