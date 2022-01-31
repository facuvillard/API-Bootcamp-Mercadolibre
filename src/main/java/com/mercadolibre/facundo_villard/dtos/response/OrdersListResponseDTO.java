package com.mercadolibre.facundo_villard.dtos.response;

import com.mercadolibre.facundo_villard.dtos.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersListResponseDTO {

    private Integer dealerNumber;
    private List<OrderDTO> order;
}
