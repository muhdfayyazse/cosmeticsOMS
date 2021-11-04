Create Table USER(
    ID       varchar(255) NOT NULL,
    EMAIL    varchar(255),
    PASSWORD varchar(255),
    IS_ADMIN  smallint,
    PRIMARY KEY (ID)
) ENGINE = INNODB;

Create Table CATEGORY(
    ID              varchar(255) NOT NULL,

) ENGINE = INNODB;




Create Table PRODUCT(
    ID              varchar(255) NOT NULL,
    NAME            varchar(255) NOT NULL,
    DESCRIPTION     varchar(255) NOT NULL,
    STOCK           int,
    PRICE           number(15,2),
    DISCOUNT        number(15,2),
    RATING          int,
    SALE_PRICE      number(15,2),
    CATEGRORY_ID    varchar(255) NOT NULL,
    IS_DELETED      boolean,
    IS_ACTIVE       boolean,
    primary key(ID)
) ENGINE = INNODB;

Create Table PRODUCT_IMAGE(
    ID              varchar(255) NOT NULL,
    IMAGE_BASED64   varchar(255) NOT NULL,
    primary key(ID)
) ENGINE = INNODB;


Create Table PRODUCT_TAG(
    ID              varchar(255) NOT NULL,
    CATEGORY_ID     varchar(255) NOT NULL,
    primary key(ID)
) ENGINE = INNODB;

Create Table PRODUCT_SIZE(
    ID              varchar(255) NOT NULL,
    SIZE_ID         varchar(255) NOT NULL,
    primary key(ID)
) ENGINE = INNODB;

Create Table PRODUCT_COLOR(
    ID              varchar(255) NOT NULL,
    COLOR_ID         varchar(255) NOT NULL,
    primary key(ID)
) ENGINE = INNODB;



