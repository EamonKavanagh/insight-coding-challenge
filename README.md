# Insight Data Engineering Coding Challenge

A Java solution to the Insight Data Engineering coding challenge (April 2016).  The challenge was to calculate the average degree of a vertex in a Twitter hashtag graph for the last 60 seconds, and update this each time a new tweet appears.

## Description

My main idea was to use hash maps to store the vertex and edge relationships while maintaining an edge priority queue, ordered by timestamp.  This allows for a quick calculation of the average degree, fast insertion, and fast removal when a tweet is evicted.

## Dependencies

- Java 7
- Gson 2.6.2 (http://mvnrepository.com/artifact/com.google.code.gson/gson/2.6.2)
- Python 2.7 (for random tweet generation)

## Exectuion

To execute the code use the _run.sh_ script.

	./run.sh

This will also handle the Gson dependency.

## Tests

Additional tests have been added in the _insight\_testsuite/tests_ folder.  This can be run by using the _run\_tests.sh_ script.  The _run.sh_ script should be executed once before running the tests so dependencies aren’t downloaded multiple times.  

A Python script _generate\_random\_tweets.py_ for generating random valid testing data has been added  to the _data-gen_ directory.  A set of 100 random tweets/rate limit messages have been included in the test directory; to generate a new set run the script as follows.

	cd data-gen
	python generate_random_tweets.py <number of tweets>

The script randomly generates either a tweet (95% probability) or a rate limit message (5% probability).  If a tweet is generated, it can also randomly move the timestamp forward one day to cause an eviction of all current edges.  While not exhausting, the test is useful for having verifiable data and testing scale (by generating many random tweets).


To run the test scripts, execute the following commands:

	cd insight_testsuite
	./run_tests.sh

