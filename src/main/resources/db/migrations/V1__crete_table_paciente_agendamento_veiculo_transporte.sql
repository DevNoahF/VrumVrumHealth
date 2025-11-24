CREATE TABLE paciente (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          cpf VARCHAR(11) UNIQUE NOT NULL,
                          data_nascimento DATE,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          senha VARCHAR(255) NOT NULL,
                          ddd INTEGER,
                          telefone VARCHAR(20),
                          email VARCHAR(100),
                          cep VARCHAR(9),
                          rua VARCHAR(100),
                          bairro VARCHAR(100),
                          numero STRING(10),
                          complemento VARCHAR(100),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          role ENUM('PACIENTE','ADMIN','MOTORISTA') DEFAULT 'PACIENTE'
);

CREATE TABLE admin (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        matricula VARCHAR(20) UNIQUE NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        senha VARCHAR(255) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        role ENUM('PACIENTE','ADMIN') DEFAULT 'ADMIN'
);
CREATE TABLE agendamento (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             paciente_id BIGINT NOT NULL,
                             motorista_id BIGINT NOT NULL,
                             data_consulta DATE NOT NULL,
                             hora_consulta TIME NOT NULL,
                             comprovante TEXT, -- pode guardar link do storage (S3, Firebase, filesystem)
                             local_atendimento ENUM('UPA1','UPA2','HOSPITAL1','HOSPITAL2') NOT NULL,
                             status_comprovante ENUM('PENDENTE','APROVADO','NEGADO') DEFAULT 'PENDENTE',
                             frequencia ENUM('SEMANAL','QUINZENAL','DIARIO','MENSAL', 'NENHUMA') DEFAULT 'NENHUMA', -- só preenche se for TRATAMENTO_CONTINUO
                             tratamento_continuo BOOLEAN DEFAULT FALSE,
                             retorno_casa BOOLEAN DEFAULT TRUE,
                             acompanhante BOOLEAN DEFAULT FALSE,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             FOREIGN KEY (paciente_id) REFERENCES paciente(id),
                             FOREIGN KEY (motorista_id) REFERENCES motorista(id)
);

CREATE TABLE veiculo (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         placa VARCHAR(10) UNIQUE NOT NULL,
                         modelo VARCHAR(50),
                         capacidade INT
);

