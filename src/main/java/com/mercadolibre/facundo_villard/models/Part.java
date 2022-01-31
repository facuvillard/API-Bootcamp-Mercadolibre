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
@Table(name = "parts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El código no puede ser nulo")
    @Pattern(regexp = "[0-9]{8}", message = "El código debe ser de ocho caracteres")
    private String partCode;

    @OneToMany(mappedBy = "part")
    private List<PriceRegister> priceRegisters;

    @OneToMany(mappedBy = "part")
    private List<PartRegister> partRegisters;

    @OneToMany(mappedBy = "part")
    private List<OrderDetail> orderDetail;

    @OneToOne
    @JoinColumn(name="id_stock_wherehouse")
    private StockWhereHouse stockWhereHouse;

    @OneToMany(mappedBy = "part")
    private List<StockCountryHouse> stockCountryHouseList;
}
