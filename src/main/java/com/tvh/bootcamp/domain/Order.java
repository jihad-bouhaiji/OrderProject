package com.tvh.bootcamp.domain;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class Order {
    @Id
    private String id;
    private String client;
    private ZonedDateTime creationDateTime;
    @ElementCollection
    private List<Line> orderLines;

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

    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    @Getter
    @Setter
    @Builder
    public static class Line{
        private String product;
        private double price;
        private int amount;

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


    public String getId() {
        return id;
    }

    public void addOrderLine(Order.Line line){
        orderLines.add(line);
    }

    public List<Line> getOrderLines() {
        return orderLines;
    }

    public String getClient() {
        return client;
    }

    public ZonedDateTime getCreationDateTime() {
        return creationDateTime;
    }
}
