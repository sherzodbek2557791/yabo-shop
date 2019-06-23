
-- Fake init categories
insert into category(id, code, title, category_status, color, header_img) VALUES (1, 'a16e', 'Телевизоры', 'ACTIVE', '#7b184d', 'bk-sale.png');
insert into category(id, code, title, category_status, color, header_img) VALUES (2, 'a16b', 'Холодильники', 'ACTIVE', '#47227b', 'banner-ppl.png');

-- Fake products

insert into product(id, code, title, description, product_status, price, currency, image) VALUES (1, 'a1', 'Khaki Suede Polish Work Boots', 'shoe', 'ACTIVE', 149099.99, 'SUM', 'shoe1.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (2, 'a2', 'Camo Fang Backpack Jungle', 'jacket', 'ACTIVE', 39099.99, 'SUM', 'jacket1.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (3, 'a3', 'Parka and Quilted Liner Jacket', 'jacket', 'ACTIVE', 49099.99, 'SUM', 'jacket2.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (4, 'a4', 'Cotton Black Cap', 'hats', 'ACTIVE', 12099.99, 'SUM', 'hat1.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (5, 'a5', 'Knit Sweater with Zips', 'sweater', 'ACTIVE', 29099.99, 'SUM', 'sweater1.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (6, 'a6', 'Long Linen-blend Shirt', 'shirt', 'ACTIVE', 18099.99, 'SUM', 'shirt1.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (7, 'a7', 'Knit Orange Sweater', 'sweater', 'ACTIVE', 28099.99, 'SUM', 'sweater2.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (8, 'a8', 'Cotton Band-collar Blouse', 'shirt', 'ACTIVE', 49099.99, 'SUM', 'shirt2.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (9, 'a9', 'Camo Fang Backpack Jungle', 'jacket', 'ACTIVE', 59099.99, 'SUM', 'jacket3.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (10, 'a10', 'Golden Pilot Jacket', 'jacket', 'ACTIVE', 129099.99, 'SUM', 'jacket4.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (11, 'a11', 'Spotted Patterned Sweater', 'jacket', 'ACTIVE', 80099.99, 'SUM', 'sweater4.png');
insert into product(id, code, title, description, product_status, price, currency, image) VALUES (12, 'a12', 'Double Knit Sweater', 'jacket', 'ACTIVE', 59099.99, 'SUM', 'sweater5.png');


-- insert foreign keys

insert into category_product(category_id, product_id) values (1, 1);
insert into category_product(category_id, product_id) values (1, 2);
insert into category_product(category_id, product_id) values (1, 5);
insert into category_product(category_id, product_id) values (1, 9);
insert into category_product(category_id, product_id) values (1, 10);
insert into category_product(category_id, product_id) values (1, 11);
insert into category_product(category_id, product_id) values (2, 3);
insert into category_product(category_id, product_id) values (2, 4);
insert into category_product(category_id, product_id) values (2, 6);
insert into category_product(category_id, product_id) values (2, 7);
insert into category_product(category_id, product_id) values (2, 8);
insert into category_product(category_id, product_id) values (2, 12);
