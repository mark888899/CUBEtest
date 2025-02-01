package com.example.cubetest.service;

import com.example.cubetest.entity.Currency;
import com.example.cubetest.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CoindeskServiceTest {

    @Autowired
    private CoindeskService coindeskService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testFetchCoindeskData() {
        String response = coindeskService.fetchCoindeskData();
        assertNotNull(response, "Coindesk API 回應應該不為空");
        System.out.println("Coindesk API 回應: " + response);
    }

    @Test
    public void testTransformCoindeskData() {
        Map<String, Object> response = coindeskService.transformCoindeskData();

        assertNotNull(response.get("updatedTime"), "更新時間應該存在");
        assertNotNull(response.get("currencies"), "應該包含幣別資訊");

        System.out.println("轉換後的資料: " + response);
    }

    @Test
    public void testTransformCoindeskDataWithName() {
        // 先在資料庫中新增測試幣別名稱
        currencyRepository.deleteAll(); // 清除舊資料，避免衝突
        currencyRepository.save(new Currency("USD", "美元"));
        currencyRepository.save(new Currency("EUR", "歐元"));
        currencyRepository.save(new Currency("GBP", "英鎊"));

        // 取得轉換後的 Coindesk 數據
        Map<String, Object> response = coindeskService.transformCoindeskData();

        assertNotNull(response.get("updatedTime"), "更新時間應該存在");
        assertNotNull(response.get("currencies"), "應該包含幣別資訊");

        System.out.println("轉換後的資料（含幣別名稱）: " + response);
    }
}
