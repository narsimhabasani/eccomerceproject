package com.techie.order_service.services;

import com.techie.order_service.dtos.OrderLineItemsDto;
import com.techie.order_service.dtos.OrderRequest;
import com.techie.order_service.entities.Order;
import com.techie.order_service.entities.OrderLineItems;
import com.techie.order_service.event.OrderPlacedEvent;
import com.techie.order_service.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.techie.order_service.dtos.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class OrderServiceImpl implements OrderService

{ @Autowired
 private OrderRepository orderRepository;
    @Autowired
    private KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    @Autowired
    private WebClient webClient;
    @Override
    public void placeOrder(OrderRequest orderRequest)
    {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());
      List<OrderLineItems>  orderLineItems=orderRequest.getOrderLineItemsDtoList().stream().map(orderLineItemsDto -> mapToDto(orderLineItemsDto)).toList();

       order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8081)
                        .path("/api/inventories")
                        .queryParam("skuCode", skuCodes.toArray())
                        .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allInStock = Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);

        if (!allInStock) {
            throw new IllegalArgumentException("One or more items are out of stock.");
        }


         orderRepository.save(order);
        kafkaTemplate.send("my-topic", new OrderPlacedEvent(order.getOrderNumber()));
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto)
    {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

    return orderLineItems;
    }
}



