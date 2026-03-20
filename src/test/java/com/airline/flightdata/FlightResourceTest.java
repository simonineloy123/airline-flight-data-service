package com.airline.flightdata;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class FlightResourceTest {

    private static final String FLIGHT_BODY = """
        {
          "flightNumber": "TST001",
          "origin": "EZE",
          "destination": "MAD",
          "aircraftId": "LV-HKP",
          "scheduledDep": "2026-12-01T10:00:00",
          "scheduledArr": "2026-12-01T22:00:00",
          "passengers": 100,
          "gate": "Z99"
        }
        """;

    @Test
    public void testGetAllFlights() {
        given()
            .when().get("/api/v1/flights")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(notNullValue());
    }

    @Test
    public void testCreateFlight() {
        given()
            .contentType(ContentType.JSON)
            .body(FLIGHT_BODY)
            .when().post("/api/v1/flights")
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .body("flightNumber", equalTo("TST001"))
            .body("status", equalTo("SCHEDULED"))
            .body("delayMinutes", equalTo(0));
    }

    @Test
    public void testCreateFlightWithMissingFields() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"origin\": \"EZE\"}")
            .when().post("/api/v1/flights")
            .then()
            .statusCode(anyOf(is(400), is(422), is(500)));
    }

    @Test
    public void testGetFlightById() {
        String id = given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "flightNumber": "TST002",
                  "origin": "SCL",
                  "destination": "GRU",
                  "aircraftId": "LV-HKP",
                  "scheduledDep": "2026-12-02T10:00:00",
                  "scheduledArr": "2026-12-02T15:00:00",
                  "passengers": 150,
                  "gate": "A01"
                }
                """)
            .when().post("/api/v1/flights")
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .extract().path("id");

        given()
            .when().get("/api/v1/flights/" + id)
            .then()
            .statusCode(200)
            .body("flightNumber", equalTo("TST002"));
    }

    @Test
    public void testApplyDelay() {
        String id = given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "flightNumber": "TST003",
                  "origin": "MIA",
                  "destination": "EZE",
                  "aircraftId": "LV-HKP",
                  "scheduledDep": "2026-12-03T10:00:00",
                  "scheduledArr": "2026-12-03T20:00:00",
                  "passengers": 200,
                  "gate": "B02"
                }
                """)
            .when().post("/api/v1/flights")
            .then()
            .statusCode(anyOf(is(200), is(201)))
            .extract().path("id");

        given()
            .contentType(ContentType.JSON)
            .body("{\"status\": \"DELAYED\", \"delayMinutes\": 90}")
            .when().patch("/api/v1/flights/" + id + "/status")
            .then()
            .statusCode(200)
            .body("status", equalTo("DELAYED"))
            .body("delayMinutes", equalTo(90));
    }

    @Test
    public void testGetFlightByIdNotFound() {
        given()
            .when().get("/api/v1/flights/00000000-0000-0000-0000-000000000000")
            .then()
            .statusCode(404);
    }

    @Test
    public void testHealthCheck() {
        given()
            .when().get("/q/health")
            .then()
            .statusCode(200)
            .body("status", equalTo("UP"));
    }
}