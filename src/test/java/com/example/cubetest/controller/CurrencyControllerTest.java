package com.example.cubetest.controller;

import com.example.cubetest.entity.Currency;
import com.example.cubetest.repository.CurrencyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        currencyRepository.deleteAll(); // 清除所有資料，避免重複主鍵錯誤
    }

    @Test
    public void testCreateUSDCurrency() throws Exception {
        Currency currency = new Currency("USD", "美元");
        MvcResult result = mockMvc.perform(post("/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.name").value("美元"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        Long createdId = jsonNode.get("id").asLong();

        assertNotNull(createdId, "新增的幣別 ID 應該存在");
        assertTrue(createdId > 0, "新增的幣別 ID 應該大於 0");
        System.out.println("測試新增美元成功，ID: " + createdId);
    }

    @Test
    public void testCreateGBPCurrency() throws Exception {
        Currency currency = new Currency("GBP", "英鎊");
        MvcResult result = mockMvc.perform(post("/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("GBP"))
                .andExpect(jsonPath("$.name").value("英鎊"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        Long createdId = jsonNode.get("id").asLong();

        assertNotNull(createdId, "新增的幣別 ID 應該存在");
        assertTrue(createdId > 0, "新增的幣別 ID 應該大於 0");
        System.out.println("測試新增英鎊成功，ID: " + createdId);
    }

    @Test
    public void testCreateEURCurrency() throws Exception {
        Currency currency = new Currency("EUR", "歐元");
        MvcResult result = mockMvc.perform(post("/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("EUR"))
                .andExpect(jsonPath("$.name").value("歐元"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        Long createdId = jsonNode.get("id").asLong();

        assertNotNull(createdId, "新增的幣別 ID 應該存在");
        assertTrue(createdId > 0, "新增的幣別 ID 應該大於 0");
        System.out.println("測試新增歐元成功，ID: " + createdId);
    }

    @Test
    public void testGetAllCurrencies() throws Exception {
        Currency currency = new Currency("USD", "美元");
        currencyRepository.save(currency);

        MvcResult result = mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("USD"))
                .andExpect(jsonPath("$[0].name").value("美元"))
                .andReturn();;

        String responseJson = result.getResponse().getContentAsString(StandardCharsets.UTF_8); // ✅ 確保讀取 UTF-8

        System.out.println("查詢到的幣別資料: " + responseJson);
    }

    @Test
    public void testUpdateCurrency() throws Exception {
        // 1️⃣ 先建立測試資料
        Currency currency = new Currency("USD", "美元");
        currency = currencyRepository.save(currency);

        // 2️⃣ 更新 JSON 請求
        Currency updatedCurrency = new Currency("USD", "USDtestUpdate");

        // 3️⃣ 執行 PUT 請求
        mockMvc.perform(put("/currencies/" + currency.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCurrency)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.name").value("USDtestUpdate"));

        // 4️⃣ 查詢資料庫確認更新
        Currency updatedCurrencyFromDB = currencyRepository.findById(currency.getId()).orElse(null);
        assertNotNull(updatedCurrencyFromDB, "更新後的幣別應該存在");
        assertEquals("USD", updatedCurrencyFromDB.getCode(), "幣別代碼應該是 USD");
        assertEquals("USDtestUpdate", updatedCurrencyFromDB.getName(), "幣別名稱應該被更新");

        System.out.println("更新後的幣別資料: " + updatedCurrencyFromDB);
    }


    @Test
    public void testDeleteCurrency() throws Exception {
        Currency currency = new Currency("USD", "美元");
        currency = currencyRepository.save(currency);

        mockMvc.perform(delete("/currencies/" + currency.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
