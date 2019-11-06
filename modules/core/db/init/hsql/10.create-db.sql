-- begin DDCME_TEST_ENTITY
create table DDCME_PRODUCTION_ENTITY (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    ENTITY varchar(255),
    ENTITY_ATTRIBUTE varchar(255),
    --
    TEST varchar(255),
    --
    primary key (ID)
)^
-- end DDCME_TEST_ENTITY
