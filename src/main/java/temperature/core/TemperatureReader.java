package temperature.core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import temperature.web.service.AverageTemperatureInYearDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

import static temperature.core.DataExtractors.extract;


public class TemperatureReader {

  private final String csvFilePath;
  private final Logger log = LogManager.getLogger(TemperatureReader.class);

  public TemperatureReader() {
    this.csvFilePath = "src/main/resources/data/random_weather_data.csv";
  }

  public TemperatureReader(String csvFilePath) {
    this.csvFilePath = csvFilePath;
  }

  public List<AverageTemperatureInYearDTO> calculateAverageTemperatureForCityInGivenYear(String city) {
    List<AverageTemperatureInYearDTO> averageTemperatureInYearDTOList = new ArrayList<>();

    for (Map.Entry<Integer, TemperatureSumWithCounter> entry : getTemperatureForCityGroupedByYear(city).entrySet()) {
      averageTemperatureInYearDTOList.add(
          new AverageTemperatureInYearDTO(
              entry.getKey().toString(),
              entry.getValue().getAverageTemperature().doubleValue()
          )
      );
    }

    return averageTemperatureInYearDTOList;
  }

  private Map<Integer, TemperatureSumWithCounter> getTemperatureForCityGroupedByYear(String city) {
    Map<Integer, TemperatureSumWithCounter> yearToAverageTemperatureMap = new ConcurrentHashMap<>();

    long startTime = System.currentTimeMillis();

    try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {

      Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
          .setHeader(Headers.class)
          .setDelimiter(";")
          .build()
          .parse(reader);

      StreamSupport
          .stream(records.spliterator(), true)
          .forEach(record -> {
            TemperatureData temperatureData = extract(record);

            if (temperatureData.city().equals(city) && temperatureData.isCorrect()) {
              int year = temperatureData.localDateTime().getYear();
              BigDecimal temperature = temperatureData.temperature();

              yearToAverageTemperatureMap.compute(year, (key, value) -> {
                if (value == null) {
                  return new TemperatureSumWithCounter(temperature);
                } else {
                  value.addTemperature(temperature);
                  return value;
                }
              });
            }
          });

      long endTime = System.currentTimeMillis();
      log.info("Parsing took {} milliseconds", (endTime - startTime));

      return yearToAverageTemperatureMap;
    } catch (IOException ioException) {
      log.error("Error reading CSV file: {}", ioException.getMessage());
      log.debug(ioException.getStackTrace());
      return Collections.emptyMap();
    }

  }

}
