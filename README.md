###Picture Spring Boot Application
## Technologies Used:
1. Spring Boot.
2. MySQL (version 5.7).
3. Spring Data JPA.
4. Java 8.
5. Swagger.
6. Spring REST.
7. Lombok (POJO annotation library)
8. Spring Security.

## How to run:
1. Firstly please download and install the lombok library from their website https://projectlombok.org/download. (it should be easy to follow the instructions from the installation wizard. This would add the jar into the relevant eclipse installation folder on your system.

2. Download and install the latest Maven if not already installed then, set MAVEN_HOME which will enable using 'mvn' on the commandline. Use the command 'mvn clean install bootRun'. This should install needed dependencies into a repository folder and run the application as a Spring Boot application.
3. Use http://localhost:8082/swagger-ui.html to view the available endpoints.
4. Configure your mySQL server to point to port 3307 or change it to 3306 in the application.yml to use existing default configuration.

*Notes:
Work is still been done to implement external calls to make-up picture meta-data.
