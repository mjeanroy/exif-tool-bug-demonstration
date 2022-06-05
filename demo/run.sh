#!/bin/bash

i=1

while true
do
  echo "${i} -- curl http://localhost:8080"
  curl http://localhost:8080
  ((i=i+1))
done
