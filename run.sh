#!/usr/bin/env bash

if [ ! -f ./src/gson-2.6.2.jar ]; then
	printf "Downloading dependency: gson-2.6.2.jar\n"
	curl -o ./src/gson-2.6.2.jar "http://central.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar"
	printf "Finished downloading\n"
fi

printf "Compiling code...\n\n"
javac -cp ./src/gson-2.6.2.jar ./src/Tweet.java ./src/HashtagGraph.java ./src/Runner.java

printf "Running code with tweets from tweet_input/tweets.txt...\n"
java -cp ./src/gson-2.6.2.jar:./src Runner ./tweet_input/tweets.txt ./tweet_output/output.txt