use mysql;

DELETE FROM user WHERE user='PMARLEN_PROD';
GRANT SELECT,INSERT,UPDATE,DELETE ON PMARLEN_DB_PROD.* TO 'PMARLEN_PROD'@'localhost' IDENTIFIED BY 'PMARLEN_PROD_PASSWORD';
