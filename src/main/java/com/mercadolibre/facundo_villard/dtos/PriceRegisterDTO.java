package com.mercadolibre.facundo_villard.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mercadolibre.facundo_villard.models.Discount;
import com.mercadolibre.facundo_villard.models.Part;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;


@Data
@Validated
public class PriceRegisterDTO {

    @JsonIgnore
    private Long id;
    private LocalDate dateModification;
    private Float normalPrice;
    private Float salePrice;
    private Float urgentPrice;
    @JsonIgnore
    private Part part;
    private Discount discount;

}
