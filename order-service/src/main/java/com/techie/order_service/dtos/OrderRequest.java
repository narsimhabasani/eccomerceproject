package com.techie.order_service.dtos;

import com.techie.order_service.entities.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderRequest
{
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
