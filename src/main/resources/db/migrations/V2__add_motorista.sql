CREATE TABLE motorista (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           cpf VARCHAR(11) UNIQUE NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           senha VARCHAR(255) NOT NULL,
                           telefone VARCHAR(20),
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           role ENUM('MOTORISTA') DEFAULT 'MOTORISTA'
);



