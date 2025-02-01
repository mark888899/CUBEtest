CUBE 測試小考 2025/02/01

*本機H2位置：http://localhost:8080/h2-console/ (需run專案)


*H2 建立語法
CREATE TABLE CURRENCY (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL
);
