package com.example.product_service.service;

import com.example.product_service.model.Category;
import com.example.product_service.model.ProductStatus;
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
public class ProductStatusIntegrationTest {

    String url="/productStatus";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Creating a productStatus with valid data")
    @Order(1)
    void TestCreateProductStatus_WithValidData_ReturnProductStatus() {
        JSONObject productStatus = new JSONObject();
        productStatus.put("name", "inCart");

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(productStatus.toJSONString(), headers);

        ResponseEntity<ProductStatus> response = testRestTemplate.postForEntity(
                url+"/add",
                request,
                ProductStatus.class);
        ProductStatus categoryResponse = response.getBody();

        Assertions.assertNotNull(categoryResponse,
                "Product Status should not be null");
        Assertions.assertEquals("incart", categoryResponse.getName(),
                "Product Status name should be 'incart'");
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode(),
                "Status code should be 201");
    }

    @Test
    @DisplayName("Get all product Status")
    @Order(2)
    void TestGetAllProductStatus_ReturnAllProductStatus(){
        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept","application/json" );
        HttpEntity request= new HttpEntity<>(null,headers);

        ResponseEntity<List<ProductStatus>> response = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<ProductStatus>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    @DisplayName("Get ProductStatus by id")
    @Order(3)
    void TestGetProductStatusById_WithValidId_ReturnProductStatus(){
        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept","application/json" );
        HttpEntity request= new HttpEntity<>(null,headers);

        String name="Avalible";
        ProductStatus productStatus=createProductStatus(name);

        ResponseEntity<ProductStatus> response = testRestTemplate.exchange(
                url+"/"+productStatus.getId(),
                HttpMethod.GET,
                request,
                ProductStatus.class
        );

        Assertions.assertNotNull(response.getBody(),
                "Product Status should not be null");
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(name.trim().toLowerCase(),response.getBody().getName());
    }

    @Test
    @DisplayName("Update Product Status")
    @Order(4)
    void TestUpdateProductStatus_WithValidData_UpdateProductStatus(){
        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept","application/json" );

        String name="UpdatedSent";
        ProductStatus productStatus=createProductStatus("sent");
        ProductStatus updatedProductStatus=new ProductStatus(name);

        HttpEntity request= new HttpEntity<>(updatedProductStatus,headers);

        ResponseEntity<ProductStatus> response = testRestTemplate.exchange(
                url+"/"+productStatus.getId(),
                HttpMethod.PUT,
                request,
                ProductStatus.class
        );

        Assertions.assertNotNull(response.getBody(),
                "Product Status should not be null");
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode(),
                "Status code should be 200");
        Assertions.assertEquals(name.trim().toLowerCase(),response.getBody().getName(),
                "Product Status name should be 'updatedsent'");
    }

    @Test
    @DisplayName("Delete product status by id")
    @Order(5)
    void DeleteProductStatus_WithValidId_DeleteProductStatus(){
        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity request= new HttpEntity<>(null,headers);

        ProductStatus productStatus=createProductStatus("purchased");

        ResponseEntity<ProductStatus> response = testRestTemplate.exchange(
                url+"/delete/"+productStatus.getId(),
                HttpMethod.DELETE,
                request,
                ProductStatus.class
        );

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private ProductStatus createProductStatus(String name){
        JSONObject productStatus = new JSONObject();
        productStatus.put("name", name);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(productStatus.toJSONString(), headers);

        ResponseEntity<ProductStatus> response = testRestTemplate.postForEntity(
                url+"/add",
                request,
                ProductStatus.class);
        return response.getBody();
    }
}
