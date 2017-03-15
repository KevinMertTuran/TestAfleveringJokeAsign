package testex.jokefetch;

import static com.jayway.restassured.RestAssured.given;
import testex.Joke;

public class Moma implements IJokeFetcher {

    @Override
    public Joke getJoke() {
        try {
            String joke = given().get("http://api.yomomma.info/").andReturn().jsonPath().getString("joke");
            //String joke = given().get("http://api.yomomma.info/").path("joke");
            return new Joke(joke, "http://api.yomomma.info/");
        } catch (Exception e) {
            return null;
        }
    }

}
