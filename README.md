Implementation of the card game set allowing users to play online

Messages between client and server:
===================

Please look at Setserver/src/SetServer/Setserver.java
to find the messages accepted by the server

Please look at SetClient/src/SetClient/SetClient.java
to find the messages accepted by the client

Where all the files are
====================
All the resources (images) are under src/main/resources

To get the resources use path from top level directory (directory containing pom.xml).

For example (src/main/resources/[insert files]) would be the path to a resource.

Or just copy resources into a folder that eclipse/netbeans uses for resources

Build instructions (command line)
====================
Maven is a dependency

Make sure JAVA_HOME to the appropriate path and the java version is >= 1.7.

see makefile or below for build instructions

make compile : compiles everything

make runServer : runs the server

make runClient : runs the client
