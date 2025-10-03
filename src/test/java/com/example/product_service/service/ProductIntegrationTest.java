package com.example.product_service.service;

import com.example.product_service.model.Category;
import com.example.product_service.model.OrderItem;
import com.example.product_service.model.Product;
import com.example.product_service.model.ProductStatus;
import com.example.product_service.repository.CategoryRepository;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.repository.ProductStatusRepository;
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
public class ProductIntegrationTest {
    private String url = "/product";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductStatusRepository productStatusRepository;

    private Category category;
    private ProductStatus productStatus;

    @BeforeAll
    void setUp() {
        category = new Category("Category");
        productStatus = new ProductStatus("Product Status");
        productStatusRepository.save(productStatus);
        categoryRepository.save(category);

        category = categoryRepository.findAll().get(0);
        productStatus = productStatusRepository.findAll().get(0);
    }

    @Test
    @DisplayName("Create")
    void TestCreateProduct_WithValidData_ReturnsProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Product product = new Product("PlayStation 5", "Description", 15.0,
                category, productStatus,
                1, "aedskqwtyh");

        HttpEntity request = new HttpEntity(product, headers);

        ResponseEntity<Product> response = restTemplate.postForEntity(
                url + "/add",
                request,
                Product.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(product.getName(), response.getBody().getName());
    }

    @Test
    @DisplayName("Get all")
    void TestGetAllProducts_WithValidData_ReturnsAllProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Product>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Get by id")
    void TestGetProductById_WithValidData_ReturnsProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Product product=generateProduct();

        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<Product> response = restTemplate.exchange(
                url+"/"+product.getId(),
                HttpMethod.GET,
                request,
                Product.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(product.getName(), response.getBody().getName());
    }

    @Test
    @DisplayName("Update")
    void TestUpdateProduct_WithValidData_ReturnsProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        Product product = generateProduct();
        Product updateProduct=new Product("PlayStation 4", "Description", 18,
                category, productStatus,
                1, "aedskqwtyh");
        HttpEntity request = new HttpEntity(updateProduct,headers);

        ResponseEntity<Product> response = restTemplate.exchange(
                url+"/"+product.getId(),
                HttpMethod.PUT,
                request,
                Product.class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(updateProduct.getName(), response.getBody().getName());
        Assertions.assertEquals(updateProduct.getPrice(), response.getBody().getPrice());
    }

    @Test
    @DisplayName("Delete")
    void TestDeleteProduct_WithValidData_ReturnsProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        Product product = generateProduct();
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<Product> response = restTemplate.exchange(
                url+"/delete/"+product.getId(),
                HttpMethod.DELETE,
                request,
                Product.class
        );
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private Product generateProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Product product = new Product("PlayStation", "Description", 15.0,
                category, productStatus,
                1, "aedskqwtyh");

        HttpEntity request = new HttpEntity(product, headers);

        ResponseEntity<Product> response = restTemplate.postForEntity(
                url + "/add",
                request,
                Product.class);

        return response.getBody();
    }
}
