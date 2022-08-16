package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Student;
import models.StudentErrorObject;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utils.Endpoints;
import utils.TestNGListener;


import static io.restassured.RestAssured.given;
import static utils.TestNGListener.data;


public class MyStepdefs {
    Response response;
    Student student;
    Student respStudent;
    long studentID;
    JsonPath jsonPath;
    ObjectMapper objectMapper = new ObjectMapper();
    JSONObject testData;

    @Given("a student")
    public void aStudent() {
        testData = (JSONObject) TestNGListener.data.get("createStudent");

    }


    @When("Creating a student")
    public void creatingAStudent() throws JsonProcessingException {

        student = new Student((Long) testData.get("id"), (String) testData.get("name"), (Long) testData.get("age"),
                (String) testData.get("email"));
        response = given()
                .body(student)
                .when().post(Endpoints.studentEndpoint)
                .then().statusCode(200).extract().response();
        respStudent = objectMapper.readValue(response.asString(), Student.class);

    }

    @Then("Student Controller is created")
    public void studentControllerIsCreated() throws JsonProcessingException {

        respStudent = objectMapper.readValue(response.asString(), Student.class);
        Assert.assertEquals(student.getId(), respStudent.getId());
        Assert.assertEquals(student.getName(), respStudent.getName());
        studentID = respStudent.getId();
    }

    @Then("Getting a student")
    public void gettingAStudent() throws JsonProcessingException {
        response = given()
                .body(student)
                .when().get(Endpoints.studentEndpoint)
                .then().statusCode(200).extract().response();


    }

    @And("Updating the student details")
    public void updatingAStudent() {
        JSONObject testData = (JSONObject) TestNGListener.data.get("updatingAStudent");
        student = new Student((Long) testData.get("id"), (String) testData.get("name"), (Long) testData.get("age"),
                (String) testData.get("email"));
        response = given()
                .body(student)
                .when().put(Endpoints.studentEndpoint + "/" + respStudent.getId())
                .then().statusCode(200).extract().response();

    }

    @Then("Student details must be updated")
    public void studentDetailsMustBeUpdated() throws JsonProcessingException {

        respStudent = objectMapper.readValue(response.asString(), Student.class);
        Assert.assertEquals(student.getName(), respStudent.getName());
        Assert.assertEquals(student.getEmail(), respStudent.getEmail());
        respStudent = objectMapper.readValue(response.asString(), Student.class);

    }

    @Then("deleting the student details")
    public void deletingTheStudentDetails() throws JsonProcessingException {
        response = given()
                .body(student)
                .when().delete(Endpoints.studentEndpoint + "/" + respStudent.getId())
                .then().statusCode(200).extract().response();


    }

    @When("Creating a student without name")
    public void creatingAStudentWithoutName() throws JsonProcessingException {
        JSONObject testData = (JSONObject) TestNGListener.data.get("createStudent");
        student = new Student((Long) testData.get("id"), null, (Long) testData.get("age"),
                (String) testData.get("email"));

    }

    @Then("Name is required error thrown")
    public void nameIsRequiredErrorThrown() throws JsonProcessingException {
        response = given()
                .body(student)
                .when().post(Endpoints.studentEndpoint)
                .then().statusCode(400).extract().response();
        StudentErrorObject studentErrorObject=objectMapper.readValue(response.asString(), StudentErrorObject.class);
        Assert.assertEquals(studentErrorObject.getMessage(), "Name is required");
    }

    @When("Creating a student without age")
    public void creatingAStudentWithoutAge() {
        JSONObject testData = (JSONObject) TestNGListener.data.get("createStudent");
        student = new Student((Long) testData.get("id"), (String) testData.get("name"), 0,
                (String) testData.get("email"));

    }

    @Then("Age is required error thrown")
    public void ageIsRequiredErrorThrown() {
        response = given()
                .body(student)
                .when().post(Endpoints.studentEndpoint)
                .then().statusCode(400).extract().response();
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("message"), "Age is required");
    }

    @When("Creating a student without email")
    public void creatingAStudentWithoutEmail() {
        JSONObject testData = (JSONObject) TestNGListener.data.get("createStudent");
        student = new Student((Long) testData.get("id"), (String) testData.get("name"),(Long) testData.get("age"),
                null);

    }

    @Then("Email is required error thrown")
    public void emailIsRequiredErrorThrown() {
        response = given()
                .body(student)
                .when().post(Endpoints.studentEndpoint)
                .then().statusCode(400).extract().response();
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("message"), "Email is required");
    }

    @When("Creating a student without id")
    public void creatingAStudentWithoutId() {
        JSONObject testData = (JSONObject) TestNGListener.data.get("createStudent");
        student = new Student(0, (String) testData.get("name"),(Long) testData.get("age"),
                (String) testData.get("email"));
    }

    @Then("Internal server error is thrown")
    public void internalServerErrorIsThrown() {

            response = given()
                    .body(student)
                    .when().post(Endpoints.studentEndpoint)
                    .then().statusCode(500).extract().response();
            jsonPath = new JsonPath(response.asString());
            Assert.assertEquals(jsonPath.getString("error"), "Internal Server Error");
    }

    @And("Updating without path param")
    public void updatingWithoutPathParam() {
        JSONObject testData = (JSONObject) TestNGListener.data.get("updatingAStudent");
        student = new Student((Long) testData.get("id"), (String) testData.get("name"), (Long) testData.get("age"),
                (String) testData.get("email"));
        response = given()
                .body(student)
                .when().put(Endpoints.studentEndpoint)
                .then().statusCode(405).extract().response();

    }

    @Then("Method not allowed error is thrown")
    public void methodNotAllowedErrorIsThrown() {
        jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(jsonPath.getString("error"), "Method Not Allowed");

    }

    @And("Deleting without path param")
    public void deletingWithoutPathParam() {
        response = given()
                .body(student)
                .when().delete(Endpoints.studentEndpoint)
                .then().statusCode(405).extract().response();

    }
}