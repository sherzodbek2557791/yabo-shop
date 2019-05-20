-- Warning: You can generate script only for two tables at a time in your Free plan
CREATE TABLE `product`
(
 `id`             bigint NOT NULL ,
 `title`          varchar(512) NOT NULL ,
 `description`    varchar(1024) ,
 `product_status` varchar(100) NOT NULL ,
 `created_date`   date NOT NULL ,
PRIMARY KEY (`id`)
);

CREATE TABLE `order`
(
 `id`           bigint NOT NULL ,
 `phone_number` varchar(100) NOT NULL ,
 `email`        varchar(45) NOT NULL ,
 `first_name`   varchar(45) NOT NULL ,
 `last_name`    varchar(45) ,
 `order_status` varchar(45) NOT NULL ,
 `careted_date` date NOT NULL ,
PRIMARY KEY (`id`)
);

CREATE TABLE `order_item`
(
 `id`         bigint NOT NULL ,
 `order_id`   bigint NOT NULL ,
 `product_id` bigint NOT NULL ,
 `count`      double NOT NULL ,
 `price`      double NOT NULL ,
PRIMARY KEY (`id`),
KEY `fkIdx_151` (`order_id`),
CONSTRAINT `FK_151` FOREIGN KEY `fkIdx_151` (`order_id`) REFERENCES `order` (`id`),
KEY `fkIdx_154` (`product_id`),
CONSTRAINT `FK_154` FOREIGN KEY `fkIdx_154` (`product_id`) REFERENCES `product` (`id`)
);







-- Create table
create table AP_REGION
(
    id            NUMBER not null,
    ordered       NUMBER not null,
    ap_country_id NUMBER default 1 not null,
    name_us_en    VARCHAR2(128) not null,
    name_ru_ru    VARCHAR2(128) not null,
    name_uz_uz    VARCHAR2(128) not null,
    name_uz_cyr   VARCHAR2(128) not null,
    name_uz_kk    VARCHAR2(128) not null,
    description   VARCHAR2(512),
    soato         VARCHAR2(128)
);

comment on table AP_REGION   is 'Viloyatlar';
alter table AP_REGION add constraint AP_REGION_PK primary key (ID);

-- Create table
create table AP_AREA
(
    id           NUMBER not null,
    ordered      NUMBER not null,
    ap_region_id NUMBER not null,
    name_us_en   VARCHAR2(128) not null,
    name_ru_ru   VARCHAR2(128) not null,
    name_uz_uz   VARCHAR2(128) not null,
    name_uz_cyr  VARCHAR2(128) not null,
    name_uz_kk   VARCHAR2(128) not null,
    description  VARCHAR2(512),
    soato        VARCHAR2(128)
);
-- Add comments to the table
comment on table AP_AREA is 'Viloyat Tumanlari';
-- Create/Recreate primary, unique and foreign key constraints
alter table AP_AREA add constraint AP_AREA_PK primary key (ID);
alter table AP_AREA add constraint AP_AREA_FK1 foreign key (AP_REGION_ID) references AP_REGION (ID);