package com.example.product_service.service;

import com.example.product_service.model.OrderStatus;
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
public class OrderStatusIntegrationTest {
    String url="/orderStatus";
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Create")
    @Order(1)
    void TestCreateOrderStatus_WithValidData_ReturnsOrderStatus(){
        JSONObject orderStatus = new JSONObject();
        orderStatus.put("statusName", "pendent");

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(orderStatus.toJSONString(), headers);

        ResponseEntity<OrderStatus> response = testRestTemplate.postForEntity(
                url+"/add",
                request,
                OrderStatus.class);
        OrderStatus createOrderStatus= response.getBody();

        Assertions.assertNotNull(createOrderStatus);
        Assertions.assertEquals("pendent", createOrderStatus.getStatusName());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Get All")
    @Order(2)
    void TestGetAllOrderStatus_WithValidData_ReturnsAllOrderStatus(){
        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity request= new HttpEntity<>(null,headers);

        ResponseEntity<List<OrderStatus>> response = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<OrderStatus>>() {}
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Get By id")
    @Order(3)
    void TestGetOrderStatusById_WithValidData_ReturnsOrderStatus(){
        OrderStatus orderStatus = createOrderStatus("delireved");
        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept","application/json");
        HttpEntity request= new HttpEntity<>(null,headers);

        ResponseEntity<OrderStatus> response = testRestTemplate.exchange(
                url+"/"+orderStatus.getId(),
                HttpMethod.GET,
                request,
                OrderStatus.class
        );

        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(orderStatus.getStatusName(), response.getBody().getStatusName());
    }

    @Test
    @DisplayName("Update Order Status By id")
    @Order(4)
    void TestUpdateOrderStatusById_WithValidData_ReturnsOrderStatus(){
        OrderStatus orderStatus = createOrderStatus("on_way");
        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept","application/json");

        String name="UpdatedOnWay";
        OrderStatus updateOrderStatus = new OrderStatus(name);

        HttpEntity request= new HttpEntity<>(updateOrderStatus,headers);

        ResponseEntity<OrderStatus> response = testRestTemplate.exchange(
                url+"/"+orderStatus.getId(),
                HttpMethod.PUT,
                request,
                OrderStatus.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(name.toLowerCase().trim(), response.getBody().getStatusName());
    }

    @Test
    @DisplayName("Delete by id")
    @Order(5)
    void TestDeleteOrderStatusById_WithValidData_ReturnsOrderStatus(){
        OrderStatus orderStatus = createOrderStatus("on_the_way");
        HttpHeaders headers= new HttpHeaders();
        headers.set("Accept","application/json");

        HttpEntity request= new HttpEntity<>(null,headers);

        ResponseEntity<OrderStatus> response = testRestTemplate.exchange(
                url+"/delete/"+orderStatus.getId(),
                HttpMethod.DELETE,
                request,
                OrderStatus.class
        );

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private OrderStatus createOrderStatus(String name){
        JSONObject orderStatus = new JSONObject();
        orderStatus.put("statusName", name);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(orderStatus.toJSONString(), headers);

        ResponseEntity<OrderStatus> response = testRestTemplate.postForEntity(
                url+"/add",
                request,
                OrderStatus.class);
        return response.getBody();
    }
}
