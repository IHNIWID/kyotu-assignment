package temperature.web.service;

import temperature.core.TemperatureReader;

import java.util.List;

public class TemperatureService {

  private final TemperatureReader temperatureReader;

  public TemperatureService(TemperatureReader temperatureReader) {
    this.temperatureReader = temperatureReader;
  }

  public List<AverageTemperatureInYearDTO> readTemperature(String city) {
    return temperatureReader.calculateAverageTemperatureForCityInGivenYear(city);
  }

}
