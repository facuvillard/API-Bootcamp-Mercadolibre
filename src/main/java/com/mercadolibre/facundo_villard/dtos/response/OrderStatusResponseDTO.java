package com.mercadolibre.facundo_villard.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mercadolibre.facundo_villard.dtos.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderStatusResponseDTO {

    private String orderNumberCE;
    private LocalDate orderDate;
    private String orderStatus;
    private List<OrderDetailDTO> orderDetails;

    public OrderStatusResponseDTO(String orderNumberCE, LocalDate orderDate, String orderStatus){
        this.orderNumberCE = orderNumberCE;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
