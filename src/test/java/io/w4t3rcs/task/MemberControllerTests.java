package io.w4t3rcs.task;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

@Import(ContainerConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTests {
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void prepare() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    void testPostMember() {
        String body = """
                {
                    "name": "Melar Deot",
                    "books": []
                }
                """;
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/members")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(7))
                .body("name", Matchers.is("Melar Deot"))
                .body("membershipDate", Matchers.is(LocalDate.now().toString()))
                .body("books", Matchers.emptyIterable());
    }

    @Test
    @Order(2)
    void testGetMembers() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/members")
                .then()
                .statusCode(200)
                .body("content", Matchers.not(Matchers.emptyIterable()));
    }

    @Test
    @Order(3)
    void testGetMember() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/members/7")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(7))
                .body("name", Matchers.is("Melar Deot"))
                .body("membershipDate", Matchers.is(LocalDate.now().toString()))
                .body("books", Matchers.emptyIterable());
    }

    @Test
    @Order(4)
    void testPutMember() {
        String body = """
                {
                    "books": [
                        {
                            "title": "Miracle of Life",
                            "author": "Dorman Ofordel"
                        }
                    ]
                }
                """;
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1/members/7")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(7))
                .body("name", Matchers.is("Melar Deot"))
                .body("membershipDate", Matchers.is(LocalDate.now().toString()))
                .body("books", Matchers.hasSize(1));
    }

    @Test
    @Order(5)
    void testDeleteMemberWithBorrowedBook() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/members/7")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(6)
    void testDeleteMember() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/v1/members/2")
                .then()
                .statusCode(204);
    }
}
