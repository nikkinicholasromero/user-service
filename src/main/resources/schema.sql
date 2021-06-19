DROP TABLE IF EXISTS USER_ACCOUNTS;

CREATE TABLE USER_ACCOUNTS (
  ID VARCHAR(255) NOT NULL,
  TYPE VARCHAR(255) NOT NULL,
  EMAIL_ADDRESS VARCHAR(255) DEFAULT NULL,
  PASSWORD VARCHAR(255) DEFAULT NULL,
  SALT VARCHAR(255) DEFAULT NULL,
  STATUS VARCHAR(255) DEFAULT NULL,
  FIRST_NAME VARCHAR(255) DEFAULT NULL,
  LAST_NAME VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);
