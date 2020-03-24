package com.tvh.bootcamp;

import com.tvh.bootcamp.api.web.OrderController;
import com.tvh.bootcamp.api.web.OrderFacade;
import com.tvh.bootcamp.application.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

@SpringBootApplication
public class OrderApp {

    private final OrderFacade orderFacade;

    public OrderApp(OrderFacade orderFacade){
        this.orderFacade = orderFacade;
    }

    public void run(String... args){
        Scanner keyboard = new Scanner(System.in);
        String client = keyboard.next();
        ArrayList<String> orderLines = new ArrayList<>();
        String input = keyboard.nextLine();

        while(!input.equals("exit")){
            orderLines.add(input);
            input = keyboard.nextLine();

        }

        orderLines.remove(0);
        UUID id = orderFacade.addOrder(client,orderLines);
        orderFacade.getOrder(id);
        orderFacade.printAllOrders();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class,args);
    }
}
