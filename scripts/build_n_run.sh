#! /usr/bin/bash
rm -r target | echo "No target directory found"
mvn compile
mvn exec:java -Dexec.mainClass=org.example.Main -Dexec.args="'$1'"