## Use to run mysql db docker image, optional if you are not using a local mysql db
# docker run --name yuan-mysql -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
# create databases
CREATE DATABASE yuan_dev;
CREATE DATABASE yuan_prod;

# create database service accounts
# identified by is setting the account password
CREATE USER 'yuan_dev_user'@'localhost' IDENTIFIED BY '1';
CREATE USER 'yuan_prod_user'@'localhost' IDENTIFIED BY '1';
CREATE USER 'yuan_dev_user'@'%' IDENTIFIED BY '1';
CREATE USER 'yuan_prod_user'@'%' IDENTIFIED BY '1';

# databases grants
GRANT SELECT ON yuan_dev.* TO 'yuan_dev_user'@'localhost';
GRANT INSERT ON yuan_dev.* TO 'yuan_dev_user'@'localhost';
GRANT DELETE ON yuan_dev.* TO 'yuan_dev_user'@'localhost';
GRANT UPDATE ON yuan_dev.* TO 'yuan_dev_user'@'localhost';
GRANT SELECT ON yuan_prod.* TO 'yuan_prod_user'@'localhost';
GRANT INSERT ON yuan_prod.* TO 'yuan_prod_user'@'localhost';
GRANT DELETE ON yuan_prod.* TO 'yuan_prod_user'@'localhost';
GRANT UPDATE ON yuan_prod.* TO 'yuan_prod_user'@'localhost';
GRANT SELECT ON yuan_dev.* TO 'yuan_dev_user'@'%';
GRANT INSERT ON yuan_dev.* TO 'yuan_dev_user'@'%';
GRANT DELETE ON yuan_dev.* TO 'yuan_dev_user'@'%';
GRANT UPDATE ON yuan_dev.* TO 'yuan_dev_user'@'%';
GRANT SELECT ON yuan_prod.* TO 'yuan_prod_user'@'%';
GRANT INSERT ON yuan_prod.* TO 'yuan_prod_user'@'%';
GRANT DELETE ON yuan_prod.* TO 'yuan_prod_user'@'%';
GRANT UPDATE ON yuan_prod.* TO 'yuan_prod_user'@'%';
