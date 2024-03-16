/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "学院";
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = 0;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                                 `menu_type`, `open_type`, `sort_number`, `authority`)
                          values (${newMenuId}, 0, '学院', 'MenuOutlined', '', '', 0, 0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                          values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "学院";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "学院信息管理";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '学院信息管理', 'MenuOutlined', '/ams/collegeEntity/college',
                                '/ams/collegeEntity/college', 0, 0, ${sort_number}, 'ams:collegeEntity:College:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "学院信息管理";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "查询学院信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '查询学院信息', '', '',
                                '', 1, 0, ${sort_number}, 'ams:collegeEntity:College:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "添加学院信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '添加学院信息', '', '', '', 1, 0, ${sort_number},
                                'ams:collegeEntity:College:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "修改学院信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '修改学院信息', '', '',
                                '', 1, 0, ${sort_number}, 'ams:collegeEntity:College:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "删除学院信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '删除学院信息', '', '',
                                '', 1, 0, ${sort_number}, 'ams:collegeEntity:College:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});




                            