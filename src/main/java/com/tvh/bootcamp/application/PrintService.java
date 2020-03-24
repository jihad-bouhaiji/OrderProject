package com.tvh.bootcamp.application;

import com.tvh.bootcamp.domain.Order;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class PrintService {

    public void printOrder(Order order){
        String printedOrder = "Order{" +  "\n" +
                "id = " + order.getId() + "\n" +
                "client = '" + order.getClient() + '\'' + "\n" +
                "creationDateTime = " + order.getCreationDateTime() + "\n" +
                "orderLines = " + order.getOrderLines() +
                '}';

        System.out.println(printedOrder);
    }

    public void printOrders(List<Order> orders) {
        orders.forEach(this::printOrder);
    }
}
