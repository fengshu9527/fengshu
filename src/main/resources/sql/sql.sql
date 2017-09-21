#############################################################
#                  MODEL       : gdi                        #
#                  AUTHOR      : leiqifeng                  #
#############################################################
SET NAMES utf8;

###########  创建数据库 ##############
CREATE DATABASE IF NOT EXISTS gdi
  CHARACTER SET utf8;

############### 建表 ################
USE gdi;

###########################枚举表##############################
#项目表

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL,
  `name` varchar(10) DEFAULT NULL,
  `introduce` text,
  `status` tinyint(4) DEFAULT NULL,
  `category` tinyint(4) DEFAULT NULL,
  `start_time` varchar(100) DEFAULT NULL,
  `end_time` varchar(100) DEFAULT NULL,
  `token_total` int(10) DEFAULT NULL,
  `official_website_url` text,
  `white_paperlink` text,
  `accept_currency` varchar(100) DEFAULT NULL,
  `other_currency` varchar(20) DEFAULT NULL,
  `minimum_target` int(10) DEFAULT NULL,
  `highest_target` int(10) DEFAULT NULL,
  `investment_threshold` int(10) DEFAULT NULL,
  `exchange_rate` int(10) DEFAULT NULL,
  `allocation_plan` varchar(50) DEFAULT NULL,
  `progress` int(10) DEFAULT NULL,
  `token_name` varchar(50) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;