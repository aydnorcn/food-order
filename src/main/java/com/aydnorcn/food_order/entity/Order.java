package com.aydnorcn.food_order.entity;

import com.aydnorcn.food_order.utils.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String fullName;
    private String address;
    private String city;
    private String phone;
    private String zipCode;

    private String note;
    private Double total;
    private Double discount;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(User user, String note, Double total, Double discount, Coupon coupon, OrderStatus status, Address address) {
        this.user = user;
        this.note = note;
        this.total = total;
        this.discount = discount;
        this.coupon = coupon;
        this.status = status;
        this.fullName = address.getFullName();
        this.address = address.getAddress();
        this.city = address.getCity();
        this.phone = address.getPhone();
        this.zipCode = address.getZipCode();
    }

    @Override
    public String toString() {
        return "Order(" +
                "id=" + id +
                ", itemsCount=" + items.size() +
                ", user=" + user.getId() +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", note='" + note + '\'' +
                ", total=" + total +
                ", discount=" + discount +
                ", coupon=" + (coupon == null ? "null" : coupon.getId()) +
                ", status=" + status.toString() +
                ')';
    }
}
