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