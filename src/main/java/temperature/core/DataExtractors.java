package temperature.core;

import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

final class DataExtractors {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
  private static final Logger log = LogManager.getLogger(DataExtractors.class);

  private DataExtractors(){}

  static TemperatureData extract(CSVRecord record) {
    String city = record.get(0);
    String date = record.get(1);
    String temperature = record.get(2);

    BigDecimal temperatureParsed = null;
    LocalDateTime dateParsed = null;
    boolean isCorrect = true;

    try {
      temperatureParsed = new BigDecimal(temperature);
      dateParsed = LocalDateTime.parse(date, formatter);
    } catch (NumberFormatException numberFormatException) {
      log.warn("Invalid temperature string, can't parse to decimal -> temperature:{}", temperature);
      isCorrect = false;
    } catch (DateTimeParseException dateTimeParseException) {
      log.warn("Invalid date string, can't parse to correct date of yyyy-MM-dd HH:mm:ss.SSS pattern -> date:{}", date);
      isCorrect = false;
    }

    return new TemperatureData(city, dateParsed, temperatureParsed, isCorrect);
  }

}
