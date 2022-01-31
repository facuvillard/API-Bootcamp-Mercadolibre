package com.mercadolibre.facundo_villard.repositories;

import com.mercadolibre.facundo_villard.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByDeliveryStatus_CodeStatusAndCardealership_DealerNumber(String codeStatus, Integer dealerNumber);
    List<Order> findAllByCardealership_DealerNumberOrderByOrderDateDesc(Integer dealerNumber);
    List<Order> findAllByCardealership_DealerNumberOrderByOrderDateAsc(Integer dealerNumber);
    Order findOrderByOrderNumberAndCardealership_DealerNumber(Integer orderNumber, Integer dealerNumber);
    List<Order> findAllByDeliveryStatus_CodeStatusAndCardealership_DealerNumberOrderByOrderDateDesc(String codeStatus, Integer dealerNumber);
    List<Order> findAllByDeliveryStatus_CodeStatusAndCardealership_DealerNumberOrderByOrderDateAsc(String codeStatus, Integer dealerNumber);

}
