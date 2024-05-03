package jp.co.axa.apidemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jp.co.axa.apidemo.controllers.AuthenticationRequest;
import jp.co.axa.apidemo.entities.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureTestDatabase
@Slf4j
public class IntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper objectMapper;

    private String token;

    // Generating the token for further used testing api endpoints
    @BeforeEach
    public void setup() {
        RestAssured.port = this.port;
        token = given()
                .contentType(ContentType.JSON)
                .body(AuthenticationRequest.builder().username("user").password("password").build())
                .when().post("/auth/signin")
                .andReturn().jsonPath().getString("token");
        log.debug("Got token:" + token);
    }

    // test for invalid param on post call to employeeapi
    @Test
    public void testInvalidParamsForEmployeeCreation() throws Exception {
        given()

                .contentType(ContentType.JSON)
                .body(Employee.builder().name("test").build())

                .when()
                .post("/api/v1/employees")

                .then()
                .statusCode(401); // Can put these in properties file to put all these https codes
    }

    // test for valid param and token on post call to employeeapi
    @Test
    public void testSavingEmployeeWithValidAuthToken() throws Exception {
        Employee employee = Employee.builder().id(1L).name("Ramesh").salary("10000").department("Tech").build();
        given()
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON)
                .body(Employee.builder().id(1L).name("Ramesh").salary("10000").department("Tech"))

                .when()
                .post("/api/v1/employees")

                .then()
                .statusCode(201); // Can put these in properties file to put all these https codes
    }

    // test for valid param but invalid token on post call to employeeapi
    @Test
    public void testSavingEmployeeWithInvalidAuth() throws Exception {
        given()
                .header("Authorization", "Bearer "+"invalidtoken")
                .contentType(ContentType.JSON)
                .body(Employee.builder().name("test").build())

                .when()
                .post("/api/v1/employees/")

                .then()
                .statusCode(401); // Can put these in properties file to put all these https codes
    }

}
