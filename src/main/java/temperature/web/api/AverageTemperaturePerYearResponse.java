package temperature.web.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import temperature.web.service.AverageTemperatureInYearDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

class AverageTemperaturePerYearResponse {

  private AverageTemperaturePerYearResponse(String year, BigDecimal averageTemperature){
    this.year = year;
    this.averageTemperature = averageTemperature;
  }

  @JsonProperty
  private String year;

  @JsonProperty
  private BigDecimal averageTemperature;

  public static AverageTemperaturePerYearResponse fromDto(AverageTemperatureInYearDTO averageTemperatureInYearDTO){
    return new AverageTemperaturePerYearResponse(
        averageTemperatureInYearDTO.year(),
        BigDecimal.valueOf(averageTemperatureInYearDTO.averageTemperature()).setScale(1, RoundingMode.HALF_UP)
    );
  }

}
