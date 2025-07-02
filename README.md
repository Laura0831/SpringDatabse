# Database

This application creates a database that interconnects Authors with the number of Books that they have individually written. The interconnection is done through 2 separate tables (Author, Book), which are connected through the ID of each Author. To create this connection, I created a DAO for the Author class and a DAO for the Book class. Test and integration tests are being made to test the function of the applications and the connection of DAO with the database. 

~~ Tech Stack ~~

Language: Java

Framework: Spring Boot with Spring Data JPA

Containerization: Docker

Database: PostGreSQL

Cloud: AWS(Ec2, Security Groups)

Testing: Junit, Integration Test

DevOps: Maven

Software: IntelliJ, PostMan