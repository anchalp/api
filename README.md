# restful-booker-api-tests

Collection of API tests for Restful-Booker

### Running the tests

You can run tests:
- Using the "Run test" option in IntelliJ IDEA
- Using `mvn clean test` in the terminal

## Allure Reporting

Allure reports are configured for this project. To generate a report:
- `mvn allure:serve` (recommended) - opens a browser with the generated report
- `mvn allure:report` - generates a report in a temp folder

## Project Structure

- `src/main/java` - Main source code (if any business logic is needed)
- `src/test/java` - All test code, requests, DTOs, helpers, and test classes
- `src/test/resources` - Test resources and configuration files
- `pom.xml` - Maven configuration and dependencies

### Key Components
- **DTOs (Data Transfer Objects):** Java classes representing request/response payloads (e.g., `BookingDto`, `BookingDatesDto`).
- **Request Classes:** Encapsulate API calls (e.g., `GetBookingsRequest`).
- **Test Classes:** Contain test cases using JUnit 5 and REST Assured.
- **Helpers:** Utility classes for validation, JSON handling, etc.

## How to Add a New DTO and Tests

### 1. Add a New DTO
1. Create a new Java class in `src/test/java/com/github/eneco/dto/` (or the appropriate package).
2. Define fields matching the API's request/response structure.
3. Use Lombok annotations like `@Data`, `@Builder`, `@NoArgsConstructor`, and `@AllArgsConstructor` for boilerplate code.
4. Example:

```java
package com.github.eneco.dto;

import lombok.Data;

@Data
public class ExampleDto {
    private String field1;
    private int field2;
}
```

### 2. Add/Update Request Classes
1. If needed, create or update a request class in `src/test/java/com/github/eneco/requests/` to use the new DTO for sending/receiving data.
2. Use REST Assured to serialize/deserialize DTOs in API calls.

### 3. Write Tests
1. Create or update a test class in `src/test/java/com/github/eneco/tests/`.
2. Use JUnit 5 annotations (`@Test`, `@ParameterizedTest`, etc.).
3. Use REST Assured to make API calls, passing DTOs as needed.
4. Use AssertJ for assertions on response data and structure.
5. Example:

```java
@Test
void createExampleTest() {
    ExampleDto dto = ExampleDto.builder().field1("value").field2(123).build();
    Response response = given()
        .spec(BaseRequest.setUp())
        .body(dto)
        .when()
        .post("/example")
        .then()
        .extract().response();
    assertThat(response.statusCode()).isEqualTo(201);
    // Further assertions...
}
```

### 4. Run and Validate
- Run your tests using your preferred method.
- Check Allure reports for results and documentation.

## Issues Found
Issues Found

1. Get Booking IDs (https://restful-booker.herokuapp.com/booking)

    Filtering by checkout date doesn’t work. It returns random results instead of bookings with the correct checkout date. I had to disable the test for checkout date filtering because of this.

2. Create Booking (https://restful-booker.herokuapp.com/booking)

    Total price can’t be a decimal number properly; precision is lost when saving.
    Check-in and check-out dates are correctly validated, so invalid bookings are blocked, but the API still returns a 200 OK. A 400 Bad Request would make more sense.

3. Delete Booking (https://restful-booker.herokuapp.com/booking/1)

    Authentication only works with a Cookie header. Using the Authorization header gives 403 Forbidden.
    Successfully deleting a booking returns 201 Created, which is incorrect. 200 OK or 204 No Content would be better.
    Deleting a non-existent booking returns 405 Method Not Allowed. It should return 404 Not Found. I had to disable this test.

4. Health Check (https://restful-booker.herokuapp.com/ping)
    Ping returns 201 Created. A 200 OK would be more appropriate.
