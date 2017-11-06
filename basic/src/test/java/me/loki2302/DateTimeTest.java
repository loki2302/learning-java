package me.loki2302;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    @Test
    public void instantIsAlwaysUtc() {
        // Timezone doesn't make any sense
        try {
            Instant.parse("1970-01-01T00:00:00+01:00");
        } catch(DateTimeParseException e) {
            // intentionally blank
        }
    }

    @Test
    public void januaryFirst1970Is0() {
        Instant instant = Instant.parse("1970-01-01T00:00:00Z");
        assertEquals(0, instant.getEpochSecond());
        assertEquals(0, instant.getNano());
    }

    @Test
    public void zeroIsJanuaryFirst1970() {
        Instant instant = Instant.ofEpochSecond(0, 0);
        assertEquals("1970-01-01T00:00:00Z", instant.toString());
    }

    @Test
    public void instantRepresentsTheAbsoluteTime() {
        Instant instant = Instant.parse("1970-01-01T00:00:00Z");

        // Day of week does not make any sense unless you know
        // how to interpret the "absolute time"
        try {
            instant.get(ChronoField.DAY_OF_WEEK);
            fail();
        } catch (UnsupportedTemporalTypeException e) {
            // intentionally blank
        }

        // Instant seconds do make sense, because it's the only thing instant has
        assertEquals(0, instant.getLong(ChronoField.INSTANT_SECONDS));

        // If we say that we're in UTC timezone, it's Thursday, 00:00
        ZonedDateTime utcZonedDateTime = instant.atZone(ZoneId.of("UTC"));
        assertEquals(DayOfWeek.THURSDAY.getValue(), utcZonedDateTime.get(ChronoField.DAY_OF_WEEK));
        assertEquals(0, utcZonedDateTime.get(ChronoField.HOUR_OF_DAY));

        // If we say that we're in EST timezone, it's Wednesday, 19:00
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("US/Eastern"));
        assertEquals(DayOfWeek.WEDNESDAY.getValue(), zonedDateTime.get(ChronoField.DAY_OF_WEEK));
        assertEquals(19, zonedDateTime.get(ChronoField.HOUR_OF_DAY));
    }

    @Test
    public void localDateTimeRepresentsBirthdaysCombinedWithLocalTimeAsSeenOnAWallClock() {
        LocalDateTime localDateTime = LocalDateTime.parse("1970-01-01T00:00:00");

        // Instant seconds don't make sense, because without timezone you can't
        // find out what the absolute time is
        try {
            localDateTime.getLong(ChronoField.INSTANT_SECONDS);
            fail();
        } catch (UnsupportedTemporalTypeException e) {
            // intentionally blank
        }

        // Days of week and hours of day make sense, because local 00:00 always is local 00:00
        assertEquals(DayOfWeek.THURSDAY.getValue(), localDateTime.get(ChronoField.DAY_OF_WEEK));
        assertEquals(0, localDateTime.get(ChronoField.HOUR_OF_DAY));

        // Given the timezone, it's possible to find out what the absolute time is
        ZonedDateTime utcDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        assertEquals(0, utcDateTime.getLong(ChronoField.INSTANT_SECONDS));

        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        assertEquals(0, instant.getEpochSecond());
    }
}
