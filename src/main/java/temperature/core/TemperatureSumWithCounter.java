package temperature.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

class TemperatureSumWithCounter {

  private BigDecimal temperatureSum;
  private long counter;

  TemperatureSumWithCounter(BigDecimal temperature) {
    this.temperatureSum = temperature;
    this.counter = 1;
  }

  void addTemperature(BigDecimal temperature) {
    this.temperatureSum = this.temperatureSum.add(temperature);
    this.counter++;
  }

  BigDecimal getAverageTemperature() {
    return this.temperatureSum.divide(BigDecimal.valueOf(counter), 1, RoundingMode.HALF_UP);
  }

  @Override
  public String toString() {
    return "TemperatureSumWithCounter{" +
        "counter=" + counter +
        ", temperatureSum=" + temperatureSum +
        '}';
  }
}
