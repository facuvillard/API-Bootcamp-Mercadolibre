package com.mercadolibre.facundo_villard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="country_houses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, country;

    @OneToMany(mappedBy = "countryHouse")
    private List<Account> accounts;

    @OneToMany(mappedBy = "countryHouse")
    private List<Cardealership> cardealership;

    @OneToMany(mappedBy = "countryHouse")
    private List<StockCountryHouse> stockCountryHouseList;
}
