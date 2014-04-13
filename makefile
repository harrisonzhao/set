compile:
	mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=11.2.0 -Dpackaging=jar -Dfile=lib/ojdbc6.jar -DgeneratePom=true
	mvn compile
runServer: 
	mvn exec:java -Dserver
runClient:
	mvn exec:java -Dclient
clean:
	mvn clean
