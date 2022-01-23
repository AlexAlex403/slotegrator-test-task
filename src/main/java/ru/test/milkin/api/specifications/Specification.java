package ru.test.milkin.api.specifications;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static ru.test.milkin.constants.PropertyKey.BASIC_AUTH_USERNAME;
import static ru.test.milkin.constants.PropertyKey.TEST_API_BASE_URL;
import static ru.test.milkin.managers.PropertyManager.getProperty;

public class Specification {

    public static RequestSpecification getRequestSpecGuestBasicAuth() {
        RequestSpecification spec = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setBaseUri(getProperty(TEST_API_BASE_URL))
                .setContentType("application/json")
                .build();
        return spec.auth().preemptive().basic(getProperty(BASIC_AUTH_USERNAME), "");
    }

    public static RequestSpecification getRequestSpecProtectedOauth2(String accessToken) {
        RequestSpecification spec = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .setBaseUri(getProperty(TEST_API_BASE_URL))
                .setContentType("application/json")
                .build();
        return spec.auth().preemptive().oauth2(accessToken);
    }

    public static ResponseSpecification getResponseSpecOK200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json;charset=UTF-8")
                .build();
    }

    public static ResponseSpecification getResponseSpecCreated201() {
        return new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .expectStatusCode(201)
                .expectContentType("application/json;charset=UTF-8")
                .build();
    }

    public static ResponseSpecification getResponseSpecNotFound404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    public static void installSpecification(RequestSpecification requestSpec, ResponseSpecification responseSpec) {
        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }

    public static void installSpecification(RequestSpecification requestSpec) {
        RestAssured.requestSpecification = requestSpec;
    }

    public static void installSpecification(ResponseSpecification responseSpec) {
        RestAssured.responseSpecification = responseSpec;
    }
}
