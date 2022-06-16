CREATE SCHEMA IF NOT EXISTS acorn;
USE acorn;

CREATE USER IF NOT EXISTS 'userAcorn'@'localhost' IDENTIFIED BY 'passwordAcorn';
GRANT ALL PRIVILEGES ON acorn.* TO 'userAcorn'@'localhost';

SELECT * FROM acorn.pantry;
SELECT * FROM acorn.pantry_product;
SELECT * FROM acorn.product_definition;

DELETE FROM acorn.pantry;
DELETE FROM acorn.pantry_product;
DELETE FROM acorn.product_definition;

INSERT INTO acorn.pantry (`id`, `name`) VALUES
	(-2, "Sylvia's Pantry"),
	(-3, "FC Groningen"),
	(-4, "Rugby Club Groningen");

INSERT INTO acorn.product_definition (`id`, `name`) VALUES
	(-2, "Cornflakes"),
	(-3, "Cheese"),
	(-4, "Feta"),
	(-5, "Couscous"),
	(-6, "Olives"),
	(-7, "Wine"),
	(-8, "Oranges"),
	(-9, "Tomatoes"),
	(-10, "Pesto"),
	(-11, "Tortellini"),
	(-12, "Jumbo Torpedo Garnalen Snacks 225g"),
	(-13, "Jumbo Lemon Cheesecake Roomijs 425g"),
	(-14, "Jumbo Vochtige Lotion Doekjes Normale Huid 3 x 80 Stuks"),
	(-15, "Chernigivske Bier Pils Blik 6 x 330ML"),
	(-16, "Jumbo Energy Gel Citroen Smaak 45g"),
	(-17, "Jumbo Knäckebröd Volkoren 5 x 5 Stuks 375g"),
	(-18, "Jumbo Ierse Black Angus Biefstuk ca. 90g"),
	(-19, "Jumbo Ierse Rosbief ca. 90g"),
	(-20, "Jumbo Wilde Zalm Filet 675g"),
	(-21, "Mora Vegetarische Kipfingers 240g"),
	(-22, "Jumbo Wok Garnalen 300g"),
	(-23, "Jumbo Mayonaise 750ml"),
	(-24, "Zaanse Mayonaise 750ml"),
	(-25, "Remia Mayonaise 500ml"),
	(-26, "Zaanse Yoghurt Mayo 170ml"),
	(-27, "Oliehoorn Vegan Mayo 465ml"),
	(-28, "Heinz Tomaten Ketchup 400ml"),
	(-29, "Heinz Tomaten Ketchup 50% Minder Suikers en Zout 400ml"),
	(-30, "Jumbo Biologisch Tomaten Ketchup 330g"),
	(-31, "Butter");

    
INSERT INTO acorn.pantry_product (`id`, `pantry_id`, `product_name`, `expiration_date`) VALUES
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
	(-12, -3, "Onions", "2022-07-20"),
	(-13, -1, "Butter", "2022-07-18");

    