package com.tvh.bootcamp.dto;

import com.tvh.bootcamp.domain.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public final class OrderDto {
    private final UUID id;
    private final String client;
    private final String creationDateTime;
    private final List<LineDto> orderLines;

    public OrderDto(UUID id, String client, String creationDateTime, List<LineDto> orderLines) {
        this.id = id;
        this.client = client;
        this.creationDateTime = creationDateTime;
        this.orderLines = orderLines;
    }

    public static class LineDto {
        private String product;
        private double price;
        private int amount;

        public LineDto(String product, double price, int amount) {
            this.price = price;
            this.product = product;
            this.amount = amount;
        }

        public double getPrice() {
            return price;
        }

        public String getProduct() {
            return product;
        }

        public int getAmount() {
            return amount;
        }
    }

    public static class Builder{
        private UUID id;
        private String client;
        private String creationDateTime;
        private ArrayList<LineDto> orderLines;

        public Builder(){}

        public OrderDto.Builder withId(UUID id){
            this.id = id;
            return this;
        }
        public OrderDto.Builder withClient(String client){
            this.client = client;
            return this;
        }
        public OrderDto.Builder withCreationDateTime(String creationDateTime){
            this.creationDateTime = creationDateTime;
            return this;
        }
        public OrderDto.Builder withOrderLines(ArrayList<LineDto> orderLines){
            this.orderLines = orderLines;
            return this;
        }

        public OrderDto build(){
            return new OrderDto(id,client,creationDateTime,orderLines);
        }
    }

    public static OrderDto.Builder builder(){
        return new OrderDto.Builder();
    }

    public UUID getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public List<LineDto> getOrderLines() {
        return orderLines;
    }
}
