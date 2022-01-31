INSERT INTO `stock_wherehouse` (`id`, `quantity`) VALUES ('1', '110');
INSERT INTO `stock_wherehouse` (`id`, `quantity`) VALUES ('2', '200');
INSERT INTO `stock_wherehouse` (`id`, `quantity`) VALUES ('3', '220');
INSERT INTO `stock_wherehouse` (`id`, `quantity`) VALUES ('4', '210');

INSERT INTO `parts` (`id`, `part_code`, `id_stock_wherehouse`) VALUES ('1', '01A', '1');
INSERT INTO `parts` (`id`, `part_code`, `id_stock_wherehouse`) VALUES ('2', '01B', '2');
INSERT INTO `parts` (`id`, `part_code`, `id_stock_wherehouse`) VALUES ('3', '02A', '3');
INSERT INTO `parts` (`id`, `part_code`, `id_stock_wherehouse`) VALUES ('4', '03A', '4');

INSERT INTO `makers` (`id`, `name`) VALUES ('1', 'FIAT');

INSERT INTO `part_registers` (`id`, `date_modification`, `description`, `long_dimension`, `id_maker`, `net_weight`, `tall_dimension`, `width_dimension`, `id_part` ) VALUES ('1', '2021-02-01', 'Puerta derecha de FIAT 600', '2', '1', '1', '3', '4', '1');
INSERT INTO `part_registers` (`id`, `date_modification`, `description`, `long_dimension`, `id_maker`, `net_weight`, `tall_dimension`, `width_dimension`, `id_part`) VALUES ('2', '2021-03-03', 'Puerta derecha de FIAT 600', '2', '1', '1', '3', '4', '1');
INSERT INTO `part_registers` (`id`, `date_modification`, `description`, `long_dimension`, `id_maker`, `net_weight`, `tall_dimension`, `width_dimension`, `id_part`) VALUES ('3', '2021-01-02', 'Volante de lujo', '1', '1', '3', '2', '2', '2');
INSERT INTO `part_registers` (`id`, `date_modification`, `description`, `long_dimension`, `id_maker`, `net_weight`, `tall_dimension`, `width_dimension`, `id_part`) VALUES ('4', '2021-03-05', 'Volante de lujo', '2', '1', '5', '2', '2', '2');
INSERT INTO `part_registers` (`id`, `date_modification`, `description`, `long_dimension`, `id_maker`, `net_weight`, `tall_dimension`, `width_dimension`, `id_part`) VALUES ('6', '2021-04-05', 'Faros de lujo', '2', '1', '2', '2', '2', '3');
INSERT INTO `part_registers` (`id`, `date_modification`, `description`, `long_dimension`, `id_maker`, `net_weight`, `tall_dimension`, `width_dimension`, `id_part`) VALUES ('5', '2021-02-02', 'Faros de lujo', '2', '1', '3', '2', '2', '3');
INSERT INTO `part_registers` (`id`, `date_modification`, `description`, `long_dimension`, `id_maker`, `net_weight`, `tall_dimension`, `width_dimension`, `id_part`) VALUES ('7', '2021-01-03', 'Retrovisor de Lujo', '5', '1', '10', '12', '2', '4');
INSERT INTO `part_registers` (`id`, `date_modification`, `description`, `long_dimension`, `id_maker`, `net_weight`, `tall_dimension`, `width_dimension`, `id_part`) VALUES ('8', '2021-03-04', 'Retrovisor de Lujo', '5', '1', '20', '12', '2', '4');


INSERT INTO `discounts` (`id`, `discount_type`,`description`,`mount`) VALUES ('1','A20','Descuento por aniversario', '50');
INSERT INTO `discounts` (`id`, `discount_type`,`description`,`mount`) VALUES ('2','B20','Descuento por pago en efectivo', '10');
INSERT INTO `discounts` (`id`, `discount_type`,`description`,`mount`) VALUES ('3','C20','Descuento por imperfeccion', '40');
INSERT INTO `discounts` (`id`, `discount_type`, `description`,`mount`) VALUES ('4','A30','Descuento por black friday', '25');
INSERT INTO `discounts` (`id`,`discount_type`, `description`,`mount`) VALUES ('5','C30','Descuento por remate', '80');


INSERT INTO `price_registers` (`id`, `date_modification`, `normal_price`, `sale_price`, `urgent_price`,`id_discount`,`id_part`) VALUES ('1','2021-03-20','500','450','550','2','1');
INSERT INTO `price_registers` (`id`, `date_modification`, `normal_price`, `sale_price`, `urgent_price`,`id_discount`,`id_part`) VALUES ('2','2021-04-20','500','300','550','3','1');
INSERT INTO `price_registers` (`id`, `date_modification`, `normal_price`, `sale_price`, `urgent_price`,`id_discount`,`id_part`) VALUES ('3','2021-01-20','1000','750','1100','4','2');
INSERT INTO `price_registers` (`id`, `date_modification`, `normal_price`, `sale_price`, `urgent_price`,`id_discount`,`id_part`) VALUES ('4','2021-02-20','1000','750','1100','2','2');
INSERT INTO `price_registers` (`id`, `date_modification`, `normal_price`, `sale_price`, `urgent_price`,`id_discount`,`id_part`) VALUES ('5','2021-02-20','800','720','850','2','3');
INSERT INTO `price_registers` (`id`, `date_modification`, `normal_price`, `sale_price`, `urgent_price`,`id_discount`,`id_part`) VALUES ('6','2021-03-20','800','400','850','1','3');
INSERT INTO `price_registers` (`id`, `date_modification`, `normal_price`, `sale_price`, `urgent_price`,`id_discount`,`id_part`) VALUES ('7','2021-01-20','2000','1800','2050','2','4');
INSERT INTO `price_registers` (`id`, `date_modification`, `normal_price`, `sale_price`, `urgent_price`,`id_discount`,`id_part`) VALUES ('8','2021-03-20','2000','400','2050','5','4');



INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('1', 'Argentina', 'Casa central de Argentina');
INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('2', 'Chile', 'Casa central de Chile');
INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('3', 'Uruguay', 'Casa central de Uruguay');
INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('4', 'Colombia', 'Casa central de Colombia');


INSERT INTO `accounts` (`id`, `password`, `rol`, `username`, `id_countryhouse`) VALUES ('1', 'contra123', '1', 'user_one', '1');
INSERT INTO `accounts` (`id`, `password`, `rol`, `username`, `id_countryhouse`) VALUES ('2', 'contra123', '1', 'user_two', '2');
INSERT INTO `accounts` (`id`, `password`, `rol`, `username`, `id_countryhouse`) VALUES ('3', 'contra123', '1', 'user_three', '3');
INSERT INTO `accounts` (`id`, `password`, `rol`, `username`, `id_countryhouse`) VALUES ('4', 'contra123', '1', 'user_four', '4');

INSERT INTO `car_dealership` (`id`,`dealer_number`, `name`, `id_countryhouse`) VALUES ('1','0001', 'Concesionario Santa Fe', '1');
INSERT INTO `car_dealership` (`id`,`dealer_number`, `name`, `id_countryhouse`) VALUES ('2','0002', 'Concesionario Bs As', '1');
INSERT INTO `car_dealership` (`id`,`dealer_number`, `name`, `id_countryhouse`) VALUES ('3','0003', 'Concesionario Medellin', '4');
INSERT INTO `car_dealership` (`id`,`dealer_number`, `name`, `id_countryhouse`) VALUES ('4','0004', 'Concesionario Barranquilla', '4');

INSERT INTO `delivery_status` (`id`, `code_status`, `description`) VALUES ('1', 'P', 'Pendiente de entrega');
INSERT INTO `delivery_status` (`id`, `code_status`, `description`) VALUES ('2', 'D', 'Demorado');
INSERT INTO `delivery_status` (`id`, `code_status`, `description`) VALUES ('3', 'F', 'Finalizado');
INSERT INTO `delivery_status` (`id`, `code_status`, `description`) VALUES ('4', 'C', 'Cancelado');

INSERT INTO `orders` (`id`, `days_delay`, `delivery_date`, `order_date`, `order_number`, `id_cardealership`, `id_deliverystatus`) VALUES ('1', '0', '2021-04-22', '2021-04-21', '123456', '0001', '1');
INSERT INTO `orders` (`id`, `days_delay`, `delivery_date`, `order_date`, `order_number`, `id_cardealership`, `id_deliverystatus`) VALUES ('2', '0', '2021-04-23', '2021-04-23', '100000', '0001', '1');
INSERT INTO `orders` (`id`, `days_delay`, `delivery_date`, `order_date`, `order_number`, `id_cardealership`, `id_deliverystatus`) VALUES ('3', '4', '2021-04-23', '2021-04-19', '445687', '0001', '2');
INSERT INTO `orders` (`id`, `days_delay`, `delivery_date`, `order_date`, `order_number`, `id_cardealership`, `id_deliverystatus`) VALUES ('4', '0', '2021-04-22', '2021-04-21', '12415', '0004', '1');

INSERT INTO `orders_details` (`id`, `account_type`, `quantity`, `reason`, `id_order`, `id_part`) VALUES ('1', 'R', '10', 'Motivo desconocido.', '1', '1');
INSERT INTO `orders_details` (`id`, `account_type`, `quantity`, `reason`, `id_order`, `id_part`) VALUES ('2', 'R', '5', 'Se desconoce.', '2', '2');
INSERT INTO `orders_details` (`id`, `account_type`, `quantity`, `reason`, `id_order`, `id_part`) VALUES ('3', 'R', '5', 'Se desconoce.', '2', '2');
INSERT INTO `orders_details` (`id`, `account_type`, `quantity`, `reason`, `id_order`, `id_part`) VALUES ('4', 'G', '1', 'Falla de fabrica.', '3', '1');
INSERT INTO `orders_details` (`id`, `account_type`, `quantity`, `reason`, `id_order`, `id_part`) VALUES ('5', 'G', '1', 'Falla de fabrica.', '3', '3');

INSERT INTO `stock_countryhouse` (`id`, `quantity`,`id_countryhouse`, `id_part` ) VALUES ('1', '110', '1', '1');
