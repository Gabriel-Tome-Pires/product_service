package com.example.product_service.service;


import com.example.product_service.model.Category;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
public class CategoryServiceIntegrationTest {

    String url="/category";
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    @DisplayName("Creating a Category with valid data")
    @Order(1)
    void TestCreateCategory_WithValidData_ReturnCategory() {
        JSONObject category = new JSONObject();
        category.put("name", "phone");

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(category.toJSONString(), headers);

        ResponseEntity<Category> response = testRestTemplate.postForEntity(
                url+"/add",
                request,
                Category.class);
        Category categoryResponse = response.getBody();

        Assertions.assertNotNull(categoryResponse,
                "Category should not be null");
        Assertions.assertEquals("phone", categoryResponse.getName(),
                "Category name should be 'phone'");
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode(),
                "Status code should be 201");
    }

    @Test
    @DisplayName("Get all categories")
    @Order(2)
    void TestGetAllCategories_ReturnCategories() {
        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity request = new HttpEntity(null,headers);

        ResponseEntity<List<Category>> response = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Category>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode(),
                "Status code should be 200");
    }

    @Test
    @DisplayName("Get category by id")
    @Order(3)
    void TestGetCategory_withValidId_ReturnCategories() {

        Category categoryResponse = generateCategory("electronics");


        ResponseEntity<Category> response = testRestTemplate.getForEntity(
                url+"/"+categoryResponse.getId(),
                Category.class
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode(),
                "Status code should be 200");
        Assertions.assertNotNull(response.getBody(),
                "Category should not be null");
        Assertions.assertEquals("electronics",response.getBody().getName(),
                "Category name should be 'electronics'");
    }

    @Test
    @DisplayName("Update category by id")
    @Order(4)
    void TestUpdateCategory_withValidId_ReturnCategory() {
        Category categoryResponse = generateCategory("game");
        String nameCategory = "UpdateGame";

        Category category = new Category(nameCategory);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(category,headers);

        ResponseEntity<Category> response = testRestTemplate.exchange(
                url+"/"+categoryResponse.getId(),
                HttpMethod.PUT,
                request,
                Category.class
        );

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode(),
                "Status code should be 200");
        Assertions.assertEquals(nameCategory.toLowerCase().trim(), response.getBody().getName(),
                "Category name should be 'updateGame'");
    }

    @Test
    @DisplayName("Delete category by id")
    @Order(5)
    void TestDeleteCategory_withValidId_ReturnNoBody() {
        Category categoryResponse = generateCategory("game");

        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(null,headers);

        ResponseEntity<Category> response = testRestTemplate.exchange(
                url+"/delete/"+categoryResponse.getId(),
                HttpMethod.DELETE,
                request,
                Category.class
        );

        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode()
        ,"Status code should be 204");
    }


   private Category generateCategory(String name) {
       JSONObject category = new JSONObject();
       category.put("name", name);

       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);

       HttpEntity<String> createRequest = new HttpEntity<>(category.toJSONString(), headers);

       ResponseEntity<Category> createResponse = testRestTemplate.postForEntity(
               url+"/add",
               createRequest,
               Category.class
       );

       return createResponse.getBody();
   }
}
