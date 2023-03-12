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
DROP TABLE IF EXISTS `cargo_delivery_db`.`delivery` ;

CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`delivery` (
  `id_delivery` BIGINT NOT NULL AUTO_INCREMENT,
  `delivery_type` ENUM('BY_TRUCK', 'COURIER') NOT NULL,
  `distance` DOUBLE UNSIGNED NOT NULL,
  `recipient_name` VARCHAR(45) NOT NULL,
  `recipient_surname` VARCHAR(45) NOT NULL,
  `recipient_phone` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id_delivery`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cargo_delivery_db`.`orders` ;

CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`orders` (
  `id_order` BIGINT NOT NULL AUTO_INCREMENT,
  `order_name` VARCHAR(100) NOT NULL,
  `order_type` ENUM('CARGO', 'PARCEL', 'DOCUMENT') NOT NULL,
  `order_description` VARCHAR(1024) NOT NULL,
  `price` DECIMAL(10,0) UNSIGNED NOT NULL,
  `weight` DOUBLE UNSIGNED NOT NULL,
  `length` DOUBLE UNSIGNED NOT NULL,
  `height` DOUBLE UNSIGNED NOT NULL,
  `width` DOUBLE UNSIGNED NOT NULL,
  `volume` DOUBLE UNSIGNED NOT NULL,
  `volume_weight` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`id_order`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cargo_delivery_db`.`users` ;

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
-- Table `cargo_delivery_db`.`second_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cargo_delivery_db`.`second_address` ;

CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`second_address` (
  `id_second_address` BIGINT NOT NULL AUTO_INCREMENT,
  `second_city` VARCHAR(20) NOT NULL,
  `second_street_name` VARCHAR(45) NOT NULL,
  `second_street_number` VARCHAR(45) NOT NULL,
  `second_house_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_second_address`));


-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`first_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cargo_delivery_db`.`first_address` ;

CREATE TABLE IF NOT EXISTS `cargo_delivery_db`.`first_address` (
  `id_first_address` BIGINT NOT NULL AUTO_INCREMENT,
  `first_city` VARCHAR(20) NOT NULL,
  `first_street_name` VARCHAR(45) NOT NULL,
  `first_street_number` VARCHAR(45) NOT NULL,
  `first_house_number` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_first_address`));


-- -----------------------------------------------------
-- Table `cargo_delivery_db`.`invoice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cargo_delivery_db`.`invoice` ;

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


