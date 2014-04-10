compile: 
	mvn compile
runServer: 
	mvn exec:java -Dserver
runClient:
	mvn exec:java -Dclient
clean:
	mvn clean
