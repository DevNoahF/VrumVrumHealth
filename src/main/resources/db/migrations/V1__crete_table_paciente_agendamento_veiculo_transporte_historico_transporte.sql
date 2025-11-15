CREATE TABLE paciente (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          cpf VARCHAR(11) UNIQUE NOT NULL,
                          data_nascimento DATE,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          senha_hash VARCHAR(255) NOT NULL,
                          telefone VARCHAR(20),
                          email VARCHAR(100),
                          cep VARCHAR(9),
                          rua VARCHAR(100),
                          bairro VARCHAR(100),
                          numero VARCHAR(10),
                          tipo_atendimento ENUM('EXAME','CONSULTA','TRATAMENTO_CONTINUO') NOT NULL,
                          frequencia ENUM('SEMANAL','QUINZENAL','DIARIO','MENSAL') NULL, -- só preenche se for TRATAMENTO_CONTINUO
                          data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          role ENUM('PACIENTE','ADMIN') DEFAULT 'PACIENTE'
);

CREATE TABLE admin (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        matricula VARCHAR(20) UNIQUE NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        senha_hash VARCHAR(255) NOT NULL,
                        data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        role ENUM('PACIENTE','ADMIN') DEFAULT 'ADMIN'
);
CREATE TABLE agendamento (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             paciente_id BIGINT NOT NULL,
                             data_consulta DATE NOT NULL,
                             hora_consulta TIME NOT NULL,
                             comprovante_consulta TEXT, -- pode guardar link do storage (S3, Firebase, filesystem)
                             local_atendimento ENUM('UPA1','UPA2','HOSPITAL1','HOSPITAL2') NOT NULL,
                             estado ENUM('PENDENTE','APROVADO','NEGADO') DEFAULT 'PENDENTE',
                             retorno_para_casa BOOLEAN DEFAULT TRUE,
                             data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
                            veiculo_id BIGINT,
                            horario_saida TIME NOT NULL,
                            data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (agendamento_id) REFERENCES agendamento(id),
                            FOREIGN KEY (veiculo_id) REFERENCES veiculo(id),
);
