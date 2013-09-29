#outbound-connector

##Glassfish
###Build and deploy

    mvn clean install
    ./deployEchoResourceAdapterOnGlassfish.sh
    ./setupConnectorResourcesOnGlassfish.sh
    ./deployDemoAppOnGlassfish.sh
    
###Test

    ./test1.sh
    ./test2.sh
    
or

    http://localhost:8080/demo-app/rest/echo/HelloWorld
    http://localhost:8080/demo-app/rest/echo2/HelloWorld
    
###Uninstall

    ./undeployDemoAppOnGlassfish.sh
    ./deleteConnectorResourcesOnGlassfish.sh
    ./undeployEchoResourceAdapterOnGlassfish.sh


