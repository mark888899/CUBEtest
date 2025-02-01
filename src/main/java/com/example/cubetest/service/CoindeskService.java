package com.example.cubetest.service;

import com.example.cubetest.entity.Currency;
import com.example.cubetest.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CoindeskService {

    private static final String COINDESK_API_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Autowired
    private CurrencyRepository currencyRepository;

    public Map<String, Object> fetchCoindeskData() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(COINDESK_API_URL, String.class);

        if (response == null) {
            throw new RuntimeException("Failed to fetch data from Coindesk API");
        }

        JSONObject json = new JSONObject(response);
        JSONObject timeObj = json.getJSONObject("time");
        String updatedTime = timeObj.getString("updatedISO");

        JSONObject bpiObj = json.getJSONObject("bpi");

        Map<String, Object> result = new HashMap<>();
        result.put("updatedTime", formatDateTime(updatedTime));

        Map<String, Object> currencies = new HashMap<>();

        for (Iterator<String> it = bpiObj.keys(); it.hasNext();) {
            String key = it.next();
            JSONObject currencyObj = bpiObj.getJSONObject(key);
            Map<String, String> currencyData = new HashMap<>();
            currencyData.put("code", currencyObj.getString("code"));
            currencyData.put("rate", currencyObj.getString("rate"));
            currencyData.put("name", getCurrencyNameFromDB(currencyObj.getString("code"))); // **從 DB 查詢幣別名稱**
            currencies.put(key, currencyData);
        }

        result.put("currencies", currencies);
        return result;
    }

    private String formatDateTime(String isoTime) {
        LocalDateTime dateTime = LocalDateTime.parse(isoTime.substring(0, 19)); //取到秒
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    private String getCurrencyNameFromDB(String code) { //從DB取得幣別名稱
        Optional<Currency> currency = currencyRepository.findByCode(code);
        return currency.map(Currency::getName).orElse("未知幣別");
    }
}
