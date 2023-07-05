/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : bookstore

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 05/07/2023 22:04:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `bname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `price` decimal(5, 1) NULL DEFAULT NULL,
  `author` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `image` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `del` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`bid`) USING BTREE,
  INDEX `cid`(`cid` ASC) USING BTREE,
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', 'Java编程思想（第4版）', 75.6, 'qdmmy6', 'book_img/9317290-1_l.jpg', '1', 0);
INSERT INTO `book` VALUES ('2', 'Java核心技术卷1', 68.5, 'qdmmy6', 'book_img/20285763-1_l.jpg', '1', 0);
INSERT INTO `book` VALUES ('3', 'Java就业培训教程', 39.9, '张孝祥', 'book_img/8758723-1_l.jpg', '1', 0);
INSERT INTO `book` VALUES ('4', 'Head First java', 47.5, '（美）塞若', 'book_img/9265169-1_l.jpg', '1', 0);
INSERT INTO `book` VALUES ('5', 'JavaWeb开发详解', 83.3, '孙鑫', 'book_img/22788412-1_l.jpg', '2', 0);
INSERT INTO `book` VALUES ('6', 'Struts2深入详解', 63.2, '孙鑫', 'book_img/20385925-1_l.jpg', '2', 0);
INSERT INTO `book` VALUES ('7', '精通Hibernate', 30.0, '孙卫琴', 'book_img/8991366-1_l.jpg', '2', 0);
INSERT INTO `book` VALUES ('8', '精通Spring2.x', 63.2, '陈华雄', 'book_img/20029394-1_l.jpg', '2', 0);
INSERT INTO `book` VALUES ('9', 'Javascript权威指南', 93.6, '（美）弗兰纳根', 'book_img/22722790-1_l.jpg', '3', 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `cid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', 'JavaSE');
INSERT INTO `category` VALUES ('2', 'JavaEE');
INSERT INTO `category` VALUES ('3', 'Javascript');

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem`  (
  `iid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `count` int NULL DEFAULT NULL,
  `subtotal` decimal(10, 2) NULL DEFAULT NULL,
  `oid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `bid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`iid`) USING BTREE,
  INDEX `oid`(`oid` ASC) USING BTREE,
  INDEX `bid`(`bid` ASC) USING BTREE,
  CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('0E840453570F482480721B62B78DDE26', 1, 83.30, '3D345E182D8D494A9C526C20612F5E3B', '5');
INSERT INTO `orderitem` VALUES ('310F2BE152394FC8879D492E28214B69', 1, 68.50, '93F041BF9DED43548B37A162ED415A3F', '2');
INSERT INTO `orderitem` VALUES ('786FABE4CF614906AE402A117C89F839', 1, 75.60, '2854DF7CA91B45DFA9622376B64B5837', '1');
INSERT INTO `orderitem` VALUES ('853AD8AD8FB841DF8D258333696EF8D6', 1, 75.60, 'EAA383D05C5445249550F81A719BE18C', '1');
INSERT INTO `orderitem` VALUES ('9963F9EF4C0C47538B9C3AED2ED94C0C', 1, 93.60, '76D3E796BC2C4DCEB2AC85B1C3572A9A', '9');
INSERT INTO `orderitem` VALUES ('A65DE0F613E24A49A0FD8D0E157C71B0', 1, 63.20, '6149644F5CAC4D059C326E0FB81C929D', '6');
INSERT INTO `orderitem` VALUES ('AEEEC81A07AB480B9012E30FD388D112', 1, 69.00, '16BB7DB8B4204E4C98A6C174EC0F3F4B', '2');
INSERT INTO `orderitem` VALUES ('D1E0013E9C6F4613A16331FC65A7E604', 1, 75.60, 'A12B62EF99C54CFBB4C5F5B489A16844', '1');
INSERT INTO `orderitem` VALUES ('DEB68424835A4BA281BC0671815093FF', 1, 75.60, 'A2A6A9356F3447EF8779C537B8D9DE23', '1');
INSERT INTO `orderitem` VALUES ('E83B26EBC97043699F57ACED5DBB9690', 1, 63.00, '733010E46D3A49FCBFECEF46569A9C8E', '6');
INSERT INTO `orderitem` VALUES ('EB15460840FD45D5BE077D95A310A64A', 1, 83.00, 'C898CCA18B1C4D0A9AA714E57D1394D0', '5');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `oid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ordertime` datetime NULL DEFAULT NULL,
  `total` decimal(10, 2) NULL DEFAULT NULL,
  `state` smallint NULL DEFAULT NULL,
  `uid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`oid`) USING BTREE,
  INDEX `uid`(`uid` ASC) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `tb_user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('16BB7DB8B4204E4C98A6C174EC0F3F4B', '2023-07-03 23:03:38', 68.50, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('2854DF7CA91B45DFA9622376B64B5837', '2023-07-04 20:23:14', 75.60, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('3D345E182D8D494A9C526C20612F5E3B', '2023-07-04 21:07:30', 83.30, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('6149644F5CAC4D059C326E0FB81C929D', '2023-07-04 20:00:07', 63.20, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('733010E46D3A49FCBFECEF46569A9C8E', '2023-07-03 23:01:50', 63.20, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('76D3E796BC2C4DCEB2AC85B1C3572A9A', '2023-07-04 20:35:44', 93.60, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('93F041BF9DED43548B37A162ED415A3F', '2023-07-04 21:10:28', 68.50, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('A12B62EF99C54CFBB4C5F5B489A16844', '2023-07-05 21:32:46', 75.60, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('A2A6A9356F3447EF8779C537B8D9DE23', '2023-07-04 21:02:05', 75.60, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('C898CCA18B1C4D0A9AA714E57D1394D0', '2023-07-03 23:09:13', 83.30, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);
INSERT INTO `orders` VALUES ('EAA383D05C5445249550F81A719BE18C', '2023-07-04 20:48:52', 75.60, 1, 'A69CEFB0439F4E27B79B2DD9730D8E57', NULL);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `uid` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `code` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('A69CEFB0439F4E27B79B2DD9730D8E57', 'banma123', '123', '464422580@qq.com', 'F10DBB57936547B1BF2A502889C373E6DEAA3ECFFD7D4E0F8AA6320E84AED7C5', 1);

SET FOREIGN_KEY_CHECKS = 1;
