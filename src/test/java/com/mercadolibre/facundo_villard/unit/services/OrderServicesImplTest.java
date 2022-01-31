package com.mercadolibre.facundo_villard.unit.services;


import com.mercadolibre.facundo_villard.dtos.OrderDTO;
import com.mercadolibre.facundo_villard.dtos.OrderDetailDTO;
import com.mercadolibre.facundo_villard.dtos.response.OrdersListResponseDTO;
import com.mercadolibre.facundo_villard.exceptions.ApiException;
import com.mercadolibre.facundo_villard.models.Cardealership;
import com.mercadolibre.facundo_villard.models.DeliveryStatus;
import com.mercadolibre.facundo_villard.models.Order;
import com.mercadolibre.facundo_villard.repositories.CardealershipRepository;
import com.mercadolibre.facundo_villard.repositories.OrderRepository;
import com.mercadolibre.facundo_villard.repositories.PartRegisterRepository;
import com.mercadolibre.facundo_villard.services.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServicesImplTest {

    MockHttpServletRequest request = new MockHttpServletRequest();

    //1-Instanciar los objetos falsos con Mockito
    private OrderRepository orderServiceMock  = Mockito.mock(OrderRepository.class);
    private CardealershipRepository cardealershipRepositoryMock = Mockito.mock(CardealershipRepository.class);
    private PartRegisterRepository partRegisterRepositoryMock = Mockito.mock(PartRegisterRepository.class);
    private final ModelMapper modelMapperMock = Mockito.mock(ModelMapper.class);

    private Map<String, String> params = new HashMap<>();

    OrderServiceImpl orderServicesImpl = Mockito.mock(OrderServiceImpl.class) ;
    OrdersListResponseDTO ordersListResponseDTOMock = new OrdersListResponseDTO();

    @BeforeEach
    void setUp(){
        ///Un parametro: DealerNumber
        params.put("dealerNumber","0001");

        ordersListResponseDTOMock.setDealerNumber(0001);

        OrderDTO orderDTOMock1 = new OrderDTO();
        orderDTOMock1.setId(1L);
        orderDTOMock1.setOrderDate(LocalDate.now());
        orderDTOMock1.setOrderDetails(new ArrayList<OrderDetailDTO>());
        orderDTOMock1.setDaysDelay(3);
        orderDTOMock1.setDeliveryDate(LocalDate.now());
        orderDTOMock1.setDeliveryStatus("P");

        List<OrderDTO> listDTO = new ArrayList<>();
        listDTO.add(orderDTOMock1);

        ordersListResponseDTOMock.setOrder(listDTO);

        Cardealership cardealershipMock = new Cardealership();
        cardealershipMock.setDealerNumber(0001);

        //Respuesta
        Mockito.when(cardealershipRepositoryMock.getByDealerNumber(0001))
                .thenReturn(cardealershipMock);

        Mockito.when(orderServicesImpl.order(params))
                .thenReturn(ordersListResponseDTOMock);

        ///Dos parametros: DealerNumber y DeliveryStatus
        params.put("deliveryStatus","P");

        OrderDTO orderDTOMock2 = new OrderDTO();
        orderDTOMock2.setId(2L);
        orderDTOMock2.setOrderDate(LocalDate.now().plusDays(2));
        orderDTOMock2.setOrderDetails(new ArrayList<OrderDetailDTO>());
        orderDTOMock2.setDaysDelay(2);
        orderDTOMock2.setDeliveryDate(LocalDate.now());
        orderDTOMock2.setDeliveryStatus("F");

        listDTO.add(orderDTOMock2);

        ///Un parametro: Order
        params.put("order","1");

        OrderDTO orderDTOMock3 = new OrderDTO();
        orderDTOMock3.setId(2L);
        orderDTOMock3.setOrderDate(LocalDate.now().plusDays(3));
        orderDTOMock3.setOrderDetails(new ArrayList<OrderDetailDTO>());
        orderDTOMock3.setDaysDelay(2);
        orderDTOMock3.setDeliveryDate(LocalDate.now());
        orderDTOMock3.setDeliveryStatus("F");

        listDTO.add(orderDTOMock3);

        ordersListResponseDTOMock.setOrder(listDTO);

        Order orderMock = new Order();
        orderMock.setDeliveryStatus(new DeliveryStatus());

        //Respuesta
        Mockito.when(cardealershipRepositoryMock.getByDealerNumber(0001))
                .thenReturn(cardealershipMock);

        Mockito.when(orderServicesImpl.order(params))
                .thenReturn(ordersListResponseDTOMock);

    }

    @Test
    void findOrdersByDealerNumberOk() {

        OrdersListResponseDTO respuesta = orderServicesImpl.order(params);
        Assertions.assertEquals(respuesta.getDealerNumber(),0001);
    }

    @Test
    void findOrdersByDealerNumberAndDeliveryStatusOk() {
        params.put("deliveryStatus","P");

        Mockito.when(orderServicesImpl.order(params))
                .thenReturn(ordersListResponseDTOMock);

        OrdersListResponseDTO respuesta = orderServicesImpl.order(params);
        Assertions.assertEquals(respuesta.getOrder().stream().findFirst().get().getDeliveryStatus(),"P");
    }

    @Test
    void findOrdersByDealerNumberAndOrderAscOk() {
        LocalDate date = LocalDate.parse(LocalDate.now().toString());

        Mockito.when(orderServicesImpl.order(params))
                .thenReturn(ordersListResponseDTOMock);

        OrdersListResponseDTO respuesta = orderServicesImpl.order(params);
        Assertions.assertEquals(respuesta.getOrder().stream().findFirst().get().getOrderDate(),date);
    }

    @Test
    void findOrdersByDealerNumberAndOrderDescOk() {
        LocalDate date = LocalDate.parse(LocalDate.now().plusDays(2).toString());;

        Mockito.when(orderServicesImpl.order(params))
                .thenReturn(ordersListResponseDTOMock);

        OrdersListResponseDTO respuesta = orderServicesImpl.order(params);
        Assertions.assertEquals(respuesta.getOrder().stream().skip(1).findFirst().get().getOrderDate(),date);
    }

    @Test
    void findOrders_NotFound() {
        Map<String, String> params_nf = new HashMap<>();

        params_nf.put("dealerNumber","0002");

        Mockito.when(orderServicesImpl.order(params_nf))
                .thenThrow(new ApiException("404","No existen ordenes",404));
    }

    @Test
    void findOrdersByDealerNumberAndDeliveryStatusAndOrderAsc_Ok() {

        LocalDate date = LocalDate.parse(LocalDate.now().toString());

        Mockito.when(orderServicesImpl.order(params))
                .thenReturn(ordersListResponseDTOMock);

        OrdersListResponseDTO respuesta = orderServicesImpl.order(params);
        Assertions.assertEquals(respuesta.getOrder().stream().findFirst().get().getOrderDate(),date);
    }

}
