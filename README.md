[![Build Status](https://travis-ci.org/scheuchzer/outbound-connector.svg?branch=master)](https://travis-ci.org/scheuchzer/outbound-connector)

#outbound-connector

A base implementation of an outbound-connector that uses the connection properties url, username and password.

This project comes with:

* A base connector api
* A base connector implementation
* Sample echo connectors consisting out of
    * echo connector api
    * echo connector implementation
    * extended connector with additional config parameter
* Demo REST applications that uses different instances of the echo connectors

This project demonstrates how easily new external system can be accessed in your applications. 
The major achievement of the connector approach is that connectivity information like url and password are 
configured at container level and not at application level like your war or ejb-jar.
The developer of the business logic does not have to care about the connection and it's configuration exactly as it's done with JDBC connections. 

This project is still work in progress. More features will follow.

##Build and deploy

###Glassfish

Build:
    mvn clean install
    
Deploy:
    examples/urlbased/echo/deployResourceAdapter.sh glassfish
	
Undeploy:
    ./undeployResourceAdapter.sh glassfish

###JBoss/Wildfly

Build:

    mvn clean install
    
Deploy:

    export JBOSS_HOME=...
    examples/urlbased/echo/deployResourceAdapter.sh jboss
    
Undeploy:

    examples/urlbased/echo/undeployResourceAdapter.sh jboss    
    
##Test

    ./test1.sh
    ./test2.sh
    
or

    http://localhost:8080/demo-app/rest/echo/HelloWorld
    http://localhost:8080/demo-app/rest/echo2/HelloWorld
    
