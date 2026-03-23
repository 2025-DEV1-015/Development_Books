About this Kata
This short and simple Kata should be performed using Test Driven Development (TDD).

Rules
The rules are described below :

One copy of the five books costs 50 EUR.

If, however, you buy two different books from the series, you get a 5% discount on those two books.
If you buy 3 different books, you get a 10% discount.
With 4 different books, you get a 20% discount.
If you go for the whole hog, and buy all 5, you get a huge 25% discount.
Note that if you buy, say, 4 books, of which 3 are different titles, you get a 10% discount on the 3 that form part of a set, but the 4th book still costs 50 EUR.
Developers seeking to deliver quality products are queueing up with shopping baskets overflowing with these books. Your mission is to write a piece of code to calculate the price of any conceivable shopping basket.


Requirements
Java : 17
Springboot : 3.2.5
Maven : For Dependency management
JUnit : 5.x
Commit Message Style Guide
The project have followed the Udacity Git Commit Message Style Guide, which provides a consistent format for writing commit messages. Each commit messages contains Title. The title consists of the type of the message and subject. type: Subject

Commit Types
feat: A new feature
fix: A bug fix
docs: Changes to documentation
style: Code formatting changes (e.g., fixing indentation, removing spaces, etc.)
refactor: Code refactoring without affecting functionality
test: Adding or refactoring tests
chore: Updates to build processes or auxiliary tools (e.g., package manager configs)
How to Build the Application
Clone this repository:
https://github.com/2025-DEV1-015/Development_Books
Build the project and run the tests by running
mvn clean install
The Model Classes used in the project are generated from the OpenAPI specification during the build process. Running mvn clean install will regenerate the models as part of the build.
Sample Input and Output
The following is a sample input to the Development Books API and the corresponding output:

Sample Input
This JSON input represents the List of Books with title and quantity.

File: src/main/resources/examples/sample-input.json
Example:
{
"books": [
{
"title": "Clean Code",
"quantity": 2
},
{
"title": "The Clean Coder",
"quantity": 2
},
{
"title": "Clean Architecture",
"quantity": 2
},
{
"title": "Test Driven Development by Example",
"quantity": 1
},
{
"title": "Working Effectively With Legacy Code",
"quantity": 1
}
]
}

Sample Output
This is the JSON response for the Development Books with Best Discounted Price.

File: src/main/resources/examples/sample-output.json
Example:
{
"groups": [
{
"books": [
"Clean Code",
"The Clean Coder",
"Clean Architecture",
"Test Driven Development by Example"
],
"groupSize": 4,
"discountPercentage": 20,
"afterdiscountPrice": 160
},
{
"books": [
"Clean Code",
"The Clean Coder",
"Clean Architecture",
"Working Effectively With Legacy Code"
],
"groupSize": 4,
"discountPercentage": 20,
"afterdiscountPrice": 160
}
],
"totalPrice": 400,
"discountedPrice": 320
}

Test reports
Once after successful build of mvn clean install, navigate to target folder of the project root directory
Jacoco code coverage report : Code Coverage report will be available in target\site\jacoco folder. View the report by launching index.html
pi test coverage report: Mutation Coverage report will be available in target\pit-reports folder. View the report by launchig index.html