CREATE DATABASE db_example;
CREATE USER 'local-test-user'@'localhost' IDENTIFIED WITH mysql_native_password BY 'local-test-secret';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, INDEX, DROP, ALTER, CREATE TEMPORARY TABLES, LOCK TABLES ON db_example.* TO 'local-test-user'@'localhost';

CREATE USER 'real-user'@'localhost' IDENTIFIED WITH mysql_native_password BY 'to_be_encrypted';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, INDEX, DROP, ALTER, CREATE TEMPORARY TABLES, LOCK TABLES ON db_example.* TO 'real-user'@'localhost';
