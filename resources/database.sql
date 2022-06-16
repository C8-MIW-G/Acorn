CCREATE SCHEMA IF NOT EXISTS acorn;
USE acorn;

CREATE USER IF NOT EXISTS 'userAcorn'@'localhost' IDENTIFIED BY 'passwordAcorn';
GRANT ALL PRIVILEGES ON acorn.* TO 'userAcorn'@'localhost';

SELECT * FROM acorn.pantry;
SELECT * FROM acorn.pantry_product;
SELECT * FROM acorn.product_definition;

-- DELETE FROM acorn.pantry;
-- DELETE FROM acorn.pantry_product;
-- DELETE FROM acorn.product_definition;

INSERT INTO acorn.pantry (`id`, `name`) VALUES
    (-1,"Sylvia's Pantry"),
    (-2,"FC Oost-Groningen"),
    (-3,"Rugby Club Groningen");

INSERT INTO acorn.product_definition (`id`, `name`) VALUES
    (-1, "Cornflakes"),
    (-2, "Couscous"),
    (-3, "Wine"),
    (-4, "Onions"),
    (-5, "Tortellini"),
    (-6, "Pesto"),
    (-7, "Feta"),
    (-8, "Cheese"),
    (-9, "Cornflakes"),
    (-10, "Butter");

INSERT INTO acorn.pantry_product (`id`, `expiration_date`, `pantry_id`, `product_definition_id`) VALUES
     (-1, "2022-07-18", -3, -1),
     (-2, "2022-07-18", -3, -3),
     (-3, "2022-07-18", -1, -2),
     (-4, "2022-07-18", -2, -3),
     (-5, "2022-07-18", -3, -5),
     (-6, "2022-07-18", -1, -7),
     (-7, "2022-07-18", -1, -4),
     (-8, "2022-07-18", -1, -2),
     (-9, "2022-07-18", -2, -8);