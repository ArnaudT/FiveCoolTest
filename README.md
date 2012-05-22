Technical Assignment
===============

> highly-scalable, fault tolerant application
>
> Akka, Scala, Play!



The objective is to build a fault tolerant system for a simple application that saves data in a key/value data store that doesn't have transaction management.

The application receives JSON data from a RESTful Web service. The application saves the data with the date of creation and increment two types of counters. Two others web services will expose the insight data.

The counter will be used to

1. count the number of hits per user (a hit = one call to the api)
2. have the number of hits per minutes and per hour


Another developer has already started to work on the application, you need to finish it. The code is available via github. You can clone the repository to get the sources.


Web Services to create
---------------------

### POST /api/hit

Third party call this service to create a hit


#### body  (application/json)

The Json sent must contain:

- userId: String
- action: String

#### response

- 200 OK - stat submitted
- 400 Bad Request - the Json is malformed


### GET  /insight/users/:userId

Return the total number of hits per user

#### parameters

- userId: String

#### response

- 200 OK - a json object

```json
{
    "userid": "user",
    "hits": 12
}
```
- 404 Not Found - userId not found


### GET  /insight/hit/:timeresolution/:datetime/

return the number of hit per time resolution

#### parameters

- timeresolution: String [minutely, hourly]
- datetime: Ending timestamp


#### response

- 200 OK - a json object

```json
{
    "timeresolution": "minutely",
    "datetime": 1337564722,
    "value": 67
}
```

- 400 Bad Request - wrong time frame / datetime not valid
- 406 Not Acceptable - time resolution incorrect


Database
---------------------

The database service is available in the database package. You can choose the Scala or the Java version.


### Available operations for the database

1. get(key: String): String
2. put(key: String, value: String)
3. increment(key: String, amount: Long): Long


### Database information

- no transaction available
- the operation of write / read is synchronous (blocking I/O)
- all mutations are atomic
- a StorageException is thrown if the operation failed (assume the database is not stable ;) )


Objectives
---------------------

- We want do save the creation date with the JSON data
- Data must be consistent between data and counters.  This includes consistent handling between the time resolutions and individual user counters.  For example the count of JSON data saved should equal the total number of increments.
- Handle data store errors with the following fault handling:
- if a StorageException is thrown redo the operation.
- if it failed 5 times, you need to do a pause of 10 seconds and try gain
- Messages must not be lost during these failures
- The operations must be non-blocking I/O


We recommend using Akka in Scala to manage the fault handling and create a horizontally scalable application. But you can use pure java/scala or other java libraries to meet the objectives.


Requirements
---------------------

- use Play Framework (at least 2.0.1 Final)
- write some automated tests
- the source code must be under version control
- you can edit the README file in order to explain your choices and how to run the application
- think about performance: Scale up, scale out, fault-tolerance / HA
- the delivery should be a link to a git repository or a zip file (with the project and the git files)


Resource
---------------------

- Play framework: (http://www.playframework.org/)
- Akka:  (http://akka.io/)
- Scala: (http://www.scala-lang.org/)


Bonus
---------------------

Explain how to calculate the number of unique users per time resolution and implement a solution.


You can send the github link of the application or the zip file to careers@fivecool.org