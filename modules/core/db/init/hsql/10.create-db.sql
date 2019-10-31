-- begin DDCME_CUSTOMER
create table DDCME_CUSTOMER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    BIRTHDAY date not null,
    --
    primary key (ID)
)^
-- end DDCME_CUSTOMER
