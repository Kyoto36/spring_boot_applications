CREATE DATABASE IF NOT EXISTS `my_funds` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE `my_funds`;

DROP TABLE IF EXISTS `operate_range`;
CREATE TABLE IF NOT EXISTS `operate_range`(
   `range_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
   `range_name` VARCHAR(50) NOT NULL COMMENT '区间名称',
   `range_sort` INT NOT NULL COMMENT '排序，每次基金赎回，操作区间就会加1，然后就会根据区间找到对应的排序',
   `upper_limit` DOUBLE COMMENT '可操作上限，基金涨幅大于等于多少时，可赎回',
   `lower_limit` DOUBLE COMMENT '可操作下限，基金跌幅大于等于多少时，可加仓',
   `lower_rate` FLOAT COMMENT '操作下限比率，基金跌幅大于等于下限时，应该按几倍跌幅加仓',
   `belong_to_fund` INT COMMENT '多属于某只基金的操作区间，为空表示全部适用' DEFAULT -1
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '基金操作区间表';

DROP TABLE IF EXISTS `hold_funds`;
CREATE TABLE IF NOT EXISTS `hold_funds`(
    `fund_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键id',
    `fund_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '基金code',
    `fund_name` VARCHAR(200) NOT NULL COMMENT '基金名称',
    `init_value` DECIMAL(10,4) NOT NULL COMMENT '初始净值，即加入该条数据时的净值',
    `hold_value` DECIMAL(10,4) NOT NULL COMMENT '持有净值，每次买入卖出会动态变化',
    `operate_range` INT COMMENT '每一次赎回区间加1',
    `last_operate_value` DECIMAL(10,4) COMMENT '上次操作净值，为空即未操作',
    `current_value` DECIMAL(10,4) COMMENT '基金当前的最新净值',
    `hold_count` DOUBLE COMMENT '该基金当前持有份额'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '持有基金表';

DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE IF NOT EXISTS `operate_log`(
    `operate_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `fund_id` INT NOT NULL COMMENT '基金ID',
    `last_operate_value` DECIMAL(10,4) NOT NULL COMMENT '上次操作净值',
    `latest_value` DECIMAL(10,4) NOT NULL COMMENT '最新净值',
    `variability_rate` DOUBLE NOT NULL COMMENT '波动率',
    `hold_count` INT NOT NULL COMMENT '操作前持有份额',
    `hold_value` DECIMAL(10,4) NOT NULL COMMENT '操作前持有净值',
    `freeze_time` LONG NOT NULL COMMENT '定格时间',
    `operate_type` INT NOT NULL COMMENT '操作类型 1、赎回 2、加仓',
    `to_range` INT COMMENT '到达区间，每次赎回在原有区间上加1，加仓为空',
    `operate_time` LONG COMMENT '操作时间，真正赎回或加仓之后的时间',
    `new_hold_count` INT COMMENT '操作之后的持有份额',
    `new_hold_value` DECIMAL(10,4) NOT NULL COMMENT '操作后的持有净值',
    `operate_count` DOUBLE NOT NULL COMMENT '操作份额',
    `operate_status` INT NOT NULL COMMENT '操作状态 1、定格中，2、已废弃，3、已完成'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '操作日志表';


DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
   `USER_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `USER_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
   `CELL_PHONE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
   `EMAIL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
   `PASSWORD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
   `USER_STATUS` int(11) NULL DEFAULT NULL COMMENT '状态',
   `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
   `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
   PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

DROP TABLE IF EXISTS `t_sys_operate_log`;
CREATE TABLE `t_sys_operate_log`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `REQUEST_IP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求IP',
  `REQUEST_URI` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求链接',
  `REQUEST_PARAMS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `HTTP_METHOD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式（GET、POST……）',
  `TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求描述',
  `EX_DESC` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常描述',
  `EX_DETAIL` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '异常详情',
  `START_TIME` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `FINISH_TIME` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `CONSUMING_TIME` bigint(20) NULL DEFAULT NULL COMMENT '操作耗时',
  `STATE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作状态（1：成功，0：失败）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


SELECT * FROM `operate_range`;
SELECT COUNT(1) FROM `hold_funds`WHERE `fund_code` = '161121';
SELECT * FROM `operate_log`;

SELECT COUNT(1) FROM `operate_range`;

CREATE PROCEDURE `fund_range_sort`

DROP PROCEDURE IF EXISTS `copy_range_to`;

CREATE PROCEDURE `copy_range_to`(IN source_id INT,IN target_id INT,OUT state INT)
top:BEGIN

IF target_id IS NULL THEN
    SET state = 0;
    LEAVE top;
END IF;

DROP TABLE IF EXISTS `temp_table_copy_range_to`;

IF source_id IS NULL THEN
    SET source_id = -1;
END IF;

CREATE TEMPORARY TABLE `temp_table_copy_range_to` AS
SELECT * FROM `operate_range` WHERE `belong_to_fund` = source_id;
		
UPDATE `temp_table_copy_range_to` set `belong_to_fund` = target_id;

INSERT INTO `operate_range`
(`range_sort`,`upper_limit`,`lower_limit`,`lower_rate`,`belong_to_fund`)
SELECT `range_sort`,`upper_limit`,`lower_limit`,`lower_rate`,`belong_to_fund`
FROM `temp_table_copy_range_to`;

DROP TABLE `temp_table_copy_range_to`;

SET state = 1;
END

CALL `copy_range_to`(NULL,1,@state);
SELECT @state;

DROP PROCEDURE IF EXISTS `test_insert`;
CREATE PROCEDURE `test_insert`(IN aaa INT)
BEGIN
DECLARE i INT DEFAULT 0;
WHILE i < aaa DO
INSERT INTO `operate_range` VALUES(NULL,1,0.2,0.05,2,1);
SET i = i + 1;
END WHILE;
END

CALL test_insert(1,NULL,@state);
