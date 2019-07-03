-- OrderPayment tables
CREATE SEQUENCE ORDER_PAYMENT_SEQ START 101;
CREATE TABLE ORDER_PAYMENT
(
    ID              BIGINT           NOT NULL default nextval('ORDER_PAYMENT_SEQ'),
    ORDER_ID        BIGINT           NOT NULL,
    PAYMENT_TYPE    VARCHAR(100)     NOT NULL,
    PAYMENT_DETAILS JSONB,
    TOTAL_PRICE     DOUBLE PRECISION NOT NULL,
    FIRST_PAY       DOUBLE PRECISION NOT NULL,
    CREATED_DATE    timestamp        NOT NULL default now()
);
comment on table ORDER_PAYMENT is 'Order payments';
alter table ORDER_PAYMENT add constraint ORDER_PAYMENT_PK primary key (ID);
alter table ORDER_PAYMENT add constraint ORDER_PAYMENT_FK1 FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID);

comment on column ORDER_PAYMENT.PAYMENT_TYPE is 'Type of payment: CLICK, UZ_CARD, CASH, INSTALLMENT_PLAN';


-- Add column
alter table orders drop column email;
alter table orders drop column first_name;
alter table orders drop column last_name;
alter table orders drop column message;

alter table orders add column full_name VARCHAR(150) not null default '';
alter table orders add column region_soato VARCHAR(128) not null default '17';
alter table orders add column area_soato VARCHAR(128) not null default '17';

alter table orders add constraint ORDERS_FK1 FOREIGN KEY (region_soato) REFERENCES REGION (soato);
alter table orders add constraint ORDERS_FK2 FOREIGN KEY (area_soato) REFERENCES REGION (soato);