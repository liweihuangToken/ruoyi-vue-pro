-- 菜单 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status, component_name
)
VALUES (
    '配货综合订单信息管理', '', 2, 0, ${table.parentMenuId},
    'comprehensive-order-info', '', 'distribution/comprehensiveOrderInfo/index', 0, 'ComprehensiveOrderInfo'
);

-- 按钮父菜单ID
-- 暂时只支持 MySQL。如果你是 Oracle、PostgreSQL、SQLServer 的话，需要手动修改 @parentId 的部分的代码
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '配货综合订单信息查询', 'distribution:comprehensive-order-info:query', 3, 1, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '配货综合订单信息创建', 'distribution:comprehensive-order-info:create', 3, 2, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '配货综合订单信息更新', 'distribution:comprehensive-order-info:update', 3, 3, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '配货综合订单信息删除', 'distribution:comprehensive-order-info:delete', 3, 4, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '配货综合订单信息导出', 'distribution:comprehensive-order-info:export', 3, 5, @parentId,
    '', '', '', 0
);
