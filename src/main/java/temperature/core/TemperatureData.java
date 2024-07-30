package temperature.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record TemperatureData (String city, LocalDateTime localDateTime, BigDecimal temperature, boolean isCorrect){
}
