CREATE TABLE veiculos
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca        VARCHAR(255),
    modelo       VARCHAR(255),
    placa        VARCHAR(10),
    ano          INT,
    cor          VARCHAR(255),
    valor_diaria DECIMAL(10, 2)
);
    INSERT INTO veiculos (marca, modelo, placa, ano, cor, valor_diaria
) VALUES ('FIAT', 'PALIO', 'ABC2C34', 2012, 'PRATA', 100.00);

CREATE TABLE pessoas
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(255),
    cpf            VARCHAR(255),
    telefone       VARCHAR(100),
    email          VARCHAR(255)
);
INSERT INTO pessoas (nome, cpf, telefone, email
) VALUES ('João Kleber', '123.456.789-00', '(11)94002-8922', 'teste@gmail.com');

CREATE TABLE alugueis
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    pessoa_id           BIGINT NOT NULL,
    veiculo_id            BIGINT NOT NULL,
    data_inicio       DATE,
    data_fim DATE,
    valor_total DECIMAL,
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id),
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id)
);
INSERT INTO alugueis (pessoa_id, veiculo_id, data_inicio, data_fim, valor_total
) VALUES (1, 1, '2025-10-01', '2025-10-15', 1000.00);