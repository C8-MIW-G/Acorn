CREATE SCHEMA IF NOT EXISTS acorn;
USE acorn;

CREATE USER IF NOT EXISTS 'userAcorn'@'localhost' IDENTIFIED BY 'passwordAcorn';
GRANT ALL PRIVILEGES ON acorn.* TO 'userAcorn'@'localhost';

SELECT * FROM acorn.pantry;
SELECT * FROM acorn.product_definition;
SELECT * FROM acorn.pantry_product;
SELECT * FROM acorn.`user`;
SELECT * FROM acorn.pantry_user;


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

INSERT INTO acorn.pantry_product (`id`, `expiration_date`, `pantry_id`, `product_definition_id`) VALUES
     (-10, "2022-07-18", -3, -31),
     (-2, "2022-07-18", -3, -3),
     (-3, "2022-07-18", -4, -2),
     (-4, "2022-07-18", -2, -3),
     (-5, "2022-07-18", -3, -5),
     (-6, "2022-07-18", -4, -7),
     (-7, "2022-07-18", -4, -4),
     (-8, "2022-07-18", -4, -2),
     (-9, "2022-07-18", -2, -8);
