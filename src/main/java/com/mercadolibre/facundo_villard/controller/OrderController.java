package com.mercadolibre.facundo_villard.controller;


import com.mercadolibre.facundo_villard.dtos.request.PartsRequestDTO;
import com.mercadolibre.facundo_villard.dtos.response.OrderStatusResponseDTO;
import com.mercadolibre.facundo_villard.dtos.response.OrderTotalDTO;
import com.mercadolibre.facundo_villard.dtos.response.OrdersListResponseDTO;
import com.mercadolibre.facundo_villard.dtos.response.PartsResponseDTO;
import com.mercadolibre.facundo_villard.exceptions.ApiException;
import com.mercadolibre.facundo_villard.services.IOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/api/v1")
@RestController
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/parts/orders")
    public OrdersListResponseDTO order(@RequestParam Map<String, String> params) {
        return orderService.order(params);
    }

    @PostMapping("/parts/orders")
    public PartsResponseDTO order(@RequestBody PartsRequestDTO partsRequestDTO) {
        return orderService.makeAnOrder(partsRequestDTO);
    }

    @GetMapping("/parts/orders/{orderNumberCM}")
    @ResponseBody
    public OrderStatusResponseDTO getOrderByNumber(@PathVariable String orderNumberCM, @RequestParam Map<String, String> params) {
        String pattern = "^\\d+-\\d{6}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(orderNumberCM);
        if (m.find()) {
            return orderService.getOrderByNumber(orderNumberCM, params);
        }
        throw new ApiException("400", "Número de orden no válido", 400);
    }

    @GetMapping("/parts/orders/{orderNumberCM}/total")
    public OrderTotalDTO getTotalOfOrderByNumber(@PathVariable String orderNumberCM) {

        return orderService.getTotalOfOrderByNumber(orderNumberCM);

    };

}
