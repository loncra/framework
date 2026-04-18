DROP TABLE IF EXISTS tb_operation_data;
CREATE TABLE tb_operation_data
(
    id            int          NOT NULL AUTO_INCREMENT,
    creation_time datetime DEFAULT NULL,
    version       int      DEFAULT 0,
    name          varchar(128) NOT NULL,
    owner         varchar(64) NULL,
    principal     varchar(64) NULL
) ENGINE=InnoDB;