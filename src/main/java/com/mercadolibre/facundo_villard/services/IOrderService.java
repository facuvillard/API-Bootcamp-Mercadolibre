package com.mercadolibre.facundo_villard.services;


import com.mercadolibre.facundo_villard.dtos.request.PartsRequestDTO;
import com.mercadolibre.facundo_villard.dtos.response.OrderStatusResponseDTO;
import com.mercadolibre.facundo_villard.dtos.response.OrderTotalDTO;
import com.mercadolibre.facundo_villard.dtos.response.OrdersListResponseDTO;
import com.mercadolibre.facundo_villard.dtos.response.PartsResponseDTO;

import java.util.Map;

public interface IOrderService {

    OrdersListResponseDTO order(Map<String, String> params);

    OrderStatusResponseDTO getOrderByNumber(String orderNumberCM, Map<String, String> params);

    PartsResponseDTO makeAnOrder(PartsRequestDTO partsRequestDTO);

    OrderTotalDTO getTotalOfOrderByNumber(String orderNumberCM);
}
