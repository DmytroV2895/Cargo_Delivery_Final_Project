-- ===========================================================================================================================
-- TEST USERS/MANAGERS
-- ===========================================================================================================================
INSERT INTO users (name, surname, email, phone, password, role, account)
values('Dmytro', 'Aliseev', 'dmytro@gmail.com', '+380665889866', '576e686a646d4a75625377754c7a493d', 'USER', 0);

INSERT INTO users (name, surname, email, phone, password, role, account)
values('Oleksandr', 'Vavilov ', 'oleksandr@gmail.com', '+380984587896', '576e686a646d4a75625377754c7a453d', 'USER', 0);

INSERT INTO users (name, surname, email, phone, password, role, account)
values('Natalia', 'Kravchenko ', 'natalia@gmail.com', '+380978523216', '576e686a646d4a75625377754c7a4d3d', 'MANAGER', 0);


-- ===========================================================================================================================
-- TEST ORDERS
-- ===========================================================================================================================


INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('BY_TRUCK', 100, 'Oleg', 'Krasnov', '+380987412598');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Computer', 'CARGO', 'New model of computer', 25000, 35, 50, 60, 35, 25, 36);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('SUMY', 'Lisna', 55, 12);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('ODESSA', 'Vodna', 5, 52);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values('2023-02-18', 150, 25300, '2023-01-23 15:01:40', 1, 'ON_THE_WAY', 1, 1, 1, 1, 1);

-- ==========================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('BY_TRUCK', 500, 'Oleg', 'Kravec', '+380987412577');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Book', 'PARCEL', 'Harry Potter', 1500, 12, 45, 14, 11, 13, 23);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('SUMY', 'Zasumska', 5, 16);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('LVIV', 'Chmelnutskogo', 45, 62);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values(null, 300, 1800, '2013-02-05 15:01:40', 0, 'IN_PROCESSING', 2, 1, 2, 2, 2);

-- ======================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('BY_TRUCK', 500, 'Olga', 'Voronina', '+380987812577');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Table', 'CARGO', 'School tabl', 3500, 150, 350, 123, 12, 13, 45);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('CHARKIV', 'Lisnova', 34, 26);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('ODESSA', 'Prymarska', 8, 9);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values('2023-02-20', 1500, 5000, '2023-02-10 13:01:40', 1, 'RECEIVED', 3, 1, 3, 3, 3);

-- =======================================================================================================


INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('BY_TRUCK', 120, 'Victor', 'Shevchyk', '+380770374177');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Monitor', 'PARCEL', 'DELL monitor', 26000, 29, 25, 24, 12, 10, 3);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('CHARKIV', 'Charkivska', 2, 10);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('ODESSA', 'Voskresenska', 15, 9);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values('2023-02-16', 100, 26100, '2023-01-29 18:01:40', 0, 'ON_THE_WAY', 4, 1, 4, 4, 4);

-- ========================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('COURIER', 1000, 'Victoria', 'Shevchenko', '+380777874177');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Books', 'PARCEL', 'Computer Science', 5000, 20, 12, 15, 2, 0.5, 1);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('SUMY', 'Charkivska', 12, 9);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('LVIV', 'Voskresenska', 5, 19);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values(null, 100, 5100, '2023-01-30 08:01:40', 0, 'NOT_PROCESSED', 5, 1, 5, 5, 5);

-- ========================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('BY_TRUCK', 150, 'Maria', 'Feshenkp', '+380777871237');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Laptop', 'PARCEL', 'Laptop', 60000, 12, 15, 15, 19, 2, 3);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('SUMY', 'Charkivska', 1, 79);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('LVIV', 'Voskresenska', 12, 15);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values(null, 100, 100, '2023-07-14 08:01:40', 0, 'NOT_PROCESSED', 6, 2, 6, 6, 6);

-- ===================================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('BY_TRUCK', 1000, 'Oleg', 'Karapec', '+380770071237');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Headphones', 'PARCEL', 'HyperX Headphones', 5000, 12, 1, 2, 2, 2, 3);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('CHARKIV', 'Charkivska', 05, 7);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('ODESSA', 'Yvileina', 1, 15);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values(null, 300, 5300, '2023-08-14 18:01:40', 0, 'IN_PROCESSING', 7, 2, 7, 7, 7);

-- ===================================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('BY_TRUCK', 500, 'Dmytro', 'Sokolenko', '+380989971237');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Container', 'CARGO', 'Container', 150000, 1000, 500, 600, 700, 142, 42);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('LVIV', 'Zakarpatska', 85, 8);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('ODESSA', 'Prumorska', 45, 25);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values('2023-02-17', 3000, 153000, '2023-02-10 22:01:39', 1, 'ON_THE_WAY', 8, 2, 8, 8, 8);

-- ===================================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('COURIER', 500, 'Kostyantyn', 'Avremenko', '+380989971255');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Documents', 'DOCUMENT', 'Documents', 100, 0.5, 0.5, 1, 2, 1, 1);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('CHARKIV', 'Dobrovilna', 40, 18);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('LVIV', 'Chmelnutskogo', 85, 27);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values('2023-02-16', 95, 195, '2023-02-01 15:01:39', 1, 'RECEIVED', 9, 2, 9, 9, 9);

-- =======================================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('BY_TRUCK', 800, 'Evgen', 'Vasuliev', '+380989970155');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Fishing tools', 'PARCEL', 'Fishing tools', 100, 15, 0.5, 1, 2, 13, 25);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('LVIV', 'Stysa', 30, 8);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('CHARKIV', 'Krych', 5, 7);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values('2023-02-16', 120, 220, '2023-02-12 20:01:39', 0, 'ON_THE_WAY', 10, 2, 10, 10, 10);

-- =======================================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone)
values('BY_TRUCK', 500, 'Oleg', 'Kravec', '+380989957155');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight)
values ('Sofa', 'CARGO', 'Sofa', 5000, 50, 200, 50, 100, 130, 25);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number)
values ('LVIV', 'Stysa', 21, 3);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number)
values ('CHARKIV', 'Krych', 1, 5);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id)
values('2023-04-16', 2000, 5700, '2023-03-12 20:01:39', 0, 'ON_THE_WAY', 11, 2, 11, 11, 11);