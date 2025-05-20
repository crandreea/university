package com.example.examen.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order extends Entity<Integer>{
    private int table;
    private List<MenuItem> menuItems;
    private LocalDateTime date;

    public Order(int table, List<MenuItem> menuItems, LocalDateTime date) {
        this.table = table;
        this.menuItems = menuItems;
        this.date = LocalDateTime.now();
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(table, order.table) && Objects.equals(menuItems, order.menuItems) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), table, menuItems, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "table=" + table +
                ", menuItems=" + menuItems +
                ", date=" + date +
                '}';
    }
}
