-- begin DDCME_ENTITY_VISIBILITY_CONFIGURATION
create table DDCME_ENTITY_VISIBILITY_CONFIGURATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    ENTITY_META_CLASS varchar(255),
    ENTITY_ATTRIBUTE varchar(255),
    VISIBLE boolean,
    --
    primary key (ID)
)^
-- end DDCME_ENTITY_VISIBILITY_CONFIGURATION
