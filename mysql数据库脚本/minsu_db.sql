/*
Navicat MySQL Data Transfer

Source Server         : mysql5.6
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : minsu_db

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2020-06-02 17:10:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_area`
-- ----------------------------
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area` (
  `areaId` int(11) NOT NULL AUTO_INCREMENT COMMENT '地区id',
  `areanName` varchar(20) NOT NULL COMMENT '地区名称',
  PRIMARY KEY (`areaId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_area
-- ----------------------------
INSERT INTO `t_area` VALUES ('1', '成华区');
INSERT INTO `t_area` VALUES ('2', '锦江区');
INSERT INTO `t_area` VALUES ('3', '金牛区');

-- ----------------------------
-- Table structure for `t_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `commentId` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `minsuObj` int(11) NOT NULL COMMENT '被评民宿',
  `content` varchar(1000) NOT NULL COMMENT '评论内容',
  `userObj` varchar(30) NOT NULL COMMENT '评论用户',
  `commentTime` varchar(20) DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`commentId`),
  KEY `minsuObj` (`minsuObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_comment_ibfk_1` FOREIGN KEY (`minsuObj`) REFERENCES `t_minsu` (`minsuId`),
  CONSTRAINT `t_comment_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES ('1', '1', '非常不错的，很方便', '13910831234', '2020-05-03 14:11:12');
INSERT INTO `t_comment` VALUES ('2', '2', '很惬意的房子！', '13688886666', '2020-06-01 21:01:50');

-- ----------------------------
-- Table structure for `t_minsu`
-- ----------------------------
DROP TABLE IF EXISTS `t_minsu`;
CREATE TABLE `t_minsu` (
  `minsuId` int(11) NOT NULL AUTO_INCREMENT COMMENT '民宿id',
  `areaObj` int(11) NOT NULL COMMENT '所在地区',
  `minsuName` varchar(50) NOT NULL COMMENT '民宿名称',
  `minsuPhoto` varchar(60) NOT NULL COMMENT '民宿照片',
  `price` float NOT NULL COMMENT '每日价格',
  `minsuDesc` varchar(800) NOT NULL COMMENT '民宿介绍',
  `minsuMemo` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `ownerObj` varchar(30) NOT NULL COMMENT '民宿主人',
  `addTime` varchar(20) DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`minsuId`),
  KEY `areaObj` (`areaObj`),
  KEY `ownerObj` (`ownerObj`),
  CONSTRAINT `t_minsu_ibfk_1` FOREIGN KEY (`areaObj`) REFERENCES `t_area` (`areaId`),
  CONSTRAINT `t_minsu_ibfk_2` FOREIGN KEY (`ownerObj`) REFERENCES `t_owner` (`ownerUserName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_minsu
-- ----------------------------
INSERT INTO `t_minsu` VALUES ('1', '2', '春熙路近地铁口萧邦民宿', 'upload/4d698d95-7f49-463d-acd0-339e14cce97a.jpg', '238', '套二民宿，配套设施有热水，淋浴，无线网络，空调，电视，电梯，洗衣机，冰箱', '非常抢手的，赶快行动吧', 'sz001', '2020-05-03 00:02:18');
INSERT INTO `t_minsu` VALUES ('2', '1', '少女心大床房带阳台', 'upload/bf3cfd4a-62f4-453b-84d7-2b40344e4985.jpg', '120', '精装大套一50平米民宿，带阳台ins装修风格、房间干净整洁、很安静，配有一张1.8米的大床，一个帐篷和床垫，床品、浴巾、毛巾、一次性马桶套、拖鞋、一次性洗漱牙膏牙具、梳子、香皂、抽纸、卷纸、液晶电视机、宽带、空调、24小时热水是妥妥的。如果有其他需要，可随时联系房东，尽量给亲提供更优质的服务。', '一个喜欢自驾旅行热爱生活的宝妈,喜欢带着宝宝一起翻山越岭､漂洋过海看不同风景,体验不同的民俗风情｡', 'sz001', '2020-06-01 20:20:46');
INSERT INTO `t_minsu` VALUES ('3', '1', '北欧小清新两居', 'upload/3d75edbb-31ab-44df-9287-43de3ddf21f7.jpg', '198', '房间配置:\r\n1.一楼:客厅、餐厅、厨房、 沙发、55寸高清电视、2匹冷暖空调\r\n二楼:主卧、次卧、茶几阳台、花架、主卧落地窗、两张大床、两个1.5匹冷暖空调、一张网红梳妆台、一套观景及休闲桌椅、密码门锁、自助入住\r\n2.配有纯棉超柔软枕芯和被褥(深睡、静音),\r\n希望带给你舒适的睡眠\r\n3.卫浴、吹风机、全套洗漱用品(洗发水、沐浴露、牙刷、牙膏、梳子、4套毛巾加4套浴巾)、热水器24小时供应热水\r\n4.免费高速无线wifi\r\n5.厨房配有冰箱、锅，铲、热水壶、咖啡机、电饭煲等、厨房用具及调味品\r\n6.晾衣架、全自动洗衣机及储衣柜', '卧室、厨房全景落地窗，观景的不二选择；整套房源总面积98平方米', 'sz002', '2020-06-02 16:08:18');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(800) NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '民宿平台成立了', '同志们，关注微信小程序，随时预定心爱的民宿', '2020-05-25 00:03:15');
INSERT INTO `t_notice` VALUES ('2', '庆祝六一，优惠多多', '凡在六一儿童节当日预定的客户，可以享受98折优惠', '2020-05-28 17:08:25');

-- ----------------------------
-- Table structure for `t_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_orderinfo`;
CREATE TABLE `t_orderinfo` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `minsuObj` int(11) NOT NULL COMMENT '预订民宿',
  `userObj` varchar(30) NOT NULL COMMENT '预订用户',
  `liveDate` varchar(20) DEFAULT NULL COMMENT '入住日期',
  `orderDays` int(11) NOT NULL COMMENT '入住天数',
  `totalPrice` float NOT NULL COMMENT '订单总价',
  `orderMemo` varchar(500) DEFAULT NULL COMMENT '订单备注',
  `orderState` varchar(20) NOT NULL COMMENT '订单状态',
  `orderTime` varchar(20) DEFAULT NULL COMMENT '预订时间',
  PRIMARY KEY (`orderId`),
  KEY `minsuObj` (`minsuObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_orderinfo_ibfk_1` FOREIGN KEY (`minsuObj`) REFERENCES `t_minsu` (`minsuId`),
  CONSTRAINT `t_orderinfo_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_orderinfo
-- ----------------------------
INSERT INTO `t_orderinfo` VALUES ('1', '1', '13910831234', '2020-05-04', '3', '714', '看起来不错', '已完成', '2020-05-03 00:02:42');
INSERT INTO `t_orderinfo` VALUES ('2', '2', '13688886666', '2020-06-03', '3', '360', '看起来很温馨的样子！', '已完成', '2020-06-01 20:08:03');

-- ----------------------------
-- Table structure for `t_owner`
-- ----------------------------
DROP TABLE IF EXISTS `t_owner`;
CREATE TABLE `t_owner` (
  `ownerUserName` varchar(30) NOT NULL COMMENT 'ownerUserName',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '宿主姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `ownerPhoto` varchar(60) NOT NULL COMMENT '宿主照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `address` varchar(80) DEFAULT NULL COMMENT '家庭地址',
  `regTime` varchar(20) DEFAULT NULL COMMENT '注册时间',
  `shzt` varchar(20) NOT NULL COMMENT '审核状态',
  PRIMARY KEY (`ownerUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_owner
-- ----------------------------
INSERT INTO `t_owner` VALUES ('sz001', '123', '王小翠', '女', '2020-05-12', 'upload/de7a186d-320b-4622-be64-20c0cff13cbc.jpg', '13080093412', '四川成都武侯区', '2020-05-03 00:01:22', '审核通过');
INSERT INTO `t_owner` VALUES ('sz002', '123', '张晓霞', '女', '2020-06-01', 'upload/c8bcb559-862e-4767-a61e-bc8b5279a8fb.jpg', '15298083423', '四川成都万年场', '2020-06-01 22:59:05', '审核通过');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) DEFAULT NULL COMMENT '家庭地址',
  `regTime` varchar(20) DEFAULT NULL COMMENT '注册时间',
  `openid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('13688886666', '--', '鼠鼠', '男', '2020-01-01', 'upload/bf2454ed3ec34c6bad09d75d1c5cbf27', '--', '--', '--', '2020-06-01 20:04:47', 'oM7Mu5XyeVJSc8roaUCRlcz_IP9k');
INSERT INTO `t_userinfo` VALUES ('13910831234', '123', '张晓芳', '女', '2020-05-12', 'upload/c5eba7f0-09f2-4d45-ac6e-3716a6dc8e82.jpg', '13508129834', 'xiaofang@126.com', '四川成都红星路', '2020-05-03 00:00:53', null);
