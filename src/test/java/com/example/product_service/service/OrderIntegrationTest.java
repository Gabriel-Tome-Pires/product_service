package com.example.product_service.service;

import com.example.product_service.model.*;
import com.example.product_service.model.Order;
import com.example.product_service.repository.OrderRepository;
import com.example.product_service.repository.OrderStatusRepository;
import net.bytebuddy.asm.Advice;
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
@TestPropertySource(locations = "/application-test.properties")
public class OrderIntegrationTest {
    String url="/order";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    private OrderStatus orderStatus;

    @BeforeAll
    void setup(){
        orderStatus = new OrderStatus("orderStatus");
        orderStatusRepository.save(orderStatus);
    }

    @Test
    @DisplayName("Create")
    void TestCreateOrder_WithValidData_ReturnsCreatedOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Order order=new Order(1,orderStatus,LocalDateTime.now());

        HttpEntity entity = new HttpEntity(order,headers);

        ResponseEntity<Order> response= restTemplate.postForEntity(
                url+"/add",
                entity,Order.class
        );

        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    @DisplayName("Get all")
    void TestGetAllOrders_ReturnsAllOrders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity request = new HttpEntity(null,headers);

        ResponseEntity<List<Order>> response= restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Order>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    @DisplayName("Get by id")
    void TestGetOrderById_ReturnsOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Order order=generateOrder();

        HttpEntity request = new HttpEntity(null,headers);

        ResponseEntity<Order> response= restTemplate.exchange(
                url+"/"+order.getId(),
                HttpMethod.GET,
                request,
                Order.class
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(order.getId(),response.getBody().getId());
    }

    @Test
    @DisplayName("Get by user id")
    void TestGetOrderByUserId_ReturnsOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Order order=generateOrder();

        HttpEntity request = new HttpEntity(null,headers);

        ResponseEntity<List<Order>> response= restTemplate.exchange(
                url+"/user/1",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Order>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    @DisplayName("Get by status id")
    void TestGetOrderByStatusId_ReturnsOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Order order=generateOrder();

        HttpEntity request = new HttpEntity(null,headers);

        ResponseEntity<List<Order>> response= restTemplate.exchange(
                url+"/status/"+orderStatus.getId(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Order>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    @DisplayName("Get before date")
    void TestGetOrderBeforeDate_ReturnsOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Order order=generateOrder();

        HttpEntity request = new HttpEntity(null,headers);

        ResponseEntity<List<Order>> response= restTemplate.exchange(
                url+"/beforeDate/"+LocalDateTime.now(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Order>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    @DisplayName("Get after date")
    void TestGetOrderAfterDate_ReturnsOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Order order=generateOrder();

        HttpEntity request = new HttpEntity(null,headers);

        ResponseEntity<List<Order>> response= restTemplate.exchange(
                url+"/afterDate/"+LocalDateTime.now().minusDays(1),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Order>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    @DisplayName("Update")
    void TestUpdateOrder_WithValidData_ReturnsUpdatedOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        Order order=generateOrder();
        LocalDateTime date=LocalDateTime.now();
        orderStatus.setStatusName("other");
        Order newOrder=new Order(1,orderStatus,date);
        HttpEntity request = new HttpEntity(newOrder,headers);

        ResponseEntity<Order> response= restTemplate.exchange(
                url+"/"+order.getId(),
                HttpMethod.PUT,
                request,
                Order.class
        );

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(orderStatus.getStatusName(),response.getBody().getOrderStatus().getStatusName());
    }

    @Test
    @DisplayName("Delete")
    void TestDeleteOrder_WithValidData_ReturnsDeletedOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        Order order=generateOrder();

        HttpEntity request = new HttpEntity(null,headers);
        ResponseEntity<Order> response= restTemplate.exchange(
                url+"/delete/"+order.getId(),
                HttpMethod.DELETE,
                request,
                Order.class
        );

        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    private Order generateOrder(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Order order=new Order(1,orderStatus,LocalDateTime.now());

        HttpEntity entity = new HttpEntity(order,headers);

        ResponseEntity<Order> response= restTemplate.postForEntity(
                url+"/add",
                entity,Order.class
        );

        return response.getBody();
    }
}
