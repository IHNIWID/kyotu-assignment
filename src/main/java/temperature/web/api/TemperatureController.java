package temperature.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import temperature.web.service.AverageTemperatureInYearDTO;
import temperature.web.service.TemperatureService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TemperatureController {

  private final TemperatureService temperatureService;

  public TemperatureController(TemperatureService temperatureService) {
    this.temperatureService = temperatureService;
  }

  @GetMapping(path = "/temperature/average/{city}")
  ResponseEntity<List<AverageTemperaturePerYearResponse>> averageTemperaturePerYear(@PathVariable("city") String city) {
    List<AverageTemperatureInYearDTO> averageTemperatureInYearDTOS = temperatureService.readTemperature(city);

    return averageTemperatureInYearDTOS.isEmpty()
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(averageTemperatureInYearDTOS.stream().map(AverageTemperaturePerYearResponse::fromDto).toList());
  }

}
