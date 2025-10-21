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

CREATE TABLE diario_bordo (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              transporte_id BIGINT NOT NULL,
                              motorista_id BIGINT NOT NULL,
                              veiculo_id BIGINT NOT NULL,
                              quilometragem_inicial DECIMAL(10,2) NOT NULL,
                              quilometragem_final DECIMAL(10,2) NOT NULL,
                              observacoes TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (transporte_id) REFERENCES transporte(id),
                              FOREIGN KEY (motorista_id) REFERENCES motorista(id),
                              FOREIGN KEY (veiculo_id) REFERENCES veiculo(id)
);

