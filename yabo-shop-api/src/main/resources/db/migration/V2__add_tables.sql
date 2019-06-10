-- Create table
CREATE SEQUENCE O_GROUP_SEQ START 1;
create table O_GROUP
(
    ID          BIGINT       NOT NULL DEFAULT NEXTVAL('O_GROUP_SEQ'),
    PARENT_ID   BIGINT,
    TITLE       VARCHAR(128) NOT NULL,
    DESCRIPTION VARCHAR(512)
);
comment on table O_GROUP is 'Object group for all products, ist like a category';
alter table O_GROUP add constraint O_GROUP_PK primary key (ID);
alter table O_GROUP add constraint O_GROUP_FK1 FOREIGN KEY (PARENT_ID) REFERENCES O_GROUP (ID);
alter table O_GROUP add constraint O_GROUP_CH1 CHECK (PARENT_ID != ID);

-- Create table
CREATE SEQUENCE O_ITEM_PRODUCT_SEQ START 1;
create table O_ITEM_PRODUCT
(
    ID          BIGINT       NOT NULL DEFAULT NEXTVAL('O_ITEM_PRODUCT_SEQ'),
    O_GROUP_ID  BIGINT       NOT NULL,
    PRODUCT_ID  BIGINT       NOT NULL
);
comment on table O_ITEM_PRODUCT is 'Item of product in group';
alter table O_ITEM_PRODUCT add constraint O_ITEM_PRODUCT_PK primary key (ID);
alter table O_ITEM_PRODUCT add constraint O_ITEM_PRODUCT_FK1 FOREIGN KEY (O_GROUP_ID) REFERENCES O_GROUP (ID);
alter table O_ITEM_PRODUCT add constraint O_ITEM_PRODUCT_FK2 FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID);