DROP TABLE IF EXISTS tb_security_tenant_resource;
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

-- Spring Security + TenantContext + MyBatis-Plus 租户行插件集成测试
CREATE TABLE tb_security_tenant_resource
(
    id         int          NOT NULL AUTO_INCREMENT,
    tenant_id  varchar(64)  NOT NULL,
    name       varchar(128) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;