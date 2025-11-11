package com.techie.order_service.controllers;

import com.techie.order_service.dtos.OrderRequest;
import com.techie.order_service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController
{

    @Autowired
   private OrderService orderService;
    @PostMapping
    public String placeOrder(@RequestBody OrderRequest orderRequest)
    {

        orderService.placeOrder(orderRequest);
        return " Order sucessfully placed ";
    }
}
