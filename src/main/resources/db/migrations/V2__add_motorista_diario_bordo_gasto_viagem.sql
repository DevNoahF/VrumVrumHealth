CREATE TABLE motorista (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           cpf VARCHAR(11) UNIQUE NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           senha VARCHAR(255) NOT NULL,
                           telefone VARCHAR(20),
                           create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           role ENUM('MOTORISTA') DEFAULT 'MOTORISTA'
);

CREATE TABLE diario_bordo (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              transporte_id BIGINT NOT NULL,
                              motorista_id BIGINT NOT NULL,
                              quilometragem_inicial DECIMAL NOT NULL,
                              quilometragem_final DECIMAL NOT NULL,
                              observacoes TEXT,
                              create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (transporte_id) REFERENCES transporte(id),
                              FOREIGN KEY (motorista_id) REFERENCES motorista(id)
);

CREATE TABLE gasto_viagem (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              diario_bordo_id BIGINT NOT NULL,
                              valor DECIMAL(10,2) NOT NULL,
                              descricao TEXT,
                              FOREIGN KEY (diario_bordo_id) REFERENCES diario_bordo(id)
);
