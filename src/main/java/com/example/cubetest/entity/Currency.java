package com.example.cubetest.entity;

import javax.persistence.*;

@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;  // 幣別代碼 (如 USD, EUR)

    @Column(nullable = false)
    private String name;  // 幣別中文名稱

    public Currency() {}

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
