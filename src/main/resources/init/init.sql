--创建数据库 base_project
drop database if exists `base_project`;
create database `base_project` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

--attachment 表
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `save_name`             varchar(255),
    `old_name`              varchar(255),
    `save_path`             varchar(255),
    `url_path`              varchar(255),
    `user_id`               varchar(255),
    `time`                  TIMESTAMP default now(),
    `type`                  varchar(255),
    `suffix`                varchar(255),
    `size`                  varchar(255),
    `width`                 varchar(255),
    `height`                varchar(255)
);

--sort_info 表
DROP TABLE IF EXISTS `sort_info`;
CREATE TABLE `sort_info`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `name`                  varchar(255),
    `des`                   varchar(255),
    `full_name`             varchar(255),
    `series`                varchar(255),
    `last_id`               varchar(255),
    `first_name`            varchar(255),
    `last_name`             varchar(255),
    `sep`                   varchar(255),
    `sort`                  varchar(255),
    `status`                varchar(255)
);

--login_log 表
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `user_name`             varchar(255),
    `login_time`            int4,
    `login_result`          varchar(255),
    `login_result_detail`   varchar(255),
    `login_url`             varchar(255)
);

--login_pic 表
DROP TABLE IF EXISTS `login_pic`;
CREATE TABLE `login_pic`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `name`                  varchar(255),
    `detail`                varchar(255),
    `status`                varchar(255),
    `path`                  varchar(255),
    `create_time`           TIMESTAMP default now(),
    `update_time`           TIMESTAMP default now(),
    `create_user_id`        varchar(255),
    `update_user_id`        varchar(255),
    `sort`                  varchar(255),
    `attachment_id`         varchar(255)
);

--menu 表
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `pid`                   varchar(255),
    `name`                  varchar(255),
    `url`                   varchar(255),
    `status`                varchar(255),
    `sort`                  int4,
    `series`                varchar(255),
    `icon`                  varchar(255)
);

--role 表
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `name`                  varchar(255),
    `des`                   varchar(255),
    `status`                varchar(255)
);

--role_menu 表
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `role_id`               varchar(255),
    `menu_id`               varchar(255)
);

--system 表
DROP TABLE IF EXISTS `system`;
CREATE TABLE `system`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `system_key`            varchar(255),
    `system_value`          varchar(255),
    `remark`                varchar(255)
);

--user_role 表
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `user_id`               varchar(255),
    `role_id`               varchar(255)
);

--t_users
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users`
(
    `id`                    varchar(255) NOT NULL PRIMARY KEY,
    `username`              varchar(255),
    `password`              varchar(255),
    `telephone`             varchar(255),
    `email`                 varchar(255),
    `home_url`              varchar(255),
    `screen_name`           varchar(255),
    `created`               int4,
    `activated`             int4,
    `logged`                int4,
    `group_name`            varchar(255),
    `status`                varchar(255),
    `type`                  varchar(255),
    `identification`        varchar(255),
    `address`               varchar(255),
    `unit`                  varchar(255)
);