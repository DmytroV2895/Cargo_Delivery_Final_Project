-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema cargo_delivery_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cargo_delivery_db` ;

-- -----------------------------------------------------
-- Schema cargo_delivery_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cargo_delivery_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `cargo_delivery_db` ;

-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`delivery`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`delivery` (
  `id_delivery` BIGINT NOT NULL AUTO_INCREMENT,
  `delivery_type` ENUM('BY_TRUCK', 'COURIER') NOT NULL,
  `distance` INT UNSIGNED NOT NULL,
  `recipient_name` VARCHAR(45) NOT NULL,
  `recipient_surname` VARCHAR(45) NOT NULL,
  `recipient_phone` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id_delivery`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`users` (
  `id_user` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(20) NOT NULL,
  `phone` VARCHAR(15) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `role` ENUM('USER', 'GUEST', 'MANAGER') NOT NULL,
  `account` DECIMAL(10,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_user`),
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `password_UNIQUE` (`password` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`orders` (
  `id_order` BIGINT NOT NULL AUTO_INCREMENT,
  `order_name` VARCHAR(100) NOT NULL,
  `order_type` ENUM('CARGO', 'PARCEL', 'DOCUMENT') NOT NULL,
  `order_description` VARCHAR(1024) NOT NULL,
  `price` DECIMAL(10,0) UNSIGNED NOT NULL,
  `weight` DOUBLE NOT NULL,
  `length` DOUBLE NOT NULL,
  `height` DOUBLE NOT NULL,
  `width` DOUBLE NOT NULL,
  `volume` DOUBLE NOT NULL,
  `volume_weight` DOUBLE NOT NULL,
  `user_id` BIGINT NOT NULL,
  `delivery_id` BIGINT NOT NULL,
  PRIMARY KEY (`id_order`),
  INDEX `fk_orders_users1_idx` (`user_id` ASC) INVISIBLE,
  INDEX `fk_orders_delivery1_idx` (`delivery_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cargo_delivery_db`.`users` (`id_user`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_orders_delivery1`
    FOREIGN KEY (`delivery_id`)
    REFERENCES `cargo_delivery_db`.`delivery` (`id_delivery`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

 
-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`second_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`second_address` (
  `id_second_address` BIGINT NOT NULL AUTO_INCREMENT,
  `second_city` VARCHAR(45) NOT NULL,
  `second_street_name` VARCHAR(45) NOT NULL,
  `second_street_number` VARCHAR(45) NOT NULL,
  `second_house_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_second_address`));


-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`first_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`first_address` (
  `id_first_address` BIGINT NOT NULL AUTO_INCREMENT,
  `first_city` VARCHAR(45) NOT NULL,
  `first_street_name` VARCHAR(45) NOT NULL,
  `first_street_number` VARCHAR(45) NOT NULL,
  `first_house_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_first_address`));


-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`invoice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`invoice` (
  `id_invoice` BIGINT NOT NULL AUTO_INCREMENT,
  `delivery_date` DATE NULL DEFAULT NULL,
  `delivery_price` DECIMAL UNSIGNED NOT NULL,
  `total_price` DECIMAL UNSIGNED NOT NULL,
  `creation_date_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `isPaid` TINYINT(1) NOT NULL DEFAULT 0,
  `order_status` ENUM('NOT_PROCESSED', 'IN_PROCESSING', 'ON_THE_WAY', 'RECEIVED') NOT NULL DEFAULT 'NOT_PROCESSED',
  `delivery_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `order_id` BIGINT NOT NULL,
  `first_address_id` BIGINT NOT NULL,
  `second_address_id` BIGINT NOT NULL,
  PRIMARY KEY (`id_invoice`),
  INDEX `fk_invoice_delivery_idx` (`delivery_id` ASC) VISIBLE,
  INDEX `fk_invoice_users1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_invoice_orders1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_invoice_second_address1_idx` (`second_address_id` ASC) VISIBLE,
  INDEX `fk_invoice_first_address1_idx` (`first_address_id` ASC) VISIBLE,
  CONSTRAINT `fk_invoice_delivery`
    FOREIGN KEY (`delivery_id`)
    REFERENCES `cargo_delivery_db`.`delivery` (`id_delivery`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `cargo_delivery_db`.`orders` (`id_order`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `cargo_delivery_db`.`users` (`id_user`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_second_address1`
    FOREIGN KEY (`second_address_id`)
    REFERENCES `cargo_delivery_db`.`second_address` (`id_second_address`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_first_address1`
    FOREIGN KEY (`first_address_id`)
    REFERENCES `cargo_delivery_db`.`first_address` (`id_first_address`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



INSERT INTO users (name, surname, email, phone, password, role, account) 
 values('Dmytro', 'Aliseev', 'dmytro@gmail.com', '+380665889866', '576e686a646d4a75625377754c7a493d', 'USER', 0);
 
 INSERT INTO users (name, surname, email, phone, password, role, account) 
 values('Oleksandr', 'Vavilov ', 'oleksandr@gmail.com', '+380984587896', '576e686a646d4a75625377754c7a453d', 'USER', 0);
 
 INSERT INTO users (name, surname, email, phone, password, role, account) 
 values('Natalia', 'Kravchenko ', 'natalia@gmail.com', '+380978523216', '576e686a646d4a75625377754c7a4d3d', 'MANAGER', 0);

-- ===========================================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
values('BY_TRUCK', 100, 'Oleg', 'Krasnov', '+380987412598');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
values ('Computer', 'CARGO', 'New model of computer', 25000, 35, 50, 60, 35, 25, 36, 1, 1);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
values ('SUMY', 'Lisna', 55, 12);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
values ('ODESSA', 'Vodna', 5, 52);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
values('2023-02-18', 150, 25300, '2023-01-23 15:01:40', 1, 'ON_THE_WAY', 1, 1, 1, 1, 1);

-- ==========================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
values('BY_TRUCK', 500, 'Oleg', 'Kravec', '+380987412577');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
values ('Book', 'PARCEL', 'Harry Potter', 1500, 12, 45, 14, 11, 13, 23, 1, 2);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
values ('SUMY', 'Zasumska', 5, 16);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
values ('LVIV', 'Chmelnutskogo', 45, 62);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
values(null, 300, 1800, '2013-02-05 15:01:40', 0, 'IN_PROCESSING', 2, 1, 2, 2, 2);

-- ======================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
values('BY_TRUCK', 500, 'Olga', 'Voronina', '+380987812577');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
values ('Table', 'CARGO', 'School tabl', 3500, 150, 350, 123, 12, 13, 45, 1, 3);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
values ('CHARKIV', 'Lisnova', 34, 26);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
values ('ODESSA', 'Prymarska', 8, 9);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
values('2023-02-20', 1500, 5000, '2023-02-10 13:01:40', 1, 'RECEIVED', 3, 1, 3, 3, 3);

-- =======================================================================================================


INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
values('BY_TRUCK', 120, 'Victor', 'Shevchyk', '+380770374177');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
values ('Monitor', 'PARCEL', 'DELL monitor', 26000, 29, 25, 24, 12, 10, 3, 1, 4);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
values ('CHARKIV', 'Charkivska', 2, 10);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
values ('ODESSA', 'Voskresenska', 15, 9);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
values('2023-02-16', 100, 26100, '2023-01-29 18:01:40', 0, 'ON_THE_WAY', 4, 1, 4, 4, 4);

-- ========================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
values('COURIER', 1000, 'Victoria', 'Shevchenko', '+380777874177');

INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
values ('Books', 'PARCEL', 'Computer Science', 5000, 20, 12, 15, 2, 0.5, 1, 1, 5);

INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
values ('SUMY', 'Charkivska', 12, 9);

INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
values ('LVIV', 'Voskresenska', 5, 19);

INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
values(null, 100, 5100, '2023-01-30 08:01:40', 0, 'NOT_PROCESSED', 5, 1, 5, 5, 5);

-- ========================================================================================================

 INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
 values('BY_TRUCK', 150, 'Maria', 'Feshenkp', '+380777871237');

 INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
 values ('Laptop', 'PARCEL', 'Laptop', 60000, 12, 15, 15, 19, 2, 3, 2, 6);

 INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
 values ('SUMY', 'Charkivska', 1, 79);

 INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
 values ('LVIV', 'Voskresenska', 12, 15);

 INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
 values(null, 100, 100, '2023-07-14 08:01:40', 0, 'NOT_PROCESSED', 6, 2, 6, 6, 6);

-- ===================================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
 values('BY_TRUCK', 1000, 'Oleg', 'Karapec', '+380770071237');

 INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
 values ('Headphones', 'PARCEL', 'HyperX Headphones', 5000, 12, 1, 2, 2, 2, 3, 2, 7);

 INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
 values ('CHARKIV', 'Charkivska', 05, 7);

 INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
 values ('ODESSA', 'Yvileina', 1, 15);

 INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
 values(null, 300, 5300, '2023-08-14 18:01:40', 0, 'IN_PROCESSING', 7, 2, 7, 7, 7);
 
 -- ===================================================================================================================

INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
 values('BY_TRUCK', 500, 'Dmytro', 'Sokolenko', '+380989971237');

 INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
 values ('Container', 'CARGO', 'Container', 150000, 1000, 500, 600, 700, 142, 42, 2, 8);

 INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
 values ('LVIV', 'Zakarpatska', 85, 8);

 INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
 values ('ODESSA', 'Prumorska', 45, 25);

 INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
 values('2023-02-17', 3000, 153000, '2023-02-10 22:01:39', 1, 'ON_THE_WAY', 8, 2, 8, 8, 8);
 
  -- ===================================================================================================================
  
  INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
 values('COURIER', 500, 'Kostyantyn', 'Avremenko', '+380989971255');

 INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
 values ('Documents', 'DOCUMENT', 'Documents', 100, 0.5, 0.5, 1, 2, 1, 1, 2, 9);

 INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
 values ('CHARKIV', 'Dobrovilna', 40, 18);

 INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
 values ('LVIV', 'Chmelnutskogo', 85, 27);

 INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
 values('2023-02-16', 95, 195, '2023-02-01 15:01:39', 1, 'RECEIVED', 9, 2, 9, 9, 9);
 
 -- =======================================================================================================================
 
 INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) 
 values('BY_TRUCK', 800, 'Evgen', 'Vasuliev', '+380989970155');

 INSERT INTO orders (order_name, order_type, order_description, price, weight, length, height, width, volume, volume_weight, user_id, delivery_id) 
 values ('Fishing tools', 'PARCEL', 'Fishing tools', 100, 15, 0.5, 1, 2, 13, 25, 2, 10);

 INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) 
 values ('LVIV', 'Stysa', 30, 8);

 INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) 
 values ('CHARKIV', 'Krych', 5, 7);

 INSERT INTO invoice (delivery_date, delivery_price, total_price, creation_date_time, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) 
 values('2023-02-16', 120, 220, '2023-02-12 20:01:39', 0, 'ON_THE_WAY', 10, 2, 10, 10, 10);
