#!/bin/bash
printf "%s\n" $(curl -s http://localhost:8080/demo-app/rest/echo2/HelloWorld)
