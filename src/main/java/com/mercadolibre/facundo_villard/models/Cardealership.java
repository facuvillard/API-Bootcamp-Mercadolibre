package com.mercadolibre.facundo_villard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="car_dealership")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Cardealership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dealerNumber;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_countryhouse", nullable = false)
    private CountryHouse countryHouse;

    @OneToMany(mappedBy = "cardealership")
    private List<Order> orders;

}
