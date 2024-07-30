package temperature.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import temperature.core.TemperatureReader;
import temperature.web.api.TemperatureController;
import temperature.web.service.TemperatureService;

@SpringBootApplication
public class TemperatureSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(TemperatureSpringBootApplication.class, args);
  }

  @Bean
  public TemperatureService temperatureService() {
    return new TemperatureService(new TemperatureReader());
  }

  @Bean
  TemperatureController temperatureController(TemperatureService temperatureService) {
    return new TemperatureController(temperatureService);
  }

}
