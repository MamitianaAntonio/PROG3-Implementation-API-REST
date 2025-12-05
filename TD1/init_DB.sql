-- create user with password
CREATE USER product_manager_user WITH PASSWORD '123456';
-- create database
CREATE DATABASE product_management_db WITH OWNER = product_manager_user ENCODING = 'UTF8';
-- ADD ALL PRIVILEGES for product_manager_user for product_management_db
GRANT ALL PRIVILEGES ON DATABASE product_management_db TO product_manager_user;
GRANT CREATE ON DATABASE product_management_db TO product_manager_user;
-- connection to db
\c product_management_db;
