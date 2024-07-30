package temperature.core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataExtractorsTest {
  private List<CSVRecord> records;

  @BeforeEach
  void setUp() throws IOException {
    try (Reader reader = Files.newBufferedReader(Paths.get("src/test/resources/test_data.csv"))) {
      CSVParser parser = CSVFormat.DEFAULT
          .builder()
          .setDelimiter(';')
          .build()
          .parse(reader);
      records = parser.getRecords();
    }
  }

  @Test
  void testValidData() {
    CSVRecord record = records.get(0);
    TemperatureData data = DataExtractors.extract(record);

    assertNotNull(data);
    assertEquals("New York", data.city());
    assertEquals(LocalDateTime.parse("2024-07-30 12:34:56.789", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), data.localDateTime());
    assertEquals(new BigDecimal("22.5"), data.temperature());
    assertTrue(data.isCorrect());
  }

  @Test
  void testInvalidTemperature() {
    CSVRecord record = records.get(1);
    TemperatureData data = DataExtractors.extract(record);

    assertNotNull(data);
    assertEquals("Los Angeles", data.city());
    assertNull(data.localDateTime());
    assertNull(data.temperature());
    assertFalse(data.isCorrect());
  }

  @Test
  void testInvalidDate() {
    CSVRecord record = records.get(2);
    TemperatureData data = DataExtractors.extract(record);

    assertNotNull(data);
    assertEquals("Chicago", data.city());
    assertNull(data.localDateTime());
    assertEquals(new BigDecimal("20.0"), data.temperature());
    assertFalse(data.isCorrect());
  }

  @Test
  void testInvalidTemperatureAndDate() {
    CSVRecord record = records.get(3);
    TemperatureData data = DataExtractors.extract(record);

    assertNotNull(data);
    assertEquals("Houston", data.city());
    assertNull(data.localDateTime());
    assertNull(data.temperature());
    assertFalse(data.isCorrect());
  }

}