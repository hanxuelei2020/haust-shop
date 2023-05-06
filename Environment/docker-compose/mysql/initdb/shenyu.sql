/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 192.168.88.128:3306
 Source Schema         : shenyu

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 15/04/2023 19:27:04
*/
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shenyu` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `shenyu`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alert_template
-- ----------------------------
DROP TABLE IF EXISTS `alert_template`;
CREATE TABLE `alert_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'primary key id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'alert template name',
  `strategy_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'alert template strategy name',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'alert template content',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `context_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the context_path',
  `api_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the api_path',
  `http_method` int NOT NULL COMMENT '0-get,1-head,2-post,3-put,4-patch,5-delete,6-options,7-trace',
  `consume` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'consume content-type',
  `produce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'produce content-type',
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'api version,for example V0.01',
  `rpc_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'http,dubbo,sofa,tars,websocket,springCloud,motan,grpc',
  `state` tinyint NOT NULL COMMENT '0-unpublished,1-published,2-offline',
  `ext` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'extended fields',
  `api_owner` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'api_owner',
  `api_desc` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the api description',
  `api_source` int NOT NULL COMMENT '0-swagger,1-annotation generation,2-create manually,3-import swagger,4-import yapi',
  `document` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'complete documentation of the api, including request parameters and response parameters',
  `document_md5` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'document_md5',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for api_rule_relation
-- ----------------------------
DROP TABLE IF EXISTS `api_rule_relation`;
CREATE TABLE `api_rule_relation`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `api_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the table api primary key id',
  `rule_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the table rule primary key id',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for app_auth
-- ----------------------------
DROP TABLE IF EXISTS `app_auth`;
CREATE TABLE `app_auth`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `app_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'application identification key',
  `app_secret` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'encryption algorithm secret',
  `user_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'user id',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'phone number when the user applies',
  `ext_info` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'extended parameter json',
  `open` tinyint NOT NULL COMMENT 'open auth path or not  (0 close, 1 open) ',
  `enabled` tinyint NOT NULL COMMENT 'delete or not  (0 close, 1 open) ',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for auth_param
-- ----------------------------
DROP TABLE IF EXISTS `auth_param`;
CREATE TABLE `auth_param`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `auth_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'Authentication table id',
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'business Module',
  `app_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'service module parameters (parameters that need to be passed by the gateway) json type',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for auth_path
-- ----------------------------
DROP TABLE IF EXISTS `auth_path`;
CREATE TABLE `auth_path`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `auth_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'auth table id',
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'module',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'path',
  `enabled` tinyint NOT NULL COMMENT 'whether pass 1 is  (0 close, 1 open) ',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for dashboard_user
-- ----------------------------
DROP TABLE IF EXISTS `dashboard_user`;
CREATE TABLE `dashboard_user`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'user name',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'user password',
  `role` int NOT NULL COMMENT 'role',
  `enabled` tinyint NOT NULL COMMENT 'delete or not (0 close, 1 open) ',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for data_permission
-- ----------------------------
DROP TABLE IF EXISTS `data_permission`;
CREATE TABLE `data_permission`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `user_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'user primary key id',
  `data_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'data(selector,rule) primary key id',
  `data_type` int NOT NULL COMMENT '0 selector type , 1 rule type',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'data permission table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for detail
-- ----------------------------
DROP TABLE IF EXISTS `detail`;
CREATE TABLE `detail`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `field_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the field id',
  `is_example` tinyint NOT NULL COMMENT 'is example or not (0 not, 1 is)',
  `field_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the field value',
  `value_desc` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'field value description',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'field value detail table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for field
-- ----------------------------
DROP TABLE IF EXISTS `field`;
CREATE TABLE `field`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `model_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'this field belongs to which model',
  `self_model_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'which model of this field is',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'field name',
  `field_desc` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'field description',
  `required` tinyint NOT NULL COMMENT 'whether to require (0 not required, 1 required)',
  `ext` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'extended fields,can store genericTypes,eg..{\"genericTypes\":[model_id1,model_id2]}',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'field document table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for meta_data
-- ----------------------------
DROP TABLE IF EXISTS `meta_data`;
CREATE TABLE `meta_data`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'id',
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'application name',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'path, cannot be repeated',
  `path_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'path description',
  `rpc_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'rpc type',
  `service_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'service name',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'method name',
  `parameter_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'parameter types are provided with multiple parameter types separated by commas',
  `rpc_ext` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'rpc extended information, json format',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  `enabled` tinyint NOT NULL DEFAULT 0 COMMENT 'enabled state  (0 close, 1 open) ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for mock_request_record
-- ----------------------------
DROP TABLE IF EXISTS `mock_request_record`;
CREATE TABLE `mock_request_record`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `api_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the api id',
  `host` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the request host',
  `port` int NOT NULL COMMENT 'the request port',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the request url',
  `path_variable` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'the request param in url',
  `query` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'the request param after url',
  `header` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'the request param in header',
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT 'the request body',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for model
-- ----------------------------
DROP TABLE IF EXISTS `model`;
CREATE TABLE `model`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the model name',
  `model_desc` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the model description',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for operation_record_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_record_log`;
CREATE TABLE `operation_record_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'log color',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'log context',
  `operator` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'operator [user or app]]',
  `operation_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'operation time',
  `operation_type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'update' COMMENT 'operation type：create/update/delete/register...',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'operation record log' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for param
-- ----------------------------
DROP TABLE IF EXISTS `param`;
CREATE TABLE `param`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `api_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the api id',
  `model_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the model id, empty if not a model',
  `type` int NOT NULL COMMENT '0-requestPathVariable,1-requestUrlParam,2-requestHeader,3-requestBody,4-responseHeader,5-responseBody',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the param name',
  `param_desc` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'the param description',
  `required` tinyint NOT NULL COMMENT 'whether to require (0 not required, 1 required)',
  `ext` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'extended fields',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `object_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'user primary key id or role primary key id',
  `resource_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'resource primary key id',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'permission table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plugin
-- ----------------------------
DROP TABLE IF EXISTS `plugin`;
CREATE TABLE `plugin`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `name` varchar(62) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'plugin name',
  `config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT 'plugin configuration',
  `role` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'plug-in role',
  `sort` int NULL DEFAULT NULL COMMENT 'sort',
  `enabled` tinyint NOT NULL DEFAULT 0 COMMENT 'whether to open (0, not open, 1 open)',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  `plugin_jar` mediumblob NULL COMMENT 'plugin jar',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for plugin_handle
-- ----------------------------
DROP TABLE IF EXISTS `plugin_handle`;
CREATE TABLE `plugin_handle`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `plugin_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'plugin id',
  `field` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'field',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'label',
  `data_type` smallint NOT NULL DEFAULT 1 COMMENT 'data type 1 number 2 string',
  `type` smallint NULL DEFAULT NULL COMMENT 'type, 1 means selector, 2 means rule, 3 means plugin',
  `sort` int NULL DEFAULT NULL COMMENT 'sort',
  `ext_obj` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'extra configuration (json format data)',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `plugin_id_field_type`(`plugin_id`, `field`, `type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `parent_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'resource parent primary key id',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'title',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'route name',
  `url` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'route url',
  `component` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'component',
  `resource_type` int NOT NULL COMMENT 'resource type eg 0:main menu 1:child menu 2:function button',
  `sort` int NOT NULL COMMENT 'sort',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'icon',
  `is_leaf` tinyint(1) NOT NULL COMMENT 'leaf node 0:no 1:yes',
  `is_route` int NOT NULL COMMENT 'route 1:yes 0:no',
  `perms` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'button permission description sys:user:add(add)/sys:user:edit(edit)',
  `status` int NOT NULL COMMENT 'status 1:enable 0:disable',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'resource table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'role name',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'role describe',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`, `role_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'role table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `selector_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'selector id',
  `match_mode` int NOT NULL COMMENT 'matching mode (0 and 1 or)',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'rule name',
  `enabled` tinyint NOT NULL COMMENT 'whether to open (0 close, 1 open) ',
  `loged` tinyint NOT NULL COMMENT 'whether to log or not (0 no print, 1 print) ',
  `match_restful` tinyint NOT NULL COMMENT 'whether to match restful(0 cache, 1 not cache)',
  `sort` int NOT NULL COMMENT 'sort',
  `handle` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'processing logic (here for different plug-ins, there will be different fields to identify different processes, all data in JSON format is stored)',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for rule_condition
-- ----------------------------
DROP TABLE IF EXISTS `rule_condition`;
CREATE TABLE `rule_condition`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `rule_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'rule id',
  `param_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'parameter type (post query uri, etc.)',
  `operator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'matching character (=> <like match)',
  `param_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'parameter name',
  `param_value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'parameter value',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for selector
-- ----------------------------
DROP TABLE IF EXISTS `selector`;
CREATE TABLE `selector`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id varchar',
  `plugin_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'plugin id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'selector name',
  `match_mode` int NOT NULL COMMENT 'matching mode (0 and 1 or)',
  `type` int NOT NULL COMMENT 'type (0, full flow, 1 custom flow)',
  `sort` int NOT NULL COMMENT 'sort',
  `handle` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'processing logic (here for different plug-ins, there will be different fields to identify different processes, all data in JSON format is stored)',
  `enabled` tinyint NOT NULL COMMENT 'whether to open (0 close, 1 open) ',
  `loged` tinyint NOT NULL COMMENT 'whether to print the log (0 no print, 1 print) ',
  `continued` tinyint NOT NULL COMMENT 'whether to continue execution',
  `match_restful` tinyint NOT NULL DEFAULT 0 COMMENT 'whether to match restful(0 cache, 1 not cache)',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for selector_condition
-- ----------------------------
DROP TABLE IF EXISTS `selector_condition`;
CREATE TABLE `selector_condition`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `selector_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'selector id',
  `param_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'parameter type (to query uri, etc.)',
  `operator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'matching character (=> <like matching)',
  `param_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'parameter name',
  `param_value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'parameter value',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for shenyu_dict
-- ----------------------------
DROP TABLE IF EXISTS `shenyu_dict`;
CREATE TABLE `shenyu_dict`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'type',
  `dict_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'dictionary encoding',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'dictionary name',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'dictionary value',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'dictionary description or remarks',
  `sort` int NOT NULL COMMENT 'sort',
  `enabled` tinyint NULL DEFAULT NULL COMMENT 'whether it is enabled (0 close, 1 open) ',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_type_dict_code_dict_name`(`type`, `dict_code`, `dict_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'tag name',
  `tag_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'tag description',
  `parent_tag_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'parent tag_id',
  `ext` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'extension info',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'api doc tag table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tag_relation
-- ----------------------------
DROP TABLE IF EXISTS `tag_relation`;
CREATE TABLE `tag_relation`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `api_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'api id',
  `tag_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'tag id',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'api doc and tag bind table' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'primary key id',
  `user_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'user primary key',
  `role_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'role primary key',
  `date_created` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'create time',
  `date_updated` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'user and role bind table' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
