package lk.ise.pos.dto;

import java.util.Date;

public class OrderDto {
    private String orderId;
    private String customer;
    private Date date;
    private double total;

    public OrderDto() {
    }

    public OrderDto(String orderId, String customer, Date date, double total) {
        this.orderId = orderId;
        this.customer = customer;
        this.date = date;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
