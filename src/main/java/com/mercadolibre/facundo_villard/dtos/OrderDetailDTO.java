package com.mercadolibre.facundo_villard.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    @JsonIgnore
    private Long id;
    private String partCode;
    private String description;
    private Integer quantity;
    private String accountType;
    private String reason;
    private String status;



}