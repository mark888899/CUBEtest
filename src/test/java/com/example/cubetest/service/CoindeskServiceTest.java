package com.example.cubetest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CoindeskServiceTest {

    @Autowired
    private CoindeskService coindeskService;

    @Test
    public void testFetchCoindeskData() {
        //呼叫外部API
        Map<String, Object> response = coindeskService.fetchCoindeskData();

        //確保回應內容
        assertNotNull(response.get("updatedTime"));
        assertNotNull(response.get("currencies"));
        assertTrue(((Map<?, ?>) response.get("currencies")).size() > 0); //確定幣別數量大於1

        Map<?, ?> currencies = (Map<?, ?>) response.get("currencies"); //取得資料
        assertTrue(currencies.containsKey("USD") || currencies.containsKey("EUR") || currencies.containsKey("GBP")); //確定包含三個幣別

        System.out.println("測試成功！");
    }
}
