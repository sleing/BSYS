/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "举办单位";
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = 0;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                                 `menu_type`, `open_type`, `sort_number`, `authority`)
                          values (${newMenuId}, 0, '举办单位', 'MenuOutlined', '', '', 0, 0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                          values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "举办单位";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "举办单位信息管理";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '举办单位信息管理', 'MenuOutlined', '/ams/unitEntity/unit',
                                '/ams/unitEntity/unit', 0, 0, ${sort_number}, 'ams:unitEntity:Unit:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "举办单位信息管理";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "查询举办单位信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '查询举办单位信息', '', '',
                                '', 1, 0, ${sort_number}, 'ams:unitEntity:Unit:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "添加举办单位信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '添加举办单位信息', '', '', '', 1, 0, ${sort_number},
                                'ams:unitEntity:Unit:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "修改举办单位信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '修改举办单位信息', '', '',
                                '', 1, 0, ${sort_number}, 'ams:unitEntity:Unit:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "删除举办单位信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '删除举办单位信息', '', '',
                                '', 1, 0, ${sort_number}, 'ams:unitEntity:Unit:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});




                            