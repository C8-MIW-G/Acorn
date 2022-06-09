CREATE SCHEMA IF NOT EXISTS acorn;
USE acorn;

CREATE USER IF NOT EXISTS 'userAcorn'@'localhost' IDENTIFIED BY 'passwordAcorn';
GRANT ALL PRIVILEGES ON acorn.* TO 'userAcorn'@'localhost';

SELECT * FROM acorn.pantry;
SELECT * FROM acorn.pantry_product;

INSERT INTO acorn.pantry (`id`, `name`) VALUES
	(-1, "Pantry -1"),
	(-2, "Pantry -2"),
	(-3, "Pantry -3");
    
CREATE TABLE acorn.pantryproducts (
	pantryId Long,
	productName varchar(300));

INSERT INTO acorn.pantry_product (`id`, `pantry_id`, `product_name`) VALUES
	(-1, -1, "Butter"),
	(-2, -1, "Butter"),
	(-3, -2, "Cheese"),
	(-4, -2, "Cheese"),
	(-5, -3, "Couscous"),
	(-6, -3, "Couscous");
