package com.example.product_service.service;

import com.example.product_service.model.*;
import com.example.product_service.model.Order;
import com.example.product_service.repository.*;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations ="/application-test.properties")
public class OrderItemIntegrationTest {

    String url="/OrderItem";
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductStatusRepository productStatusRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    private static Order order;
    private static Product product;

    @BeforeAll
    void setup(){
        ProductStatus productStatus = new ProductStatus("name");
        Category category = new Category("category");
        OrderStatus orderStatus = new OrderStatus("orderStatus");
        orderStatusRepository.save(orderStatus);
        categoryRepository.save(category);
        productStatusRepository.save(productStatus);

        product = new Product("Name","Description",15.0,
                category, productStatus,
                1,"aedskqwtyh");
        order = new Order(1,orderStatus, LocalDateTime.now());

        orderRepository.save(order);
        productRepository.save(product);

        order=orderRepository.findAll().get(0);
        product=productRepository.findAll().get(0);
    }

    @Test
    @DisplayName("Create")
    void TestCreateOrderItem_WithValidInput_ReturnOrderItem(){
        JSONObject orderJson = new JSONObject();
        orderJson.put("id", order.getId());

        JSONObject productJson = new JSONObject();
        productJson.put("id", product.getId());

        JSONObject orderItemJson = new JSONObject();
        orderItemJson.put("order", orderJson);
        orderItemJson.put("product", productJson);


        HttpHeaders headers=new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity request=new HttpEntity(orderItemJson,headers);

        ResponseEntity<OrderItem> response= restTemplate.postForEntity(
                url+"/add",
                request,
                OrderItem.class
        );

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
        Assertions.assertEquals(order.getId(),response.getBody().getId());
        Assertions.assertEquals(product.getId(),response.getBody().getId());
    }

    @Test
    @DisplayName("Get all by Order Id")
    void GetAllOrderItens_ReturnOrderItemList(){
        HttpHeaders headers=new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity request=new HttpEntity<>(null,headers);

        ResponseEntity<List<OrderItem>> response= restTemplate.exchange(
                url+"/byOrder/"+order.getId(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<OrderItem>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    @DisplayName("Get by id")
    void GetOrderItemById_WithValidInput_ReturnOrderItem(){
        OrderItem orderItem= generateOrderItem();

        HttpHeaders headers=new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity request=new HttpEntity(null,headers);
        ResponseEntity<OrderItem> response= restTemplate.exchange(
                url+"/"+orderItem.getId(),
                HttpMethod.GET,
                request,
                OrderItem.class
        );
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(orderItem.getId(),response.getBody().getId());
    }

    @Test
    @DisplayName("Delete")
    void TestDeleteOrderItem_WithValidInput_ReturnOrderItem(){
        OrderItem orderItem= generateOrderItem();

        HttpHeaders headers=new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity request=new HttpEntity(null,headers);

        ResponseEntity<OrderItem> response= restTemplate.exchange(
                url+"/delete/"+orderItem.getId(),
                HttpMethod.DELETE,
                request,
                OrderItem.class
        );

        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    private OrderItem generateOrderItem(){
        JSONObject orderJson = new JSONObject();
        orderJson.put("id", order.getId());

        JSONObject productJson = new JSONObject();
        productJson.put("id", product.getId());

        JSONObject orderItemJson = new JSONObject();
        orderItemJson.put("order", orderJson);
        orderItemJson.put("product", productJson);


        HttpHeaders headers=new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity request=new HttpEntity(orderItemJson,headers);

        ResponseEntity<OrderItem> response= restTemplate.postForEntity(
                url+"/add",
                request,
                OrderItem.class
        );

        return response.getBody();
    }
}
