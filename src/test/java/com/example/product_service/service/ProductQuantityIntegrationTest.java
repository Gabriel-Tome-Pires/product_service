package com.example.product_service.service;

import com.example.product_service.model.*;
import com.example.product_service.repository.CategoryRepository;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.repository.ProductStatusRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
public class ProductQuantityIntegrationTest {
    private String url = "/ProductQuantity";
    private static Product product;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductStatusRepository productStatusRepository;

    @BeforeAll
    void setup(){
        ProductStatus productStatus = new ProductStatus("name");
        Category category = new Category("category");
        categoryRepository.save(category);
        productStatusRepository.save(productStatus);

        product = new Product("Name","Description",15.0,
                category, productStatus,
                1,"aedskqwtyh");
        productRepository.save(product);
        
        product=productRepository.findAll().get(0);
    }

    @Test
    @DisplayName("Create")
    void TestCreateProductQuantity_WithValidData_Returns201() {
        JSONObject productJson = new JSONObject();
        productJson.put("id", product.getId());

        JSONObject productQuantityJson = new JSONObject();
        productQuantityJson.put("quantity", 15);
        productQuantityJson.put("product", productJson);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        
        HttpEntity request = new HttpEntity(productQuantityJson,headers);
        ResponseEntity<ProductQuantity> response = restTemplate.exchange(
                url+"/add",
                HttpMethod.POST, 
                request,
                ProductQuantity.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotNull(response.getBody().getId());
        Assertions.assertEquals(15, response.getBody().getQuantity());
    }

    @Test
    @DisplayName("Get all")
    void GetAllProductsQuantity_WithValidData_Returns200() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<List<ProductQuantity>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<ProductQuantity>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Get by id")
    void GetProductQuantityById_WithValidData_Returns200() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        ProductQuantity productQuantity = generateProductQuantity();
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<ProductQuantity> response = restTemplate.exchange(
                url+"/"+productQuantity.getId(),
                HttpMethod.GET,
                request,
                ProductQuantity.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(productQuantity.getQuantity(), response.getBody().getQuantity());
    }

    @Test
    @DisplayName("Get by product id")
    void GetOrderItemById_WithValidInput_ReturnOrderItem(){
        ProductQuantity productQuantity = generateProductQuantity();

        HttpHeaders headers=new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity request=new HttpEntity(null,headers);
        ResponseEntity<ProductQuantity> response= restTemplate.exchange(
                url+"/byProduct/"+product.getId(),
                HttpMethod.GET,
                request,
                ProductQuantity.class
        );
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(productQuantity.getId(),response.getBody().getId());
    }

    @Test
    @DisplayName("Update")
    void TestUpdateProductQuantity_WithValidData_Returns200() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        ProductQuantity productQuantity = generateProductQuantity();
        ProductQuantity updatedProductQuantity=new ProductQuantity(5,product);

        HttpEntity request = new HttpEntity(updatedProductQuantity,headers);

        ResponseEntity<ProductQuantity> response = restTemplate.exchange(
                url+"/"+productQuantity.getId(),
                HttpMethod.PUT,
                request,
                ProductQuantity.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(updatedProductQuantity.getQuantity(), response.getBody().getQuantity());
    }

    @Test
    @DisplayName("Delete")
    void TestDeleteProductQuantity_WithValidData_Returns200() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        ProductQuantity productQuantity = generateProductQuantity();
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<ProductQuantity> response = restTemplate.exchange(
                url+"/delete/"+productQuantity.getId(),
                HttpMethod.DELETE,
                request,
                ProductQuantity.class
        );

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private ProductQuantity generateProductQuantity() {
        JSONObject productJson = new JSONObject();
        productJson.put("id", product.getId());

        JSONObject productQuantityJson = new JSONObject();
        productQuantityJson.put("quantity", 15);
        productQuantityJson.put("product", productJson);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity request = new HttpEntity(productQuantityJson,headers);
        ResponseEntity<ProductQuantity> response = restTemplate.exchange(
                url+"/add",
                HttpMethod.POST,
                request,
                ProductQuantity.class);

        return response.getBody();
    }
}
