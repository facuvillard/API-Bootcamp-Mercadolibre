package com.mercadolibre.facundo_villard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="delivery_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeStatus;
    private String description;

    @OneToMany(mappedBy = "deliveryStatus")
    private List<Order> order;

}
