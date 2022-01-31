package com.mercadolibre.facundo_villard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer orderNumber;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private Integer daysDelay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cardealership", nullable = false)
    private Cardealership cardealership;

    @ManyToOne
    @JoinColumn(name="id_deliverystatus")
    private DeliveryStatus deliveryStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

}
