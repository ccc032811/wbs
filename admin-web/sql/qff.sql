/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : febs_base

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 19/12/2019 10:32:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Function structure for findDeptChildren
-- ----------------------------
DROP FUNCTION IF EXISTS `findDeptChildren`;
delimiter ;;
CREATE FUNCTION `findDeptChildren`(rootId INT)
    RETURNS varchar(4000) CHARSET utf8
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);
    SET sTemp = '$';
    SET sTempChd = CAST(rootId as CHAR);
    WHILE sTempChd is not null DO
    SET sTemp = CONCAT(sTemp, ',', sTempChd);
    SELECT GROUP_CONCAT(dept_id)
    INTO sTempChd
    FROM t_dept
    WHERE FIND_IN_SET(parent_id, sTempChd) > 0;
    END WHILE;
    RETURN sTemp;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for findMenuChildren
-- ----------------------------
DROP FUNCTION IF EXISTS `findMenuChildren`;
delimiter ;;
CREATE FUNCTION `findMenuChildren`(rootId INT)
    RETURNS varchar(4000) CHARSET utf8
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);
    SET sTemp = '$';
    SET sTempChd = CAST(rootId as CHAR);
    WHILE sTempChd is not null DO
    SET sTemp = CONCAT(sTemp, ',', sTempChd);
    SELECT GROUP_CONCAT(menu_id)
    INTO sTempChd
    FROM t_menu
    WHERE FIND_IN_SET(parent_id, sTempChd) > 0;
    END WHILE;
    RETURN sTemp;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for findOpinionChildren
-- ----------------------------

DROP FUNCTION IF EXISTS `findOpinionChildren`;
delimiter ;;
CREATE FUNCTION `findOpinionChildren`(rootId INT)
  RETURNS varchar(4000) CHARSET utf8
BEGIN
  DECLARE sTemp VARCHAR(4000);
  DECLARE sTempChd VARCHAR(4000);
  SET sTemp = '$';
  SET sTempChd = CAST(rootId as CHAR);
  WHILE sTempChd is not null DO
  SET sTemp = CONCAT(sTemp, ',', sTempChd);
  SELECT GROUP_CONCAT(id)
         INTO sTempChd
  FROM qff_opinion
  WHERE FIND_IN_SET(parent_id, sTempChd) > 0;
  END WHILE;
  RETURN sTemp;
END
;;
delimiter ;


