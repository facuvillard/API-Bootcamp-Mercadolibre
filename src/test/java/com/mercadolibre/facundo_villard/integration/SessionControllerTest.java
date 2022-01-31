package com.mercadolibre.facundo_villard.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@AutoConfigureMockMvc
class SessionControllerTest extends ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void loginFail() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "user_one")
                .param("password", "contra12"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void loginOk() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "user_one")
                .param("password", "contra123"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}