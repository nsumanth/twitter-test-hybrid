package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static utils.AuthUtil.getBearerToken;
import static io.restassured.RestAssured.given;

public class HttpRequests {

    public Response getRequestWithBearer(String uri, Map<String,String> params){
        RestAssured.baseURI = "https://api.twitter.com";
        RequestSpecification request = given()
            .header("Authorization","Bearer "+getBearerToken())
            .log().everything();
        for (String param : params.keySet() ){
            request.param(param,params.get(param));
        }
        return request.get(uri).then().statusCode(200).extract().response();
    }


}
