# Most Active Cookie

This service identifies the most active cookie(s) from a csv for a specified date.

## Requirements
- Java 21
- Maven 3.6 or higher

## Building the Project
To build the project, run:
```bash
mvn clean install
```

## Running the Service
To run the service, copy dependencies:
```bash
mvn dependency:copy-dependencies
```
Run the service with:
```bash
java -cp "target/classes;target/dependency/*" org.quantcast.Main -f cookies.csv -d 2018-12-09
```

## Implementation
The service reads a CSV file containing cookie logs and identifies the most active cookie(s) for a specified date. 
The objects defined in this service are:
- cookieTimestampPair: This is a tuple object that holds a cookie and its corresponding timestamp.
- cookieMap: This is a map that stores cookies as keys and their occurrence counts as values.
- cookies: This is a Set of cookies that are the most active for the specified date.

Algorithms used:
- Binary search was used to efficiently identifying the starting index and ending index of the specified date in the sorted list of cookieTimestampPair objects.

## Space Complexity
- Reading the file: O(n), stores all entries in memory
- Binary search: O(1), uses constant space
- HashMap for counting cookies: O(m), where m is the number of unique cookies on the specified date
- Total Space complexity: O(n + m) = O(n)

## Time complexity:
- Reading the file: O(n), where n is the number of lines in the file
- Binary search: O(log n)
- Counting cookies: O(k), where k is the number of entries on the specified date
- Finding max occurrences: O(m), where m is the number of unique cookies on the specified date
- Total Time complexity: O(n + log n + k + m) = O(n)

## Tests
The project includes unit tests for the main functionalities. To run the tests, use:
```bash
mvn test
```