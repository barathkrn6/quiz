# quiz

# Assusming below things have already installed on the given laptop

	1. Docker
	2. Maven
	3. Java 8
  
# Application setup

Clone the spring boot application and run the below commands (Run these commands from spring boot application root directory)

Step 1 : Build the application to generate the jar file
	
	mvn clean install

Step 2 : Create a docker Image for spring boot application
	
	docker build . -t quiz

Step 3 : Run the spring boot application by linked database docker
	
	docker run -p 8080:8080 --name quiz -d quiz

# Swagger URL

	http://{host_name/ip_addr}:8080/swagger-ui.html
