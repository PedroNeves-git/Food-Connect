CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    type_user VARCHAR(50) NOT NULL,
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(200) NOT NULL,
    neighborhood VARCHAR(150),
    city VARCHAR(150),
    state VARCHAR(50),
    zip_code VARCHAR(20),
    complement VARCHAR(255),
    user_id BIGINT NOT NULL,
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_address_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);

INSERT INTO users (
    name,
    email,
    login,
    password,
    type_user
) VALUES (
    'João Silva',
    'joao.silva@email.com',
    'joaosilva',
    '123',
    'USER'
);

INSERT INTO user_address (
    street,
    neighborhood,
    city,
    state,
    zip_code,
    complement,
    user_id
) VALUES (
    'Avenida Brasil, 456',
    'Jardim América',
    'Rio de Janeiro',
    'RJ',
    '22040-001',
    'Casa 2',
    1
);

