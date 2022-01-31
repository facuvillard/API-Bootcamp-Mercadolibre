package com.mercadolibre.facundo_villard.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name="price_registers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateModification;

    @NotNull(message = "El precio normal no puede ser nulo")
    @Digits(integer = 4, fraction = 2, message = "El precio normal no cumple con el formato esperado")
    private Float normalPrice;

    private Float salePrice;

    @NotNull(message = "El precio urgente no puede ser nulo")
    @Digits(integer = 4, fraction = 2, message = "El precio urgente no cumple con el formato esperado")
    private Float urgentPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_part", nullable = false)
    private Part part;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_discount")
    private Discount discount;
}
