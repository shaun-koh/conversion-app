# Conversion API - Shaun Koh

## Instructions

1) Build and Run
  - On Windows: mvnw.cmd spring-boot:run
  - On Linux: mvnw spring-boot:run

2) Open a web browser and visit:
  - Actuator: http://localhost:8080/actuator/health
    - To verify that the application is up and running
  - Swagger UI: http://localhost:8080/swagger-ui/index.html
    - To analyse the API specification and understand details regarding endpoints and operations exposed by the application

3) With reference to the specification, test the temperature conversion endpoint by sending a HTTP GET request with the following parameters:
  - Celcius to Fahrenheit: http://localhost:8080/conversion/temperature?from=CELCIUS&to=FAHRENHEIT&value=0
    - Response should include a 'value' of '32.0' with 'unit' of 'FAHRENHEIT'
  - Fahrenheit to Celcius: http://localhost:8080/conversion/temperature?from=FAHRENHEIT&to=CELCIUS&value=32
    - Response should include a 'value' of '0.0' with 'unit' of 'CELCIUS'
  - Exception Handling:
    - Missing Argument: http://localhost:8080/conversion/temperature?from=FAHRENHEIT&to=CELCIUS
      - Response should return a JSON formatted Error model with a HTTP status code of 400 (BAD REQUEST)
    - Invalid Argument: http://localhost:8080/conversion/temperature?from=FAHRENHEIT&to=CELCIUS&value=a
      - Response should return a JSON formatted Error model with a HTTP status of 400 (BAD REQUEST)

4) Alternatively, you can test a live deployment of the application by replacing 'localhost' with '34.71.20.158' and '8080' with '80'
  - e.g. curl "http://34.71.20.158:80/conversion/temperature?from=CELCIUS&to=FAHRENHEIT&value=0"

## Design and Implementation
- 
