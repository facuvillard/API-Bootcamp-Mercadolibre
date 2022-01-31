package com.mercadolibre.facundo_villard.services;

import com.mercadolibre.facundo_villard.dtos.OrderDTO;
import com.mercadolibre.facundo_villard.dtos.OrderDetailDTO;
import com.mercadolibre.facundo_villard.dtos.request.PartRequestDTO;
import com.mercadolibre.facundo_villard.dtos.request.PartsRequestDTO;
import com.mercadolibre.facundo_villard.dtos.response.*;
import com.mercadolibre.facundo_villard.exceptions.ApiException;
import com.mercadolibre.facundo_villard.models.*;
import com.mercadolibre.facundo_villard.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements com.mercadolibre.facundo_villard.services.IOrderService {
    private final CardealershipRepository cardealershipRepository;
    private final OrderRepository orderRepository;
    private final PartRegisterRepository partRegisterRepository;
    private final PriceRegisterRepository priceRegisterRepository;
    private final PartRepository partRepository;
    private final StockWhereHouseRepository stockWhereHouseRepository;

    public OrderServiceImpl(CardealershipRepository cardealershipRepository,
                            OrderRepository orderRepository,
                            PartRegisterRepository partRegisterRepository1,
                            PriceRegisterRepository priceRegisterRepository, PartRepository partRepository, StockWhereHouseRepository stockWhereHouseRepository) {
        this.cardealershipRepository = cardealershipRepository;
        this.orderRepository = orderRepository;
        this.partRegisterRepository = partRegisterRepository1;
        this.priceRegisterRepository = priceRegisterRepository;
        this.partRepository = partRepository;
        this.stockWhereHouseRepository = stockWhereHouseRepository;
    }

    @Override
    public OrdersListResponseDTO order(Map<String, String> params) {
        int size = params.size();

        if(params.get("dealerNumber") == ""){
            throw new ApiException("404", "La concesionaria no puede ser vacìa", 404);
        }

        int dealerNumberNum = Integer.parseInt(params.get("dealerNumber"));

        Cardealership carDealer = getDealerByDealerNumber(dealerNumberNum);

        if (carDealer == null) {
            throw new ApiException("404", "No existe la concesionaria con dicho ID", 404);
        }

        switch (size) {
            case 1:
                if (carDealer.getOrders().size() == 0) {
                    throw new ApiException("404", "No existen ordenes", 404);
                } else {
                    return generateResponse(carDealer.getOrders(), dealerNumberNum);
                }
            case 2:
                List<Order> orderList = null;

                if(params.containsKey("deliveryStatus")) {
                    if (!validateDeliveryStatus(params.get("deliveryStatus"))){
                        throw new ApiException("404", "Estado de envio no valido", 404);
                    }
                    orderList = orderRepository.findAllByDeliveryStatus_CodeStatusAndCardealership_DealerNumber(params.get("deliveryStatus"), dealerNumberNum);
                }
                if (params.containsKey("order")) {
                    if (!validateOrder(params.get("order"))){
                        throw new ApiException("404", "Orden no valido", 404);
                    }
                    if (params.get("order").equals("1")) {
                        orderList = orderRepository.findAllByCardealership_DealerNumberOrderByOrderDateAsc(dealerNumberNum);
                    }

                    if (params.get("order").equals("2")) {
                        orderList = orderRepository.findAllByCardealership_DealerNumberOrderByOrderDateDesc(dealerNumberNum);
                    }
                }
                assert orderList != null;
                if (orderList.size() == 0) {
                    throw new ApiException("404", "No existen ordenes", 404);
                }
                return generateResponse(orderList, dealerNumberNum);

            case 3:
                orderList = null;
                if (!validateDeliveryStatus(params.get("deliveryStatus"))){
                    throw new ApiException("404", "Estado de envio no valido", 404);
                }
                if (!validateOrder(params.get("order"))){
                    throw new ApiException("404", "Orden no valido", 404);
                }
                switch (params.get("order"))
                {
                    case "1":
                        orderList = orderRepository.findAllByDeliveryStatus_CodeStatusAndCardealership_DealerNumberOrderByOrderDateAsc(params.get("deliveryStatus"), dealerNumberNum);
                        break;
                    case "2":
                        orderList = orderRepository.findAllByDeliveryStatus_CodeStatusAndCardealership_DealerNumberOrderByOrderDateDesc(params.get("deliveryStatus"), dealerNumberNum);
                        break;
                    default:
                        break;
                }
                assert orderList != null;
                if (orderList.size() == 0) {
                    throw new ApiException("404", "No existen ordenes", 404);
                }

                return generateResponse(orderList, dealerNumberNum);
            default:
                break;
        }
        return null;
    }

    private boolean validateOrder(String order){
        return order.equals("1") || order.equals("2") ;
    };

    private boolean validateDeliveryStatus(String status){
        return status.equals("C") || status.equals("F") || status.equals("P") || status.equals("D");
    };

    private Cardealership getDealerByDealerNumber(Integer dealerNumber) {
        return cardealershipRepository.getByDealerNumber(dealerNumber);
    }

    private OrdersListResponseDTO generateResponse(List<Order> orders, Integer dealerNumber) {

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Order or : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderDate(or.getOrderDate());
            orderDTO.setOrderNumber(or.getOrderNumber());
            orderDTO.setDeliveryStatus(or.getDeliveryStatus().getCodeStatus());
            orderDTO.setDeliveryDate(or.getDeliveryDate());
            orderDTO.setDaysDelay(or.getDaysDelay());
            orderDTO.setOrderDetails(generateOrderDetailList(or));
            orderDTOList.add(orderDTO);
        }
        return new OrdersListResponseDTO(dealerNumber, orderDTOList);

    }

    private List<OrderDetailDTO> generateOrderDetailList(Order order) {
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            OrderDetailDTO orderDetailDTO = generateOrderDTO(orderDetail);
            orderDetailDTO.setStatus(order.getDeliveryStatus().getCodeStatus());
            orderDetailDTOList.add(orderDetailDTO);
        }
        return orderDetailDTOList;
    }

    private OrderDetailDTO generateOrderDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setReason(orderDetail.getReason());
        orderDetailDTO.setAccountType(orderDetail.getAccountType());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setPartCode(orderDetail.getPart().getPartCode());
        orderDetailDTO.setDescription(partRegisterRepository.findFirstByPartIdOrderByDateModificationDesc(orderDetail.getPart().getId()).getDescription());
        return orderDetailDTO;
    }

    public OrderStatusResponseDTO getOrderByNumber(String orderNumberCM, Map<String, String> params) {
        String[] orderNumber = orderNumberCM.split("-");
        Order order = orderRepository.findOrderByOrderNumberAndCardealership_DealerNumber(Integer.valueOf(orderNumber[1]), Integer.valueOf(orderNumber[0]));

        if (order == null) {
            throw new ApiException("404", "No se encontró ninguna orden con el número ingresado", 404);
        }

        if (params.containsKey("allInfo")) {
            if (params.get("allInfo").equals("true")) {
                return new OrderStatusResponseDTO(orderNumberCM, order.getOrderDate(), order.getDeliveryStatus().getDescription(), generateOrderDetailList(order));
            } else if (params.get("allInfo").equals("false")) {
                return new OrderStatusResponseDTO(orderNumberCM, order.getOrderDate(), order.getDeliveryStatus().getDescription());
            } else {
                throw new ApiException("404", "El parámetro ingresado no es válido", 404);
            }
        }

        return new OrderStatusResponseDTO(orderNumberCM, order.getOrderDate(), order.getDeliveryStatus().getDescription(), generateOrderDetailList(order));
    }

    @Override
    public PartsResponseDTO makeAnOrder(PartsRequestDTO partsRequestDTO) {
        PartsResponseDTO partsResponseDTO = new PartsResponseDTO();
        List<PartResponseDTO> parts = new LinkedList<>();
        double sum = 0.0;
        for (PartRequestDTO partRequestDTO : partsRequestDTO.getParts()) {
            PartResponseDTO partResponseDTO = getPart(partRequestDTO.getPartCode(), partRequestDTO.getQuantity(), partRequestDTO.getUrgent());
            sum += partResponseDTO.getTotal();
            parts.add(partResponseDTO);
        }
        partsResponseDTO.setTotal(sum);
        partsResponseDTO.setParts(parts);
        return partsResponseDTO;
    }

    public PartResponseDTO getPart(String partCode, int quantity, boolean urgent) {
        PartResponseDTO partResponseDTO = new PartResponseDTO();
        Part part = partRepository.findByPartCode(partCode).orElse(null);
        if (part == null) {
            throw new ApiException("404", "No se encontró el repuesto con código: " + partCode, 404);
        }
        PriceRegister priceRegister = priceRegisterRepository.findFirstByPartIdOrderByDateModificationDesc(part.getId());
        PartRegister partRegister = partRegisterRepository.findFirstByPartIdOrderByDateModificationDesc(part.getId());
        if (quantity <= 0) {
            throw new ApiException("400", "La cantidad solicitada debe ser mayor a 1 para el repuesto: " + partCode, 400);
        }
        if ((part.getStockWhereHouse().getQuantity() - quantity) < 0) {
            throw new ApiException("400", "No hay suficiente cantidad de repuestos para suplir la cantidad requerida de: " + partCode, 400);
        }
        StockWhereHouse stockWhereHouse = part.getStockWhereHouse();
        stockWhereHouse.setQuantity(stockWhereHouse.getQuantity() - quantity);
        Float price = getPrice(priceRegister, urgent);
        stockWhereHouseRepository.save(stockWhereHouse);
        partResponseDTO.setPartCode(partCode);
        partResponseDTO.setMaker(part.getPartCode());
        partResponseDTO.setQuantity(quantity);
        partResponseDTO.setDescription(partRegister.getDescription());
        partResponseDTO.setPrice(price);
        partResponseDTO.setTotal(quantity * price);

        return partResponseDTO;
    }

    public Float getPrice(PriceRegister priceRegister, boolean urgent) {
        Float price;
        if (urgent) {
            price = priceRegister.getUrgentPrice();
        } else {
            price = priceRegister.getNormalPrice();
        }
        if (priceRegister.getDiscount() != null) {
            price = (price - (price * (priceRegister.getDiscount().getMount() / 100F)));
        }
        return price;
    }

    public OrderTotalDTO getTotalOfOrderByNumber(String orderNumberCM) {
        String[] orderNumber = orderNumberCM.split("-");
        Order order = orderRepository.findOrderByOrderNumberAndCardealership_DealerNumber(Integer.valueOf(orderNumber[1]), Integer.valueOf(orderNumber[0]));
        if (order == null) {
            throw new ApiException("404", "No se encontró ninguna orden con el número ingresado", 404);
        }
        Double totalUrgentPrice = 0.0;
        Double totalSalePrice = 0.0;
        Double totalNormalPrice = 0.0;

        for (OrderDetail ordetail: order.getOrderDetails()) {
            Double normalPrice = priceRegisterRepository.findFirstByPartIdOrderByDateModificationDesc(ordetail.getPart().getId()).getNormalPrice().doubleValue();
            Double urgentPrice = priceRegisterRepository.findFirstByPartIdOrderByDateModificationDesc(ordetail.getPart().getId()).getUrgentPrice().doubleValue();
            Double salePrice = priceRegisterRepository.findFirstByPartIdOrderByDateModificationDesc(ordetail.getPart().getId()).getSalePrice().doubleValue();

            System.out.println(normalPrice);
            System.out.println(urgentPrice);
            System.out.println(salePrice);
            totalUrgentPrice += ordetail.getQuantity()*urgentPrice;
            totalNormalPrice += ordetail.getQuantity()*normalPrice;
            totalSalePrice += ordetail.getQuantity()*salePrice;
        }

        return new OrderTotalDTO(totalUrgentPrice, totalSalePrice, totalNormalPrice);
    }
}