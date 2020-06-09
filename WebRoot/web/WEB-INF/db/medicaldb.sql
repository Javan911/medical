/*
 Navicat Premium Data Transfer

 Source Server         : sun
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : medicaldb

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 08/06/2019 10:51:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s201
-- ----------------------------
DROP TABLE IF EXISTS `s201`;
CREATE TABLE `s201`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `itemcode` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `itemname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s201
-- ----------------------------
INSERT INTO `s201` VALUES (1, '50', '县', '02');
INSERT INTO `s201` VALUES (2, '62', '镇', '02');
INSERT INTO `s201` VALUES (3, '63', '乡', '02');
INSERT INTO `s201` VALUES (4, '1', '村卫生室', '06');
INSERT INTO `s201` VALUES (5, '2', '乡镇卫生院', '06');
INSERT INTO `s201` VALUES (6, '3', '县级医疗机构', '06');
INSERT INTO `s201` VALUES (7, '1', '综合定点', '04');
INSERT INTO `s201` VALUES (8, '2', '门诊定点', '04');
INSERT INTO `s201` VALUES (9, '3', '住院定点', '04');
INSERT INTO `s201` VALUES (10, '10', '内资', '01');
INSERT INTO `s201` VALUES (11, '11', '国有全资', '01');
INSERT INTO `s201` VALUES (12, '12', '集体全资', '01');
INSERT INTO `s201` VALUES (13, 'A', '医院', '03');
INSERT INTO `s201` VALUES (14, 'C', '卫生院', '03');
INSERT INTO `s201` VALUES (15, 'A100', '综合医院', '0301');
INSERT INTO `s201` VALUES (16, 'C220', '乡镇卫生院', '0301');
INSERT INTO `s201` VALUES (17, 'C210', '乡镇中心卫生院', '0301');

-- ----------------------------
-- Table structure for t_area
-- ----------------------------
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area`  (
  `areacode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '行政区域编号',
  `areapcode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上一级行政区域编号',
  `areaname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政区域名称',
  `grade` int(3) NULL DEFAULT NULL COMMENT '行政级别',
  PRIMARY KEY (`areacode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_area
-- ----------------------------
INSERT INTO `t_area` VALUES ('450421', '0', '苍梧县', 1);
INSERT INTO `t_area` VALUES ('45042101', '450421', '龙圩镇', 2);
INSERT INTO `t_area` VALUES ('4504210101', '45042101', '恩义村', 3);
INSERT INTO `t_area` VALUES ('450421010101', '4504210101', '多一组', 4);
INSERT INTO `t_area` VALUES ('450421010102', '4504210101', '组', 4);
INSERT INTO `t_area` VALUES ('450421010103', '4504210101', '多三组', 4);
INSERT INTO `t_area` VALUES ('4504210102', '45042101', '村', 3);
INSERT INTO `t_area` VALUES ('450421010201', '4504210102', '组', 4);
INSERT INTO `t_area` VALUES ('45042102', '450421', '新地镇', 2);
INSERT INTO `t_area` VALUES ('4504210201', '45042102', '村', 3);
INSERT INTO `t_area` VALUES ('450421020101', '4504210201', '组', 4);
INSERT INTO `t_area` VALUES ('4504210202', '45042102', '村', 3);
INSERT INTO `t_area` VALUES ('450421020201', '4504210202', '组', 4);
INSERT INTO `t_area` VALUES ('45042103', '450421', '沙头镇', 2);
INSERT INTO `t_area` VALUES ('4504210301', '45042103', '村', 3);
INSERT INTO `t_area` VALUES ('450421030101', '4504210301', '组', 4);
INSERT INTO `t_area` VALUES ('45042104', '450421', '石桥镇', 2);
INSERT INTO `t_area` VALUES ('4504210401', '45042104', '村', 3);
INSERT INTO `t_area` VALUES ('450421040101', '4504210401', '组', 4);

-- ----------------------------
-- Table structure for t_disease_card
-- ----------------------------
DROP TABLE IF EXISTS `t_disease_card`;
CREATE TABLE `t_disease_card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `diseaseCard` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '慢性病证号',
  `nongheCard` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '农合证号',
  `idCard` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '身份证号',
  `diseaseCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '疾病编码',
  `attachment` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '证明附件',
  `startTime` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_disease_card
-- ----------------------------
INSERT INTO `t_disease_card` VALUES (1, '123456', '450421010101004901', '460033199811034831', 'F2901', '暂无', '2019-05-21 11:29:38', '2019-05-31 11:29:41');

-- ----------------------------
-- Table structure for t_disease_expense
-- ----------------------------
DROP TABLE IF EXISTS `t_disease_expense`;
CREATE TABLE `t_disease_expense`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `diseaseCard` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '慢性病证号',
  `diseaseCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '疾病编码',
  `hospitalName` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '医院名称',
  `medicalCost` double(20, 0) NULL DEFAULT NULL COMMENT '医疗总费用',
  `invoiceNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '医院发票号',
  `clinicTime` datetime(0) NULL DEFAULT NULL COMMENT '就诊时间',
  `isNative` int(3) NULL DEFAULT NULL COMMENT '报销地点是否本地（1：是，0：否）',
  `expenseAccount` double(20, 0) NULL DEFAULT NULL COMMENT '报销金额',
  `expenseTime` datetime(0) NULL DEFAULT NULL COMMENT '报销时间',
  `organization` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '报销机构',
  `auditStatus` int(3) NULL DEFAULT NULL COMMENT '审核状态（1：通过，0：未通过）',
  `remittanceStatus` int(3) NULL DEFAULT NULL COMMENT '汇款状态（1：已汇款，0：未汇款）',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_disease_type
-- ----------------------------
DROP TABLE IF EXISTS `t_disease_type`;
CREATE TABLE `t_disease_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `diseaseCode` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '疾病编码',
  `pinyinCode` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '拼音码',
  `diseaseName` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '疾病名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_disease_type
-- ----------------------------
INSERT INTO `t_disease_type` VALUES (1, 'F2901', 'jsb', '精神病');
INSERT INTO `t_disease_type` VALUES (2, 'K74.151', 'gyh', '肝硬化');
INSERT INTO `t_disease_type` VALUES (3, 'I1005', 'gxys', '高血压III');
INSERT INTO `t_disease_type` VALUES (4, 'G2002', 'pjssb', '帕金森氏病');
INSERT INTO `t_disease_type` VALUES (5, 'M32.901', 'xtxhblc', '系统性红斑狼疮NOS');
INSERT INTO `t_disease_type` VALUES (6, 'N04.903', 'sbzhz', '肾病综合征');
INSERT INTO `t_disease_type` VALUES (7, 'M06.991', 'lfsxgjy', '类风湿性关节炎NOS');
INSERT INTO `t_disease_type` VALUES (8, 'C00-C97>', 'exzl', '恶性肿瘤');
INSERT INTO `t_disease_type` VALUES (9, 'A15.001', 'fjhxwjjzs', '肺结核，显微镜检证实');
INSERT INTO `t_disease_type` VALUES (10, 'I1004', 'gxye', '高血压II');
INSERT INTO `t_disease_type` VALUES (11, 'I09.901', 'fsxxzb', '风湿性心脏病(RHD)');
INSERT INTO `t_disease_type` VALUES (12, 'I25.101', 'gxb', '冠心病');
INSERT INTO `t_disease_type` VALUES (13, 'N03.903', 'mxsy', '慢性肾炎');
INSERT INTO `t_disease_type` VALUES (14, 'E14.901', 'tnb', '糖尿病');
INSERT INTO `t_disease_type` VALUES (15, 'E05.901', 'jzxjnkj', '甲状腺机能亢进');
INSERT INTO `t_disease_type` VALUES (16, 'D61.905', 'zszaxpx', '再生障碍性贫血NOS');
INSERT INTO `t_disease_type` VALUES (17, 'D56.001', 'xdzhpx', 'α型地中海贫血');
INSERT INTO `t_disease_type` VALUES (18, 'D6602', 'xyb', '血友病');
INSERT INTO `t_disease_type` VALUES (19, 'zmpsb', 'zmpsb', '终末期肾病（尿毒症）');

-- ----------------------------
-- Table structure for t_family_archives
-- ----------------------------
DROP TABLE IF EXISTS `t_family_archives`;
CREATE TABLE `t_family_archives`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `countyCode` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '县级编码',
  `townCode` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '乡镇编码',
  `villageCode` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '村编码',
  `groupCode` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '组编码',
  `familyCode` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '家庭编号',
  `houseHolder` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '户主',
  `familySize` int(10) NULL DEFAULT NULL COMMENT '家庭人口数',
  `agriculturalNum` int(10) NULL DEFAULT NULL COMMENT '农业人口数',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建档案时间',
  `registrar` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '登记员',
  `householdType` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '户属性',
  `isRural` int(3) NULL DEFAULT NULL COMMENT '户口性质（1：农业，0：非农业）',
  `status` int(3) NULL DEFAULT NULL COMMENT '状态（1：启用，0：禁用）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_family_archives
-- ----------------------------
INSERT INTO `t_family_archives` VALUES (1, '450421', '45042101', '4504210101', '450421010101', '4504210101010048', '钟海清', NULL, 3, '苍梧县龙圩镇恩义村多一组', '2019-04-27 23:03:46', '孙某人', '一般户', 1, 1, '备注信息1');
INSERT INTO `t_family_archives` VALUES (2, '450421', '45042101', '4504210101', '450421010101', '4504210101010049', '孙艺', NULL, 2, '苍梧县龙圩镇恩义村多一组', '2019-05-05 00:00:00', '超级管理员', '一般户', 1, 1, '啦啦啦');

-- ----------------------------
-- Table structure for t_institution
-- ----------------------------
DROP TABLE IF EXISTS `t_institution`;
CREATE TABLE `t_institution`  (
  `areacode` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '所属行政编码',
  `institutionCode` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '农合机构编码',
  `institutionName` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '农合机构名称',
  `grade` int(3) NULL DEFAULT NULL COMMENT '级别',
  PRIMARY KEY (`areacode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_institution
-- ----------------------------
INSERT INTO `t_institution` VALUES ('450421', '667022793', '苍梧县新农合管理中心', 1);
INSERT INTO `t_institution` VALUES ('45042101', '667022793', '龙圩镇合管办', 2);
INSERT INTO `t_institution` VALUES ('45042102', '667022793', '新地镇合管办', 2);

-- ----------------------------
-- Table structure for t_medical
-- ----------------------------
DROP TABLE IF EXISTS `t_medical`;
CREATE TABLE `t_medical`  (
  `jgbm` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `zzjgbm` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `jgmc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dqbm` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `areacode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `lsgx` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `jgjb` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sbddlx` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pzddlx` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ssjjlx` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wsjgdl` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wsjgxl` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zgdw` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `kysj` datetime(0) NULL DEFAULT NULL,
  `frdb` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zczj` decimal(10, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`jgbm`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_medical
-- ----------------------------
INSERT INTO `t_medical` VALUES ('1', '1', '1', '1', '1', '50', '1', '1', '1', '10', 'A', 'A100', '1', '2019-04-22 00:00:00', '1', 1);
INSERT INTO `t_medical` VALUES ('2', '3', '3', '3', '3', '50', '1', '1', '1', '10', 'A', 'A100', '3', '2019-04-26 00:00:00', '3', 33333);

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `menuid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `menupid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menuname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`menuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('00401', '004', '微信公众号', '/test/weixinServlet', 2);
INSERT INTO `t_menu` VALUES ('00402', '004', '手机接口', '/test/mobileServlet', 2);
INSERT INTO `t_menu` VALUES ('F01', '0', '系统管理', '/system', 1);
INSERT INTO `t_menu` VALUES ('F0101', 'F01', '用户管理', '/system/UserServlet', 2);
INSERT INTO `t_menu` VALUES ('F010101', 'F0101', '添加用户', '/system/UserServlet?m=add', 3);
INSERT INTO `t_menu` VALUES ('F010102', 'F0101', '修改用户', '/system/UserServlet?m=edit', 3);
INSERT INTO `t_menu` VALUES ('F010103', 'F0101', '删除用户', '/system/UserServlet?m=del', 3);
INSERT INTO `t_menu` VALUES ('F0102', 'F01', '角色管理', '/system/RoleServlet', 2);
INSERT INTO `t_menu` VALUES ('F010201', 'F0102', '添加角色', '/system/RoleServlet?m=add', 3);
INSERT INTO `t_menu` VALUES ('F010202', 'F0102', '删除角色', '/system/RoleServlet?m=del', 3);
INSERT INTO `t_menu` VALUES ('F010203', 'F0102', '修改角色', '/system/RoleServlet?m=edit', 3);
INSERT INTO `t_menu` VALUES ('F0103', 'F01', '菜单管理', '/system/MenuServlet', 2);
INSERT INTO `t_menu` VALUES ('F010301', 'F0103', '新增菜单', '/system/MenuServlet?m=add', 3);
INSERT INTO `t_menu` VALUES ('F010302', 'F0103', '删除菜单', '/system/MenuServlet?m=del', 3);
INSERT INTO `t_menu` VALUES ('F010303', 'F0103', '修改菜单', '/system/MenuServlet?m=edit', 3);
INSERT INTO `t_menu` VALUES ('F0104', 'F01', '行政区域管理', '/system/AreaServlet', 2);
INSERT INTO `t_menu` VALUES ('F010401', 'F0104', '新增行政区域', '/system/AreaServlet?m=add', 3);
INSERT INTO `t_menu` VALUES ('F010402', 'F0104', '导入行政区域信息', '/system/AreaServlet?m=bachInput', 3);
INSERT INTO `t_menu` VALUES ('F0105', 'F01', '农合机构管理', '/system/InstitutionServlet', 2);
INSERT INTO `t_menu` VALUES ('F010501', 'F0105', '新增农合点', '/system/InstitutionServlet?m=add', 3);
INSERT INTO `t_menu` VALUES ('F0106', 'F01', '医疗机构管理', '/system/MedicalServlet', 2);
INSERT INTO `t_menu` VALUES ('F010601', 'F0106', '新增农合定点', '/system/MedicalServlet?m=add', 3);
INSERT INTO `t_menu` VALUES ('F010602', 'F0106', '删除农合定点', '/system/MedicalServlet?m=del', 3);
INSERT INTO `t_menu` VALUES ('F010603', 'F0106', '修改农合定点', '/system/MedicalServlet?m=edit', 3);
INSERT INTO `t_menu` VALUES ('F02', '0', '业务功能', '/biz', 1);
INSERT INTO `t_menu` VALUES ('F0201', 'F02', '参合家庭档案管理', '/biz/FamilyServlet', 2);
INSERT INTO `t_menu` VALUES ('F020101', 'F0201', '新建家庭档案', '/biz/FamilyServlet?m=add', 3);
INSERT INTO `t_menu` VALUES ('F020102', 'F0201', '修改家庭档案', '/biz/FamilyServlet?m=edit', 3);
INSERT INTO `t_menu` VALUES ('F020103', 'F0201', '删除家庭档案', '/biz/FamilyServlet?m=del', 3);
INSERT INTO `t_menu` VALUES ('F0202', 'F02', '参合农民档案管理', '/biz/PersonServlet', 2);
INSERT INTO `t_menu` VALUES ('F020201', 'F0202', '新建农民档案', '/biz/PersonServlet?m=add', 3);
INSERT INTO `t_menu` VALUES ('F020202', 'F0202', '删除农民档案', '/biz/PersonServlet?m=del', 3);
INSERT INTO `t_menu` VALUES ('F020203', 'F0202', '修改农民档案', '/biz/PersonServlet?m=edit', 3);
INSERT INTO `t_menu` VALUES ('F0203', 'F02', '参合缴费登记', '/biz/PayServlet', 2);
INSERT INTO `t_menu` VALUES ('F020301', 'F0203', '办理缴费', '/biz/PayServlet?m=input', 3);
INSERT INTO `t_menu` VALUES ('F020302', 'F0203', '打印缴费单', '/biz/PayServlet?m=print', 3);
INSERT INTO `t_menu` VALUES ('F0204', 'F02', '慢病证管理', '/biz/ChronicCardServlet', 2);
INSERT INTO `t_menu` VALUES ('F020401', 'F0204', '创建档案', '/biz/ChronicCardServlet?m=add', 3);
INSERT INTO `t_menu` VALUES ('F020402', 'F0204', '删除档案', '/biz/ChronicCardServlet?m=del', 3);
INSERT INTO `t_menu` VALUES ('F020403', 'F0204', '修改档案', '/biz/ChronicCardServlet?m=edit', 3);
INSERT INTO `t_menu` VALUES ('F020404', 'F0204', '打印慢病证', '/biz/ChronicCardServlet?m=print', 3);
INSERT INTO `t_menu` VALUES ('F0205', 'F02', '慢病报销', '/biz/ChronicReibServlet', 2);
INSERT INTO `t_menu` VALUES ('F020501', 'F0205', '打印报销单', '/biz/ChronicReibServlet', 3);
INSERT INTO `t_menu` VALUES ('F020502', 'F0205', '办理报销', '/biz/ChronicReibServlet?m=deal', 3);
INSERT INTO `t_menu` VALUES ('F03', '0', '统计报表', '/report', 1);
INSERT INTO `t_menu` VALUES ('F0301', 'F03', '慢病报销情况', '/report/ChrinicReportServlet', 2);
INSERT INTO `t_menu` VALUES ('F030101', 'F0301', '导出Excel', '/report/ChrinicReportServlet?m=download', 3);
INSERT INTO `t_menu` VALUES ('F0302', 'F03', '参合缴费情况', '/report/PayReportServlet', 2);
INSERT INTO `t_menu` VALUES ('F030201', 'F0302', '导出Excel', '/report/PayReportServlet?m=download', 3);

-- ----------------------------
-- Table structure for t_pay_record
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_record`;
CREATE TABLE `t_pay_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `familyCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '家庭编号',
  `houseHolder` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '户主名称',
  `houseNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '个人户内编号',
  `joinNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '参合证号',
  `invoiceNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '参合发票号',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '参合人姓名',
  `payAccount` double(30, 0) NULL DEFAULT NULL COMMENT '缴费金额',
  `payAnnual` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '缴费年度',
  `payTime` datetime(0) NULL DEFAULT NULL COMMENT '缴费时间',
  `operator` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_pay_record
-- ----------------------------
INSERT INTO `t_pay_record` VALUES (1, '4504210101010049', '孙艺', '450421010101004901', '450421010101004901', '123456', '孙艺', 200, '2019', '2019-01-05 00:00:00', '超级管理员');
INSERT INTO `t_pay_record` VALUES (2, '4504210101010049', '孙艺', '450421010101004902', '450421010101004902', '123456', '儿子', 200, '2019', '2019-01-05 00:00:00', '超级管理员');

-- ----------------------------
-- Table structure for t_pay_standard
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_standard`;
CREATE TABLE `t_pay_standard`  (
  `annual` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '年度',
  `account` double(10, 0) NULL DEFAULT NULL COMMENT '金额',
  `startTime` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `operator` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作员',
  PRIMARY KEY (`annual`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_pay_standard
-- ----------------------------
INSERT INTO `t_pay_standard` VALUES ('2017', 180, '2017-05-09 10:26:16', '2017-05-24 10:26:21', '超级管理员');
INSERT INTO `t_pay_standard` VALUES ('2018', 200, '2018-05-09 10:26:16', '2018-05-24 10:26:21', '超级管理员');
INSERT INTO `t_pay_standard` VALUES ('2019', 200, '2019-05-31 00:00:00', '2019-06-08 00:00:00', '超级管理员');

-- ----------------------------
-- Table structure for t_person_archives
-- ----------------------------
DROP TABLE IF EXISTS `t_person_archives`;
CREATE TABLE `t_person_archives`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `familyCode` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '家庭编号',
  `nongheCard` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '农合卡号',
  `medicalCard` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '医疗证卡号',
  `houseNum` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '户内编号',
  `relationship` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '与户主关系',
  `idCard` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '身份证号',
  `name` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `gender` varchar(3) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '性别（01：男，02：女）',
  `age` int(10) NULL DEFAULT NULL COMMENT '年龄',
  `birthDate` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `nation` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '民族',
  `health` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '健康状况',
  `education` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '文化程度',
  `workType` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '人员属性（从事工作类型）',
  `isRuralHukou` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '是否农村户口（1：是，0：否）',
  `career` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '职业',
  `workUnit` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '工作单位',
  `telephone` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `liveAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '常住地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_person_archives
-- ----------------------------
INSERT INTO `t_person_archives` VALUES (1, '4504210101010048', '450421010101004801', '4504210067270', '450421010101004801', '本人', '450421195108247000', '钟海清', '0', 40, '1979-06-06 00:00:00', '汉', '1', '初中', '务农', '1', '农民', '梧州学院', '110120119', '梧州学院A12宿舍');
INSERT INTO `t_person_archives` VALUES (17, '4504210101010049', '450421010101004901', '12345678', '450421010101004901', '本人', '460033199811034831', '孙艺', '1', 20, '1998-12-21 00:00:00', '汉', '1', '本科', '技术人员', '1', '学生', '梧州学院', '18276417233', 'A12男生宿舍');
INSERT INTO `t_person_archives` VALUES (25, '4504210101010049', '450421010101004902', '12345678', '450421010101004902', '父子', '460033199701012867', '儿子', '1', 20, '1998-12-26 00:00:00', '汉', '1', '无', '务农', '1', '学生', '梧州学院', '18276417233', 'A113');
INSERT INTO `t_person_archives` VALUES (26, '4504210101010049', '450421010101004903', '12345678', '450421010101004901', '父女', '460033199811034832', '女儿', '1', 20, '1998-12-21 00:00:00', '汉', '1', '本科', '技术人员', '1', '学生', '梧州学院', '18276417233', 'A11女生宿舍');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `roleid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `rolename` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('H001', '医院收费员');
INSERT INTO `t_role` VALUES ('R01', '超级管理员');
INSERT INTO `t_role` VALUES ('R02', '县级农合经办人');
INSERT INTO `t_role` VALUES ('R03', '乡镇农合经办人');
INSERT INTO `t_role` VALUES ('R04', '乡镇农合会计员');
INSERT INTO `t_role` VALUES ('R05', '县级农合办会计');
INSERT INTO `t_role` VALUES ('R06', '县级农合办领导');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menuid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 522 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (43, 'R04', 'F01');
INSERT INTO `t_role_menu` VALUES (44, 'R04', 'F0101');
INSERT INTO `t_role_menu` VALUES (45, 'R04', 'F010101');
INSERT INTO `t_role_menu` VALUES (46, 'R04', 'F0102');
INSERT INTO `t_role_menu` VALUES (47, 'R04', 'F010201');
INSERT INTO `t_role_menu` VALUES (48, 'R04', 'F0103');
INSERT INTO `t_role_menu` VALUES (49, 'R05', 'F01');
INSERT INTO `t_role_menu` VALUES (50, 'R05', 'F0101');
INSERT INTO `t_role_menu` VALUES (51, 'R05', 'F010101');
INSERT INTO `t_role_menu` VALUES (52, 'R05', 'F0102');
INSERT INTO `t_role_menu` VALUES (53, 'R05', 'F010201');
INSERT INTO `t_role_menu` VALUES (54, 'R05', 'F0103');
INSERT INTO `t_role_menu` VALUES (69, 'R02', 'F02');
INSERT INTO `t_role_menu` VALUES (70, 'R02', 'F0201');
INSERT INTO `t_role_menu` VALUES (71, 'R02', 'F0202');
INSERT INTO `t_role_menu` VALUES (72, 'R02', 'F0203');
INSERT INTO `t_role_menu` VALUES (73, 'R03', 'F0101');
INSERT INTO `t_role_menu` VALUES (74, 'R03', 'F010101');
INSERT INTO `t_role_menu` VALUES (75, 'R03', 'F0102');
INSERT INTO `t_role_menu` VALUES (76, 'R03', 'F010201');
INSERT INTO `t_role_menu` VALUES (77, 'R03', 'F0103');
INSERT INTO `t_role_menu` VALUES (78, 'R03', 'F02');
INSERT INTO `t_role_menu` VALUES (79, 'R03', 'F0201');
INSERT INTO `t_role_menu` VALUES (80, 'R03', 'F0202');
INSERT INTO `t_role_menu` VALUES (81, 'R03', 'F0203');
INSERT INTO `t_role_menu` VALUES (82, 'R03', 'F0204');
INSERT INTO `t_role_menu` VALUES (159, 'H001', 'F02');
INSERT INTO `t_role_menu` VALUES (160, 'H001', 'F0204');
INSERT INTO `t_role_menu` VALUES (161, 'H001', 'F020404');
INSERT INTO `t_role_menu` VALUES (264, 'R01', 'F01');
INSERT INTO `t_role_menu` VALUES (265, 'R01', 'F0101');
INSERT INTO `t_role_menu` VALUES (266, 'R01', 'F010101');
INSERT INTO `t_role_menu` VALUES (267, 'R01', 'F010102');
INSERT INTO `t_role_menu` VALUES (268, 'R01', 'F010103');
INSERT INTO `t_role_menu` VALUES (269, 'R01', 'F0102');
INSERT INTO `t_role_menu` VALUES (270, 'R01', 'F010201');
INSERT INTO `t_role_menu` VALUES (271, 'R01', 'F010202');
INSERT INTO `t_role_menu` VALUES (272, 'R01', 'F010203');
INSERT INTO `t_role_menu` VALUES (273, 'R01', 'F0103');
INSERT INTO `t_role_menu` VALUES (274, 'R01', 'F010301');
INSERT INTO `t_role_menu` VALUES (275, 'R01', 'F010302');
INSERT INTO `t_role_menu` VALUES (276, 'R01', 'F010303');
INSERT INTO `t_role_menu` VALUES (277, 'R01', 'F0104');
INSERT INTO `t_role_menu` VALUES (278, 'R01', 'F010401');
INSERT INTO `t_role_menu` VALUES (279, 'R01', 'F010402');
INSERT INTO `t_role_menu` VALUES (280, 'R01', 'F0105');
INSERT INTO `t_role_menu` VALUES (281, 'R01', 'F010501');
INSERT INTO `t_role_menu` VALUES (282, 'R01', 'F0106');
INSERT INTO `t_role_menu` VALUES (283, 'R01', 'F010601');
INSERT INTO `t_role_menu` VALUES (284, 'R01', 'F010602');
INSERT INTO `t_role_menu` VALUES (285, 'R01', 'F010603');
INSERT INTO `t_role_menu` VALUES (286, 'R01', 'F02');
INSERT INTO `t_role_menu` VALUES (287, 'R01', 'F0201');
INSERT INTO `t_role_menu` VALUES (288, 'R01', 'F020101');
INSERT INTO `t_role_menu` VALUES (289, 'R01', 'F020102');
INSERT INTO `t_role_menu` VALUES (290, 'R01', 'F020103');
INSERT INTO `t_role_menu` VALUES (291, 'R01', 'F0202');
INSERT INTO `t_role_menu` VALUES (292, 'R01', 'F020201');
INSERT INTO `t_role_menu` VALUES (293, 'R01', 'F020202');
INSERT INTO `t_role_menu` VALUES (294, 'R01', 'F020203');
INSERT INTO `t_role_menu` VALUES (295, 'R01', 'F0203');
INSERT INTO `t_role_menu` VALUES (296, 'R01', 'F020301');
INSERT INTO `t_role_menu` VALUES (297, 'R01', 'F020302');
INSERT INTO `t_role_menu` VALUES (298, 'R01', 'F0204');
INSERT INTO `t_role_menu` VALUES (299, 'R01', 'F020401');
INSERT INTO `t_role_menu` VALUES (300, 'R01', 'F020402');
INSERT INTO `t_role_menu` VALUES (301, 'R01', 'F020403');
INSERT INTO `t_role_menu` VALUES (302, 'R01', 'F020404');
INSERT INTO `t_role_menu` VALUES (303, 'R01', 'F0205');
INSERT INTO `t_role_menu` VALUES (304, 'R01', 'F020501');
INSERT INTO `t_role_menu` VALUES (305, 'R01', 'F020502');
INSERT INTO `t_role_menu` VALUES (306, 'R01', 'F03');
INSERT INTO `t_role_menu` VALUES (307, 'R01', 'F0301');
INSERT INTO `t_role_menu` VALUES (308, 'R01', 'F030101');
INSERT INTO `t_role_menu` VALUES (309, 'R01', 'F0302');
INSERT INTO `t_role_menu` VALUES (310, 'R01', 'F030201');
INSERT INTO `t_role_menu` VALUES (512, 'R06', 'F0101');
INSERT INTO `t_role_menu` VALUES (513, 'R06', 'F010101');
INSERT INTO `t_role_menu` VALUES (514, 'R06', 'F0102');
INSERT INTO `t_role_menu` VALUES (515, 'R06', 'F010201');
INSERT INTO `t_role_menu` VALUES (516, 'R06', 'F0103');
INSERT INTO `t_role_menu` VALUES (517, 'R06', 'F0201');
INSERT INTO `t_role_menu` VALUES (518, 'R06', 'F0202');
INSERT INTO `t_role_menu` VALUES (519, 'R06', 'F0203');
INSERT INTO `t_role_menu` VALUES (520, 'R06', 'F0204');
INSERT INTO `t_role_menu` VALUES (521, 'R06', 'F0205');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `userid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `pwd` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fullname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `agencode` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '1', '1', '1', '1');
INSERT INTO `t_user` VALUES ('admin', 'admin', '超级管理员', '1', '0');
INSERT INTO `t_user` VALUES ('apple', '123456', '乔布斯', '1', '0');
INSERT INTO `t_user` VALUES ('charis', '123456', '张朝阳', '1', '0');
INSERT INTO `t_user` VALUES ('jack', '123456', '马云', '0', '0');
INSERT INTO `t_user` VALUES ('robin', '123456', '李彦宏', '1', '0');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `roleid` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (2, 'jack', 'R01');
INSERT INTO `t_user_role` VALUES (3, 'charis', 'R02');
INSERT INTO `t_user_role` VALUES (4, 'apple', 'R03');

SET FOREIGN_KEY_CHECKS = 1;
