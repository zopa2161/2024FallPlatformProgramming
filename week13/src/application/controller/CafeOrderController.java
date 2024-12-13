package application.controller;


import application.domain.CafeOrder;
import framework.annotations.GetMapping;
import framework.annotations.PathVariable;
import framework.annotations.PostMapping;
import framework.annotations.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CafeOrderController {
    private Map<String, CafeOrder> orderDatabase = new HashMap<>();

    @PostMapping("/orders/new")
    public String createOrder(@RequestBody CafeOrder order) {
        orderDatabase.put(order.getOrderId(), order);
        return "Order{id='" + order.getOrderId() + "', itemName='" + order.getItemName() + "', price=" + order.getPrice() + "} created successfully.";
    }

    @GetMapping("/orders/{orderId}/status")
    public String getOrderStatus(@PathVariable("orderId") String orderId) {
        CafeOrder order = orderDatabase.get(orderId);
        if (order != null) {
            return "Order{id='" + order.getOrderId() + "', itemName='" + order.getItemName() + "', price=" + order.getPrice() + "}";
        } else {
            return "Order not found.";
        }
    }

    @GetMapping("/orders/history")
    public List<CafeOrder> getOrderHistory(String customerId) {
        // For simplicity, ignore customerId and return all orders
        return new ArrayList<>(orderDatabase.values());
    }
}
