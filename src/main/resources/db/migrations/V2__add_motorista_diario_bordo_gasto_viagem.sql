CREATE TABLE paciente (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          cpf VARCHAR(11) UNIQUE NOT NULL,
                          data_nascimento DATE,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          senha VARCHAR(255) NOT NULL,
                          telefone VARCHAR(20),
                          cep VARCHAR(9),
                          rua VARCHAR(100),
                          bairro VARCHAR(100),
                          numero VARCHAR(10),
                          tipo_atendimento ENUM('EXAME','CONSULTA','TRATAMENTO_CONTINUO') NOT NULL,
                          frequencia ENUM('SEMANAL','QUINZENAL','DIARIO','MENSAL') NULL,
                          create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          role ENUM('PACIENTE') DEFAULT 'PACIENTE'
);

CREATE TABLE admin (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL,
                       matricula VARCHAR(20) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       senha VARCHAR(255) NOT NULL,
                       create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       role ENUM('ADMIN') DEFAULT 'ADMIN'
);

CREATE TABLE motorista (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           nome VARCHAR(100) NOT NULL,
                           cpf VARCHAR(11) UNIQUE NOT NULL,
                           cnh VARCHAR(20) UNIQUE NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           senha VARCHAR(255) NOT NULL,
                           telefone VARCHAR(20),
                           create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           role ENUM('MOTORISTA') DEFAULT 'MOTORISTA'
);

CREATE TABLE agendamento (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             paciente_id BIGINT NOT NULL,
                             data_consulta DATE NOT NULL,
                             hora_consulta TIME NOT NULL,
                             comprovante_consulta TEXT,
                             local_atendimento ENUM('UPA1','UPA2','HOSPITAL1','HOSPITAL2') NOT NULL,
                             status ENUM('PENDENTE','APROVADO','NEGADO') DEFAULT 'PENDENTE',
                             retorno_para_casa BOOLEAN DEFAULT TRUE,
                             create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (paciente_id) REFERENCES paciente(id)
);

CREATE TABLE veiculo (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         placa VARCHAR(10) UNIQUE NOT NULL,
                         modelo VARCHAR(50),
                         capacidade INT
);

CREATE TABLE transporte (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            agendamento_id BIGINT NOT NULL,
                            veiculo_id BIGINT NOT NULL,
                            horario_saida TIME NOT NULL,
                            create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (agendamento_id) REFERENCES agendamento(id),
                            FOREIGN KEY (veiculo_id) REFERENCES veiculo(id)
);

CREATE TABLE diario_bordo (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              transporte_id BIGINT NOT NULL,
                              motorista_id BIGINT NOT NULL,
                              quilometragem_inicial INT NOT NULL,
                              quilometragem_final INT,
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
