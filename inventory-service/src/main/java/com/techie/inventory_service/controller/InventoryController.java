package com.techie.inventory_service.controller;

import com.techie.inventory_service.dto.InventoryResponse;
import com.techie.inventory_service.service.InventoryService;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController
{
    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryResponse>  isInStock(@RequestParam List<String> skuCode)
    {
        return inventoryService.isInStock(skuCode);
    }
}
