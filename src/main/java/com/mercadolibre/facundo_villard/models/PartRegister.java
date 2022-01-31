package com.mercadolibre.facundo_villard.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Table(name = "part_registers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PartRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La descripción no puede ser nula")
    @Pattern(regexp = "[A-Za-z0-9\\s]{1,100}+", message = "La descripción debe ser de hasta 100 caracteres alfanuméricos")
    private String description;

    @NotNull(message = "La altura del repuesto no puede ser nula")
    @Digits(integer = 4, fraction = 0, message = "La altura del repuesto debe ser de hasta 4 caracteres numéricos")
    private Float tallDimension;

    @NotNull(message = "El ancho del repuesto no puede ser nulo")
    @Digits(integer = 4, fraction = 0, message = "El ancho del repuesto debe ser de hasta 4 caracteres numéricos")
    private Float widthDimension;

    @NotNull(message = "El largo del repuesto no puede ser nulo")
    @Digits(integer = 4, fraction = 0, message = "El largo del repuesto debe ser de hasta 4 caracteres numéricos")
    private Float longDimension;

    @NotNull(message = "El Peso neto del repuesto no puede ser nulo")
    @Digits(integer = 5, fraction = 0, message = "El Peso neto del repuesto debe ser de hasta 5 caracteres numéricos")
    private Float netWeight;

    @NotNull(message = "La fecha de modificación no puede ser nula")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateModification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_part", nullable = false)
    private Part part;

    @ManyToOne
    @JoinColumn(name = "id_maker")
    private Maker maker;


}