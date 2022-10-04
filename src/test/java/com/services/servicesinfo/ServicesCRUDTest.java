package com.services.servicesinfo;

import com.services.constants.EndPoints;
import com.services.model.ServicesPojo;
import com.services.testbase.TestBase;
import com.services.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.yecht.Data;

import java.util.HashMap;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.Matchers.hasValue;

public class ServicesCRUDTest extends TestBase {

    static String name = "di" + TestUtils.getRandomValue();
    static int serviceId;

    @Title("This will create a new services")
    @Test
    public void test001()
    {
        ServicesPojo servicesPojo =new ServicesPojo();
        servicesPojo.setName(name);

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(servicesPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);

    }
    @Title("Verify if student was created")
    @Test
    public void test002()
    {
        String s1 = "data.findAll{it.name='";
        String s2 = "'}.get(0)";
        HashMap<String,Object> servicesMap = SerenityRest.given().log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(s1+name+s2);
        Assert.assertThat(servicesMap, hasValue(name));
        serviceId = (int) servicesMap.get("id");


    }
    @Test
    public void test003()
    {
        name = name+"update";

        ServicesPojo servicesPojo =new ServicesPojo();
        servicesPojo.setName(name);

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("serviceId",serviceId)
                .body(servicesPojo)
                .when()
                .put(EndPoints.UPDATE_SERVICES_BY_ID)
                .then().log().all().statusCode(200);
        String s1 = "data.findAll{it.name='";
        String s2 = "'}.get(0)";
        HashMap<String,Object> servicesMap = SerenityRest.given().log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(s1+name+s2);
        Assert.assertThat(servicesMap, hasValue(name));


    }
    @Title("Delete the student and verify if the student is deleted")
    @Test
    public void test004()
    {
        SerenityRest.given()
                .pathParam("serviceId",serviceId)
                .when()
                .delete(EndPoints.DELETE_SERVICES_BY_ID)
                .then()
                .statusCode(200);
        given().log().all()
                .pathParam("serviceId",serviceId)
                .when()
                .get()
                .then()
                .statusCode(404);

    }


}
