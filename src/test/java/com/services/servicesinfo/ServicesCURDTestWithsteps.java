package com.services.servicesinfo;

import com.services.testbase.TestBase;
import com.services.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yecht.Data;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ServicesCURDTestWithsteps extends TestBase {

    static String name = "di" + TestUtils.getRandomValue();
    static int serviceId;

    @Steps
    ServicesSteps servicesSteps;

    @Title("This will create a new services")
    @Test
    public void test001()
    {
        ValidatableResponse response = servicesSteps.createServices(name);
        response.log().all().statusCode(201);

    }
    @Title("Verify if Services is created")
    @Test
    public void test002()
    {
        HashMap<String,Object> servicesMap = servicesSteps.getServicesInfoByName(name);
        Assert.assertThat(servicesMap, hasValue(name));
        serviceId = (int) servicesMap.get("id");
        System.out.println(serviceId);

    }
    @Title("update the services information")
    @Test
    public void test003()
    {
        name = name+"update";

        servicesSteps.updateServices(serviceId,name);
        HashMap<String,Object> servicesMap = servicesSteps.getServicesInfoByName(name);
        Assert.assertThat(servicesMap, hasValue(name));

    }
    @Title("Delete services info by servicesid and verify its deleted")
    @Test
    public void test004()
    {
        servicesSteps.deleteServicesInfoByid(serviceId).log().all().statusCode(200);
        servicesSteps.getServicesInfoByServicesid(serviceId).log().all().statusCode(404);

    }

}
