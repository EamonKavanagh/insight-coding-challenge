## Insight Data Engineering Coding Challenge

A Java solution to the Insight Data Engineering coding challenge (April 2016).  The challenge was to build a Twitter hashtag graph of tweets in a 60 second window with the goal of displaying the average degree as tweets streamed in.  A description of the challenge can be found here: https://github.com/InsightDataScience/coding-challenge.

## Description

My main idea was to use hash maps to store the vertex and edge relationships while maintaining an edge priority queue, ordered by timestamp.  This allows for a quick calculation of the average degree, fast insertion, and fast removal when a tweet is evicted.

## Dependencies

- Java 7
- Gson 2.6.2 (http://mvnrepository.com/artifact/com.google.code.gson/gson/2.6.2)

## Exectuion

To execute the code use the _run.sh_ script.

	./run.sh

This will also handle the Gson dependency.

## Tests

Additional tests have been added in the _insight\_testsuite/tests_ folder.  This can be run by using the _run\_tests.sh_ script.  The _run.sh_ script should be executed once before running the tests so dependencies aren’t downloaded multiple times.

	cd insight_testsuite
	./run_tests.sh

