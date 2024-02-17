# Simple Currencies Converter Application

## Features
- A Spring Boot application to serve as wrapper for currency conversions between EUR, INR, JPY and USD for given quantity
- A simple HTML UI Form to request currency conversion between two currencies and display output
- Currency conversion rates are powered by another GitHub project [currency-api](https://github.com/fawazahmed0/currency-api)

## Project Dependencies
- Backend wrapper service built using Java 17.0.10, Spring Boot 3.2.0
- UI page is a simple HTML page with associated Javascript and CSS

## Instructions

Step 1: Download & Install JDK 17.0.10 on your machine from [Oracle Website](https://www.oracle.com/java/technologies/downloads/#jdk17)

Step 2: Make sure the JAVA_HOME environment variable points to the above JDK version
- Type ```java -version``` and press Enter on your choice of terminal / command prompt to make sure it displays version 17.0.10
- If you have different version of JDK already installed on your machine, then change the JAVA_HOME env variable to point to 17.0.10

Step 3: In case the JAVA_HOME environment variable is not set-
- For Windows command prompt, run ```set JAVA_HOME=C:\Program Files\Java\jdk-17```
- For **nix based terminal, run ```export JAVA_HOME=/Library/Java/JavaVirtualMachines/open-jdk-17.jdk/Contents/Home``` (* depending on the CPU architecture, the actual path may change)

Step 3: Change the current directory to the wrapper application implementation directory

```cd currencies-converter-wrapper```

Step 4: Run Spring Boot application
- For Windows, run ```.\mvnw spring-boot:run```
- For *nix based terminal, run ```./mvnw spring-boot:run```

- Application runs on localhost:8080
- Exposes an API endpoint ```POST /currency-conversion/convert/{fromCurrency}/{toCurrency}/{quantity}```

Step 5: Go to the currencies-converter-ui directory and open the index.html in your choice of browser

Voila!!

## About the Conversion Wrapper Service

- Project Structure
  - main
    - client - Web Client to fetch conversion rates from APIs
    - configuration - configuration to allow CORS requests
    - controller - Hosts the only API endpoint of the Application
    - error - Error handling for invalid inputs and a Custom RuntimeException
    - model - Currency Enum
    - provider - Conversion Rate Provider factory with a single provider FawazAhmedConversionRateProvider
    - representation - API representation for returning the conversion data
    - service - Fetches conversion rate and calculates the output each upon request
  - test
    - Unit Tests for the application REST API controller

## More
- Run Unit Tests and build using ```mvn clean install```