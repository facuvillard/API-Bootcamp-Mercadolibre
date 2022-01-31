package com.mercadolibre.facundo_villard.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @JsonIgnore
    private Long id;
    private Integer orderNumber;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private Integer daysDelay;
    private String deliveryStatus;
    private List<OrderDetailDTO> orderDetails;

}
