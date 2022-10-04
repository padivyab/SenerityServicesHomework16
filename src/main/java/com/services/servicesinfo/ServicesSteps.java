package com.services.servicesinfo;

import com.services.constants.EndPoints;
import com.services.model.ServicesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.yecht.Data;

import java.util.HashMap;

public class ServicesSteps {

    @Step("Creating services with name :{0}")
    public ValidatableResponse createServices(String name)
    {
        ServicesPojo servicesPojo =new ServicesPojo();
        servicesPojo.setName(name);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(servicesPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);

    }
    @Step("getting services info by name:{0}")
    public HashMap<String,Object> getServicesInfoByName(String name)
    {
        String s1 = "data.findAll{it.name='";
        String s2 = "'}.get(0)";

        return SerenityRest.given().log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(s1+name+s2);

    }
    @Step("update services with serviceId:{0},name :{1}")
    public ValidatableResponse updateServices(int serviceId, String name)
    {
        ServicesPojo servicesPojo =new ServicesPojo();
        servicesPojo.setName(name);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("serviceId",serviceId)
                .body(servicesPojo)
                .when()
                .put(EndPoints.UPDATE_SERVICES_BY_ID)
                .then();

    }
    @Step("deleting services information with servicesid:{0}")
    public ValidatableResponse deleteServicesInfoByid(int serviceId)
    {
        return SerenityRest.given()
                .pathParam("serviceId",serviceId)
                .when()
                .delete(EndPoints.DELETE_SERVICES_BY_ID)
                .then();

    }
    @Step("getting services info by servicesid:{0}")
    public ValidatableResponse getServicesInfoByServicesid(int serviceId)
    {
        return SerenityRest.given().log().all()
                .pathParam("serviceId",serviceId)
                .when()
                .get(EndPoints.GET_SINGLE_SERVICES_BY_ID)
                .then();

    }


}
