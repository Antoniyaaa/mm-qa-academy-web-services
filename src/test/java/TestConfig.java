import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;

public class TestConfig {

    protected static String apiToken;
    protected static String employeesById;
    protected static String employees;
    protected static String ordersById;
    protected static String users;
    protected static String products;
    protected static String token;


    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseOkSpec;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://qaacademy.int.devsmm.com/";
        JsonPath.config = new JsonPathConfig("UTF-8");
        RestAssured.defaultParser = Parser.JSON;

        employees ="/api/Employees";
        employeesById = "/api/Employees/{id}";
        ordersById = "/api/{version}/Orders/{id}";
        users = "/api/Users";
        products = "/api/Products";
        token = "/api/Token";
        apiToken = getApiToken();
        requestSpec = getRequestSpecification();
        responseOkSpec = getResponseSpecification();
    }

    protected String getApiToken() {

        User user = new User("iliya.yanev", "Password1!");

        Response response = (Response) given()
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .post(token)
        .then()
            .statusCode(200)
            .log()
            .body()
            .extract();

        apiToken = response.asString();
        return apiToken;
    }

    private RequestSpecification getRequestSpecification() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("my-custom-header", "my-custom-header-value");
        builder.setAccept(ContentType.JSON);
        builder.setContentType(ContentType.JSON);
        builder.setAuth(RestAssured.oauth2(getApiToken()));
        builder.log(LogDetail.HEADERS);
        return builder.build();
    }

    private ResponseSpecification getResponseSpecification() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectContentType(ContentType.JSON);
        builder.expectStatusCode(201);
        builder.expectResponseTime(lessThan(1L), TimeUnit.SECONDS);
        builder.log(LogDetail.BODY);
        return builder.build();
    }
}
