DROP TABLE IF EXISTS tb_all_type_entity;
DROP TABLE IF EXISTS tb_crypto_entity;
DROP TABLE IF EXISTS tb_tenant_demo;

CREATE TABLE tb_all_type_entity
(
    id            int NOT NULL AUTO_INCREMENT,
    name          varchar(128)   DEFAULT NULL,
    age           int            DEFAULT NULL,
    title         varchar(256)   DEFAULT NULL,
    score         decimal(10, 2) DEFAULT NULL,
    price         decimal(10, 2) DEFAULT NULL,
    creation_time datetime       DEFAULT NULL,
    deleted_time  datetime       DEFAULT NULL,
    device        json           DEFAULT NULL,
    entities      json           DEFAULT NULL,
    status        tinyint        DEFAULT '0',
    creative_mode tinyint        DEFAULT '0',
    executes      json           DEFAULT NULL
) ENGINE=InnoDB;
CREATE TABLE tb_crypto_entity
(
    id           int NOT NULL AUTO_INCREMENT,
    crypto_value varchar(128) DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE tb_tenant_demo
(
    id        int         NOT NULL AUTO_INCREMENT,
    tenant_id varchar(64) NOT NULL,
    name      varchar(128) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;