Here is our project whoop de doo
Here's a useful list of links:
https://docs.google.com/document/d/1llOSZ1fAlS3XO4WRITKCzCP2PQK6z_f8ofx5u_fW9vc/edit

MESSAGES BETWEEN CLIENT AND SERVER:

Please look at Setserver/src/SetServer/Setserver.java
to find the messages accepted by the server

Please look at SetClient/src/SetClient/SetClient.java
to find the messages accepted by the client

Where all the files are
====================
All the classes except aaron's stuff is under src/main/java.

(You can copy the files over to eclipse/netbeans)

All the resources (images) are under src/main/resources

To get the resources use path from top level directory (directory containing pom.xml).

For example (src/main/resources/[insert files]) would be the path to a resource.

Or just copy resources into a folder that eclipse/netbeans uses for resources

Build instructions (command line)
====================
Added maven for super easy compilation

You need to get maven if you do not have it.

Also you need your JAVA_HOME to the appropriate path.

Make sure your java version is >= 1.7.

just see makefile for build instructions or look below

make compile : compiles everything

make runServer : runs the server

make runClient : runs the client
