-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: ai_user
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_chat_session`
--
create databace ai_user;
use ai_user;
DROP TABLE IF EXISTS `tb_chat_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_chat_session` (
  `session_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_chat_session`
--

LOCK TABLES `tb_chat_session` WRITE;
/*!40000 ALTER TABLE `tb_chat_session` DISABLE KEYS */;
INSERT INTO `tb_chat_session` VALUES (2,8,'访问ai','2025-05-03 11:23:14','2025-05-03 11:23:14',0),(15,8,'返回所有免费的api','2025-05-09 10:41:23','2025-05-09 10:41:23',0);
/*!40000 ALTER TABLE `tb_chat_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_message`
--

DROP TABLE IF EXISTS `tb_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_message` (
  `message_id` bigint NOT NULL AUTO_INCREMENT,
  `session_id` bigint NOT NULL,
  `message` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_user` tinyint(1) NOT NULL,
  `snow_id` bigint NOT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_message`
--

LOCK TABLES `tb_message` WRITE;
/*!40000 ALTER TABLE `tb_message` DISABLE KEYS */;
INSERT INTO `tb_message` VALUES (11,2,'我叫碎梦',1,575635029967765504),(12,2,'你好，碎梦！这个名字很有意境，给人一种梦幻般的感觉。如果你有任何问题或需要帮助，请随时告诉我，我会尽我所能为你提供帮助。你想要聊些什么呢？',0,575635029967765504),(13,2,'我是谁',1,575635067884273664),(14,2,'您好，碎梦！在这里我不是所指您的身份，我是一个人工智能助手，可以帮您解答问题和提供信息。如果您有其他想要了解的事情，随时可以告诉我！',0,575635067884273664),(15,2,'爱上一个人的表现是什么',1,575670374390300672),(16,2,'爱上一个人可能会有多种表现，每个人的体验和表达方式都有所不同，但以下是一些常见的迹象：\n\n1. **思念**：不在一起时，你会频繁地想念对方，对方的影子总是出现在你脑海中。\n\n2. **关心**：你会对对方的生活、情感和身体健康非常关心，愿意为对方付出时间和精力。\n\n3. **欣赏**：你会欣赏对方的优点，即使是一些小细节，而在你眼中这些都变得非常有魅力。\n\n4. **紧张**：在对方面前，你可能会感到紧张或不安，担心自己的言行是否会被对方喜欢。\n\n5. **羡慕**：当看到对方和其他异性交流时，你可能会感到嫉妒或不安。\n\n6. **愿意妥协**：为了对方，你可能会愿意做出一些妥协和牺牲，以维持和对方的关系。\n\n7. **分享**：你愿意与对方分享你的生活、想法和感受，渴望对方了解真实的你。\n\n8. **保护**：你会有一种保护对方的冲动，不希望对方受到伤害。\n\n9. **规划未来**：你开始想象和规划与对方一起的未来，希望你们的生活能够交织在一起。\n\n10. **心动**：当对方触碰你或者你们有亲密接触时，你会感到心跳加速，有一种难以言喻的愉悦感。\n\n11. **沟通**：你会更加积极地与对方沟通，希望通过交流来加深你们的理解和关系。\n\n12. **支持**：你会在对方遇到困难时给予支持和鼓励，成为对方坚实的后盾。\n\n每个人表达爱意的方式不同，这些只是一些普遍的表现。如果你对某人有以上的感受，可能意味着你已经爱上他们了。',0,575670374390300672),(19,15,'返回所有免费的api',1,575874932529954816),(20,15,'提供免费API服务的网站和组织是很多的，它们提供各种不同类型的数据和服务，支持开发的应用程序可以涵盖天气、地理位置、社交媒体、新闻、图像识别等多种领域。以下是一些提供自由API的平台：\n\n1. **公共APIS** - 提供各种各样的自由API服务，如货币转换、天气等。\n   网址：[https://www.publicapis.com/](https://www.publicapis.com/)\n\n2. **Mashape** - 现已更名为Kong，是API经济的数据交换中心，包含许多行业的API。\n   网址：[https://konghq.com/](https://konghq.com/)\n\n3. **ProgrammableWeb** - 是世界上最大的Web API资源。\n   网址：[https://www.programmableweb.com/](https://www.programmableweb.com/)\n\n4. **RapidAPI** - 将开发者和API提供者聚集在一起，有成千上万的API和服务。\n   网址：[https://rapidapi.com/](https://rapidapi.com/)\n\n5. **IFTTT** - 提供一个网络自动化平台，可创建多个API的交互。\n   网址：[https://ifttt.com/](https://ifttt.com/)\n\n6. **API List** - 一个API目录，可以在其中找到各种自由API。\n   网址：[https://apilist.fun/](https://apilist.fun/)\n\n7. **Nationalize.io** - 提供国家级别的数据如国家代码、货币、语言等。\n   网址：[https://nationalize.io/](https://nationalize.io/)\n\n8. **开发者工具箱** - 各种实用工具的集合，比如单元转换。\n   网址：[https://developerstoolbox.com/](https://developerstoolbox.com/)\n\n9. **JSONPlaceholder** - 提供一个简单的在线REST API，用于测试和原型设计。\n   网址：[https://jsonplaceholder.typicode.com/](https://jsonplaceholder.typicode.com/)\n\n10. **Postman-run.mocky** - 提供一个沙箱环境用于HTTP请求测试。\n    网址：[https://www.mocky.io/](https://www.mocky.io/)\n\n这些平台均提供不同类别和用途的API。具体选择时，请基于你的实际需要来决定使用哪些API，并且在使用前也要检查其使用条款和数据传输安全。此外某些API可能提供的基础服务是免费的，但更高级的功能可能需要付费。',0,575874932529954816);
/*!40000 ALTER TABLE `tb_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (8,'gongjie','$2a$10$dsLafZZxGyV9HWn7kysKF.xNT1JRjTCABtRFE7MT6rLS1uqhKM6NK','2025-05-03 11:23:14','2025-05-03 11:23:14');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'ai_user'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-09 11:32:13
