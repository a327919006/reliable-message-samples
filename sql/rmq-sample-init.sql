/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.21 : Database - reliable-message-sample
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`reliable-message-sample` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `reliable-message-sample`;

/*Table structure for table `t_account` */

DROP TABLE IF EXISTS `t_account`;

CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
  `money` decimal(10,2) DEFAULT NULL COMMENT '账户余额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_account` */

insert  into `t_account`(`id`,`money`,`create_time`,`update_time`) values (1,'1.00','2019-03-31 00:00:00','2019-04-01 00:00:00');

/*Table structure for table `t_pay_order` */

DROP TABLE IF EXISTS `t_pay_order`;

CREATE TABLE `t_pay_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '支付订单ID',
  `business_order_id` int(11) DEFAULT NULL COMMENT '业务系统订单ID',
  `money` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `status` tinyint(2) DEFAULT '0' COMMENT '支付状态 0未支付 1已支付',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20010 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `t_pay_order` */

insert  into `t_pay_order`(`id`,`business_order_id`,`money`,`status`,`create_time`,`update_time`) values (20000,10000,'1.00',1,'2019-04-01 00:00:00','2019-04-01 00:00:00');

/*Table structure for table `t_recharge_order` */

DROP TABLE IF EXISTS `t_recharge_order`;

CREATE TABLE `t_recharge_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '充值记录ID',
  `account_id` int(11) DEFAULT NULL COMMENT '账户ID',
  `pay_order_id` int(11) DEFAULT NULL COMMENT '支付流水ID',
  `money` decimal(10,2) DEFAULT NULL COMMENT '充值金额',
  `status` tinyint(2) DEFAULT '0' COMMENT '0待支付 1已支付',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10010 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `t_recharge_order` */

insert  into `t_recharge_order`(`id`,`account_id`,`pay_order_id`,`money`,`status`,`create_time`,`update_time`) values (10000,1,20000,'1.00',1,'2019-04-01 00:00:00','2019-04-01 00:00:00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
