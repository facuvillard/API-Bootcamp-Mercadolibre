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
@AllArgsConstructor
@NoArgsConstructor
@Table(name="stock_wherehouse")
public class StockWhereHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La cantidad no puede ser nula")
    @Max(value = 99999999, message = "La cantidad disponible en stock debe ser de hasta 8 caracteres numéricos")
    @Min(value = 0)
    private Integer quantity;

    @OneToOne(mappedBy = "stockWhereHouse")
    private Part part;
}