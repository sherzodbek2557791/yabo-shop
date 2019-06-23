-- create tables and sequences
CREATE SEQUENCE PRODUCT_SEQ START 101;
CREATE TABLE PRODUCT
(
 ID             BIGINT NOT NULL default nextval('PRODUCT_SEQ'),
 CODE           VARCHAR(512) NOT NULL ,
 TITLE          VARCHAR(512) NOT NULL ,
 DESCRIPTION    VARCHAR(1024) ,
 PRODUCT_STATUS VARCHAR(100) NOT NULL ,
 PRICE          DOUBLE PRECISION NOT NULL,
 CURRENCY       VARCHAR(50) NOT NULL,
 IMAGE          VARCHAR(512),
 START_DATE     DATE default now() NOT NULL

);
comment on table PRODUCT   is 'Product for sale';
alter table PRODUCT add constraint PRODUCT_PK primary key (ID);
alter table PRODUCT add constraint PRODUCT_UK1 unique (CODE);

CREATE SEQUENCE CATEGORY_SEQ START 101;
CREATE TABLE CATEGORY
(
    ID              BIGINT NOT NULL default nextval('CATEGORY_SEQ'),
    CODE            VARCHAR(512) NOT NULL ,
    TITLE           VARCHAR(512) NOT NULL ,
    DESCRIPTION     VARCHAR(1024) ,
    CATEGORY_STATUS VARCHAR(100) NOT NULL ,
    COLOR           VARCHAR(50) NOT NULL,
    HEADER_IMG      VARCHAR(512)

);
comment on table CATEGORY   is 'Categoriy of products';
alter table CATEGORY add constraint CATEGORY_PK primary key (ID);
alter table CATEGORY add constraint CATEGORY_UK1 unique (CODE);

CREATE TABLE CATEGORY_PRODUCT
(
    CATEGORY_ID     BIGINT NOT NULL ,
    PRODUCT_ID      BIGINT NOT NULL

);
comment on table CATEGORY_PRODUCT   is 'Categoriy many-to-many';
alter table CATEGORY_PRODUCT add constraint CATEGORY_PRODUCT_FK1 FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID);
alter table CATEGORY_PRODUCT add constraint CATEGORY_PRODUCT_FK2 FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(ID);

-- Order tables
CREATE SEQUENCE ORDERS_SEQ START 101;
CREATE TABLE ORDERS
(
 ID            BIGINT NOT NULL default nextval('ORDERS_SEQ'),
 CODE          VARCHAR(512) NOT NULL ,
 PHONE_NUMBER  VARCHAR(100) NOT NULL ,
 EMAIL         VARCHAR(45) NOT NULL ,
 FIRST_NAME    VARCHAR(45) NOT NULL ,
 LAST_NAME     VARCHAR(45) ,
 ORDER_STATUS  VARCHAR(45) NOT NULL ,
 CREATED_DATE  DATE NOT NULL
);
comment on table ORDERS   is 'Products order';
alter table ORDERS add constraint ORDERS_PK primary key (ID);
alter table ORDERS add constraint ORDERS_UK1 unique (CODE);

CREATE SEQUENCE ORDER_ITEM_SEQ START 101;
CREATE TABLE ORDER_ITEM
(
 ID         BIGINT NOT NULL default nextval('ORDER_ITEM_SEQ'),
 ORDER_ID   BIGINT NOT NULL ,
 PRODUCT_ID BIGINT NOT NULL ,
 COUNT      DOUBLE PRECISION NOT NULL ,
 PRICE      DOUBLE PRECISION NOT NULL
);
comment on table ORDER_ITEM   is 'Order items';
alter table ORDER_ITEM add constraint ORDER_ITEM_PK primary key (ID);
alter table ORDER_ITEM add constraint ORDER_ITEM_FK1 FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID);
alter table ORDER_ITEM add constraint ORDER_ITEM_FK2 FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID);

-- Create table
CREATE SEQUENCE REGION_SEQ START 101;
create table REGION
(
    ID                        BIGINT NOT NULL DEFAULT NEXTVAL('REGION_SEQ'),
    SOATO                     VARCHAR(128) NOT NULL,
    NAME_UZ_UZ                VARCHAR(512) NOT NULL,
    NAME_RU_RU                VARCHAR(512) NOT NULL,
    CENTER_REGION_ID          BIGINT,
    CENTER_REGION_NAME_UZ_UZ  VARCHAR(512),
    CENTER_REGION_NAME_RU_RU  VARCHAR(512)
);
comment on table REGION   is 'regions with soato';
alter table REGION add constraint REGION_PK primary key (ID);
alter table REGION add constraint REGION_UK1 unique (SOATO);
alter table REGION add constraint REGION_FK1 FOREIGN KEY (CENTER_REGION_ID) REFERENCES REGION(ID);
alter table REGION add constraint REGION_CH1 CHECK (CENTER_REGION_ID!=ID);
