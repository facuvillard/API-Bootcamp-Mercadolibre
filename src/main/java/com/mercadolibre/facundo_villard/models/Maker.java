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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="makers")
public class Maker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotNull(message = "La marca no puede ser nula")
    @Pattern(regexp = "[A-Za-z0-9\\s]{1,100}+", message = "La marca debe ser de hasta 100 caracteres alfanuméricos")
    private String name;

    @OneToMany(mappedBy = "maker")
    private List<PartRegister> partRegisterList;
}