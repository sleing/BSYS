/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "竞赛";
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = 0;
/* execute(!moduleId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                                 `menu_type`, `open_type`, `sort_number`, `authority`)
                          values (${newMenuId}, 0, '竞赛', 'MenuOutlined', '', '', 0, 0, ${sort_number}, '');
/* execute(!moduleId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                          values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "竞赛";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "竞赛信息管理";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '竞赛信息管理', 'MenuOutlined',
                                '/ams/competitionEntity/competition',
                                '/ams/competitionEntity/competition', 0, 0, ${sort_number},
                                'ams:competitionEntity:Competition:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});
/* find(moduleId)  */ select `menu_id`
                      from `sys_menu`
                      where `title` = "竞赛信息管理";

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "查询竞赛信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '查询竞赛信息', '', '',
                                '', 1, 0, ${sort_number}, 'ams:competitionEntity:Competition:view');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "添加竞赛信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '添加竞赛信息', '', '', '', 1, 0, ${sort_number},
                                'ams:competitionEntity:Competition:add');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "修改竞赛信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '修改竞赛信息', '', '',
                                '', 1, 0, ${sort_number}, 'ams:competitionEntity:Competition:update');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});

/* find(menuId)  */ select `menu_id`
                    from `sys_menu`
                    where `title` = "删除竞赛信息";
/* find(newMenuId)  */ select max(`menu_id`) + 1 as value
                       from `sys_menu`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_menu t
                         where parent_id = ${moduleId};
/* execute(!menuId)  */ insert into `sys_menu`(`menu_id`, `parent_id`, `title`, `icon`, `path`, `component`,
                                               `menu_type`, `open_type`, `sort_number`, `authority`)
                        values (${newMenuId}, ${moduleId}, '删除竞赛信息', '', '',
                                '', 1, 0, ${sort_number}, 'ams:competitionEntity:Competition:remove');
/* execute(!menuId)  */ insert into `sys_role_menu`(`role_id`, `menu_id`)
                        values (1, ${newMenuId});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "获奖级别";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "获奖级别", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "获奖级别";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "国家级，省市级，省级，市级，校级，院级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "国家级，省市级，省级，市级，校级，院级", ${sort_number});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "竞赛类别";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "竞赛类别", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "竞赛类别";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "政府类";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "政府类", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "学会类";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "学会类", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "未定类";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "未定类", ${sort_number});


/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "竞赛级别";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t;
/* execute(!dict_id)  */ insert INTO `sys_dict` (dict_id, dict_name, sort_number)
                         VALUES (${new_dict_id}, "竞赛级别", ${sort_number});
/* find(dict_id)  */ select `dict_id`
                     from `sys_dict`
                     where `dict_name` = "竞赛级别";


/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "I";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "I", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "II";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "II", ${sort_number});

/* find(dict_parent_id)  */ select `dict_id`
                            from `sys_dict`
                            where parent_id = ${dict_id}
                              and `dict_name` = "未定级";
/* find(new_dict_id)  */ select max(`dict_id`) + 1 as value
                         from `sys_dict`;
/* find(sort_number)  */ select IFNULL(max(t.sort_number), 0) + 1 as value
                         from sys_dict t
                         where t.parent_id = ${dict_id};
/* execute(!dict_parent_id)  */ insert INTO `sys_dict` (dict_id, parent_id, dict_name, sort_number)
                                VALUES (${new_dict_id}, ${dict_id}, "未定级", ${sort_number});
        
                    