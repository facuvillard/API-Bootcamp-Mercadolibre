package com.mercadolibre.facundo_villard.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.facundo_villard.dtos.request.UpdateStockDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest
@AutoConfigureMockMvc
class PartControllerTest extends ControllerTest{
    @Autowired
    MockMvc mockMvc;

    private String token;

    @BeforeEach
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
    void getAllPartsForbidden() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllPartsOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[0].partCode").value("01A"))
                .andExpect(jsonPath("parts[1].partCode").value("01B"))
                .andExpect(jsonPath("parts[2].partCode").value("02A"))
                .andExpect(jsonPath("parts[3].partCode").value("03A"))
                .andExpect(jsonPath("parts.size()").value(4));
    }

    @Test
    void getAllPartsFilterPOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("date", "2021-04-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[0].partCode").value("01A"))
                .andExpect(jsonPath("parts.size()").value(2));
    }

    @Test
    void getAllPartsFilterCOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "C"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[0].partCode").value("01A"))
                .andExpect(jsonPath("parts[1].partCode").value("01B"))
                .andExpect(jsonPath("parts[2].partCode").value("02A"))
                .andExpect(jsonPath("parts[3].partCode").value("03A"))
                .andExpect(jsonPath("parts.size()").value(4));
    }

    @Test
    void getAllPartsFilterPNotFound() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("date", "2021-04-22"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException().getMessage();
        assertEquals("No se encontraron resultados", message);
    }

    @Test
    void getAllPartsFilterVOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "V")
                .param("date", "2021-04-20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[0].partCode").value("01A"))
                .andExpect(jsonPath("parts.size()").value(1));
    }


    @Test
    void getAllPartsByTypeAndOrderFilterPASCOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("order", "1")
                .param("date", "2021-03-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[0].partCode").value("01A"))
                .andExpect(jsonPath("parts[3].partCode").value("03A"))
                .andExpect(jsonPath("parts.size()").value(4));
    }

    @Test
    void getAllPartsByTypeAndOrderFilterPDESCPOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("order", "2")
                .param("date", "2021-03-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[0].partCode").value("03A"))
                .andExpect(jsonPath("parts[3].partCode").value("01A"))
                .andExpect(jsonPath("parts.size()").value(4));
    }

    @Test
    void getAllPartsByTypeAndOrderFilterPLastModificationOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("order", "3")
                .param("date", "2021-03-01"))
                .andExpect(jsonPath("parts[0].lastModification").value("2021-04-05"))
                .andExpect(jsonPath("parts[3].lastModification").value("2021-03-03"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllPartsByTypeAndOrderFilterPBadParam() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "Ñ")
                .param("order", "3")
                .param("date", "2021-03-01"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPartsByTypeAndOrderFilterPInvalidDate() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("order", "3")
                .param("date", "2021-07-01"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPartsByTypeAndOrderFilterVASCOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "V")
                .param("order", "1")
                .param("date", "2021-03-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[0].partCode").value("01A"))
                .andExpect(jsonPath("parts[2].partCode").value("03A"))
                .andExpect(jsonPath("parts.size()").value(3));
    }

    @Test
    void getAllPartsByTypeAndOrderFilterVDESCPOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "V")
                .param("order", "2")
                .param("date", "2021-03-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[0].partCode").value("03A"))
                .andExpect(jsonPath("parts[2].partCode").value("01A"))
                .andExpect(jsonPath("parts.size()").value(3));
    }

    @Test
    void getAllPartsByTypeAndOrderFilterVLastModificationOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "V")
                .param("order", "3")
                .param("date", "2021-03-01"))
                .andExpect(jsonPath("parts[0].lastModification").value("2021-04-20"))
                .andExpect(jsonPath("parts[2].lastModification").value("2021-03-20"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllPartsByTypeAndOrderFilterVInvalidDate() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "V")
                .param("order", "3")
                .param("date", "2021-07-01"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPartsFilterVNotFound() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "V")
                .param("date", "2021-04-22"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException().getMessage();
        assertEquals("No se encontraron resultados", message);

    }

    @Test
    void getAllPartsFilterBadRequest() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "J")
                .param("date", "2021-04-01"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("El querytype ingresado es diferente a P o V o es nulo", message);
    }


    @Test
    void getAllPartsFilterInvalidDate() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "J")
                .param("date", "2021-05-01"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("La fecha no puede estar en el futuro", message);
    }

    @Test
    void getAllPartsFilterPOrder1Ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("date", "2021-03-20")
                .param("order", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[0].partCode").value("01A"))
                .andExpect(jsonPath("parts[1].partCode").value("02A"))
                .andExpect(jsonPath("parts[2].partCode").value("03A"))
                .andExpect(jsonPath("parts.size()").value(3));
    }

    @Test
    void getAllPartsFilterPOrder2Ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("date", "2021-03-20")
                .param("order", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("parts[2].partCode").value("01A"))
                .andExpect(jsonPath("parts[1].partCode").value("02A"))
                .andExpect(jsonPath("parts[0].partCode").value("03A"))
                .andExpect(jsonPath("parts.size()").value(3));
    }

    @Test
    void getAllPartsFilterPOrder2NotFound() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("date", "2021-04-22")
                .param("order", "2"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn().getResolvedException().getMessage();
        assertEquals("No se encontraron resultados", message);

    }

    @Test
    void getAllPartsFilterPOrder3InvalidDate() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("date", "2022-04-22")
                .param("order", "3"))
                .andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();
        assertEquals("La fecha no puede estar en el futuro", message);

    }

    @Test
    void getAllPartsFilterPInvalidOrder() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "P")
                .param("date", "2021-04-22")
                .param("order", "4"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("El orden ingresado no es 1,2 o 3", message);
    }


    @Test
    void getAllPartsFilterVInvalidOrder() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "V")
                .param("date", "2021-04-22")
                .param("order", "4"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("El orden ingresado no es 1,2 o 3.", message);
    }

    @Test
    void getAllPartsFilterQueryTypeFail() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/parts/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .param("queryType", "J"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("El parámetro ingreado es diferente a C", message);
    }

    @Test
    void updateExistentStock() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(asJsonString(new UpdateStockDTO(1L, "01A", 10))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateStockWithoutPermission() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(asJsonString(new UpdateStockDTO(5L, "01A", 10))))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn().getResolvedException().getMessage();
        assertEquals("No tiene permisos para realizar la acción solicitada", message);
    }

/*
    @Test
    void createPartStock() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/parts").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(asJsonString(new UpdateStockDTO(1L, "01B", 10))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
*/

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateStockWithoutQuantity() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(asJsonString(new UpdateStockDTO(1L, "01A", null))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("La cantidad debe ser un número positivo", message);
    }

    @Test
    void updateStockInvalidQuantity() throws Exception {
        String message = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parts").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(asJsonString(new UpdateStockDTO(1L, "01A", -10))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResolvedException().getMessage();
        assertEquals("La cantidad debe ser un número positivo", message);
    }
}