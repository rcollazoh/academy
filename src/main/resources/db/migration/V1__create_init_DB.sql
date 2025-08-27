CREATE DATABASE  IF NOT EXISTS `academy` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `academy`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: academy
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account_agency`
--

DROP TABLE IF EXISTS `account_agency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_agency` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `short_description` varchar(50) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `currency` varchar(20) NOT NULL DEFAULT 'CUP',
  `balance` decimal(19,4) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` datetime DEFAULT NULL,
  `nom_payment_method_id` int unsigned NOT NULL,
  `tip` decimal(19,4) DEFAULT '0.0000',
  PRIMARY KEY (`id`),
  KEY `fk_agency_account_payment_method` (`nom_payment_method_id`),
  CONSTRAINT `fk_agency_account_payment_method` FOREIGN KEY (`nom_payment_method_id`) REFERENCES `nom_payment_method` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Agency-managed accounts for handling financial transactions.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_agency`
--

LOCK TABLES `account_agency` WRITE;
/*!40000 ALTER TABLE `account_agency` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_agency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_operation`
--

DROP TABLE IF EXISTS `account_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_operation` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `operation_type_id` int unsigned NOT NULL,
  `from_person_id` bigint DEFAULT NULL,
  `to_person_id` bigint DEFAULT NULL,
  `from_person_type` varchar(20) DEFAULT NULL,
  `to_person_type` varchar(20) DEFAULT NULL,
  `amount` decimal(19,4) NOT NULL,
  `currency` varchar(20) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `student_course_id` bigint DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` datetime DEFAULT NULL,
  `task_code` varchar(20) DEFAULT NULL,
  `is_cancelled` tinyint(1) NOT NULL DEFAULT '0',
  `agency_account_id` int unsigned NOT NULL,
  `agency_account_remaining_balance` decimal(19,4) DEFAULT NULL,
  `is_settled` tinyint(1) NOT NULL DEFAULT '0',
  `notes` varchar(1000) DEFAULT NULL,
  `payment_method_id` bigint DEFAULT NULL,
  `tip` decimal(19,4) NOT NULL DEFAULT '0.0000',
  `confirmation_status_change_date` datetime DEFAULT NULL COMMENT 'Date when confirmation status changes to approved or rejected.',
  PRIMARY KEY (`id`),
  KEY `fk_agencia_accountId` (`agency_account_id`),
  KEY `fk_operation_typeId` (`operation_type_id`),
  KEY `FKq5qbwlkkv8k19adjfvr6jxii` (`to_person_id`),
  CONSTRAINT `fk_agencia_accountId` FOREIGN KEY (`agency_account_id`) REFERENCES `account_agency` (`id`),
  CONSTRAINT `fk_operation_typeId` FOREIGN KEY (`operation_type_id`) REFERENCES `nom_operation_type` (`id`),
  CONSTRAINT `FKq5qbwlkkv8k19adjfvr6jxii` FOREIGN KEY (`to_person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Handles money operations on accounts: movements, deposits, and withdrawals.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_operation`
--

LOCK TABLES `account_operation` WRITE;
/*!40000 ALTER TABLE `account_operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_class`
--

DROP TABLE IF EXISTS `config_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_class` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `description` text,
  `type` enum('video','imagen','pdf','link') DEFAULT NULL,
  `recourse_url` text,
  `module_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `module_id` (`module_id`),
  CONSTRAINT `config_class_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `config_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_class`
--

LOCK TABLES `config_class` WRITE;
/*!40000 ALTER TABLE `config_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_course`
--

DROP TABLE IF EXISTS `config_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `description` text,
  `duration_days` int DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  `area_id` int DEFAULT NULL,
  `practice_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `area_id` (`area_id`),
  KEY `practice_id` (`practice_id`),
  CONSTRAINT `config_course_ibfk_1` FOREIGN KEY (`area_id`) REFERENCES `nom_area` (`id`),
  CONSTRAINT `config_course_ibfk_2` FOREIGN KEY (`practice_id`) REFERENCES `nom_practice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_course`
--

LOCK TABLES `config_course` WRITE;
/*!40000 ALTER TABLE `config_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_exam`
--

DROP TABLE IF EXISTS `config_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_exam` (
  `id` int NOT NULL AUTO_INCREMENT,
  `module_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `module_id` (`module_id`),
  CONSTRAINT `config_exam_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `config_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_exam`
--

LOCK TABLES `config_exam` WRITE;
/*!40000 ALTER TABLE `config_exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_module`
--

DROP TABLE IF EXISTS `config_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_module` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `description` text,
  `order_num` int DEFAULT NULL,
  `is_common` tinyint(1) DEFAULT '0',
  `course_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `config_module_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `config_course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_module`
--

LOCK TABLES `config_module` WRITE;
/*!40000 ALTER TABLE `config_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_option`
--

DROP TABLE IF EXISTS `config_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_option` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_id` int DEFAULT NULL,
  `text` text NOT NULL,
  `is_correct` tinyint(1) DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `config_option_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `config_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_option`
--

LOCK TABLES `config_option` WRITE;
/*!40000 ALTER TABLE `config_option` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_question`
--

DROP TABLE IF EXISTS `config_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `text` text NOT NULL,
  `module_id` int DEFAULT NULL,
  `area_id` int DEFAULT NULL,
  `practice_id` int DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `module_id` (`module_id`),
  KEY `area_id` (`area_id`),
  KEY `practice_id` (`practice_id`),
  CONSTRAINT `config_question_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `config_module` (`id`),
  CONSTRAINT `config_question_ibfk_2` FOREIGN KEY (`area_id`) REFERENCES `nom_area` (`id`),
  CONSTRAINT `config_question_ibfk_3` FOREIGN KEY (`practice_id`) REFERENCES `nom_practice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_question`
--

LOCK TABLES `config_question` WRITE;
/*!40000 ALTER TABLE `config_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `external_payment`
--

DROP TABLE IF EXISTS `external_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `external_payment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `external_entity` varchar(100) NOT NULL,
  `client_first_name` varchar(45) NOT NULL,
  `client_last_name` varchar(45) NOT NULL,
  `client_phone` varchar(20) NOT NULL,
  `beneficiary_name` varchar(45) NOT NULL,
  `beneficiary_phone` varchar(20) NOT NULL,
  `amount` decimal(19,4) NOT NULL DEFAULT '0.0000',
  `currency` varchar(10) NOT NULL,
  `response` json DEFAULT NULL,
  `date_modified` datetime DEFAULT NULL,
  `reference_code` varchar(20) NOT NULL,
  `payment_status` varchar(20) NOT NULL,
  `operation_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='External payment records from systems like Tropipay';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_payment`
--

LOCK TABLES `external_payment` WRITE;
/*!40000 ALTER TABLE `external_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `external_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','<< Flyway Baseline >>','BASELINE','<< Flyway Baseline >>',NULL,'academy','2025-08-26 21:02:57',0,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nom_action`
--

DROP TABLE IF EXISTS `nom_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nom_action` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(250) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_nom_action_description` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Catalog of predefined actions that can be associated with workflows.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nom_action`
--

LOCK TABLES `nom_action` WRITE;
/*!40000 ALTER TABLE `nom_action` DISABLE KEYS */;
INSERT INTO `nom_action` VALUES (1,'Logout','2025-08-26 21:07:41',NULL),(3,'Login','2025-08-26 21:07:26',NULL);
/*!40000 ALTER TABLE `nom_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nom_area`
--

DROP TABLE IF EXISTS `nom_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nom_area` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` tinytext,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nom_area`
--

LOCK TABLES `nom_area` WRITE;
/*!40000 ALTER TABLE `nom_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `nom_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nom_country`
--

DROP TABLE IF EXISTS `nom_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nom_country` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `country_code` varchar(3) DEFAULT NULL,
  `cell_prefix` varchar(50) DEFAULT NULL,
  `nationality` varchar(30) DEFAULT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modification_date` datetime DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `max_cell_number_length` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Country catalog.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nom_country`
--

LOCK TABLES `nom_country` WRITE;
/*!40000 ALTER TABLE `nom_country` DISABLE KEYS */;
/*!40000 ALTER TABLE `nom_country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nom_operation_type`
--

DROP TABLE IF EXISTS `nom_operation_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nom_operation_type` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `short_description` varchar(20) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` datetime DEFAULT NULL,
  `clears_debt` tinyint(1) NOT NULL DEFAULT '0',
  `visible` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_short_description` (`short_description`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Types of financial operations';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nom_operation_type`
--

LOCK TABLES `nom_operation_type` WRITE;
/*!40000 ALTER TABLE `nom_operation_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `nom_operation_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nom_payment_method`
--

DROP TABLE IF EXISTS `nom_payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nom_payment_method` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `short_description` varchar(20) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` datetime DEFAULT NULL,
  `show_to_customer` tinyint(1) NOT NULL DEFAULT '1',
  `agency_transfer` tinyint(1) DEFAULT '0',
  `percentage_applied` float DEFAULT NULL,
  `foreign_transfer` tinyint(1) DEFAULT '0',
  `minimum_transfer_value` float DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `short_description` (`short_description`),
  KEY `idx_payment_method_agency_transfer` (`agency_transfer`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Payment method catalog linked directly to travel operations.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nom_payment_method`
--

LOCK TABLES `nom_payment_method` WRITE;
/*!40000 ALTER TABLE `nom_payment_method` DISABLE KEYS */;
/*!40000 ALTER TABLE `nom_payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nom_practice`
--

DROP TABLE IF EXISTS `nom_practice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nom_practice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `area_id` int DEFAULT NULL,
  `description` tinytext,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `area_id` (`area_id`),
  CONSTRAINT `nom_practice_ibfk_1` FOREIGN KEY (`area_id`) REFERENCES `nom_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nom_practice`
--

LOCK TABLES `nom_practice` WRITE;
/*!40000 ALTER TABLE `nom_practice` DISABLE KEYS */;
/*!40000 ALTER TABLE `nom_practice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `email` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `gender` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci,
  `birthday` date DEFAULT NULL,
  `verified` tinyint(1) DEFAULT '0',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `client_attraction_info` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci,
  `workplace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `about_me` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `date_creation` datetime DEFAULT NULL,
  `date_modify` datetime DEFAULT NULL,
  `is_client` bit(1) NOT NULL,
  `is_Tasker` bit(1) NOT NULL,
  `is_user` bit(1) NOT NULL,
  `last_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `password_usuario` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `id_number` varchar(45) COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `password` varchar(60) COLLATE utf8mb4_0900_as_ci DEFAULT NULL,
  `area_id` int DEFAULT NULL,
  `practice_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index2` (`id`),
  KEY `FKpi33ns6amyf9tj8qk233chmd6` (`area_id`),
  KEY `FKawqx663ofvwwqcmjo46l6whpa` (`practice_id`),
  CONSTRAINT `FKawqx663ofvwwqcmjo46l6whpa` FOREIGN KEY (`practice_id`) REFERENCES `nom_practice` (`id`),
  CONSTRAINT `FKpi33ns6amyf9tj8qk233chmd6` FOREIGN KEY (`area_id`) REFERENCES `nom_area` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_role`
--

DROP TABLE IF EXISTS `person_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `person_id` bigint NOT NULL,
  `role_id` int NOT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_person_role_role` (`role_id`),
  KEY `FKhyx1efsls0f00lxs6xd4w2b3j` (`person_id`),
  CONSTRAINT `dhfjdhfjdh` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKhyx1efsls0f00lxs6xd4w2b3j` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Person to Role association';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_role`
--

LOCK TABLES `person_role` WRITE;
/*!40000 ALTER TABLE `person_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `person_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `short_description` varchar(1000) DEFAULT NULL,
  `long_description` varchar(2000) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Application roles';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_class`
--

DROP TABLE IF EXISTS `student_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_class` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_course_id` int DEFAULT NULL,
  `class_id` int DEFAULT NULL,
  `viewed` tinyint(1) DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `student_course_id` (`student_course_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `student_class_ibfk_1` FOREIGN KEY (`student_course_id`) REFERENCES `student_course` (`id`),
  CONSTRAINT `student_class_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `config_class` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_class`
--

LOCK TABLES `student_class` WRITE;
/*!40000 ALTER TABLE `student_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_course`
--

DROP TABLE IF EXISTS `student_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `person_id` int NOT NULL,
  `course_id` int DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `status` enum('pendiente','active','finalizado','aprobado','no_aprobado') DEFAULT NULL,
  `requires_invoice` tinyint(1) DEFAULT '0',
  `receipt_url` text,
  `payment_method` enum('online','transferencia','sinpe') DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `student_course_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `config_course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_course`
--

LOCK TABLES `student_course` WRITE;
/*!40000 ALTER TABLE `student_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_exam_answer`
--

DROP TABLE IF EXISTS `student_exam_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_exam_answer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `module_student_id` int DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  `option_id` int DEFAULT NULL,
  `is_correct` tinyint(1) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `module_student_id` (`module_student_id`),
  KEY `question_id` (`question_id`),
  KEY `option_id` (`option_id`),
  CONSTRAINT `student_exam_answer_ibfk_1` FOREIGN KEY (`module_student_id`) REFERENCES `student_module` (`id`),
  CONSTRAINT `student_exam_answer_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `config_question` (`id`),
  CONSTRAINT `student_exam_answer_ibfk_3` FOREIGN KEY (`option_id`) REFERENCES `config_option` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_exam_answer`
--

LOCK TABLES `student_exam_answer` WRITE;
/*!40000 ALTER TABLE `student_exam_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_exam_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_module`
--

DROP TABLE IF EXISTS `student_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_module` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_course_id` int DEFAULT NULL,
  `module_id` int DEFAULT NULL,
  `status` enum('pendiente','aprobado','no_aprobado') DEFAULT NULL,
  `fecha_exam` date DEFAULT NULL,
  `intentos` int DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `student_course_id` (`student_course_id`),
  KEY `module_id` (`module_id`),
  CONSTRAINT `student_module_ibfk_1` FOREIGN KEY (`student_course_id`) REFERENCES `student_course` (`id`),
  CONSTRAINT `student_module_ibfk_2` FOREIGN KEY (`module_id`) REFERENCES `config_module` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_module`
--

LOCK TABLES `student_module` WRITE;
/*!40000 ALTER TABLE `student_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trace`
--

DROP TABLE IF EXISTS `trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trace` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action_id` bigint NOT NULL,
  `person_id` bigint DEFAULT NULL,
  `full_name` varchar(512) NOT NULL,
  `mobile_phone` varchar(15) NOT NULL,
  `context` varchar(100) DEFAULT NULL,
  `details` varchar(4000) DEFAULT NULL,
  `student_course_id` bigint DEFAULT NULL,
  `application_id` bigint DEFAULT NULL,
  `student_course_code` varchar(20) DEFAULT NULL COMMENT 'Format YYMMDD00000',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP address of the computer sending the request to register the trace.',
  PRIMARY KEY (`id`),
  KEY `fk_trace_action` (`action_id`),
  KEY `fk_trace_person` (`person_id`),
  KEY `idx_trace_application_id` (`application_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Registry of operations performed in the system with contextual details.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trace`
--

LOCK TABLES `trace` WRITE;
/*!40000 ALTER TABLE `trace` DISABLE KEYS */;
/*!40000 ALTER TABLE `trace` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-27 16:35:23
