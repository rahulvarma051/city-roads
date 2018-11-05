Project Description
---------------------
This project will help you determine if 2 cities are connected by roadways.


Considerations
--------------
    - Gradle as a build tool
    - API headers
        - Used "correlation-id" as an optional header. The reason for using this header, is that i believe it as a good practice to use "correlation-id" headers as part of each request.
            As it helps to track the request transaction in the logs. The correlation-id should be unique per request.


How To Use
------------

1. Build and Run the Application
    - Open Terminal and reach the path of application home
    - execute command "gradlew clean build"   (This step will compiles the app, run the test cases, and builds the app creating an executable jar)
    - After success, go to build\libs folder to find executable jar
    -  cd build\libs
    - Then execute command "gradlew java -jar city-roads-0.0.1-SNAPSHOT.jar"
    (OR)
    1.1 Run the application as SpringBootApplication (or) find the CityRoadsApplication file inside the "src" folder and do Run Application

2. Open Browser and enter
    - http://localhost:8080/swagger-ui.html (app enabled with swagger)

3. Click on Try It Out button, which will expose the API along with attributes that can be used as part of service.
    - Provide Origin City, and Destination City in their respective text box placeholders
    - Click on Execute, this will provide the result in the ResponseBody section of swagger
        - "Yes" (If cities are connected) or
        - "No" (If cities are not connected)



Enabled With
-------------
  - Logstash(logback.xml) - On enabling json format log pattern, it provides flexibility to write smart queries for log search on log engines like ELK, or even splunk dashboard.
  - Swagger UI - Ease of using API's in a browser, the user does not need any hands-on with postman or other API tools
