package it.dedagroup.microservices.trec.appversionchecker.tests;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import it.dedagroup.microservices.trec.appversionchecker.enums.Platform;
import it.dedagroup.microservices.trec.rest.requests.AppVersionRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ControllerTest {
	
	@Test
	public void testGetAppVersion() {
		
		given()
		.params("platform", "IOS")
		.when()
		.get("/api/appversion")
		.then()
		.statusCode(200)
		.body(is("1.0.1"));
		
	}
	
	@Test
	public void testInsertNewVersion() {
		
		AppVersionRequest avReq = new AppVersionRequest(Platform.IOS, "1.0.1");
		
		given()
		.header("Content-type", "application/json")
		.header("Authorization", "Basic YZRtaW46YWRtaW5wYXNzd29yZA==")
		.and()
		.body(avReq)
		.when()
		.post("/api/appversion/admin")
		.then()
		.statusCode(401);
		
		given()
		.header("Content-type", "application/json")
		.header("Authorization", "Basic YWRtaW46YWRtaW5wYXNzd29yZA==")
		.and()
		.body(avReq)
		.when()
		.post("/api/appversion/admin")
		.then()
		.statusCode(400);
		
		avReq.setVersion("111.1.1");
		given()
		.header("Content-type", "application/json")
		.header("Authorization", "Basic YWRtaW46YWRtaW5wYXNzd29yZA==")
		.and()
		.body(avReq)
		.when()
		.post("/api/appversion/admin")
		.then()
		.statusCode(201);
		
	}
	
	@Test
	public void testDeleteVersion() {
		
		AppVersionRequest avReq = new AppVersionRequest(Platform.IOS, "111.1.1");
		
		given()
		.header("Content-type", "application/json")
		.header("Authorization", "Basic YWRtaW46YWRtaW5wYXNzd29yZA==")
		.and()
		.body(avReq)
		.when()
		.delete("/api/appversion/admin")
		.then()
		.statusCode(204);
		
	}

}
