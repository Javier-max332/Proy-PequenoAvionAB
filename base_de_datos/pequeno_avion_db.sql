-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pequeno_avion_db
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `CATEGORIA`
--

DROP TABLE IF EXISTS `CATEGORIA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CATEGORIA` (
  `cat_id` int NOT NULL AUTO_INCREMENT,
  `cat_nombre` varchar(255) NOT NULL,
  `cat_descripcion` text,
  PRIMARY KEY (`cat_id`),
  UNIQUE KEY `cat_nombre` (`cat_nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CATEGORIA`
--

LOCK TABLES `CATEGORIA` WRITE;
/*!40000 ALTER TABLE `CATEGORIA` DISABLE KEYS */;
INSERT INTO `CATEGORIA` VALUES (1,'Procesadores','Unidades centrales de procesamiento (CPU) para distintos sockets.'),(2,'Tarjetas Madre','Placas base de diferentes formatos y chipsets.'),(3,'Memoria RAM','Módulos de memoria volátil de alta velocidad (DDR4, DDR5).'),(4,'Almacenamiento','Unidades de estado sólido (SSD) y discos duros (HDD).'),(5,'Tarjetas de Video','Unidades de procesamiento gráfico (GPU) para gaming y diseño.'),(6,'Fuentes de Poder','Unidades de suministro de energía con certificación de eficiencia.'),(7,'Gabinetes','Chasis para el montaje de componentes con diversos diseños.'),(8,'Enfriamiento','Sistemas de refrigeración líquida y por aire.');
/*!40000 ALTER TABLE `CATEGORIA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DIRECCION`
--

DROP TABLE IF EXISTS `DIRECCION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DIRECCION` (
  `dir_id` int NOT NULL AUTO_INCREMENT,
  `usu_id` int NOT NULL,
  `dir_pais` varchar(100) NOT NULL,
  `dir_estado` varchar(100) NOT NULL,
  `dir_municipio` varchar(100) NOT NULL,
  `dir_calle` varchar(150) NOT NULL,
  `dir_num_exterior` varchar(20) NOT NULL,
  `dir_num_interior` varchar(20) DEFAULT NULL,
  `dir_codigo_postal` varchar(20) NOT NULL,
  PRIMARY KEY (`dir_id`),
  KEY `usu_id` (`usu_id`),
  CONSTRAINT `DIRECCION_ibfk_1` FOREIGN KEY (`usu_id`) REFERENCES `USUARIO` (`usu_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DIRECCION`
--

LOCK TABLES `DIRECCION` WRITE;
/*!40000 ALTER TABLE `DIRECCION` DISABLE KEYS */;
INSERT INTO `DIRECCION` VALUES (1,1,'México','Zacatecas','Jalpa','Calle Principal','100',NULL,'99600'),(2,4,'México','Zacatecas','Jalpa','Juares','12','23','99620'),(3,3,'México','Colima','Rayo nuevo','Rosario de José','1245','28','98782'),(4,2,'México','Colima','Rayo nuevo','Rosario de José','1245','28','98782');
/*!40000 ALTER TABLE `DIRECCION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FACTURA`
--

DROP TABLE IF EXISTS `FACTURA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FACTURA` (
  `fact_id` int NOT NULL AUTO_INCREMENT,
  `pago_id` int NOT NULL,
  `fact_rfc` varchar(20) NOT NULL,
  `fact_razon_social` varchar(200) NOT NULL,
  `fact_fecha_emision` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`fact_id`),
  KEY `pago_id` (`pago_id`),
  CONSTRAINT `FACTURA_ibfk_1` FOREIGN KEY (`pago_id`) REFERENCES `PAGO` (`pago_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FACTURA`
--

LOCK TABLES `FACTURA` WRITE;
/*!40000 ALTER TABLE `FACTURA` DISABLE KEYS */;
INSERT INTO `FACTURA` VALUES (1,2,'XAXX010101000','Javier Viramontes Torres Prueba','2026-05-27 13:50:58');
/*!40000 ALTER TABLE `FACTURA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PAGO`
--

DROP TABLE IF EXISTS `PAGO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PAGO` (
  `pago_id` int NOT NULL AUTO_INCREMENT,
  `ped_id` int NOT NULL,
  `pago_metodo_pago` varchar(50) DEFAULT NULL,
  `pago_monto` decimal(10,2) NOT NULL,
  `pago_estado_pago` varchar(50) DEFAULT 'PENDIENTE',
  PRIMARY KEY (`pago_id`),
  UNIQUE KEY `ped_id` (`ped_id`),
  CONSTRAINT `PAGO_ibfk_1` FOREIGN KEY (`ped_id`) REFERENCES `PEDIDO` (`ped_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PAGO`
--

LOCK TABLES `PAGO` WRITE;
/*!40000 ALTER TABLE `PAGO` DISABLE KEYS */;
INSERT INTO `PAGO` VALUES (1,1,'PayPal',25565.00,'CANCELADO'),(2,2,'Pago en OXXO',27948.00,'COMPLETADO');
/*!40000 ALTER TABLE `PAGO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PEDIDO`
--

DROP TABLE IF EXISTS `PEDIDO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PEDIDO` (
  `ped_id` int NOT NULL AUTO_INCREMENT,
  `ped_num_pedido` varchar(255) DEFAULT NULL,
  `usu_id` int NOT NULL,
  `dir_id` int NOT NULL,
  `ped_fecha_compra` datetime DEFAULT CURRENT_TIMESTAMP,
  `ped_fecha_entrega_estimada` date DEFAULT NULL,
  `ped_total` decimal(38,2) DEFAULT NULL,
  `ped_estado_envio` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ped_id`),
  UNIQUE KEY `ped_num_pedido` (`ped_num_pedido`),
  KEY `usu_id` (`usu_id`),
  KEY `dir_id` (`dir_id`),
  CONSTRAINT `PEDIDO_ibfk_1` FOREIGN KEY (`usu_id`) REFERENCES `USUARIO` (`usu_id`),
  CONSTRAINT `PEDIDO_ibfk_2` FOREIGN KEY (`dir_id`) REFERENCES `DIRECCION` (`dir_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PEDIDO`
--

LOCK TABLES `PEDIDO` WRITE;
/*!40000 ALTER TABLE `PEDIDO` DISABLE KEYS */;
INSERT INTO `PEDIDO` VALUES (1,'PA-1779904795700',2,4,'2026-05-27 11:59:56','2026-07-01',25565.00,'CANCELADO'),(2,'PA-1779911438156',3,3,'2026-05-27 13:50:38','2026-06-23',27948.00,'PROCESANDO_ENVIO');
/*!40000 ALTER TABLE `PEDIDO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PEDIDO_DETALLE`
--

DROP TABLE IF EXISTS `PEDIDO_DETALLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PEDIDO_DETALLE` (
  `ped_id` int NOT NULL,
  `prod_id` int NOT NULL,
  `det_cantidad` int NOT NULL,
  `det_precio_unitario` decimal(38,2) DEFAULT NULL,
  `det_importe` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`ped_id`,`prod_id`),
  KEY `prod_id` (`prod_id`),
  CONSTRAINT `PEDIDO_DETALLE_ibfk_1` FOREIGN KEY (`ped_id`) REFERENCES `PEDIDO` (`ped_id`) ON DELETE CASCADE,
  CONSTRAINT `PEDIDO_DETALLE_ibfk_2` FOREIGN KEY (`prod_id`) REFERENCES `PRODUCTO` (`prod_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PEDIDO_DETALLE`
--

LOCK TABLES `PEDIDO_DETALLE` WRITE;
/*!40000 ALTER TABLE `PEDIDO_DETALLE` DISABLE KEYS */;
INSERT INTO `PEDIDO_DETALLE` VALUES (1,7,1,3800.00,3800.00),(1,8,1,3850.00,3850.00),(1,13,1,2915.00,2915.00),(1,18,1,1600.00,1600.00),(1,22,1,5300.00,5300.00),(1,26,1,2200.00,2200.00),(1,28,1,2800.00,2800.00),(1,33,1,3100.00,3100.00),(2,1,2,4499.00,8998.00),(2,5,1,7400.00,7400.00),(2,6,1,5500.00,5500.00),(2,9,1,4150.00,4150.00),(2,10,1,1900.00,1900.00);
/*!40000 ALTER TABLE `PEDIDO_DETALLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUCTO`
--

DROP TABLE IF EXISTS `PRODUCTO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PRODUCTO` (
  `prod_id` int NOT NULL AUTO_INCREMENT,
  `prod_sku` varchar(255) DEFAULT NULL,
  `prov_id` int NOT NULL,
  `cat_id` int NOT NULL,
  `prod_nombre` varchar(255) NOT NULL,
  `prod_modelo` varchar(255) DEFAULT NULL,
  `prod_descripcion` text,
  `prod_costo_adquisicion` decimal(38,2) DEFAULT NULL,
  `prod_precio_base` decimal(38,2) NOT NULL,
  `prod_stock` int DEFAULT '0',
  `prod_marca` varchar(255) DEFAULT NULL,
  `prod_fecha_ingreso` datetime DEFAULT CURRENT_TIMESTAMP,
  `prod_estado_producto` varchar(255) DEFAULT NULL,
  `prod_imagen_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`prod_id`),
  UNIQUE KEY `prod_sku` (`prod_sku`),
  KEY `prov_id` (`prov_id`),
  KEY `cat_id` (`cat_id`),
  CONSTRAINT `PRODUCTO_ibfk_1` FOREIGN KEY (`prov_id`) REFERENCES `PROVEEDOR` (`prov_id`),
  CONSTRAINT `PRODUCTO_ibfk_2` FOREIGN KEY (`cat_id`) REFERENCES `CATEGORIA` (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCTO`
--

LOCK TABLES `PRODUCTO` WRITE;
/*!40000 ALTER TABLE `PRODUCTO` DISABLE KEYS */;
INSERT INTO `PRODUCTO` VALUES (1,'KF560C30BBEA-32',1,3,'Kingston Fury Beast RGB 32GB DDR5','Kingston Fury 12 Beast 3','',3500.00,4499.00,0,'Kingston','2026-05-21 18:26:44','AGOTADO','https://m.media-amazon.com/images/I/61Nu3Bh1+5L._AC_SX679_.jpg'),(2,'BX8071514700F',2,1,'Intel Core i7-14700F','i7-14700F','LGA 1700, 5.40GHz, 20 Núcleos, 33MB Caché, Incluye Disipador - 14va. Generación Raptor Lake',6000.00,6800.00,4,'Intel','2026-05-21 10:27:52','ACTIVO','https://www.cyberpuerta.mx/img/product/XL/CP-INTEL-BX8071514700F-9d5fe2.jpg'),(5,'100-100000910WOF',3,1,'AMD Ryzen 7 7800X3D','Ryzen 7 7800X3D','Socket AM5, 4.2GHz (5.0GHz Turbo), 8 Núcleos, 16 Hilos, 96MB L3 Caché - Tecnología 3D V-Cache ideal para gaming',6500.00,7400.00,3,'AMD','2026-05-16 01:25:04','ACTIVO','https://m.media-amazon.com/images/I/51HqC0rU9HL._AC_SX679_.jpg'),(6,'BX8071514600K',2,1,'Intel Core i5-14600K','i5-14600K','LGA 1700, 3.50GHz (5.30GHz Turbo), 14 Núcleos (6 P-cores + 8 E-cores), 24MB Caché - Desbloqueado para overclock',4800.00,5500.00,5,'Intel','2026-05-16 02:01:56','ACTIVO','https://m.media-amazon.com/images/I/61DK+cQvKOL._AC_SX679_.jpg'),(7,'100-100000593WOF',3,1,'AMD Ryzen 5 7600X','Ryzen 5 7600X','Socket AM5, 4.7GHz (5.3GHz Turbo), 6 Núcleos, 12 Hilos, 32MB L3 Caché - Excelente rendimiento costo-beneficio',3200.00,3800.00,8,'AMD','2026-05-16 02:05:33','ACTIVO','https://m.media-amazon.com/images/I/51m7L9bQI8L._AC_SY300_SX300_QL70_ML2_.jpg'),(8,'TUF-B650-PLUS-WIFI',6,2,'ASUS TUF Gaming B650-Plus WiFi','TUF GAMING B650-PLUS WIFI','Socket AM5 (AMD Ryzen 7000/8000), DDR5, PCIe 5.0 M.2, Formato ATX, Wi-Fi 6E, Excelente disipación térmica',3100.00,3850.00,3,'ASUS','2026-05-16 02:10:11','ACTIVO','https://m.media-amazon.com/images/I/81ogi-krqkL._AC_SY300_SX300_QL70_ML2_.jpg'),(9,'PRO-Z790-P-WIFI',7,2,'MSI PRO Z790-P WIFI','PRO Z790-P WIFI','Socket LGA 1700 (Intel 12va, 13va y 14va Gen), DDR5, PCIe 5.0, Formato ATX, Wi-Fi 6E, ideal para procesadores K',3400.00,4150.00,2,'MSI','2026-05-16 02:12:01','ACTIVO','https://m.media-amazon.com/images/I/81qbq1OEb7L._AC_SX679_.jpg'),(10,'B550M-DS3H-AC',2,2,'Gigabyte B550M DS3H AC','B550M DS3H AC','Socket AM4 (AMD Ryzen 3000/4000/5000), DDR4, Dual M.2 PCIe 4.0, Formato Micro-ATX, Wi-Fi e Intel GbE LAN',1450.00,1900.00,6,'Gigabyte','2026-05-16 02:14:00','ACTIVO','https://m.media-amazon.com/images/I/81fTfVbZy7L._AC_SX679_.jpg'),(11,'PRIME-B760M-A-AX',2,2,'ASUS Prime B760M-A AX','PRIME B760M-A AX','Socket LGA 1700 (Intel 12va/13va/14va Gen), DDR5, Formato Micro-ATX, Wi-Fi 6, Puertos DisplayPort/HDMI, Red 2.5Gb',2100.00,2650.00,4,'ASUS','2026-05-16 02:17:17','ACTIVO','https://m.media-amazon.com/images/I/81VNRxCFeDL._AC_SX679_.jpg'),(12,'CMK16GX4M2B3200C16',7,3,'Corsair Vengeance LPX 16GB (2x8GB) DDR4','CMK16GX4M2B3200C16','Kit de 16GB (2x8GB), 3200MHz, CL16, Intel XMP 2.0, Disipador de aluminio de bajo perfil, Color Negro',1200.00,2550.00,10,'Corsair','2026-05-16 11:59:19','ACTIVO','https://m.media-amazon.com/images/I/61wCOVcyvFL._AC_SX679_.jpg'),(13,'F5-6000J3636F16GX2-TZ5RK',7,3,'G.Skill Trident Z5 RGB 32GB (2x16GB) DDR5','F5-6000J3636F16GX2-TZ5RK','Kit de 32GB (2x16GB), 6000MHz, CL36, Intel XMP 3.0, Diseño premium con barra de iluminación RGB fluida',2010.00,2915.00,5,'G.Skill','2026-05-16 12:10:40','ACTIVO','https://i.ebayimg.com/images/g/QK4AAeSwUjZpUxns/s-l1600.webp'),(14,'TF3D416G3600HC18JDC01',4,3,'Teamgroup T-Force Delta RGB 16GB (2x8GB) DDR4','TF3D416G3600HC18JDC01','Kit de 16GB (2x8GB), 3600MHz, CL18, Iluminación gran angular de 120° RGB, compatible con AMD e Intel',1200.00,2352.00,8,'Teamgroup','2026-05-16 12:13:20','ACTIVO','https://m.media-amazon.com/images/I/71yyY+Y29WL._AC_SX679_.jpg'),(15,'CP2K16G56C46U5',7,3,'Crucial Pro 32GB (2x16GB) DDR5','CP2K16G56C46U5','Kit de 32GB (2x16GB), 5600MHz, CL46, Compatible con Intel XMP 3.0 y AMD EXPO, Disipador negro mate elegante',3400.00,4553.00,4,'Crucial','2026-05-16 12:15:58','ACTIVO','https://m.media-amazon.com/images/I/516R-HtUrQL._AC_SX679_.jpg'),(16,'MZ-V9P2T0B-AM',3,4,'Samsung 990 PRO M.2 2TB PCIe 4.0 NVMe 7.0c','MZ-V9P2T0B/A','Unidad de estado sólido interna de alto rendimiento, velocidades de lectura de hasta 7,450 MB/s, ideal para PS5 y PCs de gama entusiasta',7320.00,9100.00,5,'Samsung','2026-05-16 12:19:27','ACTIVO','https://m.media-amazon.com/images/I/71OWtcxKgvL._AC_SX679_.jpg'),(17,'SNV2S-1000G',2,4,'Kingston NV2 M.2 1TB PCIe 4.0 NVMe','SNV2S/1000G','SSD interno con excelente relación calidad-precio, velocidades de lectura de hasta 3,500 MB/s, perfecto para laptops y ensambles equilibrados',1400.00,2600.00,12,'Kingston','2026-05-16 12:22:02','ACTIVO','https://m.media-amazon.com/images/I/71OTq1+0bEL._AC_SX679_.jpg'),(18,'CT480BX500SSD1',4,4,'Crucial E100 480GB PCIe 4.0 Gen4 2280 NVMe M.2 SSD','CT480E100SSD8','SSD interno de formato 2.5\" tradicional, velocidades de hasta 540 MB/s. Excelente opción económica para revivir equipos antiguos con discos mecánicos lentos',900.00,1600.00,8,'Crucial','2026-05-16 12:25:31','ACTIVO','https://m.media-amazon.com/images/I/51iH9vvWAiL._AC_SX679_.jpg'),(19,'ST2000DM008',4,4,'Seagate BarraCuda 2TB HDD 3.5 pulgadas','ST2000DM008','Disco duro mecánico interno (HDD), velocidad de 7200 RPM, 256MB de caché, interfaz SATA de 6 Gb/s. Diseñado para almacenamiento masivo de datos, juegos y respaldos',1600.00,2430.00,6,'Seagate','2026-05-16 12:28:58','ACTIVO','https://m.media-amazon.com/images/I/71V1jd3s9dL._AC_SX679_.jpg'),(20,'DUAL-RTX4060-O8G-V2',2,5,'ASUS Dual GeForce RTX 4060 V2 OC Edition 8GB','DUAL-RTX4060-O8G-V2','8GB GDDR6, arquitectura NVIDIA Ada Lovelace, diseño de ventilador Axial-tech, tecnología DLSS 3 y Ray Tracing de última generación',6500.00,7200.00,5,'ASUS','2026-05-27 10:52:47','ACTIVO','https://m.media-amazon.com/images/I/81i5KcFKysL._AC_SY450_.jpg'),(21,'RTX-4070-S-VENTUS-2X',2,5,'MSI Gaming GeForce RTX 4070 Super a12G Ventus 2X OC','RTX 4070 SUPER 12G VENTUS 2X OC','12GB GDDR6X, diseño de doble ventilador TORX Fan 4.0, overclock de fábrica, ideal para gaming competitivo a 1440p con trazado de rayos extremo',10500.00,12245.00,3,'MSI','2026-05-27 10:54:30','ACTIVO','https://i.ebayimg.com/images/g/geoAAOSwlgJntDzo/s-l1600.webp'),(22,'GV-R76XTGAMING-OC-16GD',7,5,'Gigabyte Radeon RX 7600 XT Gaming OC 16G','GV-R76XTGAMING-OC-16GD','16GB GDDR6, sistema de enfriamiento Windforce de 3 ventiladores, arquitectura AMD RDNA 3, excelente cantidad de memoria para texturas en ultra',4350.00,5300.00,4,'Gigabyte','2026-05-27 10:52:14','ACTIVO','https://m.media-amazon.com/images/I/71+Lh5QLfyL._AC_SX300_SY300_QL70_ML2_.jpg'),(23,'RX7800XT-16G-L-OC',1,5,'PowerColor Hellhound Radeon RX 7800 XT 16GB','RX7800XT 16G-L/OC','16GB GDDR6, arquitectura RDNA 3, sistema de iluminación LED azul/azul hielo, backplate metálico de protección, alto rendimiento bruto',9200.00,12400.00,4,'PowerColor','2026-05-27 10:57:54','ACTIVO','https://i.ebayimg.com/images/g/Dj4AAOSw7Ulnx9WL/s-l960.webp'),(24,'CP-9020221-NA',6,6,'Corsair CX650M 650W 80 Plus Bronze','CX650M','650 Watts, Certificación 80 Plus Bronze, Semi-modular, Ventilador de 120mm silencioso. Ideal para ensambles de entrada y gama media.',890.00,1200.00,8,'Corsair','2026-05-27 11:08:14','ACTIVO','https://m.media-amazon.com/images/I/71-8QqxEbrL._AC_SY450_.jpg'),(25,'COREREACTOR750G-BKCUS',4,6,'XPG Core Reactor 750W 80 Plus Gold','CORE REACTOR 750W','750 Watts, Certificación 80 Plus Gold, Totalmente Modular, Capacitores 100% japoneses a 105°C, Tamaño compacto. Excelente relación calidad-precio.',1400.00,2080.00,5,'XPG','2026-05-27 11:10:21','ACTIVO','https://www.cyberpuerta.mx/img/product/XL/CP-XPG-COREREACTORIIVE750G-BKCUS-d4c367.png'),(26,'220-GT-0850-Y1',5,6,'EVGA SuperNOVA 850 GT 850W 80 Plus Gold','SuperNOVA 850 GT','850 Watts, Certificación 80 Plus Gold, Totalmente Modular, Modo Auto Eco, Ventilador FDB de 135mm. Potencia de sobra para tarjetas gráficas de gama alta.',2000.00,2200.00,5,'EVGA','2026-05-27 11:19:54','ACTIVO','https://rinconcitogamer.com/wp-content/uploads/2024/09/image_2022-08-26_090738969.png'),(27,'ROG-THOR-1000P2-GAMING',5,6,'ASUS ROG Thor 1000W Platinum II','ROG THOR 1000W PLATINUM II','1000 Watts, Certificación 80 Plus Platinum, Totalmente Modular, Pantalla OLED integrada para monitoreo de consumo en tiempo real, Disipadores ROG.',4700.00,5200.00,3,'ASUS','2026-05-27 11:22:31','ACTIVO','https://m.media-amazon.com/images/I/81UobzlR4bL._AC_SY300_SX300_QL70_ML2_.jpg'),(28,'CC-9011200-WW',6,7,'Corsair 4000D Airflow Tempered Glass Black','4000D AIRFLOW','Torre media ATX con panel frontal de acero optimizado para un alto flujo de aire, panel lateral de vidrio templado, incluye dos ventiladores de 120mm.',2000.00,2800.00,6,'Corsair','2026-05-27 11:32:57','ACTIVO','https://m.media-amazon.com/images/I/81A2RcBaIwL._AC_SY450_.jpg'),(29,'CC-H51FB-01',1,7,'NZXT H5 Flow Compact ATX Mid-Tower','H5 FLOW','Gabinete ATX compacto con panel frontal perforado para un rendimiento térmico máximo, incluye un ventilador exclusivo en la parte inferior angular para la GPU.',1300.00,1900.00,4,'NZXT','2026-05-27 11:35:26','ACTIVO','https://m.media-amazon.com/images/I/51wAv5q4adL._AC_SY450_.jpg'),(30,'O11DEW',1,7,'Lian Li O11 Dynamic EVO White','O11D EVO WHITE','Gabinete Premium de doble cámara, diseño estilo pecera con cristal templado frontal y lateral, modular y reversible. Diseñado para refrigeraciones líquidas extremas.',2870.00,3560.00,3,'Lian Li','2026-05-27 11:38:50','ACTIVO','https://www.cyberpuerta.mx/img/product/XL/CP-LIANLI-G99O11DERGBW00-0fa865.jpg'),(31,'AIR-100-ARGB-BLACK',1,7,'Montech AIR 100 ARGB Micro-ATX','AIR 100 ARGB','Gabinete formato Micro-ATX optimizado para presupuestos ajustados. Panel frontal de malla fina, panel lateral magnético de vidrio templado y 4 ventiladores ARGB de 120mm incluidos.',1780.00,2100.00,8,'Montech','2026-05-27 11:41:31','ACTIVO','https://m.media-amazon.com/images/I/71+vC54eAdS._AC_SX300_SY300_QL70_ML2_.jpg'),(32,'RR-S4KK-20PA-R1',1,8,'Cooler Master Hyper 212 Halo Black','Hyper 212 Halo Black','Disipador por aire de torre individual, ventilador MF120 Halo² ARGB, 4 tubos de calor de contacto directo, cubierta superior de aluminio negro.',430.00,800.00,10,'Cooler Master','2026-05-27 11:47:07','ACTIVO','https://m.media-amazon.com/images/I/412W0br7r6L._SX342_SY445_QL70_ML2_.jpg'),(33,'NH-D15-CHROMAX.BLACK',2,8,'Noctua NH-D15 chromax.black','NH-D15 chromax.black','Disipador de CPU por aire de grado entusiasta, diseño de doble torre, 2 ventiladores NF-A15 PWM de 140 mm. Rendimiento brutal que compite con líquidas.',2400.00,3100.00,5,'Noctua','2026-05-27 11:49:11','ACTIVO','https://m.media-amazon.com/images/I/41-e9Pb7suL._SX342_SY445_QL70_ML2_.jpg'),(34,'CW-9060068-WW',1,8,'Corsair iCUE H100i Elite Capellix XT','H100i ELITE CAPELLIX XT','Refrigeración líquida AIO de 240 mm, dos ventiladores AF120 RGB ELITE PWM, bomba con iluminación LED ultrabrillante, incluye controlador iCUE Commander CORE.',2600.00,3400.00,5,'Corsair','2026-05-27 11:50:50','ACTIVO','https://m.media-amazon.com/images/I/316yv2x2I6L._SX342_SY445_QL70_ML2_.jpg');
/*!40000 ALTER TABLE `PRODUCTO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROVEEDOR`
--

DROP TABLE IF EXISTS `PROVEEDOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PROVEEDOR` (
  `prov_id` int NOT NULL AUTO_INCREMENT,
  `prov_nombre_empresa` varchar(255) NOT NULL,
  `prov_contacto` varchar(255) DEFAULT NULL,
  `prov_telefono` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`prov_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROVEEDOR`
--

LOCK TABLES `PROVEEDOR` WRITE;
/*!40000 ALTER TABLE `PROVEEDOR` DISABLE KEYS */;
INSERT INTO `PROVEEDOR` VALUES (1,'DDTECH S.A. de C.V.','Área de Ventas Mayoreo','33-1234-5678'),(2,'ZacaTech Distribuciones','Ing. Roberto Esparza','463-100-2233'),(3,'Cyberpuerta S.A. de C.V.','División de Mayoreo','33-4777-2700'),(4,'PCEL Computadoras','Atención a Distribuidores','81-8152-4000'),(5,'Ingram Micro México','Área de Componentes y GPU','55-5263-6500'),(6,'Grupo Decme','Soporte de Inventarios','272-724-6400'),(7,'NovaTech Importaciones','Lic. Alejandro Ruiz','463-955-1122');
/*!40000 ALTER TABLE `PROVEEDOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USUARIO`
--

DROP TABLE IF EXISTS `USUARIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USUARIO` (
  `usu_id` int NOT NULL AUTO_INCREMENT,
  `usu_username` varchar(255) DEFAULT NULL,
  `usu_email` varchar(255) DEFAULT NULL,
  `usu_nombre_completo` varchar(255) DEFAULT NULL,
  `usu_password_hash` varchar(255) NOT NULL,
  `usu_fecha_nacimiento` date DEFAULT NULL,
  `usu_telefono` varchar(255) DEFAULT NULL,
  `usu_fecha_registro` datetime DEFAULT CURRENT_TIMESTAMP,
  `usu_estado` varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (`usu_id`),
  UNIQUE KEY `usu_username` (`usu_username`),
  UNIQUE KEY `usu_email` (`usu_email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USUARIO`
--

LOCK TABLES `USUARIO` WRITE;
/*!40000 ALTER TABLE `USUARIO` DISABLE KEYS */;
INSERT INTO `USUARIO` VALUES (1,'cliente_prueba','cliente@pequenoavion.com','Cliente de Prueba','123456',NULL,'4951112233','2026-05-12 18:16:00','ACTIVO'),(2,'JavierTorresAdmin1','mt8966902@gmail.com','Javier Torres (Administrador)','$2a$10$mPf2wstuqkbd2lcK6eT66uL8ZLR7UepukT.RcX4CFgL.VSbEjQ0Bm',NULL,NULL,'2026-05-13 02:51:59','ACTIVO'),(3,'Prueba Javier','42302288@uaz.edu.mx','Javier Viramontes Torres Prueba','$2a$10$SEms8FWiyDrCH6aapWJfEe4XAtsqy515Vlf5/3BnA01NJ9AOnPevW','2000-01-18','4631064282','2026-05-12 23:07:29','ACTIVO'),(4,'Rambo','rambo123@gmail.com','Ramon Soto Pérez ','$2a$10$2LhKRcihaY/7ri6OFRG04Of0T2JL.ThhLyC6DqbQP9n/pu0FcYCEK','2017-01-25','1234567890','2026-05-12 23:55:57','ACTIVO'),(5,'2','1234567890@gamil.com','2','$2a$10$PEAe/NR1VY30qjfWkoeCEui9EE/vhVwSqTLQnDjh.oeHtvQNw78aG','2000-09-09','123456789','2026-05-27 13:10:36','ACTIVO');
/*!40000 ALTER TABLE `USUARIO` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-27 22:15:57
