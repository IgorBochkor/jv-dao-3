CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service`.`manufactures` (
  `manufacture_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  `country` VARCHAR(225) NOT NULL,
  `deleted` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`manufacture_id`));

  INSERT INTO `taxi_service`.`manufactures` (`name`, `country`) VALUES ('tesla', 'usa');
  INSERT INTO `taxi_service`.`manufactures` (`name`, `country`) VALUES ('bmw', 'germany');

CREATE TABLE `taxi_service`.`drivers` (
  `driver_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  `lisence_number` VARCHAR(225) NOT NULL,
  `deleted` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`driver_id`));

  INSERT INTO `taxi_service`.`drivers` (`name`, `lisence_number`) VALUES ('Tom', 'BC');

  CREATE TABLE `taxi_service`.`cars` (
  `car_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `model` VARCHAR(225) NOT NULL,
  `manufacturer_id` BIGINT(11) NULL,
  `deleted` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`car_id`),
  INDEX `far_manufacturer_fk_idx` (`manufacturer_id` ASC) VISIBLE,
  CONSTRAINT `far_manufacturer_fk`
    FOREIGN KEY (`manufacturer_id`)
    REFERENCES `taxi_service`.`manufactures` (`manufacture_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `taxi_service`.`cars_drivers` (
  `driver_id` BIGINT(11) NOT NULL,
  `car_id` BIGINT(11) NULL,
  PRIMARY KEY (`driver_id`),
  INDEX `car_id_fk_idx` (`car_id` ASC) VISIBLE,
  CONSTRAINT `driver_id_fk`
    FOREIGN KEY (`driver_id`)
    REFERENCES `taxi_service`.`drivers` (`driver_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `car_id_fk`
    FOREIGN KEY (`car_id`)
    REFERENCES `taxi_service`.`cars` (`car_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    ALTER TABLE `taxi_service`.`cars_drivers`
DROP FOREIGN KEY `car_id_fk`,
DROP FOREIGN KEY `driver_id_fk`;
ALTER TABLE `taxi_service`.`cars_drivers`
CHANGE COLUMN `driver_id` `driver_id` BIGINT NULL ,
CHANGE COLUMN `car_id` `car_id` BIGINT NULL ,
DROP PRIMARY KEY;
;
ALTER TABLE `taxi_service`.`cars_drivers`
ADD CONSTRAINT `car_id_fk`
  FOREIGN KEY (`car_id`)
  REFERENCES `taxi_service`.`cars` (`car_id`),
ADD CONSTRAINT `driver_id_fk`
  FOREIGN KEY (`driver_id`)
  REFERENCES `taxi_service`.`drivers` (`driver_id`);
