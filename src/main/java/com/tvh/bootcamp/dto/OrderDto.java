package com.tvh.bootcamp.dto;

import lombok.Builder;

import java.util.List;

@Builder
public final class OrderDto {
    private final String id;
    private final String client;
    private final String creationDateTime;
    private final List<LineDto> orderLines;

    public OrderDto(String id, String client, String creationDateTime, List<LineDto> orderLines) {
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

    public String getId() {
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
