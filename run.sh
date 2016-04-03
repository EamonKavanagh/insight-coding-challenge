#!/usr/bin/env bash



javac -cp ./src/gson-2.6.2.jar ./src/Tweet.java ./src/HashtagGraph.java ./src/Runner.java

java -cp ./src/gson-2.6.2.jar:./src Runner ./tweet_input/tweets.txt ./tweet_output/output.txt