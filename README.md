## Insight Data Engineering Coding Challenge

A Java solution to the Insight Data Engineering coding challenge (April 2016).

## Description

My main idea was to use hash maps to store the vertex and edge relationships while maintaining an edge priority queue, ordered by timestamp.  This allows for a quick calculation of the average degree, fast insertion, and fast removal when a tweet is evicted.

## Dependencies

- Java 7
- Gson 2.6.2 (http://mvnrepository.com/artifact/com.google.code.gson/gson/2.6.2)

## Exectuion

To execute the code use the _.run.sh_ script.

	./run.sh

This will also handle the Gson dependency.

## Tests

Additional tests have been added in the _insight\_testsuite/tests_ folder.  This can be run by using the _run\_tests.sh_ script.

	cd insight\_testsuite
	./run\_tests.sh

