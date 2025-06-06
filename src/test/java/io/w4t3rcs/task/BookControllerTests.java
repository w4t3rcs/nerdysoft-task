package io.w4t3rcs.task;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(ContainerConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTests {
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void prepare() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void testPostBook() {
        String body = """
                {
                    "title": "Jeans Products",
                    "author": "Stacey Lewssss",
                    "amount": 1
                }
                """;
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/books")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(6))
                .body("title", Matchers.is("Jeans Products"))
                .body("author", Matchers.is("Stacey Lewssss"))
                .body("amount", Matchers.is(1));
    }

    @Test
    @Order(2)
    void testDuplicatePostBook() {
        String body = """
                {
                    "title": "Jeans Products",
                    "author": "Stacey Lewssss",
                    "amount": 1
                }
                """;
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/books")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(6))
                .body("title", Matchers.is("Jeans Products"))
                .body("author", Matchers.is("Stacey Lewssss"))
                .body("amount", Matchers.is(2));
    }

    @Test
    @Order(3)
    void testGetBooks() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/books")
                .then()
                .statusCode(200)
                .body("content", Matchers.not(Matchers.emptyIterable()));
    }

    @Test
    @Order(4)
    void testGetBooksByMemberName() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/books?memberName={name}", "Bluck Nugget")
                .then()
                .statusCode(200)
                .body("content", Matchers.not(Matchers.emptyIterable()));
    }

    @Test
    @Order(5)
    void testGetBooksNames() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/books/borrowed-names")
                .then()
                .statusCode(200)
                .body("content", Matchers.not(Matchers.emptyIterable()));
    }

    @Test
    @Order(6)
    void testGetBooksNamesAndAmounts() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/books/borrowed-names-amounts")
                .then()
                .statusCode(200)
                .body("content", Matchers.not(Matchers.emptyIterable()));
    }

    @Test
    @Order(7)
    void testGetBook() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/books/1")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(1))
                .body("title", Matchers.is("Boooo"))
                .body("author", Matchers.is("Kim Kom"))
                .body("amount", Matchers.is(3));
    }

    @Test
    @Order(8)
    void testPutBook() {
        String body = """
                {
                    "author": "Dabe Lonye",
                    "amount": 5
                }
                """;
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1/books/4")
                .then()
                .statusCode(200)
                .body("title", Matchers.is("Miracle of Life"))
                .body("author", Matchers.is("Dabe Lonye"))
                .body("amount", Matchers.is(5));
    }

    @Test
    @Order(9)
    void testDeleteBorrowedBook() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/books/3")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(10)
    void testDeleteBook() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/books/6")
                .then()
                .statusCode(204);
    }
}
