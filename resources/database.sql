CREATE SCHEMA IF NOT EXISTS acorn;
USE acorn;

CREATE USER IF NOT EXISTS 'userAcorn'@'localhost' IDENTIFIED BY 'passwordAcorn';
GRANT ALL PRIVILEGES ON acorn.* TO 'userAcorn'@'localhost';

SELECT * FROM acorn.pantry;
SELECT * FROM acorn.pantry_product;

DELETE FROM acorn.pantry;
DELETE FROM acorn.pantry_product;

INSERT INTO acorn.pantry (`id`, `name`) VALUES
	(-1, "Sylvia's Pantry"),
	(-2, "FC Oost-Groningen"),
	(-3, "Rugby Club Groningen");
    
CREATE TABLE acorn.pantryproducts (
	pantryId Long,
	productName varchar(300));

INSERT INTO acorn.pantry_product (`id`, `pantry_id`, `product_name`, `expiration_date`) VALUES
	(-1, -1, "Butter", "2022-07-18"),
	(-2, -1, "Cornflakes", "2022-07-12"),
	(-3, -1, "Cheese", "2022-08-30"),
	(-4, -1, "Feta", "2022-07-14"),
	(-5, -2, "Couscous", "2022-07-14"),
	(-6, -2, "Olives", "2022-09-08"),
	(-7, -2, "Wine", "2022-10-01"),
	(-8, -2, "Oranges", "2022-12-09"),
	(-9, -3, "Tomatoes", "2022-03-10"),
	(-10, -3, "Pesto", "2022-08-14"),
	(-11, -3, "Tortellini", "2022-07-19"),
	(-12, -3, "Onions", "2022-07-20");
