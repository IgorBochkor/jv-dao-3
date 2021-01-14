CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `taxi_service`.`manufactures` (
  `manufacture_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(225) NOT NULL,
  `country` VARCHAR(225) NOT NULL,
  `deleted` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`manufacture_id`));

  INSERT INTO `taxi_service`.`manufactures` (`name`, `country`) VALUES ('tesla', 'usa');
  INSERT INTO `taxi_service`.`manufactures` (`name`, `country`) VALUES ('bmw', 'germany');