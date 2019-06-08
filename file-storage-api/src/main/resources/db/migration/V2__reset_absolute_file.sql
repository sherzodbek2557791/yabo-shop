-- Delete/Add column
alter table FILE_RESOURCE drop column ABSOLUTE_FILE;
alter table FILE_RESOURCE add ABSOLUTE_FILE as (("FILE_RESOURCE_PATH" || '/' || "STORED_NAME" || "DELIMITER" || "EXTENSION"));