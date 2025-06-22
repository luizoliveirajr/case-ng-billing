CREATE TABLE transacao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    conta_id BIGINT NOT NULL,
    forma_pagamento CHAR(1) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (conta_id) REFERENCES conta(id)
);