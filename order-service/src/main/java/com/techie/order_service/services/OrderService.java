package com.techie.order_service.services;

import com.techie.order_service.dtos.OrderRequest;
import com.techie.order_service.entities.Order;

public interface OrderService
{

    void placeOrder(OrderRequest orderRequest);
}
