package temperature.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import temperature.web.service.AverageTemperatureInYearDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemperatureReaderTest {

  private static final String TEST_CSV_FILE_PATH = "src/test/resources/test_weather_data.csv";
  private TemperatureReader temperatureReader;

  @BeforeEach
  void setUp() {
    temperatureReader = new TemperatureReader(TEST_CSV_FILE_PATH);
  }

  @Test
  void testCalculateAverageTemperatureForCityInGivenYear_validData() {
    List<AverageTemperatureInYearDTO> result = temperatureReader.calculateAverageTemperatureForCityInGivenYear("Chicago");

    assertNotNull(result);
    assertEquals(1, result.size());

    AverageTemperatureInYearDTO avgTemp = result.get(0);
    assertEquals("2023", avgTemp.year());
    assertEquals(-4.0, avgTemp.averageTemperature());
  }

  @Test
  void testCalculateAverageTemperatureForCityInGivenYear_invalidData() {
    List<AverageTemperatureInYearDTO> result = temperatureReader.calculateAverageTemperatureForCityInGivenYear("Houston");

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testCalculateAverageTemperatureForCityInGivenYear_noDataForCity() {
    List<AverageTemperatureInYearDTO> result = temperatureReader.calculateAverageTemperatureForCityInGivenYear("NonExistentCity");

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void testCalculateAverageTemperatureForCityInGivenYear_validDataMultipleYears() {
    List<AverageTemperatureInYearDTO> result = temperatureReader.calculateAverageTemperatureForCityInGivenYear("New York");

    assertNotNull(result);
    assertEquals(1, result.size());

    AverageTemperatureInYearDTO avgTemp = result.get(0);
    assertEquals("2024", avgTemp.year());
    assertEquals(22.5, avgTemp.averageTemperature());
  }
}
