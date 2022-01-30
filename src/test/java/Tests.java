import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class Tests extends TestConfig {

    Faker faker = new Faker();

    // Will fail
    @Test
    public void getEmployees() {
        when()
            .get(employees)
        .then()
            .statusCode(200);
    }

    @Test
    public void getUserInvalidCredentials() {
        String userId = "123";
        when()
            .get(users.concat("/").concat(userId))
        .then()
            .statusCode(401)
        .and()
            .log()
            .body();
    }

    @Test
    public void getEmployeeOauth() {
        int employeeId = 12516;

        given()
            .auth()
            .oauth2(getApiToken())
            .pathParam("id", employeeId)
        .when()
            .get(employeesById)
        .then()
            .statusCode(404);
    }

    @Test
    public void getUserBasicAuth() {
        String userId = "456";

        given()
            .accept(ContentType.JSON)
            .auth()
            .basic("userName", "password")
        .when()
            .get(users.concat("/").concat(userId))
        .then()
            .statusCode(401)
            .log()
            .body();
    }

    @Test
    public void headersTest() {
        given()
            .accept(ContentType.JSON)
        .and()
            .header("my_header", "my_value")
            .cookie("my_cookie", "my_cookie_value")
        .and()
            .auth()
            .oauth2(getApiToken())
        .and()
            .log()
            .all()
        .when()
            .get(employees)
            .then()
            .header("Server", equalTo("nginx"));
    }

    @Test
    public void queryParams() {
        given()
            .accept(ContentType.JSON)
        .and()
            .auth()
            .oauth2(getApiToken())
            .param("page", 0)
            .param("pageSize", 10)
        .when()
            .get(employees)
        .then()
            .log()
            .body()
        .and()
            .body("findAll { it.firstName.equals('Nancy') }", hasSize(1));
    }

    @Test
    public void jsonPathValidation() {
        int employeeId = 3;

        given()
            .accept(ContentType.JSON)
        .and()
            .auth()
            .oauth2(getApiToken())
            .pathParam("id", employeeId)
        .when()
            .get(employeesById)
        .then()
            .statusCode(200)
            .log()
            .body()
        .and()
            .body("employeeId", equalTo(3))
            .body("firstName", equalTo("Janet"));

    }

    @Test
    public void createNewProduct() throws IOException {
        String productJson = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/test/resources/product.json")));

        Response response = (Response) given()
            .spec(requestSpec)
        .and()
            .body(productJson)
        .when()
            .post(products)
        .then()
            .spec(responseOkSpec)
            .extract();

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String productId = jsonPath.getString("productId");

        System.out.println(productId);
    }

    @Test
    public void createNewProductSerializeBody() {
        Product product = new Product(1, true, faker.superhero().name(),
            "1", 1, 1, 200, 11, 1);

        Response response = (Response) given()
            .spec(requestSpec)
        .and()
            .body(product)
        .when()
            .post(products)
        .then()
            .spec(responseOkSpec)
            .extract();

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        String productName = jsonPath.getString("productName");

        System.out.println(productName);
    }

    @Test
    public void createAndDeleteUser() {
        User user = new User(faker.superhero().name(), "123");
        
        Response response = (Response) given()
                .spec(requestSpec)
                .and()
                .body(user)
                .when()
                .post(users)
                .then()
                .spec(responseOkSpec)
                .extract();

        int id = response.path("id");

        given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .delete(usersById)
                .then()
                .statusCode(200);
    }

    @Test
    public void jsonSchemaTest() {
        File jsonSchema = new File(System.getProperty("user.dir") + "/src/test/resources/get-users-json-schema.json");

        given().accept(ContentType.JSON)
                .and()
                .auth()
                .oauth2(getApiToken())
                .param("page", 0)
                .param("pageSize", 10)
                .when()
                .get(users)
                .then()
                .assertThat()
                .body(matchesJsonSchema(jsonSchema));
    }
}
