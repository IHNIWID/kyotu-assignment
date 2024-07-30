# Large file reading challenge

Welcome in the recruitment challenge.
Write an application that, at the endpoint specified by you, returns the yearly average temperatures for a given city in the format array of objects with the following fields: year, averageTemperature.

## Assumptions

- CSV file with data is no less than 3GB in size.
- The file represents temperature measurements in the format city;yyyy-mm-dd HH:mm:ss.SSS;temp
- The content of the source file may change during the application's running

## How to run?

Project build on Java 21, so you need that, but you can probably downgrade and run on 17.

```xml
mvn clean install
```
Make sure that you've put your csv file under **src/main/resources/data/** and named it **random_weather_data.csv**.
You can of course change whole path and expected file name in `TemperatureReader` class.

then navigate to `TemperatureSpringBootApplication` and click run!

Service will start on a [localhost](http://localhost:8080).
You can also go to [Warsaw endpoint](http://localhost:8080/api/temperature/average/Warszawa) as an example.

## Postmortem
So the objective of this challenge is done, e.g there is an endpoint that returns the yearly average temperatures for
a given city in the format array of objects with the following fields: year, averageTemperature. Could it be done better?
Sure! Would it take even more time? Of course! Overall I've tried to keep it simple.

### If I had more time

So my testing on a file around 3GB in size yielded response in around 145 seconds. Given many constraints around reading
file of this size I don't think I could shrink it even further without some library that I don't know, VM optimization,
multithreading, HAL 9000 grade computer or simply migrating data from file into a DB that will be better at returning
data faster (or at least I think so).

Of course these are not only things that I would consider improving, here's the list:
- read file path from property
- validations for null, blank strings etc
- better exception handling
- improved naming
- different package structure
- web layer and integration tests
- swagger