package utils;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static io.restassured.RestAssured.given;

public class AuthUtil {

    public static String  getBearerToken(){
        if ( true) {
            return "AAAAAAAAAAAAAAAAAAAAAALF%2FQAAAAAALRWNULyfgi76PsFaDBCHXeZVVKs%3DGX4NKmdsnJpXglnfoWaRxH8eN0AEFfMWGjCW73YBLrmzIHRiw8";
        } else {
                String consumerKey = twitterTestFileUtils.prop.getProperty("CONSUMER_KEY");
                String consumerSecret = twitterTestFileUtils.prop.getProperty("CONSUMER_SECRET");
                RestAssured.baseURI = "https://api.twitter.com";
                Response response = given().log().everything()
                    .config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                            .encodeContentTypeAs("x-www-form-urlencoded",
                                ContentType.URLENC)))
                    .header("Authorization", "Basic " + authString(consumerKey, consumerSecret))
                    .contentType(ContentType.URLENC.withCharset("UTF-8"))
                    .formParam("grant_type", "client_credentials")
                    .post("/oauth2/token")
                    .then()
                    .log().everything()
                    .extract().response();
                return response.getBody().jsonPath().getString("access_token");

            }
        }


    public static String authString(String consumerKey, String consumerSecret) {
        return Base64.getEncoder().encodeToString(
                String.format("%s:%s",consumerKey ,consumerSecret)
                    .getBytes(StandardCharsets.UTF_8));
    }

}
