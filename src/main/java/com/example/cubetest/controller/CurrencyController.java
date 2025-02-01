package com.example.cubetest.controller;

import com.example.cubetest.entity.Currency;
import com.example.cubetest.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyRepository currencyRepository;

    // 取得所有幣別
    @GetMapping
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        return ResponseEntity.ok()
                .body(currencies);
    }

    // 新增幣別
    @PostMapping
    public Currency createCurrency(@RequestBody Currency currency) {
        return currencyRepository.save(currency);
    }

    // 更新幣別
    @PutMapping("/{id}")
    public Currency updateCurrency(@PathVariable Long id, @RequestBody Currency currencyDetails) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isPresent()) {
            Currency currency = optionalCurrency.get();
            currency.setCode(currencyDetails.getCode());
            currency.setName(currencyDetails.getName());
            return currencyRepository.save(currency);
        } else {
            throw new RuntimeException("Currency not found with id " + id);
        }
    }

    // 刪除幣別
    @DeleteMapping("/{id}")
    public void deleteCurrency(@PathVariable Long id) {
        currencyRepository.deleteById(id);
    }
}
