-- 消息通知表 新增通知范围 和 通知用户两个字段
START TRANSACTION;
ALTER TABLE sys_notice
    ADD COLUMN notice_scope INT NOT NULL COMMENT '通知范围' AFTER terminate_time,
    ADD COLUMN notice_users JSON DEFAULT NULL COMMENT '通知用户' AFTER notice_scope;
COMMIT;

-- changeset chengzi
-- comment 新增应用表
CREATE TABLE IF NOT EXISTS `sys_app`  (
    `id`              bigint(0) NOT NULL COMMENT 'ID',
    `name`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用名称',
    `app_key`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用密钥',
    `app_secret`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用密码',
    `status`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用状态(0:未激活；1:激活)',
    `expiration_time` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
    `app_desc`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用描述',
    `secret_status`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用密码查看状态(0:未查看；1:已查看)',
    `create_user`     bigint(0) NOT NULL COMMENT '创建人',
    `create_time`     datetime(0) NOT NULL COMMENT '创建时间',
    `update_user`     bigint(0) NULL DEFAULT NULL COMMENT '修改人',
    `update_time`     datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';
INSERT INTO `sys_app` VALUES (639144999463690263, 'ContiNewAdmin', 'admin', '9efcf8859d754288941e61adc72cd250', '1', '2024-10-31 16:53:52', 'ContiNew Admin（Continue New Admin）持续迭代优化的前后端分离中后台管理系统框架。开箱即用，重视每一处代码规范，重视每一种解决方案细节，持续提供舒适的前、后端开发体验。', '1', 1, '2024-10-22 16:54:03', NULL, NULL);
-- 应用管理菜单数据
INSERT INTO `sys_menu` VALUES (635516486647025735, '能力开放', 0, 1, '/open', 'Open', 'Layout', NULL, 'expand', b'0', b'0', b'0', NULL, 2, 1, 1, '2024-10-12 16:35:38', 1, '2024-10-12 16:35:53');
INSERT INTO `sys_menu` VALUES (635516794676711501, '应用管理', 635516486647025735, 2, '/open/app', 'OpenApp', 'open/app/index', NULL, 'common', b'0', b'0', b'0', NULL, 1, 1, 1, '2024-10-12 16:36:51', 1, '2024-10-12 16:49:11');
INSERT INTO `sys_menu` VALUES (636598391530328174, '新增', 635516794676711501, 3, NULL, NULL, NULL, NULL, NULL, b'0', b'0', b'0', 'open:app:add', 1, 1, 1, '2024-10-15 16:14:44', 1, '2024-10-15 16:45:36');
INSERT INTO `sys_menu` VALUES (636599310447808642, '查看', 635516794676711501, 3, NULL, NULL, NULL, NULL, NULL, b'0', b'0', b'0', 'open:app:list', 999, 1, 1, '2024-10-15 16:18:23', NULL, NULL);
INSERT INTO `sys_menu` VALUES (636599448054534277, '导出', 635516794676711501, 3, NULL, NULL, NULL, NULL, NULL, b'0', b'0', b'0', 'open:app:export', 999, 1, 1, '2024-10-15 16:18:56', NULL, NULL);
INSERT INTO `sys_menu` VALUES (637299919924760580, '删除', 635516794676711501, 3, NULL, NULL, NULL, NULL, NULL, b'0', b'0', b'0', 'open:app:delete', 1, 1, 1, '2024-10-17 14:42:21', NULL, NULL);
INSERT INTO `sys_menu` VALUES (637300206014042119, '修改', 635516794676711501, 3, NULL, NULL, NULL, NULL, NULL, b'0', b'0', b'0', 'open:app:update', 1, 1, 1, '2024-10-17 14:43:30', NULL, NULL);
-- 应用管理字典数据
INSERT INTO `sys_dict` VALUES (639152724557963332, '应用状态', 'app_type', NULL, b'0', 1, '2024-10-22 17:24:44', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (639152915579150411, '禁用', '0', 'blue', 999, NULL, 1, 639152724557963332, 1, '2024-10-22 17:25:30', NULL, NULL);
INSERT INTO `sys_dict_item` VALUES (639153003290435665, '启用', '1', 'green', 999, NULL, 1, 639152724557963332, 1, '2024-10-22 17:25:51', NULL, NULL);