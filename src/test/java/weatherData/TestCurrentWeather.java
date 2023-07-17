package weatherData;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class GetWeatherData {

    String API_Key="a75c87173fc2420f81acc6ba5097f57c";
    @Before
    public void setUp(){
        System.out.println("Setup");
    }

    @Test
    public void testCurrentWeatherLatLon(){
        given().queryParam( "lat", "38").
                queryParam("lon","-78.25").
                queryParam("key",API_Key).
                when().get().then().log().all().statusCode(200)
                .body("data[0].city_name",is(notNullValue()))
                .body("data[0].city_name",is(equalToIgnoringCase("Lake Monticello")));
    }

    @Test
    public void testCurrentWeatherPostcode(){
        Response response = RestAssured.given().queryParam( "postal_code", "28546").
                queryParam("key",API_Key).
                when().get().andReturn();

//        assertThat(String.valueOf(response.getStatusCode()), is(equalToIgnoringCase("200")));
        assert(response.getStatusCode() == 200);
        JsonPath jpath = response.jsonPath();

        assertThat(jpath.get("data[0].city_name"), is(equalToIgnoringCase("Country Club Acres")));
    }

    @After
    public void tearDown(){
        System.out.println("TearDown");

    }

}
