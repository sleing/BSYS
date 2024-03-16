/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "学生获奖管理";
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = 0;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                                 `menu_type`, `open_type`, `sort_number`, `authority`)
                          values (${newMenuId}, 0, '学生获奖管理', 'MenuOutlined', '', '', 0, 0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                          values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "学生获奖管理";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "获奖登记管理";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '获奖登记管理', 'MenuOutlined', '/bms/rewardEntity/reward',
                                '/bms/rewardEntity/reward', 0, 0, ${sort_number}, 'bms:rewardEntity:Reward:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "获奖登记管理";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "查询获奖登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '查询获奖登记', '', '',
                                '', 1, 0, ${sort_number}, 'bms:rewardEntity:Reward:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "添加获奖登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '添加获奖登记', '', '', '', 1, 0, ${sort_number},
                                'bms:rewardEntity:Reward:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "修改获奖登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '修改获奖登记', '', '',
                                '', 1, 0, ${sort_number}, 'bms:rewardEntity:Reward:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "删除获奖登记";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '删除获奖登记', '', '',
                                '', 1, 0, ${sort_number}, 'bms:rewardEntity:Reward:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});




                                                                        