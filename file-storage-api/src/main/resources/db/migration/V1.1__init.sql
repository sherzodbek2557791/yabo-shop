CREATE SEQUENCE FILE_RESOURCE_SEQ INCREMENT BY 1 MINVALUE 1 NOCYCLE CACHE 20 NOORDER;
CREATE TABLE FILE_RESOURCE
(
  ID                 VARCHAR2(255) DEFAULT ('FILE_RESOURCE_SEQ-' || FILE_RESOURCE_SEQ.NEXTVAL) NOT NULL,
  ORDERED            NUMBER        DEFAULT FILE_RESOURCE_SEQ.CURRVAL                           NOT NULL,
  CREATED_DATE       DATE          DEFAULT SYSDATE                                             NOT NULL,
  STORED_NAME        VARCHAR2(512)                                                             NOT NULL,
  UPLOADED_NAME      VARCHAR2(512)                                                             NOT NULL,
  CLIENT_ID          VARCHAR2(100)                                                             NOT NULL,
  FILE_RESOURCE_TYPE VARCHAR2(255)                                                             NOT NULL,
  FILE_RESOURCE_PATH VARCHAR2(2048)                                                            NOT NULL,
  FILE_RESOURCE_SIZE NUMBER                                                                    NOT NULL,
  EXTENSION          VARCHAR2(10)                                                              NOT NULL,
  DELIMITER          CHAR(1)       DEFAULT '.'                                                 NOT NULL,
  STORED_FULL_NAME AS ("STORED_NAME" || "DELIMITER" || "EXTENSION"),
  UPLOADED_FULL_NAME AS ("UPLOADED_NAME" || "DELIMITER" || "EXTENSION"),
  ABSOLUTE_FILE AS ("FILE_RESOURCE_PATH" || '/' || "UPLOADED_NAME" || "DELIMITER" || "EXTENSION"),
  CONSTRAINT FILE_RESOURCE_PK PRIMARY KEY (ID)
);