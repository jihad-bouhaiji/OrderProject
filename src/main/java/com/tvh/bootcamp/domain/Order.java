package com.tvh.bootcamp.domain;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private UUID id;
    private String client;
    private ZonedDateTime creationDateTime;
    private ArrayList<Line> orderLines;

    private Order(UUID id, String client, ZonedDateTime creationDateTime, ArrayList<Line> orderLines) {
        this.id = id;
        this.client = client;
        this.creationDateTime = creationDateTime;
        this.orderLines = orderLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) &&
                Objects.equals(getClient(), order.getClient()) &&
                Objects.equals(getCreationDateTime(), order.getCreationDateTime()) &&
                Objects.equals(getOrderLines(), order.getOrderLines());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClient(), getCreationDateTime(), getOrderLines());
    }

    public static class Line{
        private double price;
        private String product;
        private int amount;

        public Line(String product, double price, int amount) {
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

        @Override
        public String toString() {
            return "Line{ " +
                    "product ='" + product + '\'' +
                    ", price = " + price +
                    ", amount = " + amount +
                    '}' + "\n" ;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return Double.compare(line.getPrice(), getPrice()) == 0 &&
                    getAmount() == line.getAmount() &&
                    Objects.equals(getProduct(), line.getProduct());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getPrice(), getProduct(), getAmount());
        }
    }

    public static class Builder{
        private UUID id;
        private String client;
        private ZonedDateTime creationDateTime;
        private ArrayList<Line> orderLines;

        public Builder(){}

        public Order.Builder withId(UUID id){
            this.id = id;
            return this;
        }
        public Order.Builder withClient(String client){
            this.client = client;
            return this;
        }
        public Order.Builder withCreationDateTime(ZonedDateTime creationDateTime){
            this.creationDateTime = creationDateTime;
            return this;
        }
        public Order.Builder withOrderLines(ArrayList<Order.Line> orderLines){
            this.orderLines = orderLines;
            return this;
        }

        public Order build(){
            return new Order(id,client,creationDateTime,orderLines);
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public void AddOrderLine(Order.Line line){
        orderLines.add(line);
    }

    public ArrayList<Line> getOrderLines() {
        return orderLines;
    }

    public String getClient() {
        return client;
    }

    public ZonedDateTime getCreationDateTime() {
        return creationDateTime;
    }
}
