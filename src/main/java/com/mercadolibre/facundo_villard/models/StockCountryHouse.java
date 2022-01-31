package com.mercadolibre.facundo_villard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_countryhouse")
public class StockCountryHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La cantidad no puede ser nula")
    @Max(value = 99999999, message = "La cantidad disponible en stock debe ser de hasta 8 caracteres numéricos")
    @Min(value = 0)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_countryhouse")
    private CountryHouse countryHouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_part")
    private Part part;

}
