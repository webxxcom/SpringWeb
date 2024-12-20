-- -----------------------------------------------------
-- Schema studs
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS studs;

USE studs;
DROP TABLE IF EXISTS `student`;
DROP TABLE IF EXISTS `group`;

-- -----------------------------------------------------
-- Table `group`
-- -----------------------------------------------------
CREATE TABLE `group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `student`
-- -----------------------------------------------------
CREATE TABLE `student` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `math` INT UNSIGNED NULL DEFAULT 0,
  `physics` INT UNSIGNED NULL DEFAULT 0,
  `english` INT UNSIGNED NULL DEFAULT 0,
  `stud_name` VARCHAR(45) NOT NULL,
  `group_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_group_idx` (`group_id` ASC) INVISIBLE,
  INDEX `fk_student_name_idx` USING BTREE (`stud_name`) VISIBLE,
  CONSTRAINT `fk_student_group`
    FOREIGN KEY (`group_id`)
    REFERENCES `group` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
);

INSERT INTO `group` (`group_name`)
  VALUES ('KN-14-1'), ('KN-14-4');

INSERT INTO `student` (`math`, `physics`, `english`, `stud_name`, `group_id`)
  VALUES
    ('90', '80', '70', 'Smith', '1'),
    ('80', '90', '70', 'Johnson', '2'),
    ('70', '70', '70', 'Bush', '2');

