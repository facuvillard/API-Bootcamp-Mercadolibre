package com.mercadolibre.facundo_villard.services;

import com.mercadolibre.facundo_villard.dtos.PartDTO;
import com.mercadolibre.facundo_villard.dtos.PartRegisterDTO;
import com.mercadolibre.facundo_villard.dtos.PriceRegisterDTO;
import com.mercadolibre.facundo_villard.dtos.StockCountryHouseDTO;
import com.mercadolibre.facundo_villard.dtos.request.UpdateStockDTO;
import com.mercadolibre.facundo_villard.dtos.response.PartsListResponseDTO;
import com.mercadolibre.facundo_villard.dtos.response.StockListResponseDTO;
import com.mercadolibre.facundo_villard.exceptions.ApiException;
import com.mercadolibre.facundo_villard.models.*;
import com.mercadolibre.facundo_villard.repositories.*;
import com.mercadolibre.facundo_villard.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PartServicesImpl implements IPartServices<PartsListResponseDTO> {

    private final PartRepository partRepository;
    private final PriceRegisterRepository priceRepository;
    private final PartRegisterRepository partRegisterRepository;
    private final AccountRepository accountRepository;
    private final StockCountryHouseRepository stockCountryHouseRepository;
    private final CountryHouseRepository countryHouseRepository;

    private final ModelMapper modelMapper;

    public PartServicesImpl(PartRepository partRepository,
                            PriceRegisterRepository priceReg,
                            PartRegisterRepository partReg,
                            AccountRepository accountRepository,
                            StockCountryHouseRepository stockCountryHouseRepository,
                            CountryHouseRepository countryHouseRepository,
                            ModelMapper modelMapper) {
        this.partRegisterRepository = partReg;
        this.priceRepository = priceReg;
        this.partRepository = partRepository;
        this.accountRepository = accountRepository;
        this.stockCountryHouseRepository = stockCountryHouseRepository;
        this.countryHouseRepository = countryHouseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PartsListResponseDTO listAllParts(Map<String, String> params) {
        int size = params.size();
        switch (size) {
            case 0:
                return getPartsListDTOFromList(partRepository.findAll());
            case 1:
                if (params.get("queryType").equals("C")) {
                    return getPartsListDTOFromList(partRepository.findAll());
                } else {
                    throw new ApiException("400", "El parámetro ingreado es diferente a C", 400);
                }
            case 2:
                Set<Part> parts;
                LocalDate paramDate = DateUtils.stringToLocalDate(params.get("date"));
                validateDate(paramDate);
                if (paramDate.isAfter(LocalDate.now())) {
                    throw new ApiException("400", "La fecha no puede estar en el futuro", 400);
                }
                String queryType = params.get("queryType").toUpperCase(Locale.ROOT);
                if (queryType.equals("V")) {
                    parts = partRepository.findAllLastVariation(paramDate);
                } else if (queryType.equals("P")) {
                    parts = partRepository.findAllPartialSearch(paramDate);
                } else {
                    throw new ApiException("400", "El querytype ingresado es diferente a P o V o es nulo", 400);
                }
                return getPartsListDTOFromListDTO(parts, queryType);
            //endpoint 3
            case 3:
                return getListDTOByTypeDateAndOrder(params.get("queryType"), params.get("date"), params.get("order"));
        }
        return null;
    }

    @Override
    public ResponseEntity<StockListResponseDTO> updateStock(UpdateStockDTO updateStockDTO, String token) {
        String username = getUsernameOfToken(token);
        Long countryHouseId = updateStockDTO.getCountryHouseId();
        String partCode = updateStockDTO.getPartCode();
        Integer quantity = updateStockDTO.getQuantity();
        validateUser(countryHouseId, username);
        validateQuantity(quantity);
        StockCountryHouse stockCountryHouse = findStockCountryHouse(partCode, countryHouseId);
        if (stockCountryHouse != null) {
            updateStockPart(stockCountryHouse, updateStockDTO.getQuantity());
        } else {
            createStockPart(partCode, countryHouseId, quantity);
            return ResponseEntity.status(201).body(getStockListDTOFromList(stockCountryHouseRepository.findByCountryHouseId(countryHouseId)));
            //Retornar 201 con la lista de productos
        }
        return ResponseEntity.ok(getStockListDTOFromList(stockCountryHouseRepository.findByCountryHouseId(countryHouseId)));
    }

    private void validateQuantity(Integer quantity) {
        if ((quantity == null) || (quantity <=0)){
            throw new ApiException("400", "La cantidad debe ser un número positivo", 400);
        }
    }

    private StockListResponseDTO getStockListDTOFromList(List<StockCountryHouse> stocks) {
        StockListResponseDTO returnResponse = new StockListResponseDTO();
        returnResponse.setStocks(stocks.stream().map(stock -> stockToDTO(stock)).collect(Collectors.toList()));
        return returnResponse;
    }

    private StockCountryHouseDTO stockToDTO(StockCountryHouse stock) {
        return new StockCountryHouseDTO(stock.getPart().getPartCode(), stock.getQuantity());
    }

    private String getUsernameOfToken(String token) {
        token = token.substring(7, token.length());
        return com.mercadolibre.facundo_villard.services.SessionServiceImpl.getUsername(token);
    }

    private void createStockPart(String partCode, Long countryHouseId, Integer quantity) {
        StockCountryHouse stockCountryHouse = new StockCountryHouse();
        Part part = partRepository.findByPartCode(partCode).orElseThrow(
                new ApiException("404", "No se encontró un artículo con el código ingresado", 404));
        CountryHouse countryHouse = countryHouseRepository.findById(countryHouseId).orElseThrow(
                new ApiException("404", "No se encontró una casa país con el id ingresado", 404));
        stockCountryHouse.setCountryHouse(countryHouse);
        stockCountryHouse.setPart(part);
        stockCountryHouse.setQuantity(quantity);
        stockCountryHouseRepository.save(stockCountryHouse);
    }

    private void updateStockPart(StockCountryHouse stockCountryHouse, Integer quantity) {
        stockCountryHouse.setQuantity(quantity);
        stockCountryHouseRepository.save(stockCountryHouse);
    }

    private StockCountryHouse findStockCountryHouse(String partCode, Long countryHouseId) {
        return stockCountryHouseRepository.findByCountryHouseIdAndPartPartCode(countryHouseId, partCode);
    }

    private void validateUser(Long countryHouseId, String username) {
        Account account = accountRepository.findByUsernameAndCountryHouseId(username, countryHouseId);
        if (account == null) {
            throw new ApiException("403", "No tiene permisos para realizar la acción solicitada", 403);
        }
    }

    private void validateDate(LocalDate paramDate) {
        if (paramDate.isAfter(LocalDate.now())) {
            throw new ApiException("400", "La fecha no puede estar en el futuro", 400);
        }
    }

    private PartsListResponseDTO getListDTOByTypeDateAndOrder(String queryType, String fromDate, String order) {
        LocalDate localDate = DateUtils.stringToLocalDate(fromDate);
        validateDate(localDate);
        switch (queryType) {
            case "P":
                switch (order) {
                    case "1":
                        return getPartsListDTOFromListDTO(partRepository.findAllPartialSearchAsc(localDate), "P");
                    case "2":
                        return getPartsListDTOFromListDTO(partRepository.findAllPartialSearchDesc(localDate), "P");
                    case "3":
                        return getPartsListDTOFromListDTO(partRepository.findAllPartialSearchLastModification(localDate), "P");
                    default:
                        throw new ApiException("400", "El orden ingresado no es 1,2 o 3", 400);
                }
            case "V":
                switch (order) {
                    case "1":
                        return getPartsListDTOFromListDTO(partRepository.findAllPriceVariationSearchAsc(localDate), "V");
                    case "2":
                        return getPartsListDTOFromListDTO(partRepository.findAllPriceVariationSearchDesc(localDate), "V");
                    case "3":
                        return getPartsListDTOFromListDTO(partRepository.findAllPriceVariationSearchLastVariationDate(localDate), "V");
                    default:
                        throw new ApiException("400", "El orden ingresado no es 1,2 o 3.", 400);
                }
            default:
                throw new ApiException("400", "El query type ingresado es diferente a P o V.", 400);
        }
    }

    public PartsListResponseDTO getPartsListDTOFromList(List<Part> parts) {
        if (parts.isEmpty()) {
            throw new ApiException("404", "No se encontraron resultados", 404);
        }

        PartsListResponseDTO returnResponse = new PartsListResponseDTO();
        List<PartDTO> partDTOList = parts.stream().map(this::partToDTO).collect(Collectors.toList());
        returnResponse.setParts(partDTOList);
        return returnResponse;
    }

    public PartsListResponseDTO getPartsListDTOFromListDTO(Set<Part> parts, String queryType) {
        if (parts.isEmpty()) {
            throw new ApiException("404", "No se encontraron resultados", 404);
        }
        PartsListResponseDTO returnResponse = new PartsListResponseDTO();
        returnResponse.setParts(parts.stream().map(part -> partToDTO(part, queryType)).collect(Collectors.toList()));
        return returnResponse;
    }

    private PartDTO partToDTO(Part part) {
        PartRegister partRegister = partRegisterRepository.findFirstByPartIdOrderByDateModificationDesc(part.getId());
        PriceRegister priceRegister = priceRepository.findFirstByPartIdOrderByDateModificationDesc(part.getId());
        PartRegisterDTO partRegisterDTO = modelMapper.map(partRegister, PartRegisterDTO.class);
        PriceRegisterDTO priceRegisterDTO = modelMapper.map(priceRegister, PriceRegisterDTO.class);
        return new PartDTO(
                part.getPartCode(),
                partRegisterDTO.getDescription(),
                partRegister.getMaker().getName(),
                part.getStockWhereHouse().getQuantity(),
                priceRegisterDTO.getDiscount().getDiscountType(),
                priceRegisterDTO.getNormalPrice(),
                priceRegisterDTO.getUrgentPrice(),
                partRegisterDTO.getNetWeight(),
                partRegisterDTO.getLongDimension(),
                partRegisterDTO.getWidthDimension(),
                partRegisterDTO.getTallDimension(),
                getLastModificationDate(priceRegisterDTO.getDateModification(), partRegisterDTO.getDateModification())
        );
    }

    private PartDTO partToDTO(Part part, String queryType) {
        PartRegister partRegister = partRegisterRepository.findFirstByPartIdOrderByDateModificationDesc(part.getId());
        PriceRegister priceRegister = priceRepository.findFirstByPartIdOrderByDateModificationDesc(part.getId());
        PartRegisterDTO partRegisterDTO = modelMapper.map(partRegister, PartRegisterDTO.class);
        PriceRegisterDTO priceRegisterDTO = modelMapper.map(priceRegister, PriceRegisterDTO.class);
        return new PartDTO(
                part.getPartCode(),
                partRegisterDTO.getDescription(),
                partRegisterDTO.getMaker(),
                partRegisterDTO.getQuantity(),
                priceRegisterDTO.getDiscount().getDiscountType(),
                priceRegisterDTO.getNormalPrice(),
                priceRegisterDTO.getUrgentPrice(),
                partRegisterDTO.getNetWeight(),
                partRegisterDTO.getLongDimension(),
                partRegisterDTO.getWidthDimension(),
                partRegisterDTO.getTallDimension(),
                getLastModificationDate(priceRegisterDTO.getDateModification(), partRegisterDTO.getDateModification(), queryType)
        );
    }

    private String getLastModificationDate(LocalDate priceRegisterDate, LocalDate partRegisterDate) {
        if (priceRegisterDate.isBefore(partRegisterDate)) {
            return partRegisterDate.toString();
        } else {
            return priceRegisterDate.toString();
        }
    }

    private String getLastModificationDate(LocalDate priceRegisterDate, LocalDate partRegisterDate, String queryType) {
        if (queryType.equals("P")) {
            return partRegisterDate.toString();
        } else {
            return priceRegisterDate.toString();
        }
    }

}