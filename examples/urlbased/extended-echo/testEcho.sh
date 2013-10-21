#!/bin/bash
printf "%s\n" $(curl -s http://localhost:8080/extended-demo-app/rest/echo/HelloWorld)
