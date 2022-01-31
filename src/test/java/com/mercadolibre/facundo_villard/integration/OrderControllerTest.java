package com.mercadolibre.facundo_villard.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest extends ControllerTest{

    @Autowired
    MockMvc mockMvc;

    private String token;

    @BeforeEach
    //Nos loguea y devuelve el token que vamos a necesitar
    void setUp() throws Exception {
        String content = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "user_one")
                .param("password", "contra123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        int start = content.indexOf("Bearer ");
        int end = content.indexOf("\"", start);
        token = content.substring(start, end);
    }

    @Test
    //Probamos endpoint cualquiera sin pasar el token
    void getAllPartsForbidden() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .param("dealerNumber", "0001"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getOrdersByDealerNumber() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("order[0].orderNumber").value("123456"))
                .andExpect(jsonPath("order[1].orderNumber").value("100000"))
                .andExpect(jsonPath("order[2].orderNumber").value("445687"));
    }

    @Test //Probamos con un dealerNumber inexistente
    void getOrdersByDealerNumber_NotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0005"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getOrdersByDealerNumberAndOrderAsc() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001")
                .param("order", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("order[0].orderNumber").value("445687"))
                .andExpect(jsonPath("order[1].orderNumber").value("123456"))
                .andExpect(jsonPath("order[2].orderNumber").value("100000"))
                .andExpect(jsonPath("order.size()").value(3));
    }
    @Test
    void getOrdersByDealerNumberAndOrderDesc() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001")
                .param("order", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("order[0].orderNumber").value("100000"))
                .andExpect(jsonPath("order[1].orderNumber").value("123456"))
                .andExpect(jsonPath("order[2].orderNumber").value("445687"))
                .andExpect(jsonPath("order.size()").value(3));
    }

    @Test
    void getOrdersByDealerNumberAndDeliveryStatus_Cancelado() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001")
                .param("deliveryStatus", "C"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    //Hay uno pendiente
    void getOrdersByDealerNumberAndDeliveryStatus_Pendiente() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001")
                .param("deliveryStatus", "P"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("order[0].orderNumber").value("123456"))
                .andExpect(jsonPath("order[1].orderNumber").value("100000"))
                .andExpect(jsonPath("order.size()").value(2));
    }

    @Test
    //Hay uno demorado
    void getOrdersByDealerNumberAndDeliveryStatus_Demorado() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001")
                .param("deliveryStatus", "D"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("order[0].orderNumber").value("445687"))
                .andExpect(jsonPath("order.size()").value(1));
    }

    @Test
    //No hay ninguno
    void getOrdersByDealerNumberAndDeliveryStatus_Finalizado() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001")
                .param("deliveryStatus", "F"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getOrdersByDealerNumberAndDeliveryStatus_NoExisteDealer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0005")
                .param("deliveryStatus", "D"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getOrdersByDealerNumberAndDeliveryStatusAndOrderAsc_Ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001")
                .param("deliveryStatus", "P")
                .param("order", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("order[0].orderDate").value("2021-04-21"))
                .andExpect(jsonPath("order[1].orderDate").value("2021-04-23"))
                .andExpect(jsonPath("order.size()").value(2));
    }

    @Test
    void getOrdersByDealerNumberAndDeliveryStatusAndOrderDesc_Ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001")
                .param("deliveryStatus", "P")
                .param("order", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("order[0].orderDate").value("2021-04-23"))
                .andExpect(jsonPath("order[1].orderDate").value("2021-04-21"))
                .andExpect(jsonPath("order.size()").value(2));

    }

    /*
    @Test
    void getOrdersByDealerNumberAndDeliveryStatus_IngresaMalStatus() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("dealerNumber", "0001")
                .param("deliveryStatus", "X"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
*/
    /*
    //api/v1/parts/orders?dealerNumber=[nro CE]&deliveryStatus=[estado pedido]&order=[tipo de ordenamiento]
    */

    //TEST REQ 3 -- Busqueda por numero de orden

    @Test
    void getOrdersByOrderNumber() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders/{orderNumberCM}", "1-123456")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("orderDetails.size()").value(1));
    }

    @Test
    void getOrdersByOrderNumber_NotFound() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders/{orderNumberCM}", "1-333333")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    void getOrdersByAllInfoTrue_Ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders/{orderNumberCM}", "1-123456")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("allInfo", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("orderDetails.size()").value(1));

    }
    @Test
    void getOrdersByAllInfoFalse_Ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/orders/{orderNumberCM}", "1-123456")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("allInfo", "false"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void makeAnOrderPartCodeNotExistent() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content("{\n" +
                        "    \"parts\": [\n" +
                        "        {\n" +
                        "            \"partCode\": \"01A\",\n" +
                        "            \"quantity\": 2,\n" +
                        "            \"urgent\": true\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"partCode\": \"067B\",\n" +
                        "            \"quantity\": 1,\n" +
                        "            \"urgent\": false\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    void makeAnOrderNotEnoughQuantity() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content("{\n" +
                        "    \"parts\": [\n" +
                        "        {\n" +
                        "            \"partCode\": \"01A\",\n" +
                        "            \"quantity\": 2,\n" +
                        "            \"urgent\": true\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"partCode\": \"01B\",\n" +
                        "            \"quantity\": 1000,\n" +
                        "            \"urgent\": false\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    void makeAnOrderShouldBeSuccessful() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content("{\n" +
                        "    \"parts\": [\n" +
                        "        {\n" +
                        "            \"partCode\": \"01A\",\n" +
                        "            \"quantity\": 2,\n" +
                        "            \"urgent\": true\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"partCode\": \"01B\",\n" +
                        "            \"quantity\": 2,\n" +
                        "            \"urgent\": false\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andDo(print())
                .andExpect(jsonPath("total").value("2460.0"))
                .andExpect(status().isOk());
    }
    @Test
    void makeAnOrderInvalidQuantity() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content("{\n" +
                        "    \"parts\": [\n" +
                        "        {\n" +
                        "            \"partCode\": \"01A\",\n" +
                        "            \"quantity\": -19,\n" +
                        "            \"urgent\": true\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"partCode\": \"01B\",\n" +
                        "            \"quantity\": 0,\n" +
                        "            \"urgent\": false\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
