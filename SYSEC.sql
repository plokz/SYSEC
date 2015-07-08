-- MySQL Script generated by MySQL Workbench
-- 07/04/15 19:41:20
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sysec
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `sysec` ;

-- -----------------------------------------------------
-- Schema sysec
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sysec` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `sysec` ;

-- -----------------------------------------------------
-- Table `sysec`.`Estado`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sysec`.`Estado` ;

CREATE TABLE IF NOT EXISTS `sysec`.`Estado` (
  `idEstado` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`idEstado`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sysec`.`Tipo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sysec`.`Tipo` ;

CREATE TABLE IF NOT EXISTS `sysec`.`Tipo` (
  `idTipo` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(80) NULL,
  `descripcion` VARCHAR(150) NULL,
  PRIMARY KEY (`idTipo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sysec`.`Usuarios`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sysec`.`Usuarios` ;

CREATE TABLE IF NOT EXISTS `sysec`.`Usuarios` (
  `idUsuarios` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(200) NOT NULL,
  `rfc` VARCHAR(45) NULL,
  `colonia` VARCHAR(180) NULL,
  `cp` VARCHAR(45) NULL,
  `email` VARCHAR(100) NULL,
  `usuario` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `estado` INT NOT NULL,
  `tipo` INT NOT NULL DEFAULT 2,
  `fechaMiembro` DATE NULL,
  PRIMARY KEY (`idUsuarios`),
  INDEX `fk_UsuariosEmpresa_Estado1_idx` (`estado` ASC),
  INDEX `fk_Usuarios_Tipo1_idx` (`tipo` ASC),
  UNIQUE INDEX `E-mail_UNIQUE` (`email` ASC),
  UNIQUE INDEX `Usuario_UNIQUE` (`usuario` ASC),
  CONSTRAINT `fk_UsuariosEmpresa_Estado1`
    FOREIGN KEY (`estado`)
    REFERENCES `sysec`.`Estado` (`idEstado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuarios_Tipo1`
    FOREIGN KEY (`tipo`)
    REFERENCES `sysec`.`Tipo` (`idTipo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sysec`.`Preferencias`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sysec`.`Preferencias` ;

CREATE TABLE IF NOT EXISTS `sysec`.`Preferencias` (
  `idPreferencias` INT NOT NULL AUTO_INCREMENT,
  `preferencia` VARCHAR(200) NULL,
  PRIMARY KEY (`idPreferencias`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sysec`.`TienePreferencia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sysec`.`TienePreferencia` ;

CREATE TABLE IF NOT EXISTS `sysec`.`TienePreferencia` (
  `idTienePreferencia` INT NOT NULL AUTO_INCREMENT,
  `preferencias` INT NOT NULL,
  `usuariosTPFK` INT NOT NULL,
  INDEX `fk_TienePreferencia_Preferencias1_idx` (`preferencias` ASC),
  INDEX `fk_TienePreferencia_UsuariosEmpresa1_idx` (`usuariosTPFK` ASC),
  PRIMARY KEY (`idTienePreferencia`),
  CONSTRAINT `fk_TienePreferencia_Fk`
    FOREIGN KEY (`preferencias`)
    REFERENCES `sysec`.`Preferencias` (`idPreferencias`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TienePreferencia_UsuariosEmpresa`
    FOREIGN KEY (`usuariosTPFK`)
    REFERENCES `sysec`.`Usuarios` (`idUsuarios`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sysec`.`Post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sysec`.`Post` ;

CREATE TABLE IF NOT EXISTS `sysec`.`Post` (
  `idPost` INT NOT NULL AUTO_INCREMENT,
  `nombrePublicacion` VARCHAR(150) NULL,
  `presupuesto` VARCHAR(45) NULL,
  `descripcion` VARCHAR(1200) NULL,
  `usuariospPFK` INT NOT NULL,
  `fechaPost` DATE NULL,
  PRIMARY KEY (`idPost`),
  INDEX `fk_Post_Usuarios1_idx` (`usuariospPFK` ASC),
  CONSTRAINT `fk_Post_Usuarios1`
    FOREIGN KEY (`usuariospPFK`)
    REFERENCES `sysec`.`Usuarios` (`idUsuarios`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TieneMensajes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sysec`.`TieneMensajes` ;

CREATE TABLE IF NOT EXISTS `sysec`.`TieneMensajes` (
  `idMensajes` INT NOT NULL AUTO_INCREMENT,
  `idUsuario` INT NOT NULL,
  `idRemitente` INT NOT NULL,
  `asunto` VARCHAR(45) NULL,
  `mensaje` VARCHAR(1200) NULL,
  `estado` TINYINT(1) NOT NULL DEFAULT 0,
  `fechaMensaje` DATE NULL,
  PRIMARY KEY (`idMensajes`),
  INDEX `fk_Mensajes_Usuarios1_idx` (`idUsuario` ASC),
  CONSTRAINT `fk_Mensajes_Usuarios1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `sysec`.`Usuarios` (`idUsuarios`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



INSERT INTO `estado` VALUES (1,'Aguascalientes'),(2,'Baja California'),(3,'Baja California Sur'),(4,'Campeche'),(5,'Chiapas'),(6,'Chihuahua'),(7,'Coahuila'),(8,'Colima'),(9,'Distrito Federal'),(10,'Durango'),(11,'Estado de México'),(12,'Guanajuato'),(13,'Guerrero'),(14,'Hidalgo'),(15,'Jalisco'),(16,'Michoacán'),(17,'Morelos'),(18,'Nayarit'),(19,'Nuevo León'),(20,'Oaxaca'),(21,'Puebla'),(22,'Querétaro'),(23,'Quintana Roo'),(24,'San Luis Potosí'),(25,'Sinaloa'),(26,'Sinaloa'),(27,'Sonora'),(28,'Tabasco'),(29,'Tamaulipas'),(30,'Tlaxcala'),(31,'Veracruz'),(32,'Yucatán'),(33,'Zacatecas');

INSERT INTO `preferencias` VALUES (1,'Deportes'),(2,'Tecnología'),(3,'Inmueble'),(4,'Construcción'),(5,'Textil'),(6,'Automotriz'),(7,'Alimentos'),(8,'Cinematográfica');

INSERT INTO `tipo` VALUES (1,'Administrador','Administrador de los recursos de la aplicación'),(2,'Empresa','Usuario estandar de la aplicación');

INSERT INTO `usuarios` (`nombre`, `email`, `usuario`, `password`, `estado`, `tipo`) VALUES ('Sysec Administrador', 'SysecAdm@gmail.com', 'SysecAdm', 'SysecAdm', '1', '1');
