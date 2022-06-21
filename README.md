# Conversion API - Shaun Koh

## Instructions
1) Build and Run
- Windows: mvnw.cmd spring-boot:run
- Linux: mvnw spring-boot:run

2) Open a web browser and visit:
- Actuator: http://localhost:8080/actuator/health
    - Basic health check to verify that the application is up and running
- Swagger UI: http://localhost:8080/swagger-ui/index.html
    - To analyse the API specification and understand details regarding endpoints and operations exposed by the application

3) With reference to the specification, test the temperature conversion endpoint by sending a HTTP GET request with the following parameters:
- Celcius to Fahrenheit: http://localhost:8080/conversion/temperature?from=CELCIUS&to=FAHRENHEIT&value=0
    - Response should include a 'value' of '32.0' with 'unit' of 'FAHRENHEIT'
- Fahrenheit to Celcius: http://localhost:8080/conversion/temperature?from=FAHRENHEIT&to=CELCIUS&value=32
    - Response should include a 'value' of '0.0' with 'unit' of 'CELCIUS'
- Exception Handling:
    - Missing Argument: http://localhost:8080/conversion/temperature?from=FAHRENHEIT&to=CELCIUS
        - Response should include a JSON formatted error with a HTTP status code of 400 (BAD REQUEST), timestamp, and message containing details of the exception
    - Invalid Argument: http://localhost:8080/conversion/temperature?from=FAHRENHEIT&to=CELCIUS&value=a
        - Response should include a JSON formatted error with a HTTP status of 400 (BAD REQUEST), timestamp, and message containing details of the exception
    - Global Exception: This can be tested by running the unit tests (particularly "ConversionControllerTest#testTemperatureConversion_InternalServerError" as below)
        - All tests:
            - Windows: mvnw.cmd test
            - Linux: mvnw test
        - All tests for a specific class:
            - Windows: mvnw.cmd test -Dtest="ConversionControllerTest"
            - Linux: mvnw test -Dtest="ConversionControllerTest"
        - Specific test:
            - Windows: mvnw.cmd test -Dtest="ConversionControllerTest#testTemperatureConversion_InternalServerError"
            - Linux: mvnw test -Dtest="ConversionControllerTest#testTemperatureConversion_InternalServerError"

4) Alternatively, you can test a live deployment of the application by replacing 'localhost' with '34.71.20.158' and '8080' with '80'
- e.g. curl "http://34.71.20.158:80/conversion/temperature?from=CELCIUS&to=FAHRENHEIT&value=0"

## Architecture and Development
- Architecture Overview
  - A microservices based solution was chosen as it provides a fit for purpose approach that meet the technical requirements including alignment with modern best practices and adaptation to cloud computing capabilities / services
- API Design
  - Base URL: /conversion
    - This name was chosen to indicate that the API is responsible for providing conversion capabilities and enables extensibility for measurement types other than temperature
  - Endpoint: /temperature
    - This name was chosen to indicate that the endpoint provides a service specific to temperature conversion
  - HTTP Method: GET
    - This was chosen to indicate that the operation is intended for consumption / retrieval of a resource
  - Parameter: Query
    - This was chosen to enable the retrieval of a resource through means of a query / lookup
    - The 'from','to', and 'value' query parameter combination provides a clear intention to convert between units of the same measurement type and enables extensibility for additional units of the same measurement type (e.g. Kelvin)
  - Data Type
    - Strings are used for alphanumeric values where possible
    - Double is used for the 'value' fields as temperature can have significant figures / decimal places and to preserve conversion accuracy
  - Error / Exception
    - A JSON formatted model was chosen to ensure responses containing known errors / exceptions were RESTful
- Implementation
  - Architecture: An MVC pattern was chosen as it provides an appropriate structure for the API with regard to interface, data, and controlling logic
  - Language: Java was chosen primarily to align with the organisation backend technology stack
  - Development Framework: Spring Boot was chosen to leverage established and well-supported capabilities of the Spring framework (e.g. dependency injection, integration with build tools, bootstrapping convenience, etc)
  - Testing Framework: JUnit was chosen as it is not only purpose built for Java but inherently supports a test-driven development approach which aligns with the context of the project given the scope and requirements provided have been clarified upfront 
- Distribution, Scalability, Security
  - The application has been containerised via Docker images (see Dockerfile for implementation) to leverage potential of modern cloud infrastructure capabilities / services ensuring it is highly distributable, scalable, and secure.
  - For instance, the live deployment of the API is primarily managed / supported with the following technologies:
    - GitHub (code repository): https://github.com/shaun-koh/conversion-app
    - Google Container Registry (Docker image store)
    - Google Kubernetes Engine (container orchestration)
- Limitations / Improvements
  - Given the resource constraints for this project, it was decided a minimal build meeting the functional requirements would be sufficient with opportunities / potential for extension incorporated into the design. In particular:
    - Extendability for additional measurement types
    - Extendability for additional units of a given measurement type
    - Extendability for test cases
    - Extendability for improvements with regard to error / exception handling
    - Extendability for improvements with regard to API specification