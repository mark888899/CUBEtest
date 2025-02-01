package com.example.cubetest.controller;

import com.example.cubetest.service.CoindeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/coindesk")
public class CoindeskController {

    @Autowired
    private CoindeskService coindeskService;

    // 呼叫 Coindesk API 並轉換資料格式
    @GetMapping("/fetch")
    public Map<String, Object> getCoindeskData() {
        return coindeskService.fetchCoindeskData();
    }
}
