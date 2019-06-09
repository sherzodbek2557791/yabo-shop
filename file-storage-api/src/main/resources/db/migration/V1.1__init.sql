CREATE SEQUENCE FILE_RESOURCE_SEQ START 1;
CREATE TABLE FILE_RESOURCE
(
    ID                    VARCHAR(255) DEFAULT ('FILE_RESOURCE_SEQ-' || NEXTVAL('FILE_RESOURCE_SEQ')) NOT NULL,
    ORDERED               BIGINT       DEFAULT CURRVAL('FILE_RESOURCE_SEQ')                           NOT NULL,
    FILE_RESOURCE_VERSION BIGINT       DEFAULT 1                                                      NOT NULL,
    CREATED_DATE          DATE         DEFAULT now()                                                  NOT NULL,
    STORED_NAME           VARCHAR(512)                                                                NOT NULL,
    UPLOADED_NAME         VARCHAR(512)                                                                NOT NULL,
    CLIENT_ID             VARCHAR(100)                                                                NOT NULL,
    FILE_RESOURCE_TYPE    VARCHAR(255)                                                                NOT NULL,
    FILE_RESOURCE_PATH    VARCHAR(2048)                                                               NOT NULL,
    FILE_RESOURCE_SIZE    BIGINT                                                                      NOT NULL,
    EXTENSION             VARCHAR(10)                                                                 NOT NULL,
    DELIMITER             CHAR(1)      DEFAULT '.'                                                    NOT NULL
);
comment on table FILE_RESOURCE is 'File resources';
alter table FILE_RESOURCE
    add constraint FILE_RESOURCE_PK primary key (ID);

CREATE VIEW FILE_RESOURCE_VIEW AS
SELECT r.ID,
       r.ORDERED,
       r.CREATED_DATE,
       r.STORED_NAME,
       r.UPLOADED_NAME,
       r.CLIENT_ID,
       r.FILE_RESOURCE_TYPE,
       r.FILE_RESOURCE_PATH,
       r.FILE_RESOURCE_SIZE,
       r.EXTENSION,
       r.DELIMITER,
       (r.STORED_NAME || r.DELIMITER || r.EXTENSION)                                as STORED_FULL_NAME,
       (r.UPLOADED_NAME || r.DELIMITER || r.EXTENSION)                              as UPLOADED_FULL_NAME,
       (r.FILE_RESOURCE_PATH || '/' || r.STORED_NAME || r.DELIMITER || r.EXTENSION) as ABSOLUTE_FILE
FROM FILE_RESOURCE r;
