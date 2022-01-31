package com.mercadolibre.facundo_villard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name="discounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "El código de descuento no puede ser nulo")
    @Pattern(regexp = "[A-Z]{1}+[0-9]{2}", message = "El Código de descuento no cumple con el formato esperado")
    private String discountType;

    private String description;
    private Float mount;

    @OneToMany(mappedBy = "discount")
    private List<PriceRegister> priceRegisters;
}
